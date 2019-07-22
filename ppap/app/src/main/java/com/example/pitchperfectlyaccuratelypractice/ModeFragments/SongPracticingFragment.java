package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.music.Song;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;

/**
 * a children of general fragment
 */
public class SongPracticingFragment extends SongGeneralFragment {
    private static String TAG = "SongPracticing";

    private TextView arrowText;

    private Button switchToPlayingButton;

    /**
     * constructor of IntervalFragment
     * setup resource (see parent onCreateView for use)
     */
    public SongPracticingFragment() {
        resource = R.layout.fragment_song_practicing;
        background_color = Color.parseColor("#e8e0f5");
        instruction_string = "TODO incomplete";
    }

    /**
     * set up views of questionNoteText and questionIntervalText
     */
    @Override
    void setupSongAdditionalView() {

        arrowText = constraintLayout.findViewById(R.id.arrowTextView);

        switchToPlayingButton = constraintLayout.findViewById(R.id.switchToPlayingModeButton);
        switchToPlayingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)(getActivity())).getModel().setCurrentMode(Mode.SongPlaying);
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
//                midiSongPlayer.setMidiFileUsingCurrentQuestion();
                controller.updateQuestionView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
