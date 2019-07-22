package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.music.Song;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;

/**
 * a children of general fragment
 */
public class SongPlayingFragment extends GeneralFragment {
    private static String TAG = "SongPlayingFragment";

    private TextView prevNoteText;
    private TextView currentNoteText;
    private TextView nextNoteText;

//    private TextView arrowText;
    private TextView currentLyricsText;
    private TextView songTitleText;

    private Spinner librarySpinner;
    private Button playOrPauseButton;
    private Button stopButton;

    private Button switchToPracticeButton;
    private Drawable play;
    private Drawable pause;

    private MidiSongPlayer midiSongPlayer;
    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public SongPlayingFragment() {
        resource = R.layout.fragment_song_playing;
        background_color = Color.parseColor("#e8e0f5");
        instruction_string = "TODO incomplete";
    }

    /**
     * set up views of questionNoteText and questionIntervalText
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        play = getResources().getDrawable(R.drawable.play_button);
        pause = getResources().getDrawable(R.drawable.pause_button);
        prevNoteText = constraintLayout.findViewById(R.id.prevNoteTextView);
        currentNoteText = constraintLayout.findViewById(R.id.currentNoteTextView);
        nextNoteText = constraintLayout.findViewById(R.id.nextNoteTextView);

//        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
        currentLyricsText = constraintLayout.findViewById(R.id.lyricsTextView);

        librarySpinner = constraintLayout.findViewById(R.id.librarySpinner);
        playOrPauseButton = constraintLayout.findViewById(R.id.playOrpauseButton);
        stopButton = constraintLayout.findViewById(R.id.stopButton);
        final Model model = ((MainActivity)(getActivity())).getModel();
        switchToPracticeButton = constraintLayout.findViewById(R.id.switchToPracticeModeButton);

        songTitleText = constraintLayout.findViewById(R.id.songTitleTextView);

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

        ArrayAdapter<String> all_songs_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, model.getSongList().getSongTitles());
        librarySpinner.setAdapter(all_songs_adapter);
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

    /**
     * @param texts
     */
    @Override
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 3) { throw new AssertionError("expecting texts' length is 3"); }
        prevNoteText.setText(texts[0]);
        currentNoteText.setText(texts[1]);
        nextNoteText.setText(texts[2]);
    }

    /**
     * update arrow text views
//     * @param arrowTexts
     */
//    @Override
//    public void updateArrowTexts(String[] arrowTexts){
//        if(!onCreated) return;
////        arrowText.setText(arrowTexts[0]);
//    }

//    @Override
//    public void updateArrowAnimation(Animation myAnimation){
//        if(!onCreated) return;
////        arrowText.setAnimation(myAnimation);
//    }


    public void updateLyricsView(String str) {
        if (str == null) throw new AssertionError("str is null when updating lyrics");
        currentLyricsText.setText(str);
    }


}
