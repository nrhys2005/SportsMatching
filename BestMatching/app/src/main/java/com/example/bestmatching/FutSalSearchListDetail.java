package com.example.bestmatching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FutSalSearchListDetail extends Fragment implements View.OnClickListener {

    private Context context;

    TextView detail_name;
    TextView detail_price;
    ImageView detail_ground;

    public static FutSalSearchListDetail newInstance() {
        return new FutSalSearchListDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_list_item_detail, null);

        context = container.getContext();

        detail_name = (TextView)view.findViewById(R.id.detail_name);
        detail_price = (TextView)view.findViewById(R.id.detail_price);
        detail_ground = (ImageView)view.findViewById(R.id.detail_ground);


        String name = getArguments().getString("name");
        String price = getArguments().getString("price");
        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_name.setText(name);
        detail_price.setText(price + "원");

        int id = getArguments().getInt("id");
        switch (id) {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sangju);
                detail_ground.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.daegu);
                detail_ground.setImageBitmap(bm2);
                break;
        }


        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
