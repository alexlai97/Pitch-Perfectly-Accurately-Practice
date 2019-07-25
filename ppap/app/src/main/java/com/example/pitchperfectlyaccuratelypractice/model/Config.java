package com.example.pitchperfectlyaccuratelypractice.model;

/**
 * configurations that the user can set
 */
public class Config {
  /**
   * least stable time (Δt): the user need to stay in error range for at least Δt ms
   */
  private long least_stable_time_in_milliseconds = 1000;


  /**
   * error allowance rate (Ɛ)
   * <p>
   * Example:
   * <p>
   * expected note: a
   * <p>
   * error range: [a*2^(-Ɛ/12), a*2^(Ɛ/12)]
   * <p>
   * a = 440Hz, Ɛ = 1 means error range: [G4#, A4#]
   *
   */
  private double error_allowance_rate = 0.8;

  /**
   * frequency that is recognized as NO SOUND
   */
  public final static int LOWEST_RECOGNIZED_FREQ = 10;

  /**
   * setter for Δt in milliseconds 
   */
  public void set_least_stable_time_in_milliseconds(long t) {
    least_stable_time_in_milliseconds = t;
  }

  /**
   * setter for Ɛ
   */
  public void set_error_allowance_rate(double e) {
    error_allowance_rate = e;
  }

  /**
   * getter for Ɛ
   */
  public double get_error_allowance_rate() {
    return error_allowance_rate;
  }

  /**
   * getter for Δt in milliseconds 
   */
  public long get_least_stable_time_in_milliseconds() {
    return least_stable_time_in_milliseconds;
  }

  /**
   * getter for Δt in seconds 
   */
  public double get_least_stable_time_in_seconds() {
    return (double)least_stable_time_in_milliseconds/1000;
  }

  /** how long between the user pass the question and next question */
  private long milliseconds_to_show_correct = 2000;

  public long get_milli_seconds_to_show_correct() {
    return milliseconds_to_show_correct;
  }
  public void set_milli_seconds_to_show_correct(long ms) {
    milliseconds_to_show_correct = ms;
  }


  private boolean flag_auto_playback = true;

  public boolean get_flag_auto_playback_answer() {
      return flag_auto_playback;
  }
  public void set_flag_auto_playback_answer(boolean f) {
    flag_auto_playback = f;
  }

  /**
   * test (please ignore)
   */
  public static void main(String args[]) {
  }
}
