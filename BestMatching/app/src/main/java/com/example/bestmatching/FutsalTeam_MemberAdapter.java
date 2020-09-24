package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalTeam_MemberAdapter extends BaseAdapter implements View.OnClickListener {

    private TextView mem_id;
    private TextView mem_name;
    private TextView mem_age;
    private TextView mem_loc;
    private TextView mem_phone;
    private TextView mem_position;


    //private TextView content;

    public LinearLayout search_list_click;

    MainActivity ma = new MainActivity();

    public ArrayList<FutsalTeam_MemberItems> listItems = new ArrayList<FutsalTeam_MemberItems>();

    public FutsalTeam_MemberAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return listItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listItems.get(position);
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
            convertView = inflater.inflate(R.layout.fragment_futsal_team_member_item, parent, false);
        }

        mem_id = (TextView) convertView.findViewById(R.id.m_id);
        mem_name = (TextView) convertView.findViewById(R.id.m_name);
        mem_age = (TextView) convertView.findViewById(R.id.m_age);
        mem_loc = (TextView) convertView.findViewById(R.id.m_location);
        mem_phone = (TextView) convertView.findViewById(R.id.m_phonenumber);
        mem_position = (TextView) convertView.findViewById(R.id.m_position);

       // content = (TextView) convertView.findViewById(R.id.content);

        FutsalTeam_MemberItems futsalTeamMemberItems = listItems.get(position);

        mem_id.setText(futsalTeamMemberItems.getId());
        mem_name.setText(futsalTeamMemberItems.getName());
        mem_age.setText(futsalTeamMemberItems.getAge());
        mem_loc.setText(futsalTeamMemberItems.getLocation());
        mem_phone.setText(futsalTeamMemberItems.getPhonenumberStr());
        mem_position.setText(futsalTeamMemberItems.getPositionStr());

        if(mem_age.getText().toString().equals("null")||mem_age.getText().toString().equals(""))
            mem_age.setText("정보없음");
        if(mem_loc.getText().toString().equals("null")||mem_loc.getText().toString().equals(""))
            mem_loc.setText("정보없음");
        if(mem_phone.getText().toString().equals("null")||mem_phone.getText().toString().equals(""))
            mem_phone.setText("정보없음");
        if(mem_position.getText().toString().equals("null")||mem_position.getText().toString().equals(""))
            mem_position.setText("정보없음");


        //content.setText(futSalHelp_noticeItems.getContentStrt());

     //   search_list_click = (LinearLayout)convertView.findViewById(R.id.search_list_click);

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String id,String name, String age, String location, String phonenumber, String position){
        FutsalTeam_MemberItems memberItems = new FutsalTeam_MemberItems();

        memberItems.setId(id);
        memberItems.setName(name);
        memberItems.setAge(age);
        memberItems.setLocation(location);
        memberItems.setPhonenumber(phonenumber);
        memberItems.setPosition(position);

        listItems.add(memberItems);
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch(a) {
            case R.id.search_list_click:
                /*FutSalSearchListDetail f = new FutSalSearchListDetail();
                ma.replaceFragment(FutSalSearchActivity.newInstance(), f);*/
                break;
        }
    }
}
