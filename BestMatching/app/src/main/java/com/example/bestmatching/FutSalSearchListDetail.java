package com.example.bestmatching;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FutSalSearchListDetail extends Fragment implements View.OnClickListener {

    private FutsalSearchListAdapter futsalSearchListAdapter;
    private Context context;


    TextView test1;
    TextView test2;

    private int choice;

    public static FutSalSearchListDetail newInstance() {
        return new FutSalSearchListDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.activity_futsal_search_list_item_detail, null);

        context = container.getContext();
        futsalSearchListAdapter = new FutsalSearchListAdapter();

        test1 = (TextView)view.findViewById(R.id.test1);
        test2 = (TextView)view.findViewById(R.id.test2);


        Bundle bundle = getArguments();

        if (bundle != null) {
            String text = bundle.getString("text");
            test1.setText(text);
        }

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
