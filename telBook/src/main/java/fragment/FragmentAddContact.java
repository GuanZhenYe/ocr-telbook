package fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szu.telBook.R;
import com.szu.telBook.RegnizeActivity;

import Dao.Contact;
import Dao.DbManager;


public class FragmentAddContact extends Fragment {

    private Button mAddContact;
    private Button mTakeTel;
    private Button mTakeMail;
    private Button mClear;
    private EditText mName, mCompany, mTelephone, mEmail, mBusiness;
    private Activity activity;


    public static final int REQUEST_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);

        mAddContact = (Button) view.findViewById(R.id.addContact);
        mTakeTel = (Button) view.findViewById(R.id.take_tel);
        mTakeMail = (Button) view.findViewById(R.id.take_email);
        mClear = (Button) view.findViewById(R.id.clear);

        mName = (EditText) view.findViewById(R.id.edit_name);
        mCompany = (EditText) view.findViewById(R.id.edit_company);
        mTelephone = (EditText) view.findViewById(R.id.edit_telephone);
        mEmail = (EditText) view.findViewById(R.id.edit_email);
        mBusiness = (EditText) view.findViewById(R.id.edit_business);

        mAddContact.setOnClickListener(new AddContactListener());
        mTakeTel.setOnClickListener(new AddContactListener());
        mTakeMail.setOnClickListener(new AddContactListener());
        mClear.setOnClickListener(new AddContactListener());

        this.activity = getActivity();
        return view;
    }


    class AddContactListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.addContact:
                    String name = mName.getText().toString();
                    String company = mCompany.getText().toString();
                    String telephone = mTelephone.getText().toString();
                    String email = mEmail.getText().toString();
                    String business = mBusiness.getText().toString();

                    if (name.equals("") || telephone.equals("")) {
                        Toast.makeText(activity, "用户名和电话号码不能为空!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Contact contact = new Contact();
                    contact.setName(name);
                    contact.setCompany(company);
                    contact.setTelephone(telephone);
                    contact.setEmail(email);
                    contact.setBusiness(business);
                    new MyAsycnTask().execute(activity, contact);
                    break;

                case R.id.take_tel:

                    Intent intent = new Intent(activity, RegnizeActivity.class);
                    intent.putExtra("type", "tel");
                    startActivityForResult(intent, REQUEST_CODE);
                    break;

                case R.id.take_email:
                    Intent intent1 = new Intent(activity, RegnizeActivity.class);
                    intent1.putExtra("type", "email");
                    startActivityForResult(intent1, REQUEST_CODE);
                    break;

                case R.id.clear:
                    mName.setText("");
                    mCompany.setText("");
                    mTelephone.setText("");
                    mEmail.setText("");
                    mBusiness.setText("");
                    break;
            }
        }
    }

    class MyAsycnTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {

            if (params.length < 2)
                return "ERROR";
            Activity context = (Activity) params[0];
            Contact contact = (Contact) params[1];

            Log.d("aaa", "dfdfddf");
            DbManager dbManager = new DbManager(activity);

            Log.d("aaa", "dfdfddf");
            dbManager.insertContact(contact);

            return "SUCCESS";
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();

            super.onPostExecute(s);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {

            if (resultCode == 2) {

                String result = data.getStringExtra("result");

                mTelephone.setText(result);
            } else if (resultCode == 3) {
                String result = data.getStringExtra("result");

                mEmail.setText(result);
            }
        }
    }
}
