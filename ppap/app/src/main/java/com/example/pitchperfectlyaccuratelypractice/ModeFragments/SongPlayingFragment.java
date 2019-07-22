package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.music.Song;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;

/**
 * a children of general fragment
 */
public class SongPlayingFragment extends SongGeneralFragment {
    private static String TAG = "SongPlayingFragment";

//    private TextView arrowText;

    private Button playOrPauseButton;
    private Button stopButton;

    private Button switchToPracticeButton;
    private Drawable play;
    private Drawable pause;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public SongPlayingFragment() {
        resource = R.layout.modefragment_song_playing;
        background_color = Color.parseColor("#e8e0f5");
        instruction_string = "TODO incomplete";
    }

    /**
     * set up views of questionNoteText and questionIntervalText
     */
    @Override
    void setupSongAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        play = getResources().getDrawable(R.drawable.play_button);
        pause = getResources().getDrawable(R.drawable.pause_button);

//        arrowText = constraintLayout.findViewById(R.id.arrowTextView);

        playOrPauseButton = constraintLayout.findViewById(R.id.playOrpauseButton);
        stopButton = constraintLayout.findViewById(R.id.stopButton);
        switchToPracticeButton = constraintLayout.findViewById(R.id.switchToPracticeModeButton);

        switchToPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setCurrentMode(Mode.SongPractice);
            }
        });

        midiSongPlayer = new MidiSongPlayer((MainActivity)getActivity());
        // FIXME tmporary
        playOrPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (midiSongPlayer.isPlaying()) {
                    playOrPauseButton.setBackground(play);
                    midiSongPlayer.pause();
                } else {
                    playOrPauseButton.setBackground(pause);
                    midiSongPlayer.start();
                }
            }
        });
        // FIXME has bug
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (midiSongPlayer.isPlaying()) {
                    midiSongPlayer.reset();
                }
            }
        });

        librarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Song selected_song = model.getSongList().getSongAt(i);
                if (selected_song == null ) {
                    throw new AssertionError("selected song is null");
                }
                songTitleText.setText(selected_song.getTitle());
                model.setCurrentQuestion(new SongQuestion(selected_song));
                // FIXME maybe observer pattern
                midiSongPlayer.setMidiFileUsingCurrentQuestion();
                controller.updateQuestionView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
}
