package com.example.root.rekids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.root.rekids.GUI.Draw;
import com.example.root.rekids.Logic.Move;

public class MainActivity1 extends AppCompatActivity {
    Draw ourAirplane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourAirplane = new Draw(this);
        setContentView(ourAirplane);
        Intent ourIntent = new Intent(this, Move.class);
        startActivity(ourIntent);

    }
}
