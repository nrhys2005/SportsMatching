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

public class FutSalTeam_BoardFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    LoginActivity lg = new LoginActivity();
    FutSalTeamActivity ta = new FutSalTeamActivity();

    String ip = lg.ip;
    String now_id = lg.Myid;
    String master = ta.get_master_id;

    private int pos;

    private ListView futsal_team_board;
    private FutsalTeam_BoardAdapter boardAdapter;

    HttpURLConnection con = lg.con;
    BufferedReader reader = lg.reader;

    private Context context;
    private int boardSize;

    ArrayList<String> team_name = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> part_count = new ArrayList<>();
    ArrayList<Integer> no_count = new ArrayList<>();
    ArrayList<Integer> max_part_count = new ArrayList<>();

    Button team_board_register;


    public static FutSalTeam_BoardFragment newInstance() {
        return new FutSalTeam_BoardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_board, null); // Fragment로 불러올 xml파일을 view로 가져옵니다.

        context = container.getContext();

        boardAdapter = new FutsalTeam_BoardAdapter();

        futsal_team_board = (ListView) view.findViewById(R.id.futsal_team_board);
        team_board_register = (Button) view.findViewById(R.id.team_board_register);

        futsal_team_board.setAdapter(boardAdapter);

        //new Get().execute(ip + "/Help/Notice");

        futsal_team_board.setOnItemClickListener(this);
        team_board_register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a){
            case R.id.team_board_register:
                if (master.equals(now_id)){
                    ((MainActivity)getActivity()).replaceFragment(FutSalTeamActivity.newInstance(), FutSalTeam_BoardRegisterFragment.newInstance());
                }
                else{
                    Toast.makeText(context.getApplicationContext(), "팀장만 가능합니다.", Toast.LENGTH_SHORT).show();
                }
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        pos = position;
        //Toast.makeText(view.getContext(), Integer.toString(pos), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("team_name", team_name.get(pos));
        bundle.putString("title", title.get(pos));

       /* FutSalHelp_NoticeDetailFragment f = new FutSalHelp_NoticeDetailFragment();
        f.setArguments(bundle);
        ((MainActivity)getActivity()).replaceFragment(FutSalHelpActivity.newInstance(), f);*/
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

                        boardSize = jsonArray.length();

                        for (int i = 0; i < boardSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);

                            team_name.add(js.getString("team_name"));
                            title.add(js.getString("title"));

                        }
                    } else if (msg.equals("no find")) {
                        boardSize = 0;
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
            if (boardSize != 0) {
                for (int i = 0; i < boardSize; i++) {
                    boardAdapter.addItem(team_name.get(i), title.get(i));
                }
                boardAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getActivity(), "팀 공지사항이 없습니다.", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
