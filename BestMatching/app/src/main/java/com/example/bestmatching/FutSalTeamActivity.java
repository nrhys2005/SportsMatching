package com.example.bestmatching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FutSalTeamActivity extends Fragment {

    public static FutSalTeamActivity newInstance() {
        return new FutSalTeamActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_team_main, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.
       /* Button map = (Button)view.findViewById(R.id.map);
        Button list =(Button)view.findViewById(R.id.list);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity()로 MainActivity의 replaceFragment를 불러옵니다.
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchMapActivity.newInstance());    // 새로 불러올 Fragment의 Instance를 Main으로 전달
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchListActivity.newInstance());
            }
        });*/

        return view;
    }

}
