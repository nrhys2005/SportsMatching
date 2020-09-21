package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalMatchSearchAdapter extends BaseAdapter {

    private TextView match_search_title;
    private TextView match_search_ground;
    private TextView match_search_date;
    private TextView match_search_start_time;
    private TextView match_search_end_time;
    private TextView match_search_max_user;

    public LinearLayout match_list_click;

    private ArrayList<FutSalMatchSearchItems> matchItems = new ArrayList<FutSalMatchSearchItems>();

    public FutsalMatchSearchAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return matchItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return matchItems.get(position);
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
            convertView = inflater.inflate(R.layout.fragment_futsal_match_search_item, parent, false);
        }

        match_search_title = (TextView) convertView.findViewById(R.id.match_search_title);
        match_search_ground = (TextView) convertView.findViewById(R.id.match_search_ground);
        match_search_date = (TextView) convertView.findViewById(R.id.match_search_date);
        match_search_start_time = (TextView) convertView.findViewById(R.id.match_search_start_time);
        match_search_end_time = (TextView) convertView.findViewById(R.id.match_search_end_time);
        match_search_max_user = (TextView) convertView.findViewById(R.id.match_search_max_user);

        FutSalMatchSearchItems futSalMatchSearchItems = matchItems.get(position);

        match_search_title.setText(futSalMatchSearchItems.getTitle());
        match_search_ground.setText(futSalMatchSearchItems.getGround());
        match_search_date.setText(futSalMatchSearchItems.getDate());
        match_search_start_time.setText(futSalMatchSearchItems.getStartTime());
        match_search_end_time.setText(futSalMatchSearchItems.getEndTime());
        match_search_max_user.setText(futSalMatchSearchItems.getMaxUser());

        match_list_click = (LinearLayout)convertView.findViewById(R.id.match_list_click);

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String title, String ground, String date, String start_time, String end_time, String max_user){
        FutSalMatchSearchItems matchSearchItems = new FutSalMatchSearchItems();

        matchSearchItems.setTitle(title);
        matchSearchItems.setGround(ground);
        matchSearchItems.setDate(date);
        matchSearchItems.setStartTime(start_time);
        matchSearchItems.setEndtime(end_time);
        matchSearchItems.setMaxUser(max_user);

        matchItems.add(matchSearchItems);
    }

    public void clearItem(){
        matchItems.clear();
    }


}
