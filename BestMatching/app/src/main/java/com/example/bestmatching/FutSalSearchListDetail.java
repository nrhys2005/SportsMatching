package com.example.bestmatching;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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

public class FutSalSearchListDetail extends Fragment implements View.OnClickListener {

    LoginActivity lg = new LoginActivity();
    public String ip=lg.ip;
    private Context context;

    TextView detail_name;
    TextView detail_price;
    TextView book_date;
    TextView book_start_time;
    TextView book_end_time;
    ImageView detail_ground;

    HorizontalScrollView book_time_view;

    Button back_btn;
    Button book_btn;

    String name;
    String price;

    private DatePickerDialog.OnDateSetListener callbackMethod;
    private CustomTimePickerDialog.OnTimeSetListener start;
    private CustomTimePickerDialog.OnTimeSetListener end;

    ArrayList<String> start_time = new ArrayList<>(); //시간 ex)2020-10-21 21:00
    ArrayList<String> end_time = new ArrayList<>(); //시간 ex)2020-10-21 23:00

    ArrayList<Integer> start_hour = new ArrayList<>(); //시간 ex) 21
    ArrayList<Integer> end_hour = new ArrayList<>(); //시간 ex) 23

    private int TIME_PICKER_INTERVAL = 30;

    private int stuck = 10;
    private int timeinfoSize=0;

    private ColorView colorView;

    private int select_start=0;
    private int select_end=0;

    public static FutSalSearchListDetail newInstance() {
        return new FutSalSearchListDetail();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_search_list_item_detail, null);

        context = container.getContext();

        detail_name = (TextView)view.findViewById(R.id.detail_name);
        detail_price = (TextView)view.findViewById(R.id.detail_price);
        book_date = (TextView)view.findViewById(R.id.book_date);
        book_start_time = (TextView)view.findViewById(R.id.book_start_time);
        book_end_time = (TextView)view.findViewById(R.id.book_end_time);
        detail_ground = (ImageView)view.findViewById(R.id.detail_ground);
        book_time_view = (HorizontalScrollView)view.findViewById(R.id.book_time_view);

        name = getArguments().getString("name");
        price = getArguments().getString("price");

        detail_name.setText(name);
        detail_price.setText(price + "원");


        int id = getArguments().getInt("id");
        switch (id) {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sangju);
                detail_ground.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.daegu);
                detail_ground.setImageBitmap(bm2);
                break;
        }

        back_btn = (Button)view.findViewById(R.id.back_btn);
        book_btn = (Button)view.findViewById(R.id.book_btn);

        book_date.setOnClickListener(this);
        book_start_time.setOnClickListener(this);
        book_end_time.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        book_btn.setOnClickListener(this);
        book_date.setText(String.format("%d", 2020) + "-" + String.format("%02d", 10) + "-" + String.format("%02d", 21));//오늘 날짜 추가하는 기능 추가
        colorView = new ColorView(context);

        book_time_view.addView(colorView);
  
        new Get().execute(ip + "/ground/timecheck/2020-10-21");


        BootpayAnalytics.init(context, "5f6c1743878a56001dffad61");

        return view;
    }

    class ColorView extends View {
        public ColorView(Context context){
            super(context);
        }
//        public void invalidate()
//        {
//            invalidate();
//        }

        public void onDraw(Canvas canvas){ // 캔버스는 뷰의 그리기 표면이며 이 위에 그림을 그린다.
            Paint Pnt = new Paint();
            if(book_start_time.equals(""))
            {
                book_end_time.setText("");
                select_start=0;
                select_end=0;
            }
            for(int x=0; x<1920; x+=80){

                Pnt.setStyle(Paint.Style.FILL); //선만있는 사각형 // Paint 객체 생성
                Pnt.setARGB(255, 0, 0, 0);  // 색상 정하기
                RectF rect=new RectF(x,0,x+70,100); //(시작X,시작Y,끝X,끝y) 사각형
//
//                if(x>=80 && x<=150) {
//                    Pnt.setARGB(255, 0, 255, 0);  // 색상 정하기
//                    Pnt.setStyle(Paint.Style.FILL_AND_STROKE); //선만있는 사각형 // Paint 객체 생성
//                }

                Pnt.setStrokeWidth(3f);
                canvas.drawRect(rect, Pnt);     // 모서리둥근사각형메서드 그리기 ( 사각형 좌표,가로둥글기,세로둥글기,paint ) ;
            }
            if(start_hour.size()!=0) {
                for (int i = 0; i < start_hour.size(); i++) {
                    Pnt.setStyle(Paint.Style.FILL); //선만있는 사각형 // Paint 객체 생성
                    Pnt.setARGB(255, 255, 0, 0);  // 색상 정하기
                    int time_interval=end_hour.get(i)-start_hour.get(i);
                    for(int d=0;d<time_interval;d++) {
                        RectF rect = new RectF(((start_hour.get(i)+d )* 80), 0, ((start_hour.get(i)+d) * 80)+70, 100); //(시작X,시작Y,끝X,끝y) 사각형
                        Pnt.setStrokeWidth(3f);
                        canvas.drawRect(rect, Pnt);     // 모서리둥근사각형메서드 그리기 ( 사각형 좌표,가로둥글기,세로둥글기,paint ) ;
                    }
//                    System.out.println("시작시간 " + start_hour.get(i));
//                    System.out.println("종료시간 " + end_hour.get(i));
                }
            }

            int selcet_inerval=select_end-select_start;
           // System.out.println("st"+select_start+"se"+select_end);
            for(int d=0;d<selcet_inerval;d++)
            {

                Pnt.setStyle(Paint.Style.FILL); //선만있는 사각형 // Paint 객체 생성
                Pnt.setARGB(255, 0, 255, 0);  // 색상 정하기
                RectF rect = new RectF(((select_start+d )* 80), 0, ((select_start+d) * 80)+70, 100); //(시작X,시작Y,끝X,끝y) 사각형
                Pnt.setStrokeWidth(3f);
                canvas.drawRect(rect, Pnt);
            }
            for(int x=0; x<1920; x+=80) {
                int i = x/80;
                //String t = Integer.toString(i);
                String t = String.format("%02d", i);

                Paint paint = new Paint();
                paint.setARGB(255, 0, 0, 0);
                paint.setTextSize(35);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.NORMAL));
                canvas.drawText(t, x-3, 150, paint);
            }
        }

        protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec){
            setMeasuredDimension(1920, 180);  // 뷰의 크기를 폭 2560, 높이 2560으로 강제로 지정
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

                    if (msg.equals("Success")) {
                        start_time.clear();
                        end_time.clear();
                        start_hour.clear();
                        end_hour.clear();
                        String time_info = jsonObject.getString("time_info");
                        JSONArray jsonArray = new JSONArray(time_info);

                        timeinfoSize = jsonArray.length();

                        for (int i = 0; i < timeinfoSize; i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            start_time.add(js.getString("start_time"));
                            end_time.add(js.getString("end_time"));



                        }
                    } else if (msg.equals("no find")) {
                        timeinfoSize = 0;
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

            if (timeinfoSize != 0) {
                for (int i = 0; i < timeinfoSize; i++) {

                    String[] stime = start_time.get(i).split(" ");
                    String starthour = stime[1];
                    String[] shour = starthour.split(":");
                    start_hour.add(Integer.parseInt(shour[0]));

                    String[] etime = end_time.get(i).split(" ");
                    String endhour = etime[1];
                    String[] ehour = endhour.split(":");
                    end_hour.add(Integer.parseInt(ehour[0]));
                }

            }

            else {

            }
            colorView.invalidate();
        }

    }


    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();

                //jsonObject.put("title", match_title.getText().toString());
                //jsonObject.put("ground_name", select_stadium.getText().toString());
                //jsonObject.put("date", match_date.getText().toString());
                //jsonObject.put("start_time", match_start_time.getText().toString());
                //jsonObject.put("end_time", match_end_time.getText().toString());
                //jsonObject.put("cost", match_cost.getText().toString());
                //jsonObject.put("max_user", match_max_user.getText().toString());
                //jsonObject.put("user_id", now_id);


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
                    Toast.makeText(context.getApplicationContext(), "예약 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "예약 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    //결제 api
    public void kakao(){
        BootUser bootUser = new BootUser().setPhone("010-1234-5678");
        BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});

        Bootpay.init(getActivity().getFragmentManager())
                .setApplicationId("5f6c1743878a56001dffad61") // 해당 프로젝트(안드로이드)의 application id 값
                .setPG(PG.KCP) // 결제할 PG 사
                .setMethod(Method.KAKAO) // 결제수단
                .setContext(context)
                .setBootUser(bootUser)
                .setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
                .setName(name) // 결제할 상품명
                .setOrderId("1234") // 결제 고유번호
                .setPrice(Integer.parseInt(price)) // 결제할 금액
                //.addItem("마우스", 1, "ITEM_CODE_MOUSE", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                //.addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                    @Override
                    public void onConfirm(@Nullable String message) {
                        if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                        else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                        Log.d("confirm", message);
                    }
                })
                .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                    @Override
                    public void onDone(@Nullable String message) {
                        Log.d("done", message);
                    }
                })
                .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    @Override
                    public void onReady(@Nullable String message) {
                        Log.d("ready", message);
                    }
                })
                .onCancel(new CancelListener() { // 결제 취소시 호출
                    @Override
                    public void onCancel(@Nullable String message) {

                        Log.d("cancel", message);
                    }
                })
                .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                    @Override
                    public void onError(@Nullable String message) {
                        Log.d("error", message);
                    }
                })
                .onClose(
                        new CloseListener() { //결제창이 닫힐때 실행되는 부분
                            @Override
                            public void onClose(String message) {
                                Log.d("close", "close");
                            }
                        })
                .request();
    }

    private boolean mIgnoreEvent=false;

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.back_btn:
                ((MainActivity)getActivity()).backFragment(FutSalSearchActivity.newInstance(), FutSalSearchListFragment.newInstance());
                break;

            case R.id.book_date:
                callbackMethod = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //year,month,dayOfMont
                        book_date.setText(String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth));
                        new Get().execute(ip + "/ground/timecheck/"+String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth));

                    }
                };

                DatePickerDialog d = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, callbackMethod, 2020, 9, 1);
                d.show();
                break;

            //시작시간 버튼
            case R.id.book_start_time:
                select_end=0;
                select_start=0;
                start = new CustomTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        book_start_time.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        select_start=hourOfDay;
                    }
                };// using CustomTimePickerDialog
                CustomTimePickerDialog t1 = new CustomTimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, start, 12,00, true);

                t1.setTitle("시작시간");
                t1.show();
                break;

            //종료시간 버튼
            case R.id.book_end_time:
                end = new CustomTimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        book_end_time.setText(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
                        select_end=hourOfDay;
                        for(int i=0;i<start_hour.size();i++)
                            if((start_hour.get(i)<select_start&&end_hour.get(i)>select_start)||(start_hour.get(i)<select_end&&end_hour.get(i)>=select_end)||(select_start<=start_hour.get(i)&&select_end>end_hour.get(i)))
                            {
                                Toast.makeText(context.getApplicationContext(), "이미 예약되어 있습니다. 다시 선택해 주세요.", Toast.LENGTH_SHORT).show();

                                select_start=0;
                                select_end=0;
                                book_start_time.setText("");
                                book_end_time.setText("");
                            }
                        colorView.invalidate();
                    }
                };

                CustomTimePickerDialog t2 = new CustomTimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, end, 12,00, true);
                t2.setTitle("종료시간");
                t2.show();
                break;

            //예약 버튼
            case R.id.book_btn:
                kakao();
                break;
        }

    }

}
