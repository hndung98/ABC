package com.example.myfirstapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String id = null;
    private String userName = null;
    private String testName = null;
    private int point = -1;
    private List<Integer> listAnswer = new ArrayList<>();
    public Result(){}

    public Result(String id, String userName, String testName){
        this.id = id;
        this.userName = userName;
        this.testName = testName;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getTestName() {
        return testName;
    }

    public int getPoint() {
        return point;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<Integer> getListAnswer(){return this.listAnswer;}

    public void setListAnswer(List<Integer> listAnswer){this.listAnswer = listAnswer;}

    public void addAnswer(int answer){listAnswer.add(answer);}
}
