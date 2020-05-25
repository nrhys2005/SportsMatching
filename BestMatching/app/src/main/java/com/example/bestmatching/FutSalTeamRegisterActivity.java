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

public class FutSalTeamRegisterActivity extends Fragment implements View.OnClickListener {

    private Context context;
    TextView team_level;

    public static FutSalTeamRegisterActivity newInstance() {
        return new FutSalTeamRegisterActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_team_register, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        team_level = (TextView) view.findViewById(R.id.team_level);


        team_level.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();

        switch (a) {
            case R.id.team_level:

                final String[] items = {"상", "중", "하"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener(){

                            public void onClick(DialogInterface dialog, int index){
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                team_level.setText(items[index]);
                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                        public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();
                            }
                        });;


                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }
}
