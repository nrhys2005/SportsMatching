package com.example.bestmatching;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginResultActivity extends AppCompatActivity {

    TextView TextView_get;

    Button futsal_btn;

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

        futsal_btn = findViewById(R.id.futsal_btn);
        futsal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
