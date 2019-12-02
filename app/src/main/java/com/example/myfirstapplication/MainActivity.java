package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etUser, etPassword; // for sign in
    EditText etNewUser, etNewPassword, etNewPassword2, etNewEmail, etFullName; // for sign up
    Button btnLogIn, btnSignUp, btnExit;
    private Spinner snUserType;
    String userName, password, email, userType, fullName;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        Mapping();
        ControllButton();
    }

    private void ControllButton() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Bạn muốn thoát?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(true);//default
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("ĐĂNG KÝ");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.customdialog);
                dialog.show();
                etFullName = (EditText)dialog.findViewById(R.id.edittextFullName);
                etNewUser = (EditText)dialog.findViewById(R.id.edittextUser);
                etNewPassword = (EditText)dialog.findViewById(R.id.edittextPassword);
                etNewPassword2 = (EditText)dialog.findViewById(R.id.edittextPassword2);
                etNewEmail = (EditText)dialog.findViewById(R.id.edittextEmail);
                snUserType = (Spinner)dialog.findViewById(R.id.spinnerUserType);
                Button btnOK = (Button)dialog.findViewById(R.id.buttonOK);
                Button btnCancel = (Button)dialog.findViewById(R.id.buttonCancel);

                List<String> list = new ArrayList<>();
                list.add("student");
                list.add("teacher");
                ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                snUserType.setAdapter(adapter);
                snUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        userType = snUserType.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final User user = new User(etFullName.getText().toString().trim(),
                                etNewUser.getText().toString().trim(),
                                etNewPassword.getText().toString().trim(),
                                etNewEmail.getText().toString().trim(),
                                snUserType.getSelectedItem().toString());
                        users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(user.getUserName()).exists())
                                    Toast.makeText(MainActivity.this, "Nhập tên tài khoản khác",Toast.LENGTH_SHORT).show();
                                else{
                                    if(TextUtils.isEmpty(user.getUserName()) || TextUtils.isEmpty(user.getName()) || TextUtils.isEmpty(user.getPassword()) || TextUtils.isEmpty(user.getEmail())){
                                        Toast.makeText(MainActivity.this, "Chưa nhập đủ thông tin!",Toast.LENGTH_SHORT).show();
                                    }
                                    else if(!etNewPassword.getText().toString().equals(etNewPassword2.getText().toString())){
                                        Toast.makeText(MainActivity.this, "Xác thực mật khẩu sai!",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        users.child(user.getUserName()).setValue(user);
                                        Toast.makeText(MainActivity.this, "Đăng kí thành công!",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //userName = etNewUser.getText().toString().trim();
                        //password = etNewPassword.getText().toString().trim();
                        //etNewUser.setText(userName);
                        //etNewPassword.setText(password);
                        dialog.cancel();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUser.length() == 0 ){
                    Toast.makeText(MainActivity.this, "Chưa nhập tên tài khoản!",Toast.LENGTH_SHORT).show();
                }
                else if (etPassword.length() == 0){
                    Toast.makeText(MainActivity.this, "Chưa nhập mật khẩu!",Toast.LENGTH_SHORT).show();
                }
                else{
                    userName = etUser.getText().toString().trim();
                    password = etPassword.getText().toString().trim();
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(userName).exists()){
                                User login = dataSnapshot.child(userName).getValue(User.class);
                                //Toast.makeText(MainActivity.this, "OK!",Toast.LENGTH_SHORT).show();
                                if (login.getPassword().equals(password)){
                                    Intent homeActivity;
                                    if(login.getUserType().equals("student")){
                                        homeActivity = new Intent(MainActivity.this, HomeStudent.class);
                                        homeActivity.putExtra("user", userName);
                                    }
                                    else{
                                        homeActivity = new Intent(MainActivity.this, HomeTeacher.class);
                                        homeActivity.putExtra("user", userName);
                                    }
                                    startActivity(homeActivity);
                                    finish();
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "Sai mật khẩu!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Tài khoản không tồn tại!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //Toast.makeText(MainActivity.this, "Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    //startActivity(intent);
                }
            }
        });

    }

    private void Mapping(){
        etUser = (EditText)findViewById(R.id.edittextUser);
        etPassword = (EditText)findViewById(R.id.edittextPassword);
        btnLogIn = (Button) findViewById(R.id.buttonLogIn);
        btnSignUp = (Button) findViewById(R.id.buttonSignUp);
        btnExit= (Button) findViewById(R.id.buttonExit);
    }
}
