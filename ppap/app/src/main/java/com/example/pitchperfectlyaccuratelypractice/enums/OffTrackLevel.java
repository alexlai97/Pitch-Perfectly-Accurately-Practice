package com.example.pitchperfectlyaccuratelypractice.enums;

import com.example.pitchperfectlyaccuratelypractice.model.Config;

import java.io.Serializable;

/**
 * shows how close is the expected frequency and the current frequency
 * <p>
 * 6 types: InErrorRange, LittleHigh, TooHigh, LittleLow, TooLow, NoSound;
 * <p>
 * Say expected frequency is a, second error range factor is c
 * <p>
 * error range is [lb1, ub1] = [a * 2^(-Ɛ/12), a * 2^(Ɛ/12)]
 * <p>
 * second error range is [lb2, ub2] = [c * a * 2^(-Ɛ/12), c * a * 2^(Ɛ/12)]
 */

public enum OffTrackLevel implements Serializable {
  InErrorRange, LittleHigh, TooHigh, LittleLow, TooLow, NoSound;

  /**
   * control the size of second error range centered at expected frequency
   */
  final static int SECOND_ERROR_RANGE_FACTOR = 6; 


  /**
   * compares actual frequency with expected frequency, and gives how far it is from first and second error range
   */
  public static OffTrackLevel get_OffTrackLevel(double expected_freq, double actual_freq, double error_allowance_rate) {
    double ub1 = expected_freq * Math.pow(2, error_allowance_rate/12); //ub-> upper bound
    double lb1 = expected_freq * Math.pow(2, -error_allowance_rate/12); // lb->lower bound
    double ub2 = expected_freq * Math.pow(2, SECOND_ERROR_RANGE_FACTOR * error_allowance_rate/12);
    double lb2 = expected_freq * Math.pow(2, -SECOND_ERROR_RANGE_FACTOR * error_allowance_rate/12);
    if  (actual_freq < Config.LOWEST_RECOGNIZED_FREQ) {
      return NoSound;
    } else if (actual_freq <= lb2) {
        return TooLow;
    } else if (actual_freq > lb2 && actual_freq <= lb1) {
        return LittleLow;
    } else if (actual_freq > lb1 && actual_freq <= ub1) {
        return InErrorRange;
    } else if (actual_freq > ub1 && actual_freq <= ub2) {
        return LittleHigh;
    } else { // if (actual_freq > ub2)
        return TooHigh;
    }
  }

  /**
   * give arrows that guides the user to adjust his/her voice
   */
  public String get_ArrowSuggestion() {
    switch (this) {
      case NoSound:
        return ""; // No sound
      case InErrorRange:
        return "...";
      case TooLow:
        return "⇈";
      case LittleLow:
        return "↑";
      case LittleHigh:
        return "↓";
      case TooHigh:
        return "⇊";
      default:
        return "??";
    }
  }

  /**
   * test (ignore me)
   */
  public static void main(String args[]) {
    double freq = 444;
    OffTrackLevel ofl = get_OffTrackLevel(440, freq, 1);
    System.out.println(ofl);
    System.out.println(ofl.get_ArrowSuggestion());
  }
}
