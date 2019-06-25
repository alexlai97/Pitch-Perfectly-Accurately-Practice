package com.example.perfectpitchaccuratepractice;
// configurations that the user can set
class Config {
  // Δt: the user need to stay in error range for at least Δt ms
  long least_stable_time_in_milliseconds = 1000; 

  // Ɛ
  // expected note: a
  // error range: [a*2^(-Ɛ/12), a*2^(Ɛ/12)]
  // a = 440Hz, Ɛ = 1 means error range: [G4#, A4#]
  double error_allowance_rate = 0.8; 

  public void set_least_stable_time_in_milliseconds(long t) {
    least_stable_time_in_milliseconds = t;
  }

  public void set_error_allowance_rate(double e) {
    error_allowance_rate = e;
  }

  public double get_error_allowance_rate() {
    return error_allowance_rate;
  }

  public long get_least_stable_time_in_milliseconds() {
    return least_stable_time_in_milliseconds;
  }

  public double get_least_stable_time_in_seconds() {
    return (double)least_stable_time_in_milliseconds/1000;
  }

  // test example
  public static void main(String args[]) {
  }
}
