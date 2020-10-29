package com.example.bestmatching;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;
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

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class FutSalTeam_Board_Item_Detail extends Fragment implements View.OnClickListener {

    LoginActivity lg = new LoginActivity();
    public String ip = lg.ip;
    private Context context;
    TextView board_title;
    Button team_board_vote;
    RadioButton Radio_part;
    RadioButton Radio_no_part;
    private int team_board_id;
    private String title;
    private String team_name;
    private int part_count;
    private int no_part_count;
    private int max_part_count;

    private String state;
    private String part_state;
    private String vote;

    private PartView partView;
    private NoPartView nopartView;
    public int id;
    private String now_id = lg.Myid;

    private LinearLayout part;
    private LinearLayout no_part;

    ArrayList<String> part_list = new ArrayList<String>();
    ArrayList<String> no_part_list = new ArrayList<String>();
    private int part_arr_size;
    private int no_part_arr_size;

    String[] part_memberItems;
    String[] no_part_memberItems;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private int disx;
    private int bar_width;
    private int cell_bar;

    private int part_bar_width;
    private int no_part_bar_width;

    public static FutSalTeam_Board_Item_Detail newInstance() {
        return new FutSalTeam_Board_Item_Detail();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_team_board_item_detail, null);

        context = container.getContext();

        board_title = (TextView) view.findViewById(R.id.board_title);
        team_board_vote = (Button) view.findViewById(R.id.team_board_vote);

        Radio_part = (RadioButton) view.findViewById(R.id.radio_part);
        Radio_no_part = (RadioButton) view.findViewById(R.id.radio_no_part);

        //part=(ImageView)view.findViewById(R.id.part_bar);
        part = (LinearLayout) view.findViewById(R.id.part_bar);
        no_part = (LinearLayout) view.findViewById(R.id.no_part_bar);

        team_board_id = getArguments().getInt("team_board_id");
        title = getArguments().getString("title");
        team_name = getArguments().getString("team_name");
        part_count = getArguments().getInt("part_count");
        no_part_count = getArguments().getInt("no_part_count");
        max_part_count = getArguments().getInt("max_part_count");
        System.out.println(title);
        board_title.setText(title);

        team_board_vote.setOnClickListener(this);
        partView = new PartView(context);
        nopartView = new NoPartView(context);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        disx = size.x;
        bar_width = disx-200;
        cell_bar = bar_width/max_part_count;
        new Get_state().execute(ip + "/team/team_board/state?team_board_id=" + team_board_id + "&user_id=" + now_id);

        part.addView(partView);
        no_part.addView(nopartView);
        part.setOnClickListener(this);
        no_part.setOnClickListener(this);

        new Get_Part().execute(ip+"/team/team_board/part_list?team_board_id="+team_board_id);
        new Get_NoPart().execute(ip+"/team/team_board/no_part_list?team_board_id="+team_board_id);
        return view;
    }

    class PartView extends View {
        public PartView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            Paint Pnt = new Paint();
            Pnt.setStyle(Paint.Style.FILL_AND_STROKE);
            Pnt.setARGB(255, 128, 128, 128);
            //비율설정해야함
            RectF all_rect = new RectF(100, 0, bar_width, 50);
            canvas.drawRect(all_rect, Pnt);

            Pnt.setARGB(255, 0, 210, 0);
            part_bar_width= cell_bar*part_count;
            if(max_part_count==part_count)
                part_bar_width = bar_width;
            System.out.println("ttt"+part_bar_width);
            RectF part_rect = new RectF(100, 0, part_bar_width+100, 50);
            canvas.drawRect(part_rect, Pnt);
        }
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(disx-100, 180);
        }
    }
    class NoPartView extends View {
        public NoPartView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            Paint Pnt = new Paint();
            Pnt.setStyle(Paint.Style.FILL_AND_STROKE);
            Pnt.setARGB(255, 128, 128, 128);
            RectF rect2 = new RectF(100, 0, bar_width, 50);

            canvas.drawRect(rect2, Pnt);

            Pnt.setARGB(255, 210, 0, 0);
            System.out.println("ttttttttt"+no_part_count);
            no_part_bar_width = cell_bar*no_part_count;
            if(max_part_count==no_part_count)
                no_part_bar_width = bar_width;

            RectF no_part_rect = new RectF(100, 0, no_part_bar_width+100, 50);
            canvas.drawRect(no_part_rect, Pnt);
        }
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            setMeasuredDimension(disx-100, 180);  // 뷰의 크기를 폭 2560, 높이 2560으로 강제로 지정
        }

    }

    public class Get_state extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
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
                        state = jsonObject.getString("state_info");
                        part_state = jsonObject.getString("part");
                        //System.out.println("state(yes or no)  : " + state+ "part(1 , 0 ) : "+ part_state);
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
            if (state.equals("yes")) {
                if (part_state.equals("1"))
                    Radio_part.setChecked(true);
                else
                    Radio_no_part.setChecked(true);
            }
        }
    }

    public class Get_Part extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
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
                        part_list.clear();
                        String part_arr = jsonObject.getString("part_list");
                        JSONArray ja = new JSONArray(part_arr);
                        part_arr_size = ja.length();
                        for(int i=0;i<part_arr_size;i++)
                        {
                            JSONObject ob = ja.getJSONObject(i);
                            part_list.add(ob.getString("user_id"));
                        }
                        //System.out.println("state(yes or no)  : " + state+ "part(1 , 0 ) : "+ part_state);
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
            if(part_arr_size!=0)
            {
                for( int i=0;i<part_arr_size;i++)
                {
                    part_memberItems = part_list.toArray(new String[part_list.size()]);
                    partView.invalidate();
                    nopartView.invalidate();
                }
            }
        }
    }

    public class Get_NoPart extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
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
                        no_part_list.clear();
                        String no_part_arr = jsonObject.getString("no_part_list");
                        JSONArray ja = new JSONArray(no_part_arr);
                        no_part_arr_size = ja.length();
                        for(int i=0;i<no_part_arr_size;i++)
                        {
                            JSONObject ob = ja.getJSONObject(i);
                            no_part_list.add(ob.getString("user_id"));
                        }

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
            if(no_part_arr_size!=0)
            {
                for( int i=0;i<no_part_arr_size;i++)
                {
                    no_part_memberItems = no_part_list.toArray(new String[no_part_list.size()]);
                    partView.invalidate();
                    nopartView.invalidate();
                }
            }
        }
    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("team_board_id", team_board_id);
                jsonObject.put("user_id", now_id);
                jsonObject.put("state", state);
                jsonObject.put("vote", vote);
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
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
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
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

            try {
                JSONObject jsonObject = new JSONObject(result);
                String msg = jsonObject.getString("result");

                if (msg.equals("Success")) {
                    Toast.makeText(context.getApplicationContext(), "투표 성공", Toast.LENGTH_SHORT).show();
                    partView.invalidate();
                    nopartView.invalidate();
                } else {
                    Toast.makeText(context.getApplicationContext(), "투표 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.back_btn:
                ((MainActivity) getActivity()).backFragment(FutSalSearchActivity.newInstance(), FutSalSearchListFragment.newInstance());
                break;
            case R.id.part_bar:
                part_list.clear();
                builder = new AlertDialog.Builder(context);
                builder.setTitle("참가자 리스트")
                        .setItems(part_memberItems, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int pos)
                            {
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int pos)
                            {
                                new Get_Part().execute(ip+"/team/team_board/part_list?team_board_id="+team_board_id);
                                new Get_NoPart().execute(ip+"/team/team_board/no_part_list?team_board_id="+team_board_id);
                                partView.invalidate();
                                nopartView.invalidate();
                            }
                        });
                dialog = builder.create();
                dialog.show();

                Toast.makeText(context.getApplicationContext(), "참가자", Toast.LENGTH_SHORT).show();
                break;
            case R.id.no_part_bar:
                no_part_list.clear();
                builder = new AlertDialog.Builder(context);
                builder.setTitle("불참가자 리스트")
                        .setItems(no_part_memberItems, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int pos)
                            {
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int pos)
                            {
                                new Get_Part().execute(ip+"/team/team_board/part_list?team_board_id="+team_board_id);
                                new Get_NoPart().execute(ip+"/team/team_board/no_part_list?team_board_id="+team_board_id);
                                partView.invalidate();
                                nopartView.invalidate();
                            }
                        });
                dialog = builder.create();
                dialog.show();
               // Toast.makeText(context.getApplicationContext(), "불참가자", Toast.LENGTH_SHORT).show();
                break;
            case R.id.team_board_vote:
                if (Radio_part.isChecked() || Radio_no_part.isChecked()) {
                    if (Radio_part.isChecked())
                        vote = "1";
                    else if (Radio_no_part.isChecked())
                        vote = "0";
                    new Post().execute(ip+"/team/team_board/vote");
                    new Get_Part().execute(ip+"/team/team_board/part_list?team_board_id="+team_board_id);
                    new Get_NoPart().execute(ip+"/team/team_board/no_part_list?team_board_id="+team_board_id);
                } else
                    Toast.makeText(context.getApplicationContext(), "참가, 불참 중 선택해주세요.", Toast.LENGTH_SHORT).show();
                partView.invalidate();
                nopartView.invalidate();
                break;
        }

    }

}
