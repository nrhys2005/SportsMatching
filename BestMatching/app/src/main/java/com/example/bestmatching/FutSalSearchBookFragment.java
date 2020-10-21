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

public class FutSalSearchBookFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private ListView futsal_my_book;
    private FutsalSearchBookAdapter futsalSearchBookAdapter;

    LoginActivity lg = new LoginActivity();

    String ip = lg.ip;
    private String now_id = lg.Myid;

    private int pos;

    //팀 개수
    private int mybookSize;

    ArrayList<String> my_book_groundName = new ArrayList<>();
    ArrayList<String> my_book_price = new ArrayList<>();
    ArrayList<String> my_book_start_time = new ArrayList<>();
    ArrayList<String> my_book_end_time = new ArrayList<>();

    public static FutSalSearchBookFragment newInstance() {
        return new FutSalSearchBookFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_search_my_book, container, false); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        futsalSearchBookAdapter = new FutsalSearchBookAdapter();

        futsal_my_book = (ListView) view.findViewById(R.id.futsal_my_book);
        futsal_my_book.setAdapter(futsalSearchBookAdapter);

        new Get().execute(ip + "/ground/booking_list/" + now_id);

        futsalSearchBookAdapter.notifyDataSetChanged();

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
                        String item = jsonObject.getString("info");
                        JSONArray jsonArray = new JSONArray(item);

                        mybookSize = jsonArray.length();

                        for (int i = 0; i < mybookSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            my_book_groundName.add(js.getString("name"));
                            my_book_price.add(js.getString("price"));
                            my_book_start_time.add(js.getString("start_time"));
                            my_book_end_time.add(js.getString("end_time"));
                        }
                    } else if (msg.equals("no find")) {
                        mybookSize = 0;
                    } else {
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

            if (mybookSize != 0) {
                for (int i = 0; i < mybookSize; i++) {

                    String start1 = my_book_start_time.get(i).substring(0,10);
                    String start2 = my_book_start_time.get(i).substring(11,16);

                    String end1 = my_book_end_time.get(i).substring(0,10);
                    String end2 = my_book_end_time.get(i).substring(11,16);

                    futsalSearchBookAdapter.addItem(my_book_groundName.get(i), my_book_price.get(i),
                            start1 + "   " + start2, end1 + "   " + end2);
                }
                futsalSearchBookAdapter.notifyDataSetChanged();
            } else {
                //Toast.makeText(getActivity(), my_book_groundName.get(0), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "검색결과 없습니다.", Toast.LENGTH_SHORT).show();
            }


        }

    }


    @Override
    public void onClick(View v) {
        int a = v.getId();

    }
}
