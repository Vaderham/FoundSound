package com.example.rharper.foundsound;

import android.media.MediaRecorder;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class AudioRecorder {

    private static String LOG_TAG = "AUDIO RECORDER";
    private String mRecordingName;
    MediaRecorder mAudioRecorder;

    public AudioRecorder(String recordingName, File saveLocation) {
       mRecordingName = recordingName;
       mAudioRecorder = new MediaRecorder();

       mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
       mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
       mAudioRecorder.setOutputFile(saveLocation + "/" + recordingName + ".3gp");
       mAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    public void startRecording(){
        try {
            mAudioRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mAudioRecorder.start();
    }

    public void pauseRecording(){
        mAudioRecorder.pause();
    }

    public void stopRecording() {
        mAudioRecorder.stop();
        mAudioRecorder.release();
    }

}
