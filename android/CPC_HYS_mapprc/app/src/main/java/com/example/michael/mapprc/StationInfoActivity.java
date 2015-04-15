package com.example.michael.mapprc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class StationInfoActivity extends ActionBarActivity {
    double latitude;
    double longitude;
    String address;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_info);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            latitude=extras.getDouble("latitude");
            longitude=extras.getDouble("longitude");
            name=extras.getString("name");
            address=extras.getString("address");
        }

        TextView name_text=(TextView)findViewById(R.id.name_textView);
        TextView address_text=(TextView)findViewById(R.id.address_textview);
        name_text.setText(name);
        address_text.setText(address);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_station_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void start_navagate(View view){
        Uri uri = Uri.parse("http://maps.google.com/maps?q=loc:"+latitude + "," + longitude);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }
}
