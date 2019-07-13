package com.example.perfectpitchaccuratepractice.activities;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

import com.example.perfectpitchaccuratepractice.common.ModelController;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * uses Tarsos library functions to spawn a frequency detection thread 
 *
 */
public class VoiceListener extends Activity {

    private static final String TAG = "VoiceListener";
  /**
   * modelController
   */
  private ModelController modelController;

  /**
   * Constructor for VoiceListener
   */
  public VoiceListener(ModelController mc) {
    modelController = mc;
  }

  /**
   * runs a thread to detect frequency
   */
  public void startListening() {
        int sampleRate = 8000;

        for (int rate : new int[]{22050, 11025, 16000, 8000}) {  // add the rates you wish to check against
            int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
            if (bufferSize > 0) {
                sampleRate = rate;
                break;
            }
        }

//      Log.i(TAG, "I have been here.");

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(sampleRate, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        modelController.processFrequency(pitchInHz);
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
