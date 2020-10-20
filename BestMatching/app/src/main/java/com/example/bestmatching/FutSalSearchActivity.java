package com.example.bestmatching;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class FutSalSearchActivity extends Fragment implements View.OnClickListener {

    public static FutSalSearchActivity newInstance() {
        return new FutSalSearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_main, null);
        Button map = (Button)view.findViewById(R.id.map);
        Button list =(Button)view.findViewById(R.id.list);
        Button book = (Button)view.findViewById(R.id.book);

       /* map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity()로 MainActivity의 replaceFragment를 불러옴
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(),FutSalSearchMapActivity.newInstance());
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(),FutSalSearchListActivity.newInstance());
            }
        });*/

       map.setOnClickListener(this);
       list.setOnClickListener(this);
       book.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.map:
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchMapFragment.newInstance());
                break;
            case R.id.list:
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchListFragment.newInstance());
                break;
            case R.id.book:
                ((MainActivity)getActivity()).replaceFragment(FutSalSearchActivity.newInstance(), FutSalSearchBookFragment.newInstance());
                break;
        }
    }
}
