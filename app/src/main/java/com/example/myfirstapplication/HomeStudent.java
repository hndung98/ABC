package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapplication.Model.Bank;
import com.example.myfirstapplication.Model.Test;
import com.example.myfirstapplication.ViewHolder.DoTestActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeStudent extends AppCompatActivity {
    Button btnLogOut, btnEnterRoom;
    String userName, testName, password;
    TextView tvUserName;
    EditText edtTestName, edtPassword;
    //private Spinner snUserType;
    FirebaseDatabase database;
    DatabaseReference users, tests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        database = FirebaseDatabase.getInstance();
        tests = database.getReference("Tests");
        //users = database.getReference("Users");
        userName = getIntent().getExtras().getString("user");
        Mapping();
        ControllButton();
        tvUserName.setText(userName);
    }

    private void ControllButton() {
        btnEnterRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomeStudent.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.enter_room_layout);
                dialog.show();
                Button btnEnter = (Button)dialog.findViewById(R.id.buttonStart);
                edtTestName = (EditText)dialog.findViewById(R.id.edtTestName);
                edtPassword = (EditText)dialog.findViewById(R.id.edtPassword);
                testName = edtTestName.getText().toString();
                password = edtPassword.getText().toString();
                btnEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tests.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String note = "Không tồn tại bài test";
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    Test test = postSnapshot.getValue(Test.class);
                                    if (test.getTestName().equals(edtTestName.getText().toString())){
                                        if(test.getPassword().equals(edtPassword.getText().toString())){
                                            Intent intent = new Intent(HomeStudent.this, DoTestActivity.class);
                                            intent.putExtra("time", test.getTime());
                                            intent.putExtra("userName", userName);
                                            intent.putExtra("testName", test.getTestName());
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            note = "Sai mật khẩu " + edtPassword.getText().toString();
                                        }
                                    }
                                }
                                Toast.makeText(HomeStudent.this, note, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        });
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
        btnEnterRoom = (Button)findViewById(R.id.buttonEnterRoom);
    }
}
