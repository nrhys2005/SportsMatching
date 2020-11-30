package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FutSalHelp_NoticeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    private int pos;
    private ListView notice;
    private FutsalHelp_NoticeAdapter noticeAdapter;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;

    private Context context;
    private int noticeSize;

    ArrayList<String> notice_title = new ArrayList<>();
    ArrayList<String> notice_create_time = new ArrayList<>();
    ArrayList<String> notice_content = new ArrayList<>();




    public static FutSalHelp_NoticeFragment newInstance() {
        return new FutSalHelp_NoticeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_help_notice, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        noticeAdapter = new FutsalHelp_NoticeAdapter();
        notice = (ListView) view.findViewById(R.id.futsal_help_notice);
        notice.setAdapter(noticeAdapter);
        new Get().execute(ip + "/Help/Notice");
        new Post_S().execute(ip+"/user/login/auto");
        notice.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        pos = position;
        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("notice_title", notice_title.get(pos));
        bundle.putString("notice_create_time", notice_create_time.get(pos));
        bundle.putString("notice_content", notice_content.get(pos));

        FutSalHelp_NoticeDetailFragment f = new FutSalHelp_NoticeDetailFragment();
        f.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), f);
    }
    public class Post_S extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("user_id", "androidTest");
                //jsonObject.put("name", "yun");



                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //test1.setText(result);//서버로 부터 받은 값을 출력해주는 부분
            try {
                System.out.println("***"+result);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
                        String notice_info = jsonObject.getString("notice_info");
                        JSONArray jsonArray = new JSONArray(notice_info);

                        noticeSize = jsonArray.length();

                        for (int i = 0; i < noticeSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);

                            notice_title.add(js.getString("title"));
                            notice_create_time.add(js.getString("create_time"));
                            notice_content.add(js.getString("content"));


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
                    noticeAdapter.addItem(notice_title.get(i),notice_create_time.get(i),notice_content.get(i));
                }
                noticeAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "공지사항이 없습니다.", Toast.LENGTH_SHORT).show();
            }




        }

    }


}
