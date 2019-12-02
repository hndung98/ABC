package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class CreateTestActivity extends AppCompatActivity {

    private Button btnDone, btnBack, btnRefresh;
    private String bankName, password, testName;
    private ListView lvQuestions;
    private ArrayList<Contact> list = new ArrayList<>();
    CustomAdapter customAdapter;
    private int time = 0;
    private Test test = null;
    FirebaseDatabase database;
    DatabaseReference questions, tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_questions_layout);
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        tests = database.getReference("Tests");
        bankName = getIntent().getExtras().getString("bankName");
        testName = getIntent().getExtras().getString("testName");
        password = getIntent().getExtras().getString("password");
        time = getIntent().getExtras().getInt("time");
        Mapping();
        ControllButton();

    }



    private void ControllButton() {

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
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questions.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot post: dataSnapshot.getChildren()) {
                            Question question = post.getValue(Question.class);
                            if (question.getBank().equals(bankName)){
                                Contact contact = new Contact(question.getID(), question.getQuestion());
                                list.add(contact);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                String output = "list size = " + list.size();
                Toast.makeText(CreateTestActivity.this, output, Toast.LENGTH_SHORT).show();
                customAdapter = new CustomAdapter(CreateTestActivity.this, R.layout.row_item_in_list_view, list);
                lvQuestions.setAdapter(customAdapter);
                list.clear();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> listQuestion = new ArrayList<>();
                int size = 0;
                for (int i = 0; i < customAdapter.objects.size(); i++){
                    if (customAdapter.objects.get(i).getCheck()){
                        listQuestion.add(customAdapter.objects.get(i).getQuestion());
                    }
                }

                size = listQuestion.size();
                Toast.makeText(CreateTestActivity.this, "size : " + size, Toast.LENGTH_SHORT).show();
//                lvQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String note = "size : " + parent.getCount();
//                        Toast.makeText(CreateTestActivity.this, note, Toast.LENGTH_SHORT).show();
//                        Contact contact = (Contact) parent.getItemAtPosition(position);
//                        listQuestion.add(contact.getId());
//                    }
//                });
                if(size < 5){
                    Toast.makeText(CreateTestActivity.this, "Số câu hỏi phải lớn hơn 5", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String ID = tests.push().getKey();
                test = new Test(testName, bankName, password, time);
                for (int i = 0; i < listQuestion.size(); i++){
                    test.addQuestion(listQuestion.get(i));
                }
                tests.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tests.child(ID).setValue(test);
                        Toast.makeText(CreateTestActivity.this, "Tạo bài test thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateTestActivity.this, BankActivity.class);
                        intent.putExtra("bankName", bankName);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NewActivity = new Intent(CreateTestActivity.this, BankActivity.class);
                NewActivity.putExtra("bankName", bankName);
                startActivity(NewActivity);
                finish();
            }
        });
    }
    private void Mapping() {
        btnDone = (Button)findViewById(R.id.btnDone);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        lvQuestions =(ListView) findViewById(R.id.lvListQuestions);
    }
}
