package com.example.pitchperfectlyaccuratelypractice.tools;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;

import java.util.Observable;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * a microphone to store a frequency as a buffer
 * other object can listen to it
 */
public class Microphone extends Observable {

    private static final String TAG = "Microphone";

    /**
     * to run thread on this activity
     */
    private Activity activity;

    /**
     * a float buffer to store a frequency
     */
    private float currentFrequency;

    /**
     * starts a microphone (it will keeps it buffer updated)
     */
    public Microphone(Activity ac) {
        activity = ac;
        int sampleRate = 8000;

        for (int rate : new int[]{22050, 11025, 16000, 8000}) {  // add the rates you wish to check against
            int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
            if (bufferSize > 0) {
                sampleRate = rate;
                break;
            }
        }

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, 1024, 0);
        PitchDetectionHandler pdh = new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                        final float pitchInHz = res.getPitch();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentFrequency = pitchInHz;
                                setChanged();
                                notifyObservers(currentFrequency);
                            }
                        });
                    }
                };

        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, sampleRate, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);
        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }
}
