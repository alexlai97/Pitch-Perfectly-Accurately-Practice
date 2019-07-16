package com.example.pitchperfectlyaccuratelypractice.tools;

import android.os.Handler;

import android.media.AudioTrack;
import android.media.AudioFormat;
import android.media.AudioManager;

/**
 * a speaker which can play music note(s)
 * <p>
 */
public class NotePlayer  {
    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Jialin Shan for pitch perfect
    private final int sampleRate = 44100;
    byte generatedSnd[];
    int PLAY_NOTE_DURATION = 1;
    Handler handler = new Handler();

    public void genTone(int freqOfTone, int duration){
        // fill out the array
        int numSamples = duration * sampleRate;
        double sample[] = new double[numSamples];
        generatedSnd = new byte[2 * numSamples];

        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        int i = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            i ++;
            // Ramp down to avoid click
            final short val = (short) ((dVal * 32767 * (sampleRate*duration - i) / sampleRate*duration));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    public void playSound(){
        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

    public void playOneNote(int freq) {
        genTone(freq, PLAY_NOTE_DURATION);
        playSound();
    }
}
