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

    MidiProcessor midiProcessor;

//    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private NotesPlayer notesPlayer;
    ArrayList<Note> notes_arr = new ArrayList<>();

    int last_note_index = 0;

    MyMidiTool myMidiTool;

    MidiFile midiFile;

    ArrayList<MidiTrack> midiTracks;


    SongPlayingFragment songPlayingFragment;

    MainActivity mainActivity;

//    Controller controller;

    Model model;

    Note[] notes;
    public MidiSongPlayer(MainActivity ac) {
        mainActivity = ac;
        model = mainActivity.getModel();
        this.notesPlayer = mainActivity.getNotesPlayer();
        this.songPlayingFragment = (SongPlayingFragment) model.getCurrentFragment();

        setMidiFileUsingCurrentQuestion();
    }

    public void setMidiFileUsingCurrentQuestion() {
        setMidiFile(((SongQuestion)(model.getCurrentQuestion())).getSong().getMidiFile());
        midiProcessor.stop();
        flag = true;
        midiTracks_index = 0;
        current_note_index = 0;
    }

    public void setMidiFile(MidiFile mfile) {
        midiFile = mfile;
        midiProcessor = new MidiProcessor(mfile);
        midiProcessor.registerEventListener(this, MidiEvent.class);
        midiTracks = parseMidiFileToNoteTracks(midiFile);
        notes = MyMidiTool.parseMidiToNotes(midiFile);
    }

    private ArrayList<MidiTrack> parseMidiFileToNoteTracks(MidiFile midifile) {
        ArrayList<MidiTrack> midiTracks = new ArrayList<MidiTrack>();
        boolean note_pair_first = true;
        long first_note_tick = 0;
        int first_note_velocity = 0;
        int first_note_value = 0;
        MidiTrack noteTrack = new MidiTrack();
        for (MidiEvent event: midifile.getTracks().get(1).getEvents()) {
            if (event.getClass() == NoteOn.class) {
                NoteOn noteOnEvent = (NoteOn) event;
//                Log.d(TAG, "NoteOnEvent: " + new Note(-33+((NoteOn)event).getNoteValue()).getText() + " tick: " + noteOnEvent.getTick() + " velocity: " + noteOnEvent.getVelocity());
                if (note_pair_first) {
                    noteTrack = new MidiTrack();
                    first_note_tick = noteOnEvent.getTick();
                    first_note_velocity = noteOnEvent.getVelocity();
                    first_note_value = noteOnEvent.getNoteValue();
                } else {
                    noteTrack.insertEvent(noteOnEvent);
//                    Log.d(TAG, "onEvent: playthis note");
                    noteTrack.insertNote(noteOnEvent.getChannel(), first_note_value, first_note_velocity, 0, noteOnEvent.getTick() - first_note_tick);
//                    notesPlayer.playNoteTrack(noteTrack);
                    midiTracks.add(noteTrack);
                }
                note_pair_first = !note_pair_first;
            } else if (event.getClass() == NoteOff.class) {
            }
        }
        return midiTracks;
    }

    public void start() {
        midiProcessor.start();
    }

    public boolean isPlaying() {
        return midiProcessor.isRunning();
    }


    public void pause() {
        midiProcessor.stop();
    }

    public void reset() {
        setMidiFileUsingCurrentQuestion();
        songPlayingFragment.updateQuestionTexts(getNotesTexts());
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

    private String[] getNotesTexts() {
        String[] texts = new String[3];
        if (current_note_index == 0) {
            texts[0] = "";
            texts[1] = Note.getText(notes[current_note_index]);
            texts[2] = Note.getText(notes[current_note_index + 1]);
        } else if (current_note_index == notes.length - 1) {
            texts[0] = Note.getText(notes[current_note_index-1]);
            texts[1] = Note.getText(notes[current_note_index]);
            texts[2] = "";
        } else {
            texts[0] = Note.getText(notes[current_note_index-1]);
            texts[1] = Note.getText(notes[current_note_index]);
            texts[2] = Note.getText(notes[current_note_index + 1]);
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
                        songPlayingFragment.updateQuestionTexts(getNotesTexts());
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
