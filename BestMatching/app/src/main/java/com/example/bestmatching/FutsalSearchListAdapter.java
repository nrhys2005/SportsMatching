package com.example.bestmatching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FutsalSearchListAdapter extends BaseAdapter {

    private TextView list_stadium_name;
    private TextView list_price;
    private ImageView list_ground_id;

    public LinearLayout search_list_click;

    MainActivity ma = new MainActivity();

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
        list_ground_id = (ImageView) convertView.findViewById(R.id.list_ground_id);

        FutSalSearchListItems futSalSearchListItems = listItems.get(position);

        list_stadium_name.setText(futSalSearchListItems.getStadium());
        list_price.setText(futSalSearchListItems.getPrice());

        switch (futSalSearchListItems.getId())
        {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.sangju);
                list_ground_id.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.background);
                list_ground_id.setImageBitmap(bm2);
                break;
            default:
                Bitmap bm3 = BitmapFactory.decodeResource(convertView.getResources(), R.drawable.sangju);
                list_ground_id.setImageBitmap(bm3);
                break;
        }




        search_list_click = (LinearLayout)convertView.findViewById(R.id.search_list_click);
        search_list_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FutSalSearchListDetail f = new FutSalSearchListDetail();
                ma.replaceFragment(FutSalSearchActivity.newInstance(), f);*/
            }
        });

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String stadium_name, String prcie, int id){
        FutSalSearchListItems searchListItems = new FutSalSearchListItems();

        searchListItems.setStadium(stadium_name);
        searchListItems.setPrice(prcie);
        searchListItems.setId(id);

        listItems.add(searchListItems);
    }

}
