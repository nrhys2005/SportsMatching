package com.example.bestmatching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FutSalSearchListActivity extends AppCompatActivity {

    Button search_place;
    Button match;
    Button team;
    Button help;
    Button map;
    Button list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futsal_search_list);

        search_place = findViewById(R.id.search_place);
        match = findViewById(R.id.match);
        team = findViewById(R.id.team);
        help = findViewById(R.id.help);
        map = findViewById(R.id.map);
        list = findViewById(R.id.list);

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
                Intent intent = new Intent(FutSalSearchListActivity.this, FutSalMatchActivity.class);
                startActivity(intent);
            }
        });

        //마이팀 눌렀을때
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchListActivity.this, FutSalTeamActivity.class);
                startActivity(intent);
            }
        });

        //지도로보기 눌렀을때
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchListActivity.this,FutSalSearchMapActivity.class);
                startActivity(intent);
            }
        });

       /* //리스토로보기 눌렀을때
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FutSalSearchListActivity.this,FutSalSearchListActivity.class);
                startActivity(intent);
            }
        });*/
    }

}
