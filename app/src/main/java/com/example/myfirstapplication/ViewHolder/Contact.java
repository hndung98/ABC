package com.example.myfirstapplication.ViewHolder;

public class Contact {
    private String id;
    private String question;
    private boolean check = false;

    public Contact(){}
    public Contact(String id, String question) {
        this.id = id;
        this.question = question;
    }
    public boolean getCheck(){return this.check;}
    public void setCheck(boolean check){this.check = check;}
    public String getId(){return this.id;}
    public void setId(String id){this.id = id;}
    public String getQuestion(){return this.question;}
    public void setQuestion(String question){this.question = question;}
    @Override
    public String toString() {
        return this.question;
    }
}
