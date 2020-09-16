package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FutSalTeamMemberFragment_member extends Fragment implements View.OnClickListener{

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;

    private ListView notice;
    private FutsalHelp_NoticeAdapter noticeAdapter;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;

    private Context context;
    private int noticeSize;
    ArrayList<String> notice_category = new ArrayList<>();
    ArrayList<String> notice_title = new ArrayList<>();
  //  ArrayList<String> notice_content = new ArrayList<>();
    ArrayList<Integer> notice_id = new ArrayList<>();


    public static FutSalTeamMemberFragment_member newInstance() {
        return new FutSalTeamMemberFragment_member();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_help_notice, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        noticeAdapter = new FutsalHelp_NoticeAdapter();
        notice = (ListView) view.findViewById(R.id.futsal_help_notice);
        notice.setAdapter(noticeAdapter);
        new Get().execute(ip + "/Help/Notice");

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();


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

                    if (msg.equals("Success")) {
                        String notice_info = jsonObject.getString("notice_info");
                        JSONArray jsonArray = new JSONArray(notice_info);

                        noticeSize = jsonArray.length();

                        for (int i = 0; i < noticeSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            notice_category.add(js.getString("category"));
                            notice_title.add(js.getString("title"));
                           // notice_content.add(js.getString("content"));
                            notice_id.add(js.getInt("id"));

                        }
                    } else if (msg.equals("no find")) {
                        noticeSize = 0;
                    } else {
                        //Toast.makeText(context.getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
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
            if (noticeSize != 0) {
                for (int i = 0; i < noticeSize; i++) {
                    noticeAdapter.addItem(notice_category.get(i), notice_title.get(i),notice_id.get(i));
                }
                noticeAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "공지사항이 없습니다.", Toast.LENGTH_SHORT).show();
            }




        }

    }


}
