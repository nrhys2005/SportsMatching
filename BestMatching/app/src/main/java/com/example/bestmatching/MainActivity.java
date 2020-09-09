package com.example.bestmatching;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

/*    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();*/
    Button search_place;
    Button match;
    Button team;
    Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container1, FutSalSearchActivity.newInstance());
        ft.add(R.id.fragment_container2, FutSalSearchMapActivity.newInstance());
        ft.commit();

        search_place=findViewById(R.id.search_place);
        match = findViewById(R.id.match);
        team = findViewById(R.id.team);
        help = findViewById(R.id.help);


        search_place.setOnClickListener(this);
        match.setOnClickListener(this);
        team.setOnClickListener(this);
        help.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.search_place:
                replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchMapActivity.newInstance());
                break;
            case R.id.match:
                replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchRegisterActivity.newInstance());
                break;
            case R.id.team:
                replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamRegisterActivity.newInstance());
                break;
            case R.id.help:
                //replaceFragment(FutSalHelpActivity.newInstance(), FutSalSearchMapActivity.newInstance());
                break;
        }
    }
}
