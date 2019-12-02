package com.example.myfirstapplication;

public class Category {
    private  String Name, Image;

    public Category(){

    }
    public Category(String Name, String Image){
        this.Name = Name;
        this.Image = Image;
    }
    public String getName(){return Name;}
    public void setName(String Name){this.Name = Name;}
    public String getImagee(){return Image;}
    public void setImage(String Image){this.Image = Image;}
}
