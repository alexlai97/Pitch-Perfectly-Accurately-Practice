\version "2.18.2"
\language english

\header {
  title = "London Bridge Is Falling Down"
}

\score {
  \new Staff  {
    \set Staff.midiInstrument = "acoustic grand"
    \time 4/4
    \tempo "Allegretto" 4=120

    \relative c' {
      g'4. a8 g4 f |
      e4 f g2 |
      d4 e f2 |
      e4 f g2 |

      g4. a8 g4 f |
      e4 f g2 |
      d2 g2  |
      e4 c2. \bar "|." |
    }

    \addlyrics {
      Lon -- don bridge is |
      fall -- ing down, |
      fall -- ing down, |
      fall -- ing down, |

      Lon -- don bridge is |
      fall -- ing down, |
      My fair |
      la -- dy. |
    }
  }


  \layout {}
  \midi {}
}
