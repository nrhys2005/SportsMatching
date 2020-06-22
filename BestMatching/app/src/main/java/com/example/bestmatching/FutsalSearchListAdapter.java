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

public class FutsalSearchListAdapter extends BaseAdapter {

    private TextView list_stadium_name;
    private TextView list_price;


    public ArrayList<FutSalSearchListItems> listItems = new ArrayList<FutSalSearchListItems>();

    public FutsalSearchListAdapter() {

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
            convertView = inflater.inflate(R.layout.activity_futsal_search_list_item, parent, false);
        }

        list_stadium_name = (TextView) convertView.findViewById(R.id.list_stadium_name);
        list_price = (TextView) convertView.findViewById(R.id.list_price);

        FutSalSearchListItems futSalSearchListItems = listItems.get(position);

        list_stadium_name.setText(futSalSearchListItems.getStadium());
        list_price.setText(futSalSearchListItems.getPrice());

        LinearLayout search_list_click = (LinearLayout)convertView.findViewById(R.id.search_list_click);
        search_list_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), listItems.get(pos).getPrice(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String stadium_name, String prcie){
        FutSalSearchListItems searchListItems = new FutSalSearchListItems();

        searchListItems.setStadium(stadium_name);
        searchListItems.setPrice(prcie);

        listItems.add(searchListItems);
    }

}
