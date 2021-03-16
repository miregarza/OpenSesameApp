package com.example.opensesameapp;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ViewListContents extends AppCompatActivity {
    String AES = "AES";


    //ArrayList accountList = intent.getStringArrayListExtra("accountList");

    DatabaseHelper myDB;
    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);
        Bundle bundle = getIntent().getExtras();
        String userPassword = bundle.getString("password");

        //************************************ OLD DB LISTVIEW STARTS **************************************
        ListView listView = (ListView) findViewById(R.id.listView);
        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        ArrayList<String> PlatformList = new ArrayList<>();
        ArrayList<String> PassList = new ArrayList<>();
        Cursor data = myDB.getListContents(); //gets all data from db

        ArrayList<Platform> accountList =  new ArrayList<>();
        while(data.moveToNext()){
            try {
                accountList.add(new Platform(data.getString(1),decrypt(data.getString(2),userPassword)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        data.moveToFirst();
        data.moveToPrevious();

        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }

        else{
            /*//FOR LIST ADAPTER START
            PlatformListAdapter adapter = new PlatformListAdapter(this, R.layout.list_item, accountList);
            listView.setAdapter(adapter);
            //FOR LIST ADAPTER END*/
            while(data.moveToNext()){
                PlatformList.add(data.getString(1)); //stores 1st column from db in platform list
                try {
                    PassList.add(decrypt(data.getString(2), userPassword)); //stores 2nd column from db in passlist
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, PassList); //if want button need to use list_item 2/25
                PlatformListAdapter adapter = new PlatformListAdapter(this, R.layout.list_item, accountList);
                //listView.setAdapter(listAdapter);
                listView.setAdapter(adapter);



            }
        }
        //************************************ OLD DB LISTVIEW ENDS **************************************
        //create OnClick listener for remove button on list_view






        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ViewListContents.this, "List item was clicked at" + position, Toast.LENGTH_SHORT).show();
            }
        });*/

    }



    //DECRYPT START
    private String decrypt (String outputString, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] deccodedValue = Base64.decode(outputString, Base64.DEFAULT);
        byte[] decValue = c.doFinal(deccodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec (key, "AES");
        return secretKeySpec;
    }

}