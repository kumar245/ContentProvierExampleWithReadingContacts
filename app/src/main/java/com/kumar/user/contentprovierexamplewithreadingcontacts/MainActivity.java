package com.kumar.user.contentprovierexamplewithreadingcontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckPermissions();
    }
    void CheckPermissions(){
        if (Build.VERSION.SDK_INT>=23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!=
                    PackageManager.PERMISSION_GRANTED){
                requestPermissions(new  String[]{
                        Manifest.permission.READ_CONTACTS},REQUEST_CODE_ASK_PERMISSIONS);
                return;
                }
            }
    ReadContacts();
        }
    final private int REQUEST_CODE_ASK_PERMISSIONS=123;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode){
        case REQUEST_CODE_ASK_PERMISSIONS:
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                ReadContacts();
            }
            else {
                Toast.makeText(this, "You Denied the Request, Try Again", Toast.LENGTH_SHORT).show();
            }
            break;
        default:
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    }

    ArrayList<Contact_Items> listcontact=new ArrayList<Contact_Items>();
    void ReadContacts(){
        String selection=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "like'%h%'";
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        ,null,null,null,null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String PhNumber=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            listcontact.add(new Contact_Items(name,PhNumber));
        }
        MyCustomAdapter myadapter=new MyCustomAdapter(listcontact);
        ListView lsView= (ListView) findViewById(R.id.lView);
        lsView.setAdapter(myadapter);


    }
    public class MyCustomAdapter extends BaseAdapter{
    public ArrayList<Contact_Items> listnewsDataAdapter;

        public MyCustomAdapter(ArrayList<Contact_Items> listnewsDataAdapter) {
            this.listnewsDataAdapter = listnewsDataAdapter;
        }

        @Override
        public int getCount() {
            return listnewsDataAdapter.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater=getLayoutInflater();
            View myView=mInflater.inflate(R.layout.list_item,null);
            final Contact_Items s=listnewsDataAdapter.get(position);
            TextView tvUser= (TextView) myView.findViewById(R.id.tvName);
            tvUser.setText(s.name);
            TextView tvPass= (TextView) myView.findViewById(R.id.tvPassword);
            tvPass.setText(s.PhNumber);
            return myView;
        }
    }
}
