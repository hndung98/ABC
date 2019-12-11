package com.example.myfirstapplication.ViewHolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapplication.HomeStudent;
import com.example.myfirstapplication.HomeTeacher;
import com.example.myfirstapplication.Model.Question;
import com.example.myfirstapplication.Model.Result;
import com.example.myfirstapplication.Model.Test;
import com.example.myfirstapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DoTestActivity extends AppCompatActivity {

    String testName, userName;
    int time = 0, page = 1;
    List<String> listQuestion = new ArrayList<>();
    Button btnBack, btnNext;
    EditText edtQuestion;
    TextView tvTime;
    CheckBox cbxA, cbxB, cbxC, cbxD;
    Test test;
    Result result;
    FirebaseDatabase database;
    DatabaseReference questions, tests, results;
    private long timeLeft;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_test_layout);
        database = FirebaseDatabase.getInstance();
        tests = database.getReference("Tests");
        time = getIntent().getExtras().getInt("time");
        testName = getIntent().getExtras().getString("testName");
        userName = getIntent().getExtras().getString("userName");
        Mapping();
        StartTimer();
        ControllButton();
//        LoadTest();
//        LoadQuestion(page);
        Toast.makeText(DoTestActivity.this, "number question of test: "+ listQuestion.size(),Toast.LENGTH_SHORT).show();
    }

    private void ControllButton(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                Toast.makeText(DoTestActivity.this, "page: "+ page,Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page--;
                Toast.makeText(DoTestActivity.this, "page: "+ page,Toast.LENGTH_SHORT).show();
            }
        });
        cbxA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbxA.isChecked()){
                    cbxB.setChecked(false);
                    cbxC.setChecked(false);
                    cbxD.setChecked(false);
                }
            }
        });
        cbxB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbxB.isChecked()){
                    cbxA.setChecked(false);
                    cbxC.setChecked(false);
                    cbxD.setChecked(false);
                }
            }
        });
        cbxC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbxC.isChecked()){
                    cbxB.setChecked(false);
                    cbxA.setChecked(false);
                    cbxD.setChecked(false);
                }
            }
        });
        cbxD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbxD.isChecked()){
                    cbxB.setChecked(false);
                    cbxC.setChecked(false);
                    cbxA.setChecked(false);
                }
            }
        });
    }

    private void LoadQuestion(int index){
        questions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    Question question = post.getValue(Question.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void LoadTest(){
        tests.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Test temp = postSnapshot.getValue(Test.class);
                    if (temp.getTestName().equals(testName)){
                        test = temp;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listQuestion = test.getQuestionList();
    }

    private void StartTimer(){
        timeLeft = time * 60 * 1000;
        countDownTimer = new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void updateTimer(){
        int minutes = (int) (timeLeft/1000)/60;
        int seconds = (int) (timeLeft/1000)%60;
        String timeLeftFormatted = String.format(Locale.getDefault() ,"%02d:%02d", minutes, seconds);
        tvTime.setText(timeLeftFormatted);
    }

    private void Mapping(){
        tvTime = (TextView)findViewById(R.id.tvTimer);
        edtQuestion = (EditText)findViewById(R.id.edtQuestion);
        btnBack = (Button)findViewById(R.id.buttonBack);
        btnNext = (Button)findViewById(R.id.buttonNext);
        cbxA = (CheckBox)findViewById(R.id.cbxA);
        cbxB = (CheckBox)findViewById(R.id.cbxB);
        cbxC = (CheckBox)findViewById(R.id.cbxC);
        cbxD = (CheckBox)findViewById(R.id.cbxD);

    }
}
