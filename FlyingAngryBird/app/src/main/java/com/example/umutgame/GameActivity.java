package com.example.umutgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity
{


    private Button startGame;
    private TextView DisplayScore;
    private String score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        score = getIntent().getExtras().get("score").toString();

        startGame = (Button) findViewById(R.id.play_again_btn);
        DisplayScore = (TextView) findViewById(R.id.displayScore);

        startGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        DisplayScore.setText("Score : " + score);
    }
}