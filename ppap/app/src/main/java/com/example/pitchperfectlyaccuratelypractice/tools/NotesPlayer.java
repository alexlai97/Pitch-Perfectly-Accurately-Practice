package com.example.pitchperfectlyaccuratelypractice.tools;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.music.Note;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NotesPlayer {
    private static final String TAG = "NotesPlayer";
    private Context context;
    private static MediaPlayer mediaPlayer = new MediaPlayer();
    private File tmp_midi_file;

    /** * a temp file, media player will play this file */
    private static String pathname = Environment.getExternalStorageDirectory() + "/playing.mid";

    /**
     * constructor, given a context
     * @param ct
     */
    public NotesPlayer(Context ct) {
        context = ct;
    }

    /**
     * how notes can be played
     */
    public enum PlayingStrategy {
        OneByOne,  /** Sequentially */
        Together, /** Simultaneously */
        OneByOneThenTogether /** individual notes, then chord */
    }


    /**
     * play notes given strategy
     */
    public void play(Note[] notes, PlayingStrategy playingStrategy) {
        create_midi_tmp_file(create_notes_track(notes, playingStrategy));
        load_tmp_file();
        mediaPlayer.start();
    }

    /**
     * play a note
     * @param note
     */
    public void play(Note note) {
        create_midi_tmp_file(create_note_track(note));
        load_tmp_file();
        mediaPlayer.start();
    }

    /**
     * create a MidiTrack using a single note
     */
    private MidiTrack create_note_track(Note note) {
        return create_notes_track(new Note[] {note}, PlayingStrategy.OneByOne);
    }


    /**
     * create a note track given notes and strategy
     * @param notes
     * @param playingStrategy
     * @return
     */
    private MidiTrack create_notes_track(Note[] notes, PlayingStrategy playingStrategy) {
        MidiTrack noteTrack = new MidiTrack();
        long each_tick = 240;
        int channel = 0;
        int velocity = 50;
        long duration = 280;

        switch (playingStrategy) {
            case OneByOne:
                for (int i = 0; i < notes.length; i ++) {
                    int pitch = notes[i].getIndex() + 33;
                    long tick = i* each_tick;
                    noteTrack.insertNote(channel, pitch, velocity, tick, duration);
                }
                break;
            case Together:
                for (int i = 0; i < notes.length; i ++) {
                    int pitch = notes[i].getIndex() + 33;
                    long tick = each_tick;
                    noteTrack.insertNote(channel, pitch, velocity, tick, duration);
                }
                break;
            case OneByOneThenTogether:
                long current_tick = 0;
                for (int i = 0; i < notes.length; i ++) {
                    int pitch = notes[i].getIndex() + 33;
                    long tick = i* each_tick;
                    current_tick = tick;
                    noteTrack.insertNote(channel, pitch, velocity, tick, duration);
                }
                current_tick += 720;
                for (int i = 0; i < notes.length; i ++) {
                    int pitch = notes[i].getIndex() + 33;
                    long tick = current_tick;
                    noteTrack.insertNote(channel, pitch, velocity, tick, duration);
                }
                break;
        }
        return noteTrack;
    }


    /**
     * create a midi file given MidiTrack (notes track)
     * @param notesTrack
     */
    private void create_midi_tmp_file(MidiTrack notesTrack) {
        // 1. Create some MidiTracks
        MidiTrack tempoTrack = new MidiTrack();
        // 2. Add events to the tracks
        // Track 0 is the tempo map
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);
        Tempo tempo = new Tempo();
        tempo.setBpm(120);
        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);


        // 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(notesTrack);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

        // 4. Write the MIDI data to a file
        tmp_midi_file = new File(pathname);
        try {
            midi.writeToFile(tmp_midi_file);

        } catch(IOException e) {
            Log.e(TAG, "create_note: "+ e );
        }
    }


    /**
     * let media player prepare playing the tmp file
     */
    private void load_tmp_file() {
        try {
            FileInputStream input = new FileInputStream(tmp_midi_file);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(input.getFD());
            input.close();
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.e(TAG, "play_example: "+ e );
        }
    }

}
