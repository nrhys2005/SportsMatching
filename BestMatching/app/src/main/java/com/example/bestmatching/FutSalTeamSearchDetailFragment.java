package com.example.bestmatching;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FutSalTeamSearchDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;

    TextView detail_team_name;
    TextView detail_team_matser;
    TextView detail_team_number;
    TextView detail_team_location;
    TextView detail_team_week;
    TextView detail_team_age;
    TextView detail_team_comment;

    Button back_btn;

    public static FutSalTeamSearchDetailFragment newInstance() {
        return new FutSalTeamSearchDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_search_item_detail, null);

        context = container.getContext();

        detail_team_name = (TextView)view.findViewById(R.id.detail_team_name);
        detail_team_matser = (TextView)view.findViewById(R.id.detail_master_id);
        detail_team_number = (TextView)view.findViewById(R.id.detail_team_number);
        detail_team_location = (TextView)view.findViewById(R.id.detail_team_locaion);
        detail_team_week = (TextView)view.findViewById(R.id.detail_team_week);
        detail_team_age = (TextView)view.findViewById(R.id.detail_team_age);
        detail_team_comment = (TextView)view.findViewById(R.id.detail_team_comment);

        String team_name = getArguments().getString("team_name");
        String team_master = getArguments().getString("team_master");
        String team_number = getArguments().getString("phonenumber");
        String team_location = getArguments().getString("location");
        String team_week = getArguments().getString("week");
        String team_age= getArguments().getString("avg_age");
        String team_comment = getArguments().getString("comment");
        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_team_name.setText(team_name);
        detail_team_matser.setText(team_master);
        detail_team_number.setText(team_number);
        detail_team_location.setText(team_location);
        detail_team_week.setText(team_week);
        detail_team_age.setText(team_age);
        detail_team_comment.setText(team_comment);

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
                ((MainActivity) getActivity()).backFragment(FutSalTeamActivity.newInstance(), FutSalTeamSearchFragment.newInstance());
                break;

        }
    }
}
