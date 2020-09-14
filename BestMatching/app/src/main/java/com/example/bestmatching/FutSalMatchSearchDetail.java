package com.example.bestmatching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FutSalMatchSearchDetail extends Fragment implements View.OnClickListener {

    private Context context;

    TextView detail_match_title;

    public static FutSalMatchSearchDetail newInstance() {
        return new FutSalMatchSearchDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_match_search_item_detail, null);

        context = container.getContext();

        detail_match_title = (TextView)view.findViewById(R.id.detail_match_title);

        String name = getArguments().getString("title");
        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_match_title.setText(name);

        /*int id = getArguments().getInt("id");
        switch (id) {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sangju);
                detail_ground.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.daegu);
                detail_ground.setImageBitmap(bm2);
                break;
        }*/


        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
