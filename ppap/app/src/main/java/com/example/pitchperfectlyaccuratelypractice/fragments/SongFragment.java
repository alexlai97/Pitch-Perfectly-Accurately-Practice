package com.example.pitchperfectlyaccuratelypractice.fragments;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;

/**
 * a children of general fragment
 */
public class SongFragment extends GeneralFragment {
    private static String TAG = "SongFragment";

    private TextView prevNoteText;
    private TextView currentNoteText;
    private TextView nextNoteText;

    private TextView arrowText;

    private Button libraryButton;
    private Button playOrPauseButton;
    private Button stopButton;

    private int position;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public SongFragment() {
        resource = R.layout.fragment_song;
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

        libraryButton = constraintLayout.findViewById(R.id.libraryButton);
        playOrPauseButton = constraintLayout.findViewById(R.id.playOrpauseButton);
        stopButton = constraintLayout.findViewById(R.id.stopButton);

        // FIXME tmporary
        final MediaPlayer mediaPlayer = notesPlayer.getMediaPlayer();
        notesPlayer.prepare_song(((SongQuestion)controller.getCurQuestion()).getSong());
        playOrPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    position = mediaPlayer.getCurrentPosition();
                    playOrPauseButton.setText("Play");
                    mediaPlayer.pause();
                } else {
                    playOrPauseButton.setText("Pause");
                    mediaPlayer.seekTo(position);
                    mediaPlayer.start();
                }
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position =0;
                mediaPlayer.pause();
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
     * @param arrowTexts
     */
    @Override
    public void updateArrowTexts(String[] arrowTexts){
        if(!onCreated) return;
        arrowText.setText(arrowTexts[0]);
    }

    @Override
    public void updateArrowAnimation(Animation myAnimation){
        if(!onCreated) return;
        arrowText.setAnimation(myAnimation);
    }


}
