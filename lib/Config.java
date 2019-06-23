class Config {
  // Δt: the user need to stay in error range for at least Δt ms
  double least_stable_time_in_milliseconds; 

  // Ɛ
  // expected note: a
  // error range: [a*2^(-Ɛ/12), a*2^(Ɛ/12)]
  // a = 440Hz, Ɛ = 1 means error range: [G4#, A4#]
  double error_allowance_rate; 

  public set_least_stable_time_in_milliseconds(double t) {
    least_stable_time_in_milliseconds = t;
  }

  public set_error_allowance_rate(double e) {
    error_allowance_rate = e;
  }

  public double get_least_stable_time_in_milliseconds() {
    return least_stable_time_in_milliseconds;
  }

  public double get_least_stable_time_in_seconds() {
    return least_stable_time_in_milliseconds/1000;
  }

  // test example
  public static void main(String args[]) {
  }
}
