package com.example.bestmatching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FutSalHelpActivity extends AppCompatActivity {

    Button search_place;
    Button match;
    Button team;
    Button help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futsal_help);

        search_place = findViewById(R.id.search_place);
        match = findViewById(R.id.match);
        team = findViewById(R.id.team);
        help = findViewById(R.id.help);

        //구장검색 눌렀을때
        search_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalHelpActivity.this,FutSalSearchActivity.class);
                startActivity(intent);
            }
        });

        //매치 눌렀을때
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalHelpActivity.this,FutSalMatchActivity.class);
                startActivity(intent);
            }
        });

        //마이팀 눌렀을때
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalHelpActivity.this,FutSalTeamActivity.class);
                startActivity(intent);
            }
        });

        /*//고객센터 눌렀을때
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalHelpActivity.this,FutSalHelpActivity.class);
                startActivity(intent);
            }
        });*/

    }
}
