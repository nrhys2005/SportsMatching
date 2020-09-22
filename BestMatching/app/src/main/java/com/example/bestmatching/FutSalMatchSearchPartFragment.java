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

public class FutSalMatchSearchPartFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private ListView futsal_match_search_part;
    private FutsalMatchSearchPartAdapter futsalMatchSearchPartAdapter;

    LoginActivity lg = new LoginActivity();

    String ip = lg.ip;

    private int pos;

    //팀 개수
    private int matchPartSize;

    ArrayList<String> part_name = new ArrayList<>();
    ArrayList<String> part_age = new ArrayList<>();
    ArrayList<String> part_email = new ArrayList<>();
    ArrayList<String> part_phone = new ArrayList<>();


    public static FutSalMatchSearchPartFragment newInstance() {
        return new FutSalMatchSearchPartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_match_search_part, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        futsalMatchSearchPartAdapter = new FutsalMatchSearchPartAdapter();

        String match_id = getArguments().getString("id");

        futsal_match_search_part = (ListView) view.findViewById(R.id.futsal_match_search_part);
        futsal_match_search_part.setAdapter(futsalMatchSearchPartAdapter);

        new Get().execute(ip + "/match/match_participants_list/" + match_id);

        futsalMatchSearchPartAdapter.notifyDataSetChanged();

        return view;
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
                        String item = jsonObject.getString("mymatch_info");
                        JSONArray jsonArray = new JSONArray(item);

                        matchPartSize = jsonArray.length();

                        for (int i = 0; i < matchPartSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            part_name.add(js.getString("name"));
                            part_age.add(js.getString("age"));
                            part_email.add(js.getString("email"));
                            part_phone.add(js.getString("phone"));
                        }
                    } else if (msg.equals("no find")) {
                        matchPartSize = 0;
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

            if (matchPartSize != 0) {
                for (int i = 0; i < matchPartSize; i++) {
                    futsalMatchSearchPartAdapter.addItem(part_name.get(i), part_age.get(i), part_email.get(i), part_phone.get(i));
                }
                futsalMatchSearchPartAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "검색결과 없습니다.", Toast.LENGTH_SHORT).show();
            }


        }

    }


    @Override
    public void onClick(View v) {
        int a = v.getId();
    }
}
