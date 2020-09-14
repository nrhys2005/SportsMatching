package com.example.bestmatching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FutSalHelpActivity extends Fragment implements View.OnClickListener{

    Button Myinfo;
    Button Notice;
    Button Inquiry;
    Button Report;
    public static FutSalHelpActivity newInstance() {
        return new FutSalHelpActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_help_main, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.
        Myinfo = (Button)view.findViewById(R.id.Myinfo);
        Notice = (Button)view.findViewById(R.id.Notice);
        Inquiry = (Button)view.findViewById(R.id.Inquiry);
        Report = (Button)view.findViewById(R.id.Report);


        Myinfo.setOnClickListener(this);
        Notice.setOnClickListener(this);
        Inquiry.setOnClickListener(this);
        Report.setOnClickListener(this);




        return view;
    }

    @Override
    public void onClick(View v) {

        int a = v.getId();

        switch (a){
            case R.id.Myinfo:
                ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), FutSalHelp_MyinfoActivity.newInstance());
                break;
            case R.id.Notice:
                ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), FutSalTeamSearchActivity.newInstance());
                break;
            case R.id.Inquiry:
                ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), FutSalHelp_MyinfoActivity.newInstance());
                break;
            case R.id.Report:
                ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), FutSalTeamSearchActivity.newInstance());
                break;
        }
    }
}
