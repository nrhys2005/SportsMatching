package com.example.bestmatching;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FutSalMatchActivity extends Fragment implements View.OnClickListener {

    private Context context;
    LoginActivity lg = new LoginActivity();
    //LoginResultActivity lr = new LoginResultActivity();
   // int Team_Master = lr.Team_Master;
    String ip = lg.ip;
    String now_id = lg.Myid;

    Button match_1;
    Button match_2;
    Button match_3;

    //팀장인지 판단
    public static String get_master_id="";
    public static String get_id="";
    public static int Team_Master=0;


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

        new Get().execute(ip + "/team/team?id=" + now_id);

        return view;
    }

    public class Get extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            InputStream is = null;
            try {
                is = new URL(urls[0]).openStream();
                // System.out.println(urls[0]);
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str;
                StringBuffer buffer = new StringBuffer();
                while ((str = rd.readLine()) != null) {
                    buffer.append(str);
                }
                //URL 내용들
                String receiveMsg = buffer.toString();
                try {
                    JSONObject jsonObject = new JSONObject(receiveMsg);
                    String msg = jsonObject.getString("result");
                    if (msg.equals("200")) {
                        String team_main = jsonObject.getString("team_main");
                        JSONArray jsarr = new JSONArray(team_main);
                        JSONObject js = jsarr.getJSONObject(0);

                        get_id = js.getString("id");
                        get_master_id = js.getString("master_id");
                    }
                    else {
                        Toast.makeText(context.getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //TODO 겟 처리 후 결과

            if(get_id.equals(get_master_id)) {

                Team_Master = 1;
            }
            else
                Team_Master=0;

        }

    }


    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a){
            case R.id.match_1:
                //((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchRegisterFragment.newInstance());

                final String[] items = {"용병 매칭등록", "팀 매칭등록"};
                final ArrayList<String> selectedItems = new ArrayList<>();
                selectedItems.add("NULL");

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
                                else if(selectedItems.get(0)== "NULL") {
                                    Toast.makeText(context, "다시 선택하세요.", Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    if (Team_Master == 0){
                                        Toast.makeText(context, "팀장만 가능합니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        ((MainActivity) getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalTeam_Match_RegisterFragment.newInstance());
                                    }
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
                final String[] items2 = {"용병 매칭검색", "팀 매칭검색"};
                final ArrayList<String> selectedItems2 = new ArrayList<>();
                selectedItems2.add("NULL");

                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);

                builder2.setTitle("선택하세요")
                        .setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int index) {
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                selectedItems2.clear();
                                selectedItems2.add(items2[index]);

                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();*/
                                if (selectedItems2.get(0).equals("용병 매칭검색")){
                                    ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMatchSearchFragment.newInstance());
                                }
                                else if(selectedItems2.get(0)== "NULL") {
                                    Toast.makeText(context, "다시 선택하세요.", Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    if (Team_Master == 0){
                                        Toast.makeText(context, "팀장만 가능합니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        ((MainActivity) getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalTeam_Match_RegisterFragment.newInstance());
                                    }
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

                AlertDialog dialog2 = builder2.create();
                dialog2.show();
                break;

            case R.id.match_3:
                //((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMyMatchFragment.newInstance());
                final String[] items3 = {"나의 용병매칭", "나의 팀매칭"};
                final ArrayList<String> selectedItems3 = new ArrayList<>();
                selectedItems3.add("NULL");

                AlertDialog.Builder builder3 = new AlertDialog.Builder(context);

                builder3.setTitle("선택하세요")
                        .setSingleChoiceItems(items3, -1, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int index) {
                                /*Toast.makeText(context, items[index], Toast.LENGTH_SHORT).show();*/
                                selectedItems3.clear();
                                selectedItems3.add(items3[index]);

                            }
                        })

                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();*/
                                if (selectedItems3.get(0).equals("나의 용병매칭")){
                                    ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), FutSalMyMatchFragment.newInstance());
                                }
                                else if(selectedItems3.get(0)== "NULL") {
                                    Toast.makeText(context, "다시 선택하세요.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    ((MainActivity) getActivity()).replaceFragment(FutSalMatchActivity.newInstance(),FutSalMyTeamMatchFragment.newInstance());
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

                AlertDialog dialog3 = builder3.create();
                dialog3.show();
                break;
        }
    }
}
