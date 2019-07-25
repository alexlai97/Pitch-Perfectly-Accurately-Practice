package com.example.pitchperfectlyaccuratelypractice.modeFragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Song;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;
import com.example.pitchperfectlyaccuratelypractice.tools.NotesPlayer;

/**
 * a children of general fragment
 */
public class SongPlayingFragment extends SongModeFragment {
    private static String TAG = "SongPlayingFragment";


    /** the button that can play or pause */
    private Button playOrPauseButton;
    /** the button that can stop the current playing song and back to start */
    private Button stopButton;

    /** a button to switchh to song practicing mode */
    private Button switchToPracticeButton;
    /** image of play button */
    private Drawable play;
    /** image of pause button */
    private Drawable pause;

    /** have access to a midiSongPlayer */
    private MidiSongPlayer midiSongPlayer;

    /**
     * constructor of IntervalModeFragment
     * setup resource (see parent onCreateView for use)
     */
    public SongPlayingFragment() {
        mode = Mode.SongPlaying;
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

        playOrPauseButton = constraintLayout.findViewById(R.id.playOrpauseButton);
        stopButton = constraintLayout.findViewById(R.id.stopButton);
        switchToPracticeButton = constraintLayout.findViewById(R.id.switchToPracticeModeButton);

        switchToPracticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                midiSongPlayer.reset();
                model.setCurrentMode(Mode.SongPractice);
            }
        });

        // FIXME put it in constructor
        midiSongPlayer = new MidiSongPlayer((MainActivity)getActivity());
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
                midiSongPlayer.reset();
                midiSongPlayer.setMidiFileUsingCurrentQuestion();
                midiSongPlayer.updateQuestionTexts();
                playOrPauseButton.setBackground(play);
            }
        });

        playSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesPlayer.start_playing(midiSongPlayer.getCurrentPlayingNote());
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
                if (midiSongPlayer.isPlaying()) {
                    midiSongPlayer.reset();
                    playOrPauseButton.setBackground(play);
                }
                midiSongPlayer.setMidiFileUsingCurrentQuestion();
                controller.updateQuestionView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
}
