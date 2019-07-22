package com.example.pitchperfectlyaccuratelypractice.model;

import com.example.pitchperfectlyaccuratelypractice.musicComponent.Song;

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

    public String[] getSongTitles() {
        int len = songs_arr.size();
        String[] titles = new String[len];
        for (int i = 0; i < len; i++) {
            titles[i] = songs_arr.get(i).getTitle();
        }
        return titles;
    }

    public Song getSongAt(int i) {
       return songs_arr.get(i);
    }

}
