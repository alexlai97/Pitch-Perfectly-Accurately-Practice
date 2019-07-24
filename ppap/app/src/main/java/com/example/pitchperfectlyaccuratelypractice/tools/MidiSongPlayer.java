package com.example.pitchperfectlyaccuratelypractice.tools;

import android.util.Log;

//import com.example.pitchperfectlyaccuratelypractice.controller.Controller;
import com.example.pitchperfectlyaccuratelypractice.modeFragments.SongPlayingFragment;
import com.example.pitchperfectlyaccuratelypractice.activities.MainActivity;
import com.example.pitchperfectlyaccuratelypractice.model.Model;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.question.SongQuestion;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Lyrics;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

import java.util.ArrayList;

public class MidiSongPlayer implements MidiEventListener {
    private final static String TAG = "MidiSongPlayer";

    private MidiProcessor midiProcessor;

    private NotesPlayer notesPlayer;

    private MidiFile midiFile;

    private ArrayList<MidiTrack> midiTracks;

    private SongPlayingFragment songPlayingFragment;

    private MainActivity mainActivity;

    private Model model;

    private Note[] notes_from_midiFile;

    public MidiSongPlayer(MainActivity ac) {
        mainActivity = ac;
        model = mainActivity.getModel();
        this.notesPlayer = mainActivity.getNotesPlayer();
        this.songPlayingFragment = (SongPlayingFragment) model.getCurrentFragment();

//        setMidiFileUsingCurrentQuestion();
    }

    public void setMidiFileUsingCurrentQuestion() {
        setMidiFile(((SongQuestion)(model.getCurrentQuestion())).getSong().getMidiFile());
        midiProcessor.reset();
        flag = true;
        midiTracks_index = 0;
        current_note_index = 0;
    }

    public void setMidiFile(MidiFile mfile) {
        midiFile = mfile;
        midiProcessor = new MidiProcessor(mfile);
        midiProcessor.registerEventListener(this, MidiEvent.class);
        midiTracks = MyMidiTool.parseMidiFileToNoteTracks(midiFile);
        notes_from_midiFile = MyMidiTool.parseMidiToNotes(midiFile);
    }


    public void start() {
        midiProcessor.start();
    }

    public boolean isPlaying() {
        if (midiProcessor == null) {
            return false;
        }
        return midiProcessor.isRunning();
    }


    public void pause() {
        midiProcessor.stop();
    }

    public void reset() {
        midiProcessor.reset();
    }

    public void updateQuestionTexts() {
        songPlayingFragment.updateQuestionTexts(getCurrentNotesTexts());
    }

    @Override
    public void onStart(boolean fromBeginning) {
        if (fromBeginning) {
            Log.d(TAG, "onStart: from beginning");

        } else {
            Log.d(TAG, "onStart: not from beginning");
        }
    }

    private boolean flag = true;
    private int midiTracks_index = 0;

    private int current_note_index = 0;

    public Note getCurrentPlayingNote() {
        return notes_from_midiFile[current_note_index];
    }

    private String[] getCurrentNotesTexts() {
        String[] texts = new String[3];
        if (current_note_index == 0) {
            texts[0] = "";
            texts[1] = Note.getText(notes_from_midiFile[current_note_index]);
            texts[2] = Note.getText(notes_from_midiFile[current_note_index + 1]);
        } else if (current_note_index == notes_from_midiFile.length - 1) {
            texts[0] = Note.getText(notes_from_midiFile[current_note_index-1]);
            texts[1] = Note.getText(notes_from_midiFile[current_note_index]);
            texts[2] = "";
        } else {
            texts[0] = Note.getText(notes_from_midiFile[current_note_index-1]);
            texts[1] = Note.getText(notes_from_midiFile[current_note_index]);
            texts[2] = Note.getText(notes_from_midiFile[current_note_index + 1]);
        }
        return texts;
    }

    @Override
    public void onEvent(final MidiEvent event, long ms) {
        Log.d(TAG, "event: " + event);
        if (event.getClass() == NoteOn.class) {
            if (flag) {
                notesPlayer.playNoteTrack(midiTracks.get(midiTracks_index));
                midiTracks_index++;
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        songPlayingFragment.updateQuestionTexts(getCurrentNotesTexts());
                        updateQuestionTexts();
                        current_note_index++;
                    }
                });
            } else {
            }
            flag = !flag;
        } else if (event.getClass() == NoteOff.class) {
        } else if (event.getClass() == Lyrics.class) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    songPlayingFragment.updateLyricsView(((Lyrics)event).getLyric());
                }
            });
        }
    }

    @Override
    public void onStop(boolean finished) {
        if (finished) {
            Log.d(TAG, "onStop: finished");
        } else {
            Log.d(TAG, "onStop: not finished");
        }
    }

}
