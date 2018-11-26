package com.codecaique3296.delivrenoapp.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codecaique3296.delivrenoapp.R;
import com.codecaique3296.delivrenoapp.backgroundtasks.MyService;
import com.codecaique3296.delivrenoapp.utils.Sesstion;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.resturant_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });
        findViewById(R.id.report_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportsActivity.class));
            }
        });
        findViewById(R.id.request_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RequestsActivity.class));
            }
        });
        findViewById(R.id.call_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CallActivity.class));
            }
        });

        View headerView = navigationView.getHeaderView(0);
        de.hdodenhof.circleimageview.CircleImageView drawerImage = (de.hdodenhof.circleimageview.CircleImageView)
                headerView.findViewById(R.id.draw_imageView);
        TextView drawerUsername = (TextView) headerView.findViewById(R.id.draw_name);
        drawerUsername.setText(Sesstion.getInstance(this).getTayar().getFirstname() + " "
                + Sesstion.getInstance(this).getTayar().getSecondname());
        TextView drawerAccount = (TextView) headerView.findViewById(R.id.draw_email);
        drawerAccount.setText(Sesstion.getInstance(this).getTayar().getEmail());
        if (Sesstion.getInstance(this).getTayar().getImage() != "") {
            Uri builtUri = Uri.parse(Sesstion.getInstance(this).getTayar().getImage()).buildUpon().build();
            Picasso.get().load(builtUri).placeholder(R.drawable.ert).into(drawerImage);
        }
        drawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        MyService myService = new MyService();
        if(isMyServiceRunning(myService.getClass())){
            Toast.makeText(this, "Tracking is running .", Toast.LENGTH_SHORT).show();
        }
        else {
            startService(new Intent(this, MyService.class));
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, RequestsActivity.class));
        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, ReportsActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, TermsActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, CallActivity.class));
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, ProblemsActivity.class));
        }
        else if (id == R.id.logout) {
          Sesstion.getInstance(this).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!Sesstion.getInstance(this).isLoggedIn()) {
            finish();
            Sesstion.getInstance(this).logout();
        }
    }

}
