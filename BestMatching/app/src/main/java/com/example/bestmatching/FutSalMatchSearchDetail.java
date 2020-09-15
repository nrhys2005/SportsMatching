package com.example.bestmatching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FutSalMatchSearchDetail extends Fragment implements View.OnClickListener {

    private Context context;

    TextView detail_match_title;
    TextView detail_match_ground_name;
    TextView detail_match_date;
    TextView detail_match_start_time;
    TextView detail_match_end_time;
    TextView detail_match_cost;

    Button back_btn;

    public static FutSalMatchSearchDetail newInstance() {
        return new FutSalMatchSearchDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_search_item_detail, null);

        context = container.getContext();

        detail_match_title = (TextView)view.findViewById(R.id.detail_match_title);
        detail_match_ground_name = (TextView)view.findViewById(R.id.detail_match_ground_name);
        detail_match_date = (TextView)view.findViewById(R.id.detail_match_date);
        detail_match_start_time = (TextView)view.findViewById(R.id.detail_match_start_time);
        detail_match_end_time = (TextView)view.findViewById(R.id.detail_match_end_time);
        detail_match_cost = (TextView)view.findViewById(R.id.detail_match_cost);

        String title = getArguments().getString("title");
        String ground_name = getArguments().getString("ground_name");
        String date = getArguments().getString("date");
        String start_time = getArguments().getString("start_time");
        String end_time = getArguments().getString("end_time");
        String cost = getArguments().getString("cost");

        detail_match_title.setText(title);
        detail_match_ground_name.setText(ground_name);
        detail_match_date.setText(date);
        detail_match_start_time.setText(start_time);
        detail_match_end_time.setText(end_time);
        detail_match_cost.setText(cost + "원");

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
                ((MainActivity) getActivity()).backFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchActivity.newInstance());
                break;
        }

    }
}
