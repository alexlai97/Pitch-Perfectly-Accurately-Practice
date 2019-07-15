\version "2.18.2"
\language english

\header {
  title = "Auld Lang Syne"
}

\score {
  \new Staff  {
    \set Staff.midiInstrument = "acoustic grand"

    \tempo "Allegretto" 4=112

    \key g \major
    \time 4/4

    \relative g'{
      %\clef treble 
      \partial 4

      d4 | %

      g4. fs8 g4 b | %1
      a4. g8 a4 b |
      g4. g8 b4 d |
      e2. e4 | 

      d4. b8 b4 g | % 5
      a4. g8 a4 b8( a) |
      g4. e8 e4 d4 |
      g2. e'4 |

      d4. b8 b4 g4 | % 9
      a4. g8 a4 e'4 |
      d4. b8 b4 d4 |
      e2. e4 |

      d4. b8 b4 g4 | % 13
      a4. g8 a4 b8( a8) |
      g4. e8 e4 d4 |
      g2.  \bar "|."
    }

    \addlyrics {
      Should |

      old ac -- quaint -- ance |
      be for -- got and |
      nev -- er brought to |
      mind? Should |

      old ac -- quaint -- ance |
      be for -- got and __ |
      auld ____ lang ____ |
      syne?  For |

      auld ____ lang ____ |
      syne, my dear, for |
      auld ____ lang ____ |
      syne, We'll |

      tak' a cup o' |
      kind -- ness yet, for __ |
      auld ____ lang ____ |
      syne.
    }
  }

  \layout {}
  \midi {}
}
