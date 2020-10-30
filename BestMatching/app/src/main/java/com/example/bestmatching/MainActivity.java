package com.example.bestmatching;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button search_place;
    Button match;
    Button team;
    Button help;

    int blue;
    int darkblue;

    boolean click1;
    boolean click2;
    boolean click3;
    boolean click4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blue = ContextCompat.getColor(this, R.color.blue);
        darkblue = ContextCompat.getColor(this, R.color.darkblue);

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container1, FutSalSearchActivity.newInstance());
        ft.add(R.id.fragment_container2, FutSalSearchMapFragment.newInstance());
        ft.commit();

        search_place = findViewById(R.id.search_place);
        match = findViewById(R.id.match);
        team = findViewById(R.id.team);
        help = findViewById(R.id.help);

        search_place.setBackgroundColor(darkblue);

        search_place.setOnClickListener(this);
        match.setOnClickListener(this);
        team.setOnClickListener(this);
        help.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
       //  Toast.makeText(this.getApplicationContext(), "뒤로가기 버튼", Toast.LENGTH_SHORT).show();
        // 코드 작성
    }
    //프레그먼트 교체 함수
    public void replaceFragment(Fragment fragment1, Fragment fragment2) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_container1, fragment1);
        ft.replace(R.id.fragment_container2, fragment2);
        ft.commit();
        //ft.commitAllowingStateLoss();
    }

    //뒤로가기 함수
    public void backFragment(Fragment fragment1, Fragment fragment2) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_container1, fragment1);
        ft.replace(R.id.fragment_container2, fragment2);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void settingColor(boolean c1, boolean c2, boolean c3, boolean c4) {
        if (c1 == true) {
            search_place.setBackgroundColor(darkblue);
            match.setBackgroundColor(blue);
            team.setBackgroundColor(blue);
            help.setBackgroundColor(blue);
        }
        else if (c2==true) {
            search_place.setBackgroundColor(blue);
            match.setBackgroundColor(darkblue);
            team.setBackgroundColor(blue);
            help.setBackgroundColor(blue);
        }
        else if (c3==true) {
            search_place.setBackgroundColor(blue);
            match.setBackgroundColor(blue);
            team.setBackgroundColor(darkblue);
            help.setBackgroundColor(blue);
        }
        else if (c4==true) {
            search_place.setBackgroundColor(blue);
            match.setBackgroundColor(blue);
            team.setBackgroundColor(blue);
            help.setBackgroundColor(darkblue);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.search_place:
                settingColor(true, false, false, false);
                replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchMapFragment.newInstance());
                break;
            case R.id.match:
                settingColor(false, true, false, false);
                replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchFragment.newInstance());
                break;
            case R.id.team:
                settingColor(false, false, true, false);
                replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());
                break;
            case R.id.help:
                settingColor(false, false, false, true);
                replaceFragment(FutSalHelpActivity.newInstance(), FutSalHelp_MyinfoFragment.newInstance());
                break;
        }
    }
}
