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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapplication.Model.Bank;
import com.example.myfirstapplication.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeTeacher extends AppCompatActivity {
    private String userName;
    private Button btnLogOut, btnAddQuestion, btnCreateBank, btnListBanks, btnHistory;
    private TextView tvUserName;
    private FirebaseDatabase database;
    private DatabaseReference questions, banks;
    private DatabaseReference mDatabase;
    private Spinner ValidAnswer, Level;
    private int validAnswer = 0, level = 0, questionID;
    List<String> answer = new ArrayList<>();
    List<String> list = new ArrayList<>();
    private String question;
    private String bankName;
    boolean ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        userName = getIntent().getExtras().getString("user");
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        banks = database.getReference("Banks");
        Mapping();
        ControllButton();
        tvUserName.setText(userName);
    }

    private void ControllButton() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeTeacher.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Đăng xuất?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setCancelable(true);//default
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainActivity = new Intent(HomeTeacher.this,MainActivity.class);
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

        btnCreateBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomeTeacher.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.create_bank_layout);
                dialog.show();
                Button btnCreate = (Button)dialog.findViewById(R.id.btnCreate);
                Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
                final EditText edtBankName = (EditText)dialog.findViewById(R.id.edtBankName);
                btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bankName = edtBankName.getText().toString();
                        final Bank bank = new Bank(userName, bankName, 0);
                        banks.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(bank.getBankName()).exists()){
                                    Toast.makeText(HomeTeacher.this, "Tên ngân hàng đã tồn tại",Toast.LENGTH_SHORT).show();
                                    //edtBankName.setText("");
                                } else{
                                    Toast.makeText(HomeTeacher.this, "Tạo thành công!",Toast.LENGTH_SHORT).show();
                                    banks.child(bank.getBankName()).setValue(bank);
                                    dialog.cancel();
                                    edtBankName.setText("");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomeTeacher.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.add_question_layout);
                dialog.show();
                Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
                Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
                final EditText edtBankID = (EditText)dialog.findViewById(R.id.edtBankID);
                final EditText edtQuestion = (EditText)dialog.findViewById(R.id.edtQuestion);
                final EditText edtAnswer1 = (EditText)dialog.findViewById(R.id.edtAnswer1);
                final EditText edtAnswer2 = (EditText)dialog.findViewById(R.id.edtAnswer2);
                final EditText edtAnswer3 = (EditText)dialog.findViewById(R.id.edtAnswer3);
                final EditText edtAnswer4 = (EditText)dialog.findViewById(R.id.edtAnswer4);
                ValidAnswer = (Spinner)dialog.findViewById(R.id.spinnerValidAnswer);
                Level = (Spinner)dialog.findViewById(R.id.spinnerLevel);

                List<Integer> listValidAnswer = new ArrayList<>();
                listValidAnswer.add(1);
                listValidAnswer.add(2);
                listValidAnswer.add(3);
                listValidAnswer.add(4);
                ArrayAdapter<Integer> adapter = new ArrayAdapter(HomeTeacher.this, android.R.layout.simple_spinner_item,listValidAnswer);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                ValidAnswer.setAdapter(adapter);
                ValidAnswer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        validAnswer = Integer.parseInt(ValidAnswer.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                List<Integer>  listLevel = new ArrayList<>();
                listLevel.add(1);
                listLevel.add(2);
                listLevel.add(3);
                ArrayAdapter<Integer> adapter2 = new ArrayAdapter(HomeTeacher.this, android.R.layout.simple_spinner_item,listLevel);
                adapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                Level.setAdapter(adapter2);
                Level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        level = Integer.parseInt(Level.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bankName = edtBankID.getText().toString().trim();
                        final String ID = questions.push().getKey();
                        question = edtQuestion.getText().toString().trim();
                        answer.add(edtAnswer1.getText().toString().trim());
                        answer.add(edtAnswer2.getText().toString().trim());
                        answer.add(edtAnswer3.getText().toString().trim());
                        answer.add(edtAnswer4.getText().toString().trim());
                        final Question ques = new Question(bankName,level, question, validAnswer);
                        ques.setListAnswers(answer);
                        ques.setID(ID);
                        if(TextUtils.isEmpty(bankName) ||TextUtils.isEmpty(ques.getQuestion())  || TextUtils.isEmpty(String.valueOf(ques.getLevel())) || TextUtils.isEmpty(String.valueOf(ques.getAnswer()))){
                            Toast.makeText(HomeTeacher.this, "Chưa nhập đủ thông tin!",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            banks.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    boolean b = false;
                                    for (DataSnapshot post: dataSnapshot.getChildren()) {
                                        Bank bank = post.getValue(Bank.class);
                                        if (bank.getBankName().equals(bankName)){
                                            questions.child(ID).setValue(ques);
                                            banks.child(bankName).child("size").setValue(bank.getSize()+1);
                                            b = true;
                                        }
                                    }
                                    if (b){
                                        Toast.makeText(HomeTeacher.this, "Thêm câu hỏi thành công!",Toast.LENGTH_SHORT).show();
                                        edtBankID.setText("");
                                        edtQuestion.setText("");
                                        edtAnswer1.setText("");
                                        edtAnswer2.setText("");
                                        edtAnswer3.setText("");
                                        edtAnswer4.setText("");

                                    }else {
                                        Toast.makeText(HomeTeacher.this, "Tên ngân hàng không tồn tại!",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
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
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomeTeacher.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.list_questions_layout);
                dialog.show();

                ListView lvTest = (ListView)dialog.findViewById(R.id.lvListQuestions);
                questions.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            list.add(postSnapshot.getKey().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                list.add("example");
                ArrayAdapter arrayAdapter = new ArrayAdapter(HomeTeacher.this,android.R.layout.simple_list_item_1,list);
                lvTest.setAdapter(arrayAdapter);
                list.clear();
            }
        });
        btnListBanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(HomeTeacher.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.list_banks_layout);
                dialog.show();
                final List<Button> btnBank = new ArrayList<>();
                btnBank.add((Button)dialog.findViewById(R.id.buttonBank1));
                btnBank.add((Button)dialog.findViewById(R.id.buttonBank2));
                btnBank.add((Button)dialog.findViewById(R.id.buttonBank3));
                btnBank.add((Button)dialog.findViewById(R.id.buttonBank4));
                btnBank.add((Button)dialog.findViewById(R.id.buttonBank5));
                final List<Bank> bankList = new ArrayList<>();
                //final List<String> listBank = new ArrayList<>();
                banks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Bank post = postSnapshot.getValue(Bank.class);
                            if (post.getUserName().equals(userName)) bankList.add(post);
                        }
                        for(int i = 0; i < btnBank.size(); i++){
                            btnBank.get(i).setText("Empty");
                        }
                        for(int i = 0; i < bankList.size(); i++){
                            btnBank.get(i).setText(bankList.get(i).getBankName());
                        }

                        for(int i = 0; i < bankList.size(); i++){
                            bankName = bankList.get(i).getBankName();
                            final int finalI = i;
                            btnBank.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int j = finalI;
                                    Intent bankActivity = new Intent(HomeTeacher.this, BankActivity.class);
                                    bankActivity.putExtra("bankName", bankList.get(j).getBankName());
                                    startActivity(bankActivity);
                                    finish();
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //

                //
            }
        });
    }

    private void Mapping() {
        btnLogOut = (Button)findViewById(R.id.buttonLogOut);
        btnAddQuestion = (Button) findViewById(R.id.buttonAddNewQues);
        btnCreateBank = (Button) findViewById(R.id.buttonCreateNewBank);
        btnListBanks = (Button) findViewById(R.id.buttonListBanks);
        tvUserName = (TextView)findViewById(R.id.tvName);
        btnHistory = (Button) findViewById(R.id.buttonHistory);
    }
}
