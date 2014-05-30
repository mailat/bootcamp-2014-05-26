package com.androider.demo02_contentprovider;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.ListActivity;
import android.database.Cursor;

public class ContactsActivity extends ListActivity {

	@Override
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] fields = new String[] {ContactsContract.Data.DISPLAY_NAME};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_listview, getContacts(), fields, new int[] {R.id.name_entry});
        getListView().setAdapter(adapter);         
    }    

    private Cursor getContacts() {   
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{ ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME }; 
        return managedQuery(uri, projection, null, null, null);
    }

}
