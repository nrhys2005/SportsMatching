package com.example.bestmatching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FutSalMatchActivity extends Fragment implements View.OnClickListener {

    Button match_1;
    Button match_2;


    public static FutSalMatchActivity newInstance() {
        return new FutSalMatchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_main, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        match_1 = (Button)view.findViewById(R.id.match_1);
        match_2 =(Button)view.findViewById(R.id.match_2);

        match_1.setOnClickListener(this);
        match_2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a){
            case R.id.match_1:
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchRegisterFragment.newInstance());
                break;
            case R.id.match_2:
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchFragment.newInstance());
                break;
            case R.id.match_3:
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchFragment.newInstance());
                break;
        }
    }
}
