package com.example.myfirstapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String testName = null;
    private String bankName = null;
    private String password = null;
    private int time = 0;
    private boolean active = true;
    List<String> questionList = new ArrayList<>();

    public Test(){}
    public Test(String testName, String bankName, String password, int time){
        this.testName = testName;
        this.bankName = bankName;
        this.password = password;
        this.time = time;
    }
    public boolean getActive(){return this.active;}
    public void setActive(boolean active){this.active = active;}
    public String getTestName(){return this.testName;}
    public void setTestName(String name){this.testName = name;}
    public String getBankName(){return this.bankName;}
    public void setBankName(String name){this.bankName = name;}
    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}
    public int getTime(){return this.time;}
    public void setTime(int time){this.time = time;}
    public List<String> getQuestionList(){return  questionList;}
    public void setQuestionList(List<String> questionList) {
        this.questionList = questionList;
    }
    public void addQuestion(String questionID){questionList.add(questionID);}
    public int getLength(){return questionList.size();}
}
