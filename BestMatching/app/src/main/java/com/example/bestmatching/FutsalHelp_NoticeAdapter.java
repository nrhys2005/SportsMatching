package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalHelp_NoticeAdapter extends BaseAdapter implements View.OnClickListener {

    private TextView category;
    private TextView title;
    //private TextView content;

    public LinearLayout search_list_click;

    MainActivity ma = new MainActivity();

    public ArrayList<FutSalHelp_NoticeItems> listItems = new ArrayList<FutSalHelp_NoticeItems>();

    public FutsalHelp_NoticeAdapter() {

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
            convertView = inflater.inflate(R.layout.fragment_futsal_help_notice_item, parent, false);
        }

        category = (TextView) convertView.findViewById(R.id.category);
        title = (TextView) convertView.findViewById(R.id.title);
       // content = (TextView) convertView.findViewById(R.id.content);

        FutSalHelp_NoticeItems futSalHelp_noticeItems = listItems.get(position);

        category.setText(futSalHelp_noticeItems.getCategoryStr());
        title.setText(futSalHelp_noticeItems.gettitleStr());
        //content.setText(futSalHelp_noticeItems.getContentStrt());

     //   search_list_click = (LinearLayout)convertView.findViewById(R.id.search_list_click);

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem(String category, String title, int id){
        FutSalHelp_NoticeItems noticeItems = new FutSalHelp_NoticeItems();

        noticeItems.setCategory(category);
        noticeItems.settitle(title);
       // noticeItems.setcontent(content);
        noticeItems.setId(id);

        listItems.add(noticeItems);
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
