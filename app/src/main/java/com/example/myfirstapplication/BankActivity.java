package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapplication.Adapter.CustomAdapter;
import com.example.myfirstapplication.ViewHolder.Contact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BankActivity extends AppCompatActivity {

    TextView tvbankName;
    Button btnCreateTest, btnAddQuestion, btnViewQuestion, btnBack, btnBack2, btnDone, btnAddQuesToTest, btnRefresh;
    Spinner snTime;
    EditText edtTestName, edtPassword;
    int time = 0;
    ListView lvQuestions;
    String userName, bankName, note;
    List<Question> listQuestion = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference questions,banks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_layout);
        bankName = getIntent().getExtras().getString("bankName");
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        banks = database.getReference("Banks");
        Mapping();
        ControllButton();
        tvbankName.setText(bankName);
    }

    private void ControllButton(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(bankName).exists()){
                            Bank bank = dataSnapshot.child(bankName).getValue(Bank.class);
                            userName = bank.getUserName();
                            Intent homeTeacher = new Intent(BankActivity.this, HomeTeacher.class);
                            homeTeacher.putExtra("user", userName);
                            startActivity(homeTeacher);
                            finish();
                        }
                        else{
                            Toast.makeText(BankActivity.this, "Error !",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }
        });
        btnViewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listQuestion.clear();
                final Dialog dialog = new Dialog(BankActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.list_questions_layout);
                dialog.show();
                lvQuestions = (ListView)dialog.findViewById(R.id.lvListQuestions);
                btnBack2 = (Button)dialog.findViewById(R.id.btnBack);
                btnDone = (Button)dialog.findViewById(R.id.btnDone);
                btnRefresh =  (Button)dialog.findViewById(R.id.btnRefresh);
                btnRefresh.setVisibility(View.INVISIBLE);
                btnBack2.setVisibility(View.INVISIBLE);
                btnDone.setVisibility(View.INVISIBLE);
                questions.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Question post = postSnapshot.getValue(Question.class);
                            if (post.getBank().equals(bankName)) listQuestion.add(post);
                            //listQuestion.add(post);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                note = "bank size: " + listQuestion.size();
                List<String> listStringQuestion = new ArrayList<>();
                listStringQuestion.add("start");
                for (int i = 0; i < listQuestion.size(); i++){
                    listStringQuestion.add(listQuestion.get(i).getQuestion());
                }
                listStringQuestion.add("end");
                ArrayAdapter arrayAdapter = new ArrayAdapter(BankActivity.this,android.R.layout.simple_list_item_1,listStringQuestion);
                lvQuestions.setAdapter(arrayAdapter);
                //note = "number of question("+ bankName + "): " + listQuestion.size() + " ";
                Toast.makeText(BankActivity.this, note, Toast.LENGTH_SHORT).show();
                listQuestion.clear();
            }
        });
//        btnViewQuestion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog dialog = new Dialog(BankActivity.this);
//                dialog.setCancelable(true);
//                dialog.setContentView(R.layout.list_questions_layout);
//                dialog.show();
//                lvQuestions = (ListView)dialog.findViewById(R.id.lvListQuestions);
//                ArrayList<Contact> list = new ArrayList<>();
//
//                for (int i = 1; i < 11; i++){
//                    Contact contact = new Contact("" + i, "Cau hoi " + i + " ?");
//                    list.add(contact);
//                }
//
//                CustomAdapter customAdapter = new CustomAdapter(BankActivity.this, R.layout.row_item_in_list_view, list);
//                lvQuestions.setAdapter(customAdapter);
//            }
//        });
        btnCreateTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = 0;
                final Dialog dialog = new Dialog(BankActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.create_test_layout);
                dialog.show();
                snTime = (Spinner)dialog.findViewById(R.id.spinnerTime);
                edtTestName = (EditText)dialog.findViewById(R.id.edtTestName);
                edtPassword = (EditText)dialog.findViewById(R.id.edtPassword);
                btnAddQuesToTest = (Button)dialog.findViewById(R.id.buttonAddQuestions);
                List<Integer> list = new ArrayList<>();
                list.add(15);
                list.add(45);
                list.add(90);
                list.add(120);
                ArrayAdapter<Integer> adapter = new ArrayAdapter(BankActivity.this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                snTime.setAdapter(adapter);
                snTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        time = Integer.parseInt(snTime.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnAddQuesToTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtTestName.getText().length() == 0 || edtPassword.getText().length() == 0 || time == 0){
                            Toast.makeText(BankActivity.this, "Chưa đủ thông tin!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent NewActivity;
                            NewActivity = new Intent(BankActivity.this, CreateTestActivity.class);
                            NewActivity.putExtra("bankName", bankName);
                            NewActivity.putExtra("testName", edtTestName.getText().toString().trim());
                            NewActivity.putExtra("password", edtPassword.getText().toString().trim());
                            NewActivity.putExtra("time", time);
                            startActivity(NewActivity);
                            finish();
                        }
                    }
                });

            }
        });
    }

    private void Mapping() {
        btnCreateTest = (Button)findViewById(R.id.buttonCreateTest);
        btnAddQuestion = (Button)findViewById(R.id.buttonAddQuestion);
        btnViewQuestion = (Button)findViewById(R.id.buttonViewQuestion);
        btnBack = (Button)findViewById(R.id.buttonBack);
        tvbankName = (TextView)findViewById(R.id.tvName);
    }

}
