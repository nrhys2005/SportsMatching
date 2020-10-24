package com.example.bestmatching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalSearchBookAdapter extends BaseAdapter {

    private TextView my_ground_name;
    private TextView my_price;
    private TextView my_start_time;
    private TextView my_end_time;
    private ImageView my_ground_id;

    private ArrayList<FutSalSearchBookItems> mybookItems = new ArrayList<FutSalSearchBookItems>();

    public FutsalSearchBookAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return mybookItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return mybookItems.get(position);
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
            convertView = inflater.inflate(R.layout.fragment_futsal_search_my_book_item, parent, false);
        }

        my_ground_name = (TextView) convertView.findViewById(R.id.my_ground_name);
        my_price = (TextView) convertView.findViewById(R.id.my_price);
        my_start_time = (TextView) convertView.findViewById(R.id.my_start_time);
        my_end_time = (TextView) convertView.findViewById(R.id.my_end_time);
        my_ground_id = (ImageView)convertView.findViewById(R.id.my_ground_id);

        FutSalSearchBookItems futSalSearchBookItems = mybookItems.get(position);

        my_ground_name.setText(futSalSearchBookItems.getGroundName());
        my_price.setText(futSalSearchBookItems.getPrcie());
        my_start_time.setText(futSalSearchBookItems.getStartTime());
        my_end_time.setText(futSalSearchBookItems.getEndTime());

        switch (futSalSearchBookItems.getId())
        {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.sangju);
                my_ground_id.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.daegu);
                my_ground_id.setImageBitmap(bm2);
                break;
            default:
                Bitmap bm3 = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.sangju);
                my_ground_id.setImageBitmap(bm3);
                break;
        }

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String groundName, String price, String start_time, String end_time, int groundID){
        FutSalSearchBookItems mybookingItems = new FutSalSearchBookItems();

        mybookingItems.setGroundName(groundName);
        mybookingItems.setPrcie(price);
        mybookingItems.setStartTime(start_time);
        mybookingItems.setEndtime(end_time);
        mybookingItems.setId(groundID);

        mybookItems.add(mybookingItems);
    }

    public void clearItem(){
        mybookItems.clear();
    }


}
