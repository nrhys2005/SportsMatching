package com.example.bestmatching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FutSalSearchActivity extends AppCompatActivity {

    Button search_place;
    Button match;
    Button team;
    Button help;
    Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futsal_search);

        search_place = findViewById(R.id.search_place);
        match = findViewById(R.id.match);
        team = findViewById(R.id.team);
        help = findViewById(R.id.help);
        map = findViewById(R.id.map);

        /*//구장검색 눌렀을때
        search_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchActivity.this,FutSalSearchActivity.class);
                startActivity(intent);
            }
        });*/

        //매치 눌렀을때
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchActivity.this, FutSalMatchActivity.class);
                startActivity(intent);
            }
        });

        //마이팀 눌렀을때
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchActivity.this, FutSalTeamActivity.class);
                startActivity(intent);
            }
        });

        //고객센터 눌렀을때
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchActivity.this, FutSalHelpActivity.class);
                startActivity(intent);
            }
        });

        /*map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"버튼클릭",Toast.LENGTH_LONG).show();
            }
        });*/
    }

    public void map_btn(View v){
        Toast.makeText(getApplicationContext(),"버튼클릭",Toast.LENGTH_LONG).show();
    }
}
