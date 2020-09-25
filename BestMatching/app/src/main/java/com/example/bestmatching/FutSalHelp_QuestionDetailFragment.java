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

public class FutSalHelp_QuestionDetailFragment extends Fragment implements View.OnClickListener {



    private Context context;

    FutSalTeamActivity fa = new FutSalTeamActivity();
    LoginActivity lg = new LoginActivity();

    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;
    String ip = lg.ip;

    public int team_member = fa.in_team;
    TextView detail_question_title;
    TextView detail_question_id;
    TextView detail_question_category;
    TextView detail_question_content;

    Button back_btn;

    public static FutSalHelp_QuestionDetailFragment newInstance() {
        return new FutSalHelp_QuestionDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_help_question_item_detail, null);

        context = container.getContext();

        detail_question_title = (TextView)view.findViewById(R.id.detail_question_title);
        detail_question_id = (TextView)view.findViewById(R.id.detail_question_id);
        detail_question_category = (TextView)view.findViewById(R.id.detail_question_category);
        detail_question_content = (TextView)view.findViewById(R.id.detail_question_content);

        String question_category = getArguments().getString("question_category");
        String question_title = getArguments().getString("question_title");
        String question_id = getArguments().getString("question_id");
        String question_content = getArguments().getString("question_content");

        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_question_title.setText(question_category);
        detail_question_id.setText(question_title);
        detail_question_category.setText(question_id);
        detail_question_content.setText(question_content);


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
                ((MainActivity) getActivity()).backFragment(FutSalHelpActivity.newInstance(), FutSalHelp_QuestionFragment.newInstance());
                break;
        }
    }
}
