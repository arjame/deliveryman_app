package com.example.delivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Click on Not yet a rider?
        TextView txtNewRider =findViewById(R.id.txtNewRider);
        txtNewRider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(LoginActivity.this,ApplyHelpActivity.class);
                startActivity(intent);
            }
        });
        // End
    }
}
