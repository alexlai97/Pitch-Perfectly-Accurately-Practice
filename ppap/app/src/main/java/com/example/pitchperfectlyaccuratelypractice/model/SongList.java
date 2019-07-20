package com.example.pitchperfectlyaccuratelypractice.model;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.example.pitchperfectlyaccuratelypractice.music.Song;
import com.example.pitchperfectlyaccuratelypractice.tools.MyMidiTool;
import com.leff.midi.MidiFile;

import java.io.InputStream;
import java.util.ArrayList;

public class SongList {
    private ArrayList<Song> songs_arr = new ArrayList<>();

    public SongList() {
    }


    public void add(Song song) {
        songs_arr.add(song);
    }

    public Song getSong(int id) {
        for (Song s: songs_arr) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }
}
