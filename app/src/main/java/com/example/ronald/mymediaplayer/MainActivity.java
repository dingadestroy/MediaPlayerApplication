package com.example.ronald.mymediaplayer;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    MediaPlayer mp;
    Button play, stop, pause;
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;
    String SETTINGS = "AUDIO_SETTINGS";
    Boolean STOP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (Button) findViewById(R.id.button);
        stop = (Button) findViewById(R.id.button1);
        pause = (Button) findViewById(R.id.button2);
        stop.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.INVISIBLE);
        STOP = false;
    }

    public void playAudio(View view) {
        settings = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        mp = MediaPlayer.create(this, R.raw.shortaudio);

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                stop.setVisibility(View.GONE);
            }
        });
        if (STOP) {
            mp.start();
            play.setVisibility(View.GONE);
            stop.setVisibility(View.VISIBLE);
            pause.setVisibility(View.VISIBLE);
        } else  {
            if(settings.getInt("VOLUME",0) != 0) {
                mp.seekTo(settings.getInt("VOLUME",0));
                mp.start();
                play.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
            } else {
                mp.start();
                play.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                pause.setVisibility(View.VISIBLE);
        }

        }
    }
    public void stopAudio(View view) {
        if(mp.isPlaying()) {
            mp.stop();
            STOP = true;
            play.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
            stop.setVisibility(View.GONE);
        }
    }
    public void pauseAudio(View view) {
        settings = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        prefEditor = settings.edit();
        STOP = false;
        if(mp.isPlaying()) {
            mp.pause();
            pause.setVisibility(View.GONE);
            stop.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
           // mp.getCurrentPosition();
            Toast.makeText(this,"Current position: " + mp.getCurrentPosition(), Toast.LENGTH_LONG).show();
            prefEditor.putInt("VOLUME", mp.getCurrentPosition());
            prefEditor.commit();
        }
    }
}


