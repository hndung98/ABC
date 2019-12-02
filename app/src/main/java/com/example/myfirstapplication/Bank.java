package com.example.myfirstapplication;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String userName = null;
    private String bankName = null;
    private int size = 0;
    //private List<Question> list = new ArrayList<>();
    public Bank(){}
    public Bank(String userName, String bankName, int size){
        this.userName = userName;
        this.bankName = bankName;
        this.size = size;
    }

    //public int getSize(){return list.size();}
    //public void addQuestion(Question question){
    //    list.add(question);
    //}
    public int getSize(){return this.size;}
    public void setSize(int size){this.size = size;}
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getUserName(){return userName;}
}
