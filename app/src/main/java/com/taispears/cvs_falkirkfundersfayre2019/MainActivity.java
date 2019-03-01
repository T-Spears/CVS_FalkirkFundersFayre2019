package com.taispears.cvs_falkirkfundersfayre2019;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    //expandable list management
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    final CharSequence notificationText = "Are you attending the Crowdfunder Workshop at 12:30? If you have not yet registered please do so at reception!";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        TextView twitter = (TextView) findViewById(R.id.twitterLink);
        twitter.setMovementMethod(LinkMovementMethod.getInstance());



        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_Layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // notification text is set as Char sequence in order to display bigger notification
        //coudn't be bothered to change the method
        showNotification("Falkirk Funders Fayre", "");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_Layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent backPressed = new Intent(this, MainActivity.class);
            startActivity(backPressed);
            finish();
        }



//        @Override
//        public boolean onCreateOptionsMenu (Menu menu){
//
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//
//            //noinspection SimplifiableIfStatement
//            if (id == R.id.i1) {
//                return true;
//            }
//
//            return super.onOptionsItemSelected(item);
//        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       Button homeButton = findViewById(R.id.home);
       homeButton.setOnClickListener(new View.OnClickListener() {
          @Override           public void onClick(View v) {
              Intent homeButtonIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(homeButtonIntent);
                finish();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_Layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Home", true, false, "menu1");
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Exhibitors", true, true, "exhibitors");
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Funders", false, false, "exhibitors_funders");
        childModelsList.add(childModel);

        childModel = new MenuModel("Third Sector Support", false, false,"exhibitors_3s");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            Log.d("API123", "here");
            childList.put(menuModel, childModelsList);
        }


        menuModel = new MenuModel("Funding Top Tips", true, false, "fundingtt");
        headerList.add(menuModel);
        menuModel = new MenuModel("Map", true, false, "map");
        headerList.add(menuModel);
        menuModel = new MenuModel("Contact", true, false, "contact");
        headerList.add(menuModel);
        menuModel = new MenuModel("Credits", true, false, "credits");
        headerList.add(menuModel);



        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {

                    // changing the header links

                    if (!headerList.get(groupPosition).hasChildren) {

                        if (headerList.get(groupPosition).getItemID() == "menu1") {


                                    startActivity( new Intent(v.getContext(), MainActivity.class));
                                    finish();
                                    onBackPressed();

                        } else if (headerList.get(groupPosition).getItemID() == "fundingtt") {

                            getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                                    new FragmentFundingTips()).commit();
                            onBackPressed();

                        }else if (headerList.get(groupPosition).getItemID() == "map") {

                            getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                                    new FragmentMap()).commit();
                            onBackPressed();

                        }else if (headerList.get(groupPosition).getItemID() == "contact") {

                            getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                                    new FragmentContact()).commit();
                            onBackPressed();


                        }else if (headerList.get(groupPosition).getItemID() == "credits") {


                            getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                                    new FragmentCredits()).commit();
                            onBackPressed();

                        }
                    }

                }

                return false;
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {

                    if (childList.get(headerList.get(groupPosition)).get(childPosition).getItemID() =="exhibitors_funders") {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                                new FragmentExhibitorsFunders()).commit();
                                onBackPressed();
                    }
                    else if (childList.get(headerList.get(groupPosition)).get(childPosition).getItemID() =="exhibitors_3s") {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                                new FragmentExhibitors3s()).commit();
                        onBackPressed();
                    }
                }

                    return false;
                }

            });

    }

    void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "channel1",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("desc");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")

                .setSmallIcon(R.drawable.fffvectorlogo) // notification icon

                .setSmallIcon(R.drawable.fffvectorlogo)
                .setTicker("Falkirk Funders Fayre")
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)

                .setContentTitle(title) // title for notification
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))

                .setAutoCancel(true); // clear notification after click

        Intent intent= new Intent();// ne Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0); // PendingIntent.FLAG_UPDATE_CURRENT instead of last 0
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }


}





