package in.weclub.srmweclubapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        showData();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.partners:
                Intent it = new Intent(Profile.this, Partners.class);
                startActivity(it);
                Bundle b = new Bundle();
                b.putInt("Position", p);
                break;
            case R.id.webLink2:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.weclub.in/"));
                startActivity(browserIntent);
                break;
            case R.id.eventsUp:
                Intent it1 = new Intent(Profile.this, UpcomingEvents.class);
                startActivity(it1);
                b = new Bundle();
                b.putInt("Position", p);
                break;
        }
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showData() {
        DatabaseHelper dh = new DatabaseHelper(Profile.this);
        Cursor res = dh.getProile();
        TextView name = findViewById(R.id.nameProf);
        TextView mobNo = findViewById(R.id.mobNo2);
        TextView email = findViewById(R.id.email2);
        TextView Uid = findViewById(R.id.UID);
        TextView hName = findViewById(R.id.holderName);
        ImageView qrCode = findViewById(R.id.QRCode2);
        try
        {
            Bundle b = getIntent().getExtras();
            p = b.getInt("Position");
    }catch(Exception ex)

    {
        p = -1;
    }
        res.moveToPosition(p);
        /*while (res.moveToNext()) {
            if(res.getString(4).equals(it.getStringExtra("MobNo"))){*/
            String fullName = res.getString(1) + " " + res.getString(2);
            name.setText(fullName);
            hName.setText(fullName);
            Uid.setText(res.getString(0));
            email.setText(res.getString(3));
            mobNo.setText(res.getString(4));
            /*break;
        }
        }*/
        MultiFormatWriter mfw = new MultiFormatWriter();
        try
        {
            BitMatrix bm = mfw.encode(Uid.getText().toString()
                    , BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder be = new BarcodeEncoder();
            Bitmap i = be.createBitmap(bm);
            qrCode.setImageBitmap(i);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
    }
}
