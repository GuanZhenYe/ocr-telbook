package com.szu.telBook;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fragment.FragmentAddContact;
import fragment.FragmentContacts;

public class MainActivity extends Activity {


    private Fragment fragment[] = new Fragment[2];

    private TextView mTopBarTitle;

    private Button mFlush;

    private Button mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopBarTitle = (TextView) findViewById(R.id.topBar_title);
        mFlush = (Button) findViewById(R.id.flush);
        mSearch = (Button) findViewById(R.id.search);

        mSearch.setOnClickListener(new MyListener());

        fragment[0] = new FragmentContacts();
        fragment[1] = new FragmentAddContact();

        getFragmentManager().beginTransaction()
                .add(R.id.main_fragment, fragment[0])
                .add(R.id.main_fragment, fragment[1]).commit();

        showContactsClick(null);
//        mFlush.setOnClickListener(null);
        Log.d("aaa", "init success");

    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (v == mSearch) {
                Intent intent = new Intent(MainActivity.this, SearchContactActivity.class);
                startActivity(intent);
            }
        }
    }

    public void showContactsClick(View view) {
        getFragmentManager().beginTransaction().hide(fragment[1]).show(fragment[0]).commit();
        mTopBarTitle.setText("名片");
        mFlush.setVisibility(View.VISIBLE);
        mSearch.setVisibility(View.VISIBLE);
    }

    public void addContactsClick(View view) {
        getFragmentManager().beginTransaction().hide(fragment[0]).show(fragment[1]).commit();
        mTopBarTitle.setText("添加名片");
        mFlush.setVisibility(View.INVISIBLE);
        mSearch.setVisibility(View.INVISIBLE);
    }

}
