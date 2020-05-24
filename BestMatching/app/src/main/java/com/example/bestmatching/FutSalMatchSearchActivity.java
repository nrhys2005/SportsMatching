package com.example.bestmatching;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class FutSalMatchSearchActivity extends Fragment implements View.OnClickListener {

    private Context context;
    private ListView futsal_maatch_search;
    private FutsalMatchSearchAdapter futsalMatchSearchAdapter;

    public static FutSalMatchSearchActivity newInstance() {
        return new FutSalMatchSearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_search, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        futsalMatchSearchAdapter = new FutsalMatchSearchAdapter();

        futsal_maatch_search = (ListView)view.findViewById(R.id.futsal_match_search);
        futsal_maatch_search.setAdapter(futsalMatchSearchAdapter);

        futsalMatchSearchAdapter.addItem("제목1", "내용1");
        futsalMatchSearchAdapter.addItem("제목2", "내용2");
        futsalMatchSearchAdapter.addItem("제목3", "내용3");
        futsalMatchSearchAdapter.addItem("제목4", "내용4");
       /* futsalMatchSearchAdapter.addItem(getString(R.string.newsTitle5), getString(R.string.text5));*/

        futsalMatchSearchAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();

    }
}
