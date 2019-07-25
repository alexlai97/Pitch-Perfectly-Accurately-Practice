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

/**
 * MidiSongPlayer can play a song
 */
public class MidiSongPlayer implements MidiEventListener {
    private final static String TAG = "MidiSongPlayer";

    /** stores a a midi File processor */
    private MidiProcessor midiProcessor;

    /** a notesPlayer */
    private NotesPlayer notesPlayer;

    /** midi processor processes this MidiFile */
    private MidiFile midiFile;

    /** an array of noteTrack */
    private ArrayList<MidiTrack> notesTracks;

    /** have access to songPlaying Fragment */
    private SongPlayingFragment songPlayingFragment;

    /** have access to MainActivity public getter methods */
    private MainActivity mainActivity;

    /** have access to the model */
    private Model model;

    /** an array of notes from the midi file */
    private Note[] notes_from_midiFile;

    /** constructor of a MidiSongPlayer given MainActivity*/
    public MidiSongPlayer(MainActivity ac) {
        mainActivity = ac;
        model = mainActivity.getModel();
        this.notesPlayer = mainActivity.getNotesPlayer();
        this.songPlayingFragment = (SongPlayingFragment) model.getCurrentFragment();

//        setMidiFileUsingCurrentQuestion();
    }

    /** get current question from model, and get the current song, and set midi file */
    public void setMidiFileUsingCurrentQuestion() {
        setMidiFile(((SongQuestion)(model.getCurrentQuestion())).getSong().getMidiFile());
        midiProcessor.reset();
        flag = true;
        noteTracks_index = 0;
        current_note_index = 0;
    }

    /** change midifile to a new midifile, and related logic  */
    public void setMidiFile(MidiFile mfile) {
        midiFile = mfile;
        midiProcessor = new MidiProcessor(mfile);
        midiProcessor.registerEventListener(this, MidiEvent.class);
        notesTracks = MyMidiTool.parseMidiFileToNoteTracks(midiFile);
        notes_from_midiFile = MyMidiTool.parseMidiToNotes(midiFile);
    }


    /** start processing */
    public void start() {
        midiProcessor.start();
    }

    /** is midiprocessor running */
    public boolean isPlaying() {
        if (midiProcessor == null) {
            return false;
        }
        return midiProcessor.isRunning();
    }


    /** pause the midiprocessor */
    public void pause() {
        midiProcessor.stop();
    }

    /** reset the midiprocessor */
    public void reset() {
        midiProcessor.reset();
    }

    /** update the note texts in songfragment */
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

    /** flag to indicate if it the first NoteOn event or the second NoteOn event */
    private boolean flag = true;

    /** index to indicate current noteTrack */
    private int noteTracks_index = 0;

    /** index to indicate current playing note */
    private int current_note_index = 0;

    /** get the note that is current playing */
    public Note getCurrentPlayingNote() {
        return notes_from_midiFile[current_note_index];
    }

    /** get the prev notes, current notes, next note texts*/
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
                notesPlayer.playNoteTrack(notesTracks.get(noteTracks_index));
                noteTracks_index++;
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
