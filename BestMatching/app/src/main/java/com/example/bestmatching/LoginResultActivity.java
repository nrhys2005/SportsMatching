package com.example.bestmatching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginResultActivity extends AppCompatActivity {

    TextView TextView_get;

    Button football_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

/*    값확인용    TextView_get = findViewById(R.id.TextView_get);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String id = bundle.getString("id");
        String password = bundle.getString("password");

        TextView_get.setText(id + " / " + password);*/

        football_btn = findViewById(R.id.football_btn);
        football_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginResultActivity.this,FootBallActivity.class);
                startActivity(intent);
            }
        });


    }
}
