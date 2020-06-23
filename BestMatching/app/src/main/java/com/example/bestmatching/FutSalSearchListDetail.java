package com.example.bestmatching;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    TextView test1;
    TextView test2;

    public static FutSalSearchListDetail newInstance() {
        return new FutSalSearchListDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_list_item_detail, null);

        context = container.getContext();

        test1 = (TextView)view.findViewById(R.id.test1);
        test2 = (TextView)view.findViewById(R.id.test2);

        String name = getArguments().getString("name");
        String price = getArguments().getString("price");
        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        test1.setText(name);
        test2.setText(price);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
