package com.example.myfirstapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUser, etPassword;
    Button btnLogIn, btnSignUp, btnExit;
    String userName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                final EditText etUser = (EditText)dialog.findViewById(R.id.edittextUser);
                final EditText etPassword = (EditText)dialog.findViewById(R.id.edittextPassword);
                Button btnOK = (Button)dialog.findViewById(R.id.buttonOK);
                Button btnCancel = (Button)dialog.findViewById(R.id.buttonCancel);
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userName = etUser.getText().toString().trim();
                        password = etPassword.getText().toString().trim();
                        etUser.setText(userName);
                        etPassword.setText(password);
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
                if (etUser.length() == 0 || etPassword.length() == 0){
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
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
