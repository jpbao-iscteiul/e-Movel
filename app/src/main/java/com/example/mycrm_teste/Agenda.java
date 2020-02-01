package com.example.mycrm_teste;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

public class Agenda extends AppCompatActivity {

    private static final int MY_PERMISSIONS_READ_CALENDAR = 10;

    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT,                 // 3
            CalendarContract.Calendars.ACCOUNT_TYPE                   // 4
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final int PROJECTION_OWNER_ACCOUNT_TYPE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, MY_PERMISSIONS_READ_CALENDAR);
            return;
        }
        readCalendar();
    }
    /*
    public void readCalendar (){
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {"jpbao@iscte-iul.pt", "com.google"};
        // Submit the query and get a Cursor object back.
        cur = cr.query(uri, EVENT_PROJECTION, null, null, null);


        // Use the cursor to step through the returned records
        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;
            String accountType = null;

            // Get the field values
            calID = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
            accountType = cur.getString(PROJECTION_OWNER_ACCOUNT_TYPE);

            // Do something with the values...
            Log.d("TUDO", "---------------------");
            Log.d("TUDO", "displayName: " + displayName);
            Log.d("TUDO", "accountName: " + accountName);
            Log.d("TUDO", "accountType: " + accountType);
        }
    }

*/

    public void readCalendar (){
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, Calendar.getInstance().getTime().getTime());
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_READ_CALENDAR){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                readCalendar();
            }
        }
    }
}
