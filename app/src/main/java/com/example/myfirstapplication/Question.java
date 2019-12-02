package com.example.myfirstapplication;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int level = 0;
    private String ID;
    private String question = null;
    private int answer = 0;
    private String bank = null;
    List<String> listAnswers = new ArrayList<>();

    public Question(){}
    public Question(String bank, int level, String question, int answer){
        this.bank = bank;
        this.level = level;
        this.question = question;
        this.answer = answer;
    }
    public Question(String bank, int level, String question,List<String> list, int answer){
        this.bank = bank;
        this.level = level;
        this.question = question;
        this.answer = answer;
        this.listAnswers = list;
    }
    public void setID(String id){this.ID = id;}
    public String getID(){return this.ID;}
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
    public int getAnswer() {
        return answer;
    }
    public void setAnswer(int answer) {
        this.answer = answer;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public List<String> getListAnswers() {
        return listAnswers;
    }
    public String getBank(){return bank;}
    public void setBank(String bank) {
        this.bank = bank;
    }
    public void setListAnswers(List<String> listAnswers) {
        this.listAnswers = listAnswers;
    }
}
