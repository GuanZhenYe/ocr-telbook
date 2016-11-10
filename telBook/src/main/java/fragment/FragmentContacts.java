package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szu.telBook.MainActivity;
import com.szu.telBook.R;

import java.util.ArrayList;
import java.util.List;

import Dao.Contact;
import Dao.DbManager;
import adapter.ContactsListViewAdapter;


public class FragmentContacts extends Fragment {

    private Activity activity;

    private ContactsListViewAdapter adapter;

    private ListView mListView;

    private Button flush;

    private List<Contact> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        adapter = new ContactsListViewAdapter(activity, null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        this.activity = getActivity();

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mListView = (ListView) activity.findViewById(R.id.fragment_contacts_listview);
        flush = (Button) activity.findViewById(R.id.flush);
        flush.setOnClickListener(new MyClickListener());

        adapter = new ContactsListViewAdapter(activity, null);
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(new MyLongClickListener());

        new MySyncTask().execute(activity);
        super.onActivityCreated(savedInstanceState);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

    class MyLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            int firstPosition = parent.getFirstVisiblePosition();

            View view1 = parent.getChildAt(position - firstPosition);
            TextView nameView = (TextView) view1.findViewById(R.id.contactName);
            final String name = nameView.getText().toString();

            TextView telView = (TextView) view1.findViewById(R.id.contactTel);
            final String tel = telView.getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("删除");
            builder.setIcon(R.drawable.ic_delete_forever_black_36dp);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    new DeleteContactAsyncTask().execute(activity, name, tel);
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
    }

    class DeleteContactAsyncTask extends AsyncTask<Object, Void, List<Contact>> {

        @Override
        protected List<Contact> doInBackground(Object... params) {
            Activity activity = (Activity) params[0];
            String name = (String) params[1];
            String tel = (String) params[2];

            DbManager dbManager = new DbManager(activity);
            dbManager.deleteContact(name, tel);
            List<Contact> list = dbManager.getContacts();
            if (list == null) {
                return new ArrayList<Contact>();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {

            adapter.addData(contacts);
            adapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            super.onPostExecute(contacts);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new MySyncTask().execute(activity);
        }
    }

    class MySyncTask extends AsyncTask<Object, Void, List<Contact>> {

        @Override
        protected List<Contact> doInBackground(Object... params) {

            Activity activity = (Activity) params[0];
            DbManager dbManager = new DbManager(activity);
            List<Contact> list = dbManager.getContacts();
            if (list == null) {
                return new ArrayList<Contact>();
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {

            adapter.addData(contacts);
            adapter.notifyDataSetChanged();
            super.onPostExecute(contacts);
        }
    }
}
