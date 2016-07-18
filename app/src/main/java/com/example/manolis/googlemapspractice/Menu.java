package com.example.manolis.googlemapspractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Manolis on 2016-07-18.
 */
public class Menu extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Button joinGameButton = (Button) findViewById(R.id.joinGameButton);
        Button createGameButton = (Button) findViewById(R.id.showTeammates);
        Button playerProfileButton = (Button) findViewById(R.id.showTeammates);
        Button myGamesButton = (Button) findViewById(R.id.showTeammates);

        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, JoinGame.class));

            }
        });

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, CreateGame.class));

            }
        });

//        playerProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Menu.this, PlayerProfile.class));
//
//            }
//        });
//
//        myGamesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Menu.this, myGames.class));
//
//            }
//        });

    }
}
