package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalTeamMatchSearchAdapter extends BaseAdapter {

    private TextView team_match_search_title;
    private TextView team_match_search_ground;
    private TextView team_match_search_start_time;
    private TextView team_match_search_end_time;
    private TextView team_match_search_participants;
    private TextView team_match_search_max_user;

    public LinearLayout team_match_list_click;

    private ArrayList<FutSalTeamMatchSearchItems> teammatchItems = new ArrayList<FutSalTeamMatchSearchItems>();

    public FutsalTeamMatchSearchAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return teammatchItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return teammatchItems.get(position);
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
            convertView = inflater.inflate(R.layout.fragment_futsal_team_match_search_item, parent, false);
        }

        team_match_search_title = (TextView) convertView.findViewById(R.id.team_match_search_title);
        team_match_search_ground = (TextView) convertView.findViewById(R.id.team_match_search_ground);
        team_match_search_start_time = (TextView) convertView.findViewById(R.id.team_match_search_start_time);
        team_match_search_end_time = (TextView) convertView.findViewById(R.id.team_match_search_end_time);
        team_match_search_participants = (TextView) convertView.findViewById(R.id.team_match_search_participants);
        team_match_search_max_user = (TextView) convertView.findViewById(R.id.team_match_search_max_user);

        FutSalTeamMatchSearchItems futSalTeamMatchSearchItems = teammatchItems.get(position);

        team_match_search_title.setText(futSalTeamMatchSearchItems.getTeamTitle());
        team_match_search_ground.setText(futSalTeamMatchSearchItems.getTeamGround());
        team_match_search_start_time.setText(futSalTeamMatchSearchItems.getTeamStartTime());
        team_match_search_end_time.setText(futSalTeamMatchSearchItems.getTeamEndTime());
        team_match_search_participants.setText(futSalTeamMatchSearchItems.getTeamParticipants());
        team_match_search_max_user.setText(futSalTeamMatchSearchItems.getTeamMaxUser());

        team_match_list_click = (LinearLayout)convertView.findViewById(R.id.team_match_list_click);

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String title, String ground, String start_time, String end_time, String participants, String max_user){
        FutSalTeamMatchSearchItems matchTeamSearchItems = new FutSalTeamMatchSearchItems();

        matchTeamSearchItems.setTeamTitle(title);
        matchTeamSearchItems.setTeamGround(ground);
        matchTeamSearchItems.setTeamStartTime(start_time);
        matchTeamSearchItems.setTeamEndtime(end_time);
        matchTeamSearchItems.setTeamParticipants(participants);
        matchTeamSearchItems.setTeamMaxUser(max_user);

        teammatchItems.add(matchTeamSearchItems);
    }

    public void clearItem(){
        teammatchItems.clear();
    }

    public void clearItem(int i) { teammatchItems.remove(i); }


}
