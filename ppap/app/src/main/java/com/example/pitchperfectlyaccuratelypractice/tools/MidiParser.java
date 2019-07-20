package com.example.pitchperfectlyaccuratelypractice.tools;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.pitchperfectlyaccuratelypractice.R;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.util.MidiEventListener;
import com.leff.midi.util.MidiProcessor;

import java.io.InputStream;

public class MidiParser {
    private static final String TAG = "MidiParser";
    Context context;
    public MidiParser(Context ct) {
        context = ct;

    }

    public void do_something() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.auld_lang_syne);
        MidiFile midiFile = new MidiFile();
        try {
            midiFile = new MidiFile(inputStream);
        } catch (Exception e) {
            Log.d(TAG, "MidiParser: " + e);
        }
        Log.d(TAG, "do_something: doing something");

        MidiProcessor processor = new MidiProcessor(midiFile);
        MidiEventPrinter midiEventPrinter = new MidiEventPrinter();
        processor.registerEventListener(midiEventPrinter, NoteOn.class);

        processor.start();
    }

    public static void main(String args[]) {
//        MidiParser midiParser = new MidiParser();
    }
}
