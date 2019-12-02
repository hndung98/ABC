package com.example.myfirstapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfirstapplication.R;
import com.example.myfirstapplication.ViewHolder.Contact;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    public ArrayList<Contact> objects;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item_in_list_view, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.cbxChoose = (CheckBox) convertView.findViewById(R.id.cbxChoose);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Contact contact = objects.get(position);
        contact.setCheck(viewHolder.cbxChoose.isChecked());
        viewHolder.cbxChoose.setText(contact.getQuestion());
        return convertView;
    }


    public class ViewHolder{
        CheckBox cbxChoose;
    }
}
