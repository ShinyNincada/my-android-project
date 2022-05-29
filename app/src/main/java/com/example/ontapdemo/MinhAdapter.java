package com.example.ontapdemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MinhAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    ArrayList<Contact_Minh> data;
    LayoutInflater inflater;
    ArrayList<Contact_Minh> dataBackup;

    public MinhAdapter(Activity activity, ArrayList<Contact_Minh> data) {
        this.activity = activity;
        this.data = data;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).id;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null) {
//            phân tích layout
            v = inflater.inflate(R.layout.contact_view, null);
        }
        TextView id = v.findViewById(R.id.contact_id);
        TextView txtName = v.findViewById(R.id.contact_name);
        TextView phone = v.findViewById(R.id.contact_phone);
        ImageView img = v.findViewById(R.id.contact_img);

        id.setText(data.get(i).getId()+"");
        txtName.setText(data.get(i).getFullName());
        phone.setText(data.get(i).getPhoneNumber());
        img.setImageResource(data.get(i).getImgID());

        return v;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                if(dataBackup==null)
                    dataBackup = new ArrayList<>(data);
                if(charSequence==null || charSequence.length()==0){
                    fr.count = dataBackup.size();
                    fr.values = dataBackup;
                }
                else{
                    ArrayList<Contact_Minh> newdata = new ArrayList<>();
                    for(Contact_Minh c: dataBackup)
                        if(c.getFullName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            newdata.add(c);
                    fr.count = newdata.size();
                    fr.values = newdata;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = new ArrayList<>();
                ArrayList<Contact_Minh> tmp = (ArrayList<Contact_Minh>) filterResults.values;
                for(Contact_Minh c:tmp){
                    Contact_Minh nu = new Contact_Minh(c.getId(), c.getFullName(), c.getPhoneNumber(),  c.getImgID());
                    data.add(nu);
                }
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
