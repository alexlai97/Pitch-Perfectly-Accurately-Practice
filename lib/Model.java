public class Model {
  double current_frequency = -2000;
  Question question;
  Mode current_Mode;

  public set_current_Mode(Mode m) {
    current_Mode = m;
  }

  // Called by run in PitchDetectionHandler
  // things it will do:
  // 1. 
  public void processFrequency(double freq) {
    current_frequency = freq;

  }

  public static void main(String args[]) {
  }
}
