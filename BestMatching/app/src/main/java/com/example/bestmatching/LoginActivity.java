package com.example.bestmatching;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText TextInputEditText_id;
    TextInputEditText TextInputEditText_password;
    Button sign_btn;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText_id = findViewById(R.id.TextInputEditText_id);
        TextInputEditText_password = findViewById(R.id.TextInputEditText_password);

        Button sign_btn = findViewById(R.id.sign_btn);
        Button login_btn = findViewById(R.id.login_btn);

        //////////////////////////////////////////////////////////////////////////////////

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = TextInputEditText_id.getText().toString();
                String password = TextInputEditText_password.getText().toString();

                Intent intent = new Intent(LoginActivity.this, LoginResultActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("password",password);
                startActivity(intent);
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////

        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getApplicationContext(),"회원가입 버튼 눌림",Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(LoginActivity.this,SignActivity.class);
                startActivity(intent);
            }
        });
    }
}
