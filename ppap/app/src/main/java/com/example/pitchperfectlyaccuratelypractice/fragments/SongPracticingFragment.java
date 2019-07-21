package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;

/**
 * a children of general fragment
 */
public class SongPracticingFragment extends GeneralFragment {
    private static String TAG = "SongPracticing";

    private TextView prevNoteText;
    private TextView currentNoteText;
    private TextView nextNoteText;

    private TextView arrowText;
    private TextView currentLyricsText;

    private Spinner librarySpinner;
    private Button playOrPauseButton;
    private Button stopButton;


    private Button switchToPlayingButton;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public SongPracticingFragment() {
        resource = R.layout.fragment_song_practicing;
        background_color = Color.parseColor("#44EA80FC");
        instruction_string = "TODO incomplete";
    }

    /**
     * set up views of questionNoteText and questionIntervalText
     */
    @Override
    void setupAdditionalView() {
        Log.d(TAG, "setupAdditionalView: ");
        prevNoteText = constraintLayout.findViewById(R.id.prevNoteTextView);
        currentNoteText = constraintLayout.findViewById(R.id.currentNoteTextView);
        nextNoteText = constraintLayout.findViewById(R.id.nextNoteTextView);

        arrowText = constraintLayout.findViewById(R.id.arrowTextView);
        currentLyricsText = constraintLayout.findViewById(R.id.lyricsTextView);

        librarySpinner = constraintLayout.findViewById(R.id.librarySpinner);
//        playOrPauseButton = constraintLayout.findViewById(R.id.playOrpauseButton);
//        stopButton = constraintLayout.findViewById(R.id.stopButton);

        switchToPlayingButton = constraintLayout.findViewById(R.id.switchToPlayingModeButton);
        // FIXME tmporary
//        final MidiSongPlayer midiSongPlayer = new MidiSongPlayer(this, getActivity() ,((SongQuestion)(controller.getCurQuestion())).getSong().getMidiFile(), notesPlayer);
//        notesPlayer.prepare_song(((SongQuestion)controller.getCurQuestion()).getSong());
//        playOrPauseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (midiSongPlayer.isPlaying()) {
//                    playOrPauseButton.setText("Play");
//                    midiSongPlayer.pause();
//                } else {
//                    playOrPauseButton.setText("Pause");
//                    midiSongPlayer.start();
//                }
//            }
//        });
//        stopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                midiSongPlayer.reset();
//            }
//        });
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
     * @param arrowTexts
     */
    @Override
    public void updateArrowTexts(String[] arrowTexts){
        if(!onCreated) return;
//        arrowText.setText(arrowTexts[0]);
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
//        arrowText.setAnimation(myAnimation);
    }


    public void updateLyricsView(String str) {
        if (str == null) throw new AssertionError("str is null when updating lyrics");
        currentLyricsText.setText(str);
    }


}
