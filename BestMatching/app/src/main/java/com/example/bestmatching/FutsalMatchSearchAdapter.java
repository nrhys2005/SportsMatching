package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FutsalMatchSearchAdapter extends BaseAdapter {

    private TextView test1;
    private TextView test2;

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
            convertView = inflater.inflate(R.layout.activity_futsal_match_search_item, parent, false);
        }

        test1 = (TextView) convertView.findViewById(R.id.test1);
        test2 = (TextView) convertView.findViewById(R.id.test2);

        FutSalMatchSearchItems futSalMatchSearchItems = matchItems.get(position);

        test1.setText(futSalMatchSearchItems.getTitle());
        test2.setText(futSalMatchSearchItems.getText());

        LinearLayout layoutClick = (LinearLayout)convertView.findViewById(R.id.layoutClick);
        layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), matchItems.get(pos).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String title, String text){
        FutSalMatchSearchItems matchSearchItems = new FutSalMatchSearchItems();

        matchSearchItems.setTitle(title);
        matchSearchItems.setText(text);

        matchItems.add(matchSearchItems);
    }

}
