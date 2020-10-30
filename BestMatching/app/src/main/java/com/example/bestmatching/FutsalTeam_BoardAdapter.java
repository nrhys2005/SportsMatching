package com.example.bestmatching;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FutsalTeam_BoardAdapter extends BaseAdapter implements View.OnClickListener {



    private TextView team_board_title;
    private TextView team_board_time;


    public ArrayList<FutSalTeam_BoardItems> boardItems = new ArrayList<FutSalTeam_BoardItems>();

    public FutsalTeam_BoardAdapter() {

    }


    // 어댑터에 사용되는 데이터 개수 리턴
    @Override
    public int getCount() {
        return boardItems.size();
    }


    //지정한 포지션에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return boardItems.get(position);
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
            convertView = inflater.inflate(R.layout.fragment_futsal_team_board_item, parent, false);
        }

        team_board_title = (TextView) convertView.findViewById(R.id.team_board_title);
        //team_board_time = (TextView) convertView.findViewById(R.id.team_board_time);
        FutSalTeam_BoardItems futSalTeam_BoardItems = boardItems.get(position);

        team_board_title.setText(futSalTeam_BoardItems.getTitleStr());
     //   team_board_time.setText(futSalTeam_BoardItems.getTimeStr());

        return convertView;
    }


    //아이템 데이터 추가를 위한 함수
    public void addItem( String title){
        FutSalTeam_BoardItems teamBoardItems = new FutSalTeam_BoardItems();

        teamBoardItems.setTitle(title);
      //  teamBoardItems.setTime(time);

        boardItems.add(teamBoardItems);
    }

    @Override
    public void onClick(View v) {

    }
}
