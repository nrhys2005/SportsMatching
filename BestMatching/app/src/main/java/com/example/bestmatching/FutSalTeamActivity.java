package com.example.bestmatching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FutSalTeamActivity extends Fragment implements View.OnClickListener {

    Button team_1;
    Button team_2;

    public static FutSalTeamActivity newInstance() {
        return new FutSalTeamActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {

        View view = inflater.inflate(R.layout.activity_futsal_team_main, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        team_1 = (Button)view.findViewById(R.id.team_1);
        team_2 = (Button)view.findViewById(R.id.team_2);

        team_1.setOnClickListener(this);
        team_2.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        int a = v.getId();

        switch (a){
            case R.id.team_1:
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamRegisterFragment.newInstance());
                break;
            case R.id.team_2:
                ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());
                //new Get();
                break;
        }

    }
}
