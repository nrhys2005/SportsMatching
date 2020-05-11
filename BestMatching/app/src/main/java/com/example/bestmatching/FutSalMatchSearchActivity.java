package com.example.bestmatching;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FutSalMatchSearchActivity extends Fragment implements View.OnClickListener {

    private Context context;

    public static FutSalMatchSearchActivity newInstance() {
        return new FutSalMatchSearchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_search, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();

    }
}
