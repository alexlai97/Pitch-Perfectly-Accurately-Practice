\version "2.18.2"
\language english

\header {
  title = "Carrying you (Castle in the sky)"
  composer= "Joe Hisaishi"
}

\score {
  \new Staff  {
    \set Staff.midiInstrument = "acoustic grand"

    \time 4/4

    \relative g'{
      %\clef treble 
      \partial 4

      a8 b8| 

      c4. b8 c4 e | % 1
      b2. e,4 |
      a4. g8 a4 c4 | 
      g2. e4 | 

      f4. e8 f4 c' | %5
      e,2. c'8 c8 |
      b4. fs8 fs4 b4 |
      b2. a8 b8 | 

      c4. b8 c4 e4 | %9 
      b2. e,4 | 
      a4. g8 a4 c4 | 
      g2. e4 | 

      f4 c'8 b8~ b4 c4 | %13
      d4 e8 c8~ c2 | 
      c8 b8 a4 b4 gs4 |
      a1  \bar "|."
    }

  }

  \layout {}
  \midi {}
}
