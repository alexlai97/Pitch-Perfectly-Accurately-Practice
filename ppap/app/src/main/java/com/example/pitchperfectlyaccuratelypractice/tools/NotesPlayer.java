package com.example.pitchperfectlyaccuratelypractice.tools;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.R;
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
    private MediaPlayer mediaPlayer = new MediaPlayer();
    public NotesPlayer(Context ct) {
        context = ct;
//        mediaPlayer = MediaPlayer.create(context, R.raw.carrying_you);
    }

    private File tmp_midi_file;
    private String pathname = Environment.getExternalStorageDirectory() + "/playing.mid";

    public enum PlayingStrategy {
        OneByOne, Together, OneByOneThenTogether
    }

    public MidiTrack create_note_track(Note note) {
        return create_notes_track(new Note[] {note}, PlayingStrategy.OneByOne);
    }


    public MidiTrack create_notes_track(Note[] notes, PlayingStrategy playingStrategy) {
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


    public void create_midi(MidiTrack notesTrack) {
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


    public void create_example() {
        // 1. Create some MidiTracks
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();

// 2. Add events to the tracks
// Track 0 is the tempo map
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(120);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);

// Track 1 will have some notes in it
        final int NOTE_COUNT = 80;

        for(int i = 0; i < NOTE_COUNT; i++)
        {
            int channel = 0;
            int pitch = 1 + i;
            int velocity = 100;
            long tick = i * 480;
            long duration = 140;

            noteTrack.insertNote(channel, pitch, velocity, tick, duration);
        }

// 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);

// 4. Write the MIDI data to a file
        tmp_midi_file = new File(pathname);
        try
        {
            midi.writeToFile(tmp_midi_file);

        }
        catch(IOException e)
        {
            Log.e(TAG, "create_example: "+ e );
        }

    }


    public void load_tmp_file() {
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

    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }
}
