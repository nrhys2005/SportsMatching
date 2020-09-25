package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

public class FutSalHelp_QuestionFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    LoginActivity lg = new LoginActivity();
    String ip = lg.ip;
    private int pos;
    private ListView question;
    private FutsalHelp_QuestionAdapter questionAdapter;
    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;

    private Context context;
    private int questionSize;
    ArrayList<String> question_category = new ArrayList<>();
    ArrayList<String> question_title = new ArrayList<>();
    ArrayList<String> question_content = new ArrayList<>();
    ArrayList<String> question_id = new ArrayList<>();

    Button question_regist;
    public static FutSalHelp_QuestionFragment newInstance() {
        return new FutSalHelp_QuestionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_help_question, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        questionAdapter = new FutsalHelp_QuestionAdapter();
        question = (ListView) view.findViewById(R.id.futsal_help_question);
        question.setAdapter(questionAdapter);

        question_regist = (Button)view.findViewById(R.id.question_regist);

        new Get().execute(ip + "/Help/Question");

        question_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), FutSalHelp_Question_RegisterFragment.newInstance());
            }
        });

        question.setOnItemClickListener(this);
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(view.getContext(), stadium_name.get(position), Toast.LENGTH_SHORT).show();
        pos = position;
        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("question_category", question_category.get(pos));
        bundle.putString("question_title", question_title.get(pos));
        bundle.putString("question_id", question_id.get(pos));
        bundle.putString("question_content", question_content.get(pos));
        FutSalHelp_QuestionDetailFragment f = new FutSalHelp_QuestionDetailFragment();
        f.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), f);
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

                    if (msg.equals("200")) {
                        String notice_info = jsonObject.getString("question_info");
                        JSONArray jsonArray = new JSONArray(notice_info);

                        questionSize = jsonArray.length();

                        for (int i = 0; i < questionSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);

                            question_id.add(js.getString("user_id"));
                            question_category.add(js.getString("category"));
                            question_title.add(js.getString("title"));
                            question_content.add(js.getString("content"));


                        }
                    } else if (msg.equals("no find")) {
                        questionSize = 0;
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
            if (questionSize != 0) {
                for (int i = 0; i < questionSize; i++) {
                    questionAdapter.addItem(question_category.get(i), question_title.get(i),question_id.get(i),question_content.get(i));
                }
                questionAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "문의내용이 없습니다.", Toast.LENGTH_SHORT).show();
            }




        }

    }


}
