package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FutsalTeamSearchAdapter extends BaseAdapter {

    private TextView team_search_name;
    private TextView team_search_master;
    private TextView team_search_phone;
    private TextView team_search_location;
    private TextView team_search_week;

    public LinearLayout team_list_click;

    private ArrayList<FutSalTeamSearchItems> teamItems = new ArrayList<FutSalTeamSearchItems>();

    public FutsalTeamSearchAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return teamItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return teamItems.get(position);
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
            convertView = inflater.inflate(R.layout.activity_futsal_team_search_item, parent, false);
        }

        team_search_name = (TextView) convertView.findViewById(R.id.team_search_name);
        team_search_master = (TextView) convertView.findViewById(R.id.team_search_master);
        team_search_phone = (TextView) convertView.findViewById(R.id.team_search_phone);
        team_search_location = (TextView) convertView.findViewById(R.id.team_search_loacation);
        team_search_week = (TextView) convertView.findViewById(R.id.team_search_week);

        FutSalTeamSearchItems futSalTeamSearchItems = teamItems.get(position);

        team_search_name.setText(futSalTeamSearchItems.getTitle());
        team_search_master.setText(futSalTeamSearchItems.getMaster());
        team_search_phone.setText(futSalTeamSearchItems.getPhone());
        team_search_location.setText(futSalTeamSearchItems.getLocation());
        team_search_week.setText(futSalTeamSearchItems.getWeek());

        team_list_click = (LinearLayout)convertView.findViewById(R.id.team_list_click);

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String title, String master,String phone, String location, String week){
        FutSalTeamSearchItems teamSearchItems = new FutSalTeamSearchItems();

        teamSearchItems.setTitle(title);
        teamSearchItems.setMaster(master);
        teamSearchItems.setPhone(phone);
        teamSearchItems.setLocation(location);
        teamSearchItems.setWeek(week);

        teamItems.add(teamSearchItems);
    }

    public void clearItem(){
        teamItems.clear();
    }

}
