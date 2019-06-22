package com.mobileaders.penguinprofession.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileaders.penguinprofession.Adapter.MenuAdapter;
import com.mobileaders.penguinprofession.Fragment.FragmentAlert;
import com.mobileaders.penguinprofession.Fragment.FragmentProfile;
import com.mobileaders.penguinprofession.Fragment.HandicapFragment;
import com.mobileaders.penguinprofession.Fragment.HomeFragment;
import com.mobileaders.penguinprofession.Fragment.Massage_Delete;
import com.mobileaders.penguinprofession.Fragment.StarFragment;
import com.mobileaders.penguinprofession.Module.MenuModel;
import com.mobileaders.penguinprofession.R;
import com.mobileaders.penguinprofession.Utilities.Fonts;
import com.mobileaders.penguinprofession.Utilities.URLS;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerMenu;
    ArrayList<MenuModel> menuModelList;
    MenuAdapter adapter;
    ImageView iSearch, iHome, iStar, iHandicap, iNotification, iUserProfile;
    FrameLayout searchLayout;
    boolean isOpen = false;
    boolean isClicked = false;
    AppBarLayout appBarLayout;
    LinearLayout layout, lContainer;
    RelativeLayout content;
    int height;
    Toolbar toolbar;
    DrawerLayout drawer;
    LinearLayout linearLayout;
    EditText eSearch, eCityState;
    TextView t10, t11, t12, t13, tUsername, tProfession;
    Button bFullTime, bPartTime, bFreeLance, bInternShip, bRelocate, bDistance;
    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;

        Log.e("height", "in DP" + dpHeight);

        setUpReference();

//        setUpFonts();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setNavDrawer();

        setMenuData();

        getWindowHeight();

        setUpClick();

        showFragment();

    }

    //    setup Views
    private void setUpReference() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = (RelativeLayout) findViewById(R.id.content_home);
        iSearch = (ImageView) findViewById(R.id.search);
        searchLayout = (FrameLayout) findViewById(R.id.searchLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        layout = (LinearLayout) findViewById(R.id.pictLayout);
        linearLayout = (LinearLayout) findViewById(R.id.linearSearch);
        eSearch = (EditText) findViewById(R.id.edit_search);
        eCityState = (EditText) findViewById(R.id.edit_cityState);
        t10 = (TextView) findViewById(R.id.t10);
        t11 = (TextView) findViewById(R.id.t11);
        t12 = (TextView) findViewById(R.id.t12);
        t13 = (TextView) findViewById(R.id.t13);
        tUsername = (TextView) findViewById(R.id.userMenuName);
        tProfession = (TextView) findViewById(R.id.profession);
        bFullTime = (Button) findViewById(R.id.button_full_time);
        bPartTime = (Button) findViewById(R.id.button_part_time);
        bFreeLance = (Button) findViewById(R.id.button_freelance);
        bInternShip = (Button) findViewById(R.id.button_internship);
        bRelocate = (Button) findViewById(R.id.button_relocate);
        bDistance = (Button) findViewById(R.id.button_distance);

        iHome = (ImageView) findViewById(R.id.home);
        iStar = (ImageView) findViewById(R.id.favourite);
        iHandicap = (ImageView) findViewById(R.id.handicap);
        iNotification = (ImageView) findViewById(R.id.notification);
        iUserProfile = (ImageView) findViewById(R.id.user);
        lContainer = (LinearLayout) findViewById(R.id.container);
    }

    private void setUpFonts() {
//        Fonts.set(new TextView[]{t10, t11, t12, t13, tUsername, tProfession}, Home.this, 1);
        Fonts.set(new EditText[]{eSearch, eCityState}, Home.this, 1);
        Fonts.set(new Button[]{bFullTime, bPartTime, bFreeLance, bInternShip, bRelocate, bDistance}, Home.this, 1);
    }

    //    set NavDrawer to Toolbar
    private void setNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //   set RecyclerView to nav Drawer
    public void setMenuData() {
        recyclerMenu = (RecyclerView) findViewById(R.id.recycler_menu);
        recyclerMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        menuModelList = new ArrayList<>();
        int[] images = {R.drawable.blog, R.drawable.message, R.drawable.setting, R.drawable.about,
                R.drawable.faq, R.drawable.logout};

        final String[] texts = {"Blogger", "Message", "Setting", "About", "Faq", "Logout"};

        for (int i = 0; i < texts.length && i < images.length; i++) {
            MenuModel menuModel = new MenuModel();

            menuModel.setText(texts[i]);
            menuModel.setImage(images[i]);
            menuModelList.add(menuModel);


        }
        Log.e("xxxx", "" + menuModelList.size());
        adapter = new MenuAdapter(Home.this, menuModelList);

        recyclerMenu.setAdapter(adapter);
        adapter.setOnClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onclick(int position) {
//                String text = texts[position];
//                Toast.makeText(Home.this, text, Toast.LENGTH_SHORT).show();
//                switch (position){
//                    case 0:
//                     //   searchLayout
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.container, new Massage_Delete(),"Massage delete")
//                                .commit();
//                        break;
//
//
//                }
            }
        });

    }

    //    get mobile screen height
    private void getWindowHeight() {
        Display ss = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        ss.getSize(size);
        height = size.y;
    }


    //    convert dp to pixel
    private static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    //    set height to appbar when change
    private void setAppbarHeight(int pixelHeight) {

        ViewGroup.LayoutParams appBarParams = appBarLayout.getLayoutParams();
        appBarParams.height = pixelHeight;
        appBarLayout.setLayoutParams(appBarParams);
    }


    //    set changes to toolbar content Gravity
    private void setToolbarChanges() {
        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
        layoutParams.height = (int) convertDpToPixel(80, Home.this);
        toolbar.setLayoutParams(layoutParams);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER | Gravity.RIGHT;
        linearLayout.setLayoutParams(params);

    }


    //      handle Each click
    private void setUpClick() {
        //        open search frame
        iSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eSearch.setVisibility(View.VISIBLE);
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                homeFragment.searchJob(URLS.userJob);

                if (isOpen == false) {
                    isOpen = true;
                    float v = convertDpToPixel(110, getApplicationContext());
                    setAppbarHeight((int) v);
                    searchLayout.setVisibility(View.VISIBLE);

                }
            }
        });

        // open Collapse toolbar
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setToolbarChanges();
                isClicked = true;
                if (isClicked == true) {
                    float v = (float) (height * 0.76);
                    setAppbarHeight((int) v);
                    searchLayout.setEnabled(false);

                }

            }
        });

        //        when appbar height change
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBar, int verticalOffset) {

                Log.e("tool", "" + toolbar.getY() + "  tool bar " + toolbar.getHeight());

                Log.e("tool", "" + appBarLayout.getY() + " app bar  " + appBarLayout.getHeight());

                Log.e("height", "" + height);

                if (isOpen == true) {

                    layout.setY(layout.getY() - appBar.getTotalScrollRange());
                }
                if (isClicked == true) {

                    float x = (height - appBar.getHeight());
                    float z = (x * 1) / 3;
                    float w = (x - z);
                    float q = w + 9;
                    layout.setY(q);
                }
                if (verticalOffset < 0) {
                    float x = (height - appBar.getHeight());
                    float z = (x * 1) / 3;
                    float w = (x - z);
                    float q = w + 9;
                    layout.setY(q - verticalOffset);
                }

            }
        });

    }

    /* show Home Fragment at first
        replace Fragments when click
        */
    private void showFragment() {
        if (state == 0) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment(), "homeFragment").commit();
            iHome.setImageResource(R.drawable.home_active);
            state = 1;
        }
        iHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                iHome.setImageResource(R.drawable.home_active);

                iStar.setImageResource(R.drawable.star);
                iHandicap.setImageResource(R.drawable.handicap);
                iNotification.setImageResource(R.drawable.notification);
                iUserProfile.setImageResource(R.drawable.user);
            }
        });
        iStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new StarFragment()).commit();
                iStar.setImageResource(R.drawable.star_active);
                iHome.setImageResource(R.drawable.home);
                iHandicap.setImageResource(R.drawable.handicap);
                iNotification.setImageResource(R.drawable.notification);
                iUserProfile.setImageResource(R.drawable.user);
            }
        });
        iHandicap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HandicapFragment()).commit();
                iHandicap.setImageResource(R.drawable.handicap_active);
                iStar.setImageResource(R.drawable.star);
                iHome.setImageResource(R.drawable.home);
                iNotification.setImageResource(R.drawable.notification);
                iUserProfile.setImageResource(R.drawable.user);
            }
        });

        iNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentAlert()).commit();
                iNotification.setImageResource(R.drawable.notification_active);
                iHandicap.setImageResource(R.drawable.handicap);
                iStar.setImageResource(R.drawable.star);
                iHome.setImageResource(R.drawable.home);
                iUserProfile.setImageResource(R.drawable.user);
            }
        });
        iUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new FragmentProfile()).commit();
                iUserProfile.setImageResource(R.drawable.user_active);
                iNotification.setImageResource(R.drawable.notification);
                iHandicap.setImageResource(R.drawable.handicap);
                iStar.setImageResource(R.drawable.star);
                iHome.setImageResource(R.drawable.home);
            }
        });

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

        return true;
    }


}
