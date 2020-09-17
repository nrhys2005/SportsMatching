package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class FutSalMyMatchFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;
    private ListView futsal_my_match;
    private FutsalMyMatchAdapter futsalMyMatchAdapter;

    LoginActivity lg = new LoginActivity();

    EditText my_match_search;
    Button my_match_search_btn;

    String ip = lg.ip;
    private String now_id = lg.Myid;

    private int pos;

    //팀 개수
    private int mymatchSize;

    ArrayList<String> my_match_title = new ArrayList<>();
    ArrayList<String> my_match_ground = new ArrayList<>();
    ArrayList<String> my_match_date = new ArrayList<>();
    ArrayList<String> my_match_start_time = new ArrayList<>();
    ArrayList<String> my_match_end_time = new ArrayList<>();
    ArrayList<String> my_match_cost = new ArrayList<>();
    ArrayList<String> my_match_id = new ArrayList<>();

    public static FutSalMyMatchFragment newInstance() {
        return new FutSalMyMatchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_my_match, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();
        my_match_search = (EditText) view.findViewById(R.id.my_match_search);
        my_match_search_btn = (Button) view.findViewById(R.id.my_match_search_btn);

        futsalMyMatchAdapter = new FutsalMyMatchAdapter();

        futsal_my_match = (ListView) view.findViewById(R.id.futsal_my_match);
        futsal_my_match.setAdapter(futsalMyMatchAdapter);

        new Get().execute(ip + "/match/mymatching_list/" + now_id);

        futsalMyMatchAdapter.notifyDataSetChanged();

        my_match_search_btn.setOnClickListener(this);
        futsal_my_match.setOnItemClickListener(this);

        return view;
    }

    //아이템값 가져오기 및 화면전환
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(view.getContext(), stadium_name.get(position), Toast.LENGTH_SHORT).show();
        pos = position;
        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("title", my_match_title.get(pos));
        bundle.putString("ground_name", my_match_ground.get(pos));
        bundle.putString("date", my_match_date.get(pos));
        bundle.putString("start_time", my_match_start_time.get(pos));
        bundle.putString("end_time", my_match_end_time.get(pos));
        bundle.putString("cost", my_match_cost.get(pos));
        bundle.putString("id", my_match_id.get(pos));
        FutSalMyMatchDetailFragment f = new FutSalMyMatchDetailFragment();
        f.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(FutSalMatchActivity.newInstance(), f);
    }

    // 노드js에서 안스로 데이터 받는 부분
    public class Get extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url = "";
            InputStream is = null;
            try {
                is = new URL(urls[0]).openStream();
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
                        String item = jsonObject.getString("mymatch_list_info");
                        JSONArray jsonArray = new JSONArray(item);

                        mymatchSize = jsonArray.length();

                        for (int i = 0; i < mymatchSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            my_match_title.add(js.getString("title"));
                            my_match_ground.add(js.getString("ground_name"));
                            my_match_date.add(js.getString("date"));
                            my_match_start_time.add(js.getString("start_time"));
                            my_match_end_time.add(js.getString("end_time"));
                            my_match_cost.add(js.getString("cost"));
                            my_match_id.add(js.getString("id"));
                        }
                    } else if (msg.equals("no find")) {
                        mymatchSize = 0;
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

            if (mymatchSize != 0) {
                for (int i = 0; i < mymatchSize; i++) {
                    futsalMyMatchAdapter.addItem(my_match_title.get(i).toString(), my_match_ground.get(i).toString(), my_match_date.get(i).toString(),
                            my_match_start_time.get(i).toString(), my_match_end_time.get(i).toString());
                }
                futsalMyMatchAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "검색결과 없습니다.", Toast.LENGTH_SHORT).show();
            }


        }

    }

    public void clear() {
        my_match_title.clear();
        my_match_ground.clear();
        my_match_date.clear();
        my_match_start_time.clear();
        my_match_end_time.clear();
        my_match_cost.clear();
        my_match_id.clear();
        futsalMyMatchAdapter.clearItem();
        futsalMyMatchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
     /*   int a = v.getId();
        switch (a) {
            case R.id.my_match_search_btn:
                String text = my_match_search.getText().toString();

                if (text.length() > 0) {
                    clear();
                    new Get().execute(ip + "/match/search/" + text);
                } else {
                    Toast.makeText(getActivity(), "검색내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }*/
    }
}
