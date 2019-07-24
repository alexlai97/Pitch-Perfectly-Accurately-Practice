package com.example.pitchperfectlyaccuratelypractice.modeFragments;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.enums.Mode;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;

/**
 * General Song Mode Fragment, it has two children, SongPlayingFragment, SongPracticingFragment
 */
public class SongModeFragment extends ModeFragment {
    private static String TAG = "SongModeFragment";

    /** prev note constraint layout */
    private ConstraintLayout prevNoteLayout;
    /** current note constraint layout */
    private ConstraintLayout currentNoteLayout;
    /** next note constraint layout */
    private ConstraintLayout nextNoteLayout;

    /** the lyrics textview */
    private TextView currentLyricsText;
    /** the song title textview*/
    protected TextView songTitleText;

    /** FIXME a temporary spinner to select song */
    protected Spinner librarySpinner;

    /** have access to model */
    protected Model model;

    /** constructor */
    SongModeFragment(){
        mode = Mode.SongPractice;
    }

    @Override
    void setupAdditionalView() {
        prevNoteLayout = constraintLayout.findViewById(R.id.previous_note_include);
        currentNoteLayout = constraintLayout.findViewById(R.id.current_note_include);
        nextNoteLayout = constraintLayout.findViewById(R.id.next_note_include);
        currentLyricsText = constraintLayout.findViewById(R.id.lyricsTextView);
        librarySpinner = constraintLayout.findViewById(R.id.librarySpinner);
        model = ((MainActivity)(getActivity())).getModel();
        songTitleText = constraintLayout.findViewById(R.id.songTitleTextView);

        ArrayAdapter<String> all_songs_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, model.getSongList().getSongTitles());
        librarySpinner.setAdapter(all_songs_adapter);


        setupSongAdditionalView();
    }


    /** setup additional view for song modes */
    void setupSongAdditionalView() {

    }

    /** update the question texts view */
    @Override
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 3) { throw new AssertionError("expecting texts' length is 3"); }
        updateSingleNoteText(prevNoteLayout, texts[0]);
        updateSingleNoteText(currentNoteLayout, texts[1]);
        updateSingleNoteText(nextNoteLayout, texts[2]);
    }

    /** update the lyrics view */
    public void updateLyricsView(String str) {
        if (str == null) throw new AssertionError("str is null when updating lyrics");
        currentLyricsText.setText(str);
    }
}
