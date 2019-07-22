package com.example.pitchperfectlyaccuratelypractice.ModeFragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pitchperfectlyaccuratelypractice.ModeFragments.GeneralFragment;
import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.music.Song;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.example.pitchperfectlyaccuratelypractice.tools.MidiSongPlayer;

public class SongGeneralFragment extends GeneralFragment {
    private static String TAG = "SongGeneralFragment";

    private TextView prevNoteText;
    private TextView currentNoteText;
    private TextView nextNoteText;

    private TextView currentLyricsText;
    protected TextView songTitleText;

    protected Spinner librarySpinner;

    protected MidiSongPlayer midiSongPlayer;
    protected Model model;

    @Override
    void setupAdditionalView() {
        prevNoteText = constraintLayout.findViewById(R.id.prevNoteTextView);
        currentNoteText = constraintLayout.findViewById(R.id.currentNoteTextView);
        nextNoteText = constraintLayout.findViewById(R.id.nextNoteTextView);
        currentLyricsText = constraintLayout.findViewById(R.id.lyricsTextView);
        librarySpinner = constraintLayout.findViewById(R.id.librarySpinner);
        model = ((MainActivity)(getActivity())).getModel();
        songTitleText = constraintLayout.findViewById(R.id.songTitleTextView);

        ArrayAdapter<String> all_songs_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, model.getSongList().getSongTitles());
        librarySpinner.setAdapter(all_songs_adapter);


        setupSongAdditionalView();
    }

    // FIXME
    @Override
    public void listenerSetUp() {

    }

    void setupSongAdditionalView() {

    }

    @Override
    public void updateQuestionTexts(String [] texts){
        if(!onCreated) return;
        if (texts.length != 3) { throw new AssertionError("expecting texts' length is 3"); }
        prevNoteText.setText(texts[0]);
        currentNoteText.setText(texts[1]);
        nextNoteText.setText(texts[2]);
    }

    public void updateLyricsView(String str) {
        if (str == null) throw new AssertionError("str is null when updating lyrics");
        currentLyricsText.setText(str);
    }
}
