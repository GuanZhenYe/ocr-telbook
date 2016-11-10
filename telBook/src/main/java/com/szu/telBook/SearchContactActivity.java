package com.szu.telBook;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Dao.Contact;
import Dao.DbManager;
import adapter.ContactsListViewAdapter;

import static android.view.Gravity.CENTER;

/**
 * Created by Administrator on 2016/6/22.
 */
public class SearchContactActivity extends Activity implements View.OnClickListener {

    private Button search;
    private EditText editText;
    private ListView listView;
    private ContactsListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        search = (Button) findViewById(R.id.searchContact);
        editText = (EditText) findViewById(R.id.edit_contact);
        listView = (ListView) findViewById(R.id.searchListView);
        search.setOnClickListener(this);
        adapter = new ContactsListViewAdapter(this, null);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStop() {
        this.finish();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v == search) {
            String name = editText.getText().toString();

            if (name.equals("")) {
                Toast toast = Toast.makeText(SearchContactActivity.this, "查询不能为空", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            new MySyncTask().execute(this, name);
        }
    }

    class MySyncTask extends AsyncTask<Object, Void, List<Contact>> {

        @Override
        protected List<Contact> doInBackground(Object... params) {

            Activity activity = (Activity) params[0];
            String name = (String) params[1];

            DbManager dbManager = new DbManager(activity);
            List<Contact> list = dbManager.fuzzySearchContacts(name);

            if (list == null) {
                return new ArrayList<Contact>();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {

            if (contacts.size() < 1) {
                Toast toast = Toast.makeText(SearchContactActivity.this, "查询不到此人", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            adapter.addData(contacts);
            adapter.notifyDataSetChanged();
            super.onPostExecute(contacts);
        }
    }
}
