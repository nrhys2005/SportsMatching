package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalMatchSearchPartAdapter extends BaseAdapter {

    private TextView part_name;
    private TextView part_age;
    private TextView part_location;
    private TextView part_pos;
    private TextView part_email;
    private TextView part_phone;

    private ArrayList<FutSalMatchSearchPartItems> matchPartItems = new ArrayList<FutSalMatchSearchPartItems>();

    public FutsalMatchSearchPartAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return matchPartItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return matchPartItems.get(position);
    }


    //지정한 포지션에 있는 데이터와 관계된 아이템의 id리턴
    @Override
    public long getItemId(int position) {
        return position;
    }


    // 포지션에 위치한 데이터를 화면에 출력하는데 사용될 뷰를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_futsal_match_search_part_item, parent, false);
        }

        part_name = (TextView) convertView.findViewById(R.id.part_name);
        part_age = (TextView) convertView.findViewById(R.id.part_age);
        part_location = (TextView) convertView.findViewById(R.id.part_location);
        part_pos = (TextView) convertView.findViewById(R.id.part_pos);
        part_email = (TextView) convertView.findViewById(R.id.part_email);
        part_phone = (TextView) convertView.findViewById(R.id.part_phone);

        FutSalMatchSearchPartItems futSalMatchSearchPartItems = matchPartItems.get(position);

        part_name.setText(futSalMatchSearchPartItems.getName());
        part_age.setText(futSalMatchSearchPartItems.getAge());
        part_location.setText(futSalMatchSearchPartItems.getLoc());
        part_pos.setText(futSalMatchSearchPartItems.getPos());
        part_email.setText(futSalMatchSearchPartItems.getEmail());
        part_phone.setText(futSalMatchSearchPartItems.getPhone());

        if (part_name.getText().toString().equals("null") || part_name.getText().toString().equals(""))
            part_name.setText("정보없음");

        if (part_age.getText().toString().equals("null") || part_age.getText().toString().equals(""))
            part_age.setText("정보없음");

        if (part_location.getText().toString().equals("null") || part_location.getText().toString().equals(""))
            part_location.setText("정보없음");

        if (part_pos.getText().toString().equals("null") || part_pos.getText().toString().equals(""))
            part_pos.setText("정보없음");

        if (part_email.getText().toString().equals("null") || part_email.getText().toString().equals(""))
            part_email.setText("정보없음");

        if (part_phone.getText().toString().equals("null") || part_phone.getText().toString().equals(""))
            part_phone.setText("정보없음");

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String name, String age, String location, String pos, String email, String phone){
        FutSalMatchSearchPartItems matchSearchPartItems = new FutSalMatchSearchPartItems();

        matchSearchPartItems.setName(name);
        matchSearchPartItems.setAge(age);
        matchSearchPartItems.setLoc(location);
        matchSearchPartItems.setPos(pos);
        matchSearchPartItems.setEmail(email);
        matchSearchPartItems.setPhone(phone);

        matchPartItems.add(matchSearchPartItems);
    }

    public void clearItem(){
        matchPartItems.clear();
    }


}
