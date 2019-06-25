public enum OffTrackLevel {
  InErrorRange, LittleHigh, TooHigh, LittleLow, TooLow, NoSound;

  final static int RANGE_FACTOR = 6; // FIXME edit me
  final static int LOWEST_RECOGNIZED_FREQ = 10;

  public static OffTrackLevel get_OffTrackLevel(double expected_freq, double actual_freq, double error_allowance_rate) {
    double ub1 = expected_freq * Math.pow(2, error_allowance_rate/12);
    double lb1 = expected_freq * Math.pow(2, -error_allowance_rate/12);
    double ub2 = expected_freq * Math.pow(2, RANGE_FACTOR * error_allowance_rate/12);
    double lb2 = expected_freq * Math.pow(2, -RANGE_FACTOR * error_allowance_rate/12);
    if  (actual_freq < LOWEST_RECOGNIZED_FREQ) {
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

  public String get_ArrowSuggestion() {
    switch (this) {
      case NoSound:
        return "ns"; // No sound
      case InErrorRange:
        return "✓";
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

  // test example
  public static void main(String args[]) {
    double freq = 444;
    OffTrackLevel ofl = get_OffTrackLevel(440, freq, 1);
    System.out.println(ofl);
    System.out.println(ofl.get_ArrowSuggestion());
  }
}
