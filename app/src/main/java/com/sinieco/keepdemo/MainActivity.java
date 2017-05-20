package com.sinieco.keepdemo;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SurfaceView sv;
    private Button bt;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sv = (SurfaceView) findViewById(R.id.sv);
        bt = (Button) findViewById(R.id.btn_goto);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"点击立即体验",Toast.LENGTH_SHORT).show();
            }
        });
        SurfaceHolder holder = sv.getHolder();
        holder.setKeepScreenOn(true);
        holder.addCallback(new MyCallback());
    }
    public class MyCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            //播放视频
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                AssetFileDescriptor fileDescriptor = MainActivity.this.getAssets().openFd("test1.mp4");
                player.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
                player.setLooping(true);
                player.setDisplay(sv.getHolder());
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            //结束视频
            player.stop();
            player.release();
        }
    }
}
