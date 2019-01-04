package com.andersonsouza.mediaplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button botaoTocar;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoTocar = findViewById(R.id.botaoPlay);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.drumshot);

        botaoTocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pararSom();
                } else {
                    tocarSom();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                botaoTocar.setText("Tocar");
            }
        });

    }

    private void tocarSom() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            botaoTocar.setText("Pausar");
        }
    }

    private void pararSom() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            botaoTocar.setText("Tocar");
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        super.onDestroy();
    }

}
