package com.example.bestmatching;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class FutSalHelp_NoticeDetailFragment extends Fragment implements View.OnClickListener {



    private Context context;

    FutSalTeamActivity fa = new FutSalTeamActivity();
    LoginActivity lg = new LoginActivity();

    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    String ip = lg.ip;

    public int team_member = fa.in_team;
    TextView detail_notice_title;
    TextView detail_notice_create_time;
    TextView detail_notice_content;

    Button back_btn;

    public static FutSalHelp_NoticeDetailFragment newInstance() {
        return new FutSalHelp_NoticeDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_help_notice_item_detail, null);

        context = container.getContext();

        detail_notice_title = (TextView)view.findViewById(R.id.detail_notice_title);
        detail_notice_create_time = (TextView)view.findViewById(R.id.detail_notice_create_time);
        detail_notice_content = (TextView)view.findViewById(R.id.detail_notice_content);

        String notice_title = getArguments().getString("notice_title");
        String notice_create_time = getArguments().getString("notice_create_time");
        String notice_content = getArguments().getString("notice_content");


        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_notice_title.setText(notice_title);
        detail_notice_create_time.setText(notice_create_time);
        detail_notice_content.setText(notice_content);



        /*int id = getArguments().getInt("id");
        switch (id) {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sangju);
                detail_ground.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.daegu);
                detail_ground.setImageBitmap(bm2);
                break;
        }*/

        back_btn = (Button)view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.back_btn:
                ((MainActivity) getActivity()).backFragment(FutSalHelpActivity.newInstance(), FutSalHelp_NoticeFragment.newInstance());
                break;
        }
    }
}
