\version "2.18.2"
\language english

\header {
  title = "団子大家族 -- Clanned"
  composer = "麻枝准"
}

\score {
  \new Staff  {
    \set Staff.midiInstrument = "acoustic grand"

    \key af \major
    \time 4/4

    \relative af''{
      %\clef treble 

      f8 ef af4 af bf | %1
      bf4 c af ef | 
      f8 ef af4 af bf |
      bf4 c bf2 | 

      f8 ef af4 af bf | %5
      bf4 c bf ef, | 
      f8 ef8 af4 af bf | 
      af2 ef |

      df'8 c af f bf, af' bf f | %9
      af4 ef f af | 
      df8 c af f bf, af' bf f |
      af1 | 

      df8 c af f bf, af' bf f | %13
      af4 ef f af8 ef~ | 
      ef4. af8 df,4. df8 | 
      ef8 df ef df bf8 ef af bf | 

      bf4 af8 f~ f8 af bf c |  %17
      ef8 bf c4 af8 ef af bf | 
      bf4 af8 f~ f8 bf af df | 
      c2 af,8 ef' af bf | 

      bf4 af8 f~ f8 af bf c |  %21 
      ef8 bf c4 af4 g | 
      f2 g4 af8 g~ | 
      g2 af2  \bar "|."
    }

    \addlyrics {
    }
  }

  \layout {}
  \midi {}
}
