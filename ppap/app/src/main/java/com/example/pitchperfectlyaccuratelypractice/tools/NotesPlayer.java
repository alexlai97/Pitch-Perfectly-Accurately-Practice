package com.example.pitchperfectlyaccuratelypractice.tools;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Note;
import com.example.pitchperfectlyaccuratelypractice.musicComponent.Song;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * NotesPlayer any combination of notes, all of these are achieved by creating midi on the fly and play it
 */
public class NotesPlayer {
    private static final String TAG = "NotesPlayer";
    /** store a MediaPlayer */
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    /** the midi file that contains any combination of notes */
    private File notes_midi_file;

    /** * a temp file, media player will start_playing this file */
    // TODO use cache
    private static String notes_pathname = Environment.getExternalStorageDirectory() + "/playing_notes.mid";
    private static String song_pathname = Environment.getExternalStorageDirectory() + "/playing_song.mid";


    /**
     * how notes can be played
     */
    public enum PlayingStrategy {
        OneByOne,  /** Sequentially */
        Together, /** Simultaneously */
        OneByOneThenTogether /** individual notes, then chord */
    }


    /**
     * start_playing notes given strategy
     */
    public void start_playing(Note[] notes, PlayingStrategy playingStrategy) {
        playNoteTrack(create_notes_track(notes, playingStrategy));
    }

    /**
     * start_playing a note
     * @param note
     */
    public void start_playing(Note note) {
        playNoteTrack(create_note_track(note));
    }

    public void prepare_song(Song song) {
        MidiFile midiFile = song.getMidiFile();
        File song_file = midi_write_to_file(midiFile, song_pathname);
        load_tmp_file(song_file);
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
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
     * use play a NoteTrack using mediaPlayer
     * @param noteTrack
     */
    public void playNoteTrack(MidiTrack noteTrack) {
        create_midi_tmp_file(noteTrack);
        load_tmp_file(notes_midi_file);
        mediaPlayer.start();
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

        notes_midi_file = midi_write_to_file(midi, notes_pathname);
    }

    /**
     * create a File given midiFile and pathname
     * @param midiFile
     * @param pathname
     * @return
     */
    private File midi_write_to_file(MidiFile midiFile, String pathname) {
        File a_file = new File(pathname);
        try {
            midiFile.writeToFile(a_file);
        } catch(IOException e) {
            Log.e(TAG, "create_note: "+ e );
        }
        return a_file;
    }

    /**
     * let media player prepare playing the tmp file
     */
    private void load_tmp_file(File file) {
        try {
            FileInputStream input = new FileInputStream(file);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(input.getFD());
            input.close();
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.e(TAG, "play_example: "+ e );
        }
    }

}
