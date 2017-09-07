package com.example.root.rekids.GUI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.root.rekids.R;

public class MainActivity extends AppCompatActivity {


    Button buttonGame1, buttonGame2, buttonGame3, buttonMusic,howto1,howto2,howto3,salir;
    MediaPlayer mediaPlayer;
    Boolean play;
    //RegularExpressions r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGame1= (Button)findViewById(R.id.bGame1);
        buttonGame2=(Button)findViewById(R.id.bGame2);
        buttonGame3=(Button)findViewById(R.id.bGame3);
        buttonMusic=(Button)findViewById(R.id.bMusic);
        howto1=(Button)findViewById(R.id.howto1);
        howto2=(Button)findViewById(R.id.howto2);
        howto3=(Button)findViewById(R.id.howto3);
        salir=(Button) findViewById(R.id.bsalir);
        play=false;
        mediaPlayer=MediaPlayer.create(this, R.raw.music);
        buttonGame1.setOnClickListener(Onelistener);
        buttonGame2.setOnClickListener(Twolistener);
        buttonGame3.setOnClickListener(Threelistener);
        buttonMusic.setOnClickListener(Musiclistener);
        howto1.setOnClickListener(ht1);
        howto2.setOnClickListener(ht2);
        howto3.setOnClickListener(ht3);
        salir.setOnClickListener(exit);

    }

    View.OnClickListener exit = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            finish();

        }};

    View.OnClickListener ht1 = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, HowTo1.class));

        }};

    View.OnClickListener ht2 = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, HowTo2.class));

        }};
    View.OnClickListener ht3 = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, HowTo3.class));

        }};

    View.OnClickListener Musiclistener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(play){
                play=false;
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else{
                play=true;
                mediaPlayer.start();
            }

        }};


    //Listener Button First Game
    View.OnClickListener Onelistener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Game1Activity.class));
        }};

    //Listener Button Second Game
    View.OnClickListener Twolistener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Game2Activity.class));
        }};


    //Listener Button Third Game
    View.OnClickListener Threelistener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Game3Activity.class));
        }};
}
