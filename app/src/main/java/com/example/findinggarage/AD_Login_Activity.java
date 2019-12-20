package com.example.findinggarage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AD_Login_Activity extends AppCompatActivity {
    private Button log,ba;
    private TextView u,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad__login_);
        log = (Button) findViewById(R.id.butLog);
        ba = (Button) findViewById(R.id.butBack);
        u = (TextView) findViewById(R.id.textName);
        p = (TextView) findViewById(R.id.textId);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(u.getText().toString().trim().equals("admin")&&p.getText().toString().trim().equals("1234")){
                    Intent i = new Intent(AD_Login_Activity.this, Insert_Activity.class);
                    startActivity(i);
                    Toast.makeText(AD_Login_Activity.this,"Welcome Boss",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AD_Login_Activity.this,"Uncorrect",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
