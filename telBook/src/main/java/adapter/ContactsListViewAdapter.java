package adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.szu.telBook.R;

import java.util.ArrayList;
import java.util.List;

import Dao.Contact;
import Dao.DbManager;


public class ContactsListViewAdapter extends BaseAdapter {

    private List<Contact> mList;
    private LayoutInflater mLayoutInflater;
    private Activity context;

    public ContactsListViewAdapter(Activity context, List<Contact> list) {

        mLayoutInflater = LayoutInflater.from(context);

        this.context = context;
        if (list == null) {
            this.mList = new ArrayList<>();
        } else {
            this.mList = list;
        }
    }

    public void addData(List<Contact> contacts) {
        mList.clear();
        mList.addAll(contacts);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        View view=mLayoutInflater.inflate(R.layout.listview_item,null);
//        TextView name= (TextView) view.findViewById(R.id.contactName);
//        TextView company= (TextView) view.findViewById(R.id.contactCompany);
//        TextView telephone= (TextView) view.findViewById(R.id.contactTel);
//        TextView email= (TextView) view.findViewById(R.id.contactMail);
//        TextView business= (TextView) view.findViewById(R.id.contactBusiness);
//
//        Contact contact=mList.get(position);
//        name.setText(contact.getName());
//        company.setText(contact.getCompany());
//        telephone.setText(contact.getTelephone());
//        email.setText(contact.getEmail());
//        business.setText(contact.getBusiness());
//
//        return view;

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
            viewHolder.cname = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.company = (TextView) convertView.findViewById(R.id.contactCompany);
            viewHolder.telephone = (TextView) convertView.findViewById(R.id.contactTel);
            viewHolder.email = (TextView) convertView.findViewById(R.id.contactMail);
            viewHolder.business = (TextView) convertView.findViewById(R.id.contactBusiness);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact = mList.get(position);
        viewHolder.cname.setText(contact.getName());
        viewHolder.company.setText(contact.getCompany());
        viewHolder.telephone.setText(contact.getTelephone());
        viewHolder.email.setText(contact.getEmail());
        viewHolder.business.setText(contact.getBusiness());

        return convertView;
    }

    static class ViewHolder {

        TextView cname;
        TextView company;
        TextView telephone;
        TextView email;
        TextView business;
    }

}
