package com.example.bestmatching;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FutSalMatchActivity extends Fragment implements View.OnClickListener {

    private Context context;
    LoginActivity lg = new LoginActivity();
    LoginResultActivity lr = new LoginResultActivity();
    int Team_Master = lr.Team_Master;
    String ip = lg.ip;
    String now_id = lg.Myid;

    Button match_1;
    Button match_2;
    Button match_3;


    public static FutSalMatchActivity newInstance() {
        return new FutSalMatchActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_main, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        match_1 = (Button)view.findViewById(R.id.match_1);
        match_2 =(Button)view.findViewById(R.id.match_2);
        match_3 =(Button)view.findViewById(R.id.match_3);

        match_1.setOnClickListener(this);
        match_2.setOnClickListener(this);
        match_3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a){
            case R.id.match_1:
                //((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchRegisterFragment.newInstance());

                final String[] items = {"용병 매칭등록", "팀 매칭등록"};
                final ArrayList<String> selectedItems = new ArrayList<>();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("선택하세요")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int index) {
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                selectedItems.clear();
                                selectedItems.add(items[index]);

                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();*/
                                if (selectedItems.get(0).equals("용병 매칭등록")){
                                    ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchRegisterFragment.newInstance());
                                }
                                else{
                                    if(Team_Master==1)
                                        ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalTeam_Match_RegisterFragment.newInstance());
                                    else
                                        Toast.makeText(context, "팀장만 등록할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                }


                            }
                        })

                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();*/
                            }
                        });
                ;

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.match_2:
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchFragment.newInstance());
                break;
            case R.id.match_3:
                ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMyMatchFragment.newInstance());
                break;
        }
    }
}
