package com.example.myfirstapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeStudent extends AppCompatActivity {
    Button btnLogOut;
    String userName;
    TextView tvUserName;
    //private Spinner snUserType;
    //FirebaseDatabase database;
    //DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        //database = FirebaseDatabase.getInstance();
        //users = database.getReference("Users");
        userName = getIntent().getExtras().getString("user");
        Mapping();
        ControllButton();
        tvUserName.setText(userName);
    }

    private void ControllButton() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeStudent.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Đăng xuất?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(true);//default
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainActivity = new Intent(HomeStudent.this,MainActivity.class);
                        startActivity(mainActivity);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void Mapping() {
        tvUserName = (TextView) findViewById(R.id.tvName);
        btnLogOut = (Button)findViewById(R.id.buttonLogOut);
    }
}
