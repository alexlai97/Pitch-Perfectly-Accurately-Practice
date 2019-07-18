\version "2.18.2"
\language english

\header {
  title = "団子大家族 -- Clanned"
  composer = "麻枝准"
}

\score {
  \new Staff  {
    \set Staff.midiInstrument = "acoustic grand"

    \tempo "adagio" 4=72

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
      bf4 af8 f~ f8 bf af ef' | 
      c2 bf,8 ef af bf | 

      bf4 af8 f~ f8 af bf c |  %21 
      ef8 bf c4 af4 g | 
      f2 g4 af8 g~ | 
      g4 af2.  \bar "|."
    }

    \addlyrics {
      だ -- ん ご だん ご |  %1
      だん ご だん ご |
      だ -- ん ご だん ご |
      だ -- ん ご |

      だ -- ん ご だん ご | %5
      だん ご だん ご | 
      だ -- ん ご だい か |
      ぞ く | 

      あ か ちゃ ん _  だ ん ご |  % 9
      は い つ も | 
      し あ わ せ _ の な か |
      で |

      と し よ り _  だ ん ご | % 13
      は め  を  ほ  そ  | % FIXME barcheck failed
      め て る  | %15
      _ _ _ _ _ な か よ |

      し だ んご て を つ  |  % 17
      な ぎ お お  き な  ま |
      る い わ に な る | 
      よ ま ち を つ | 

      く り だん ご ほ し |  %21 
      の う え みん な |
      で わ らい あう | % FIXME barcheck failed
      よ
    }
  }

  \layout {}
  \midi {}
}
