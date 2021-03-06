package com.example.opgrad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

final public class MainActivity extends AppCompatActivity {
    private AlertDialog negativeDialog;

//    server url
    static public String mainurl = "45.120.69.135";
//    static public String mainurl = "192.168.219.105";
    static public String insertUrl         = "http://"+mainurl+"/distance.php";
    static public String insertFavoriteUrl = "http://"+mainurl+"/insertFavorite.php";
    static public String deleteFavoriteUrl = "http://"+mainurl+"/deleteFavorite.php";
    static public String memberUrl         = "http://"+mainurl+"/members.php";
    static public String searchAddressUrl  = "http://"+mainurl+"/searchAddress.php";
    static public String sendteamUrl       = "http://"+mainurl+"/sendteam.php";
    static public String receiveteamUrl    = "http://"+mainurl+"/receiveteam.php";
    static public String updateUrl         = "http://"+mainurl+"/update.php";
    static public String recommendUrl      = "http://"+mainurl+"/recommendTeam.php";
    static public String insertTeamUrl     = "http://"+mainurl+"/insertTeam.php";
    
    static public String mainposterUrl     = "http://"+mainurl+"/mainposter.php";
    static public String curtcompUrl       = "http://"+mainurl+"/curtcomp.php";
    static public String favoriteUrl       = "http://"+mainurl+"/favorite.php";
    static public String detailUrl       = "http://"+mainurl+"/detail.php";
    static public String loginUrl       = "http://"+mainurl+"/login.php";
    static public String registerUrl       = "http://"+mainurl+"/register.php";
    static public String comidUrl       = "http://"+mainurl+"/comid.php";

    static public URLConnector conn;
    static public URLConnector postconn;
    static public String result;
    static public String postresult;


    //?????????(User) member_id
    static public int userID = -1;

    //????????? ?????? ?????? ?????????
    ConnectivityManager connectivityManager;

    /******************************* drawer ?????? ?????? *********************************************************************/
    //Layout??? width, height, weight, margin (=parameters) ??????
    private LinearLayout.LayoutParams params;
    private ViewGroup.LayoutParams viewParams;

    private DrawerLayout drawerLayout;
    private View drawerView;

    //drawer ImageView
    private ImageView homeImage;
    private ImageView loginImage;
    private ImageView correctionImage;
    private ImageView logoutImage;
    private ImageView closeImage;

    private RelativeLayout interestArea;
    private RelativeLayout target;
    private RelativeLayout host;
    private RelativeLayout awards;

    //?????? ??????
    public boolean isClicked_interestArea=false;
    public boolean isCLicked_target=false;
    public boolean isClicked_host=false;
    public boolean isClicked_awards=false;

    //ListView
    public ListView interestAreaList;
    public ListView targetList;
    public ListView hostList;
    public ListView awardsList;

    //ArrayList
    public List<Menu> interestAreaArrayList;
    public List<Menu> targetArrayList;
    public List<Menu> hostArrayList;
    public List<Menu> awardsArrayList;

    //Adapter
    public MenuListAdapter interestAreaAdapter;
    public MenuListAdapter targetAdapter;
    public MenuListAdapter hostAdapter;
    public MenuListAdapter awardsAdapter;

    //more or less ImageView
    public ImageView moreInterestAreaImage;
    public ImageView moreTargetImage;
    public ImageView moreHostImage;
    public ImageView moreAwardsImage;

    //Layout
    public LinearLayout interestAreaLayout;
    public LinearLayout targetLayout;
    public LinearLayout hostLayout;
    public LinearLayout awardsLayout;
    /******************************* drawer ?????? ??? ***********************************************************************/

    //?????? ?????????
    private LinearLayout searchWindow;
    private ImageButton searchBtn;
    private ImageButton cancelBtn;
    private EditText searchTitle;
    private String searchKey;

    private ViewPager viewPager;
    private static Handler autoSlideHandler;

    private autoSlideRunnable slideThread;
    private Thread autoSlideThread;

    private ListView compList;
    private CompListAdapter compAdapter;
    private List<Comp> compArrayList;

    private ScrollView scrollView;

    // ????????? ??????, ????????? ??????
    static String[] getBlob;
    static String[] getNo;
    static int list_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        // ????????? ?????? ?????? - ???????????? ?????? ??????, ?????? ??? ????????? ??? ??????
        checkInternetState();

        /************************************666666666666666666666***********************************************************/
        compList = (ListView) findViewById(R.id.compList_main);
        compArrayList = new ArrayList<Comp>();

        View header = getLayoutInflater().inflate(R.layout.list_header, null, false);
        TextView more = (TextView) header.findViewById(R.id.more_list_header);
        TextView headerTitle = (TextView) header.findViewById(R.id.title_list_header);

        headerTitle.setOnClickListener(new TextView.OnClickListener() {
            //empty clickListener
            @Override
            public void onClick(View v) {}
        });
        more.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                intent.putExtra("key", " More");
                startActivity(intent);
            }
        });

        compAdapter = new CompListAdapter(getApplicationContext(), compArrayList);
        compList.setAdapter(compAdapter);

        compList.addHeaderView(header);

        compList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Comp item = (Comp) adapterView.getItemAtPosition(position);
                int selected_item_id = item.getcomp_id();

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("key", selected_item_id);
                startActivity(intent);
            }
        });
        /************************************666666666666666666666***********************************************************/

        /***************************************************************************************************************************/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_main);
        drawerView = (View) findViewById(R.id.drawerLayout_drawer);

        ImageView drawer = (ImageView) findViewById(R.id.drawer_top_bar);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        searchBtn = (ImageButton) findViewById(R.id.searchBtn_top_bar);
        searchBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                searchWindow = (LinearLayout)inflater.inflate(R.layout.search_window, null);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                addContentView(searchWindow, params);

                searchTitle = (EditText) findViewById(R.id.searchTitle_search_window);
                searchTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        searchKey = searchTitle.getText().toString();

                        if ( searchKey.isEmpty() ) {
                            Toast.makeText(getApplicationContext(), "????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                            intent.putExtra("key", "search "+searchKey);
                            startActivity(intent);
                        }
                        return true;
                    }
                });
                cancelBtn = (ImageButton) findViewById(R.id.cancelBtn_search_window);
                cancelBtn.setOnClickListener(new ImageButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchWindow.removeAllViews();
                    }
                });
            }
        });
        /************************************111111111111111111111***********************************************************/

        // ????????? ????????? ???????????? php ??????
        postconn = new URLConnector(mainposterUrl);
        postconn.start();
        try {
            postconn.join();
        } catch(InterruptedException e) {

        }
        postresult = postconn.getResult();

        try {
            JSONObject proot = new JSONObject(postresult);
            JSONArray pja = proot.getJSONArray("poster");
            list_cnt = pja.length();
            getBlob = new String[4];
            for(int i = 0; i < 4; i++)
            {
                // ????????? ????????? ????????? String?????? ????????? ????????? ??????
                JSONObject pjo = pja.getJSONObject(list_cnt-i-1);
                getBlob[i] = pjo.getString("poster_jpg");
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        saveBitmapToJpeg(StringToBitmap(getBlob[0]),"main1");
        saveBitmapToJpeg(StringToBitmap(getBlob[1]),"main2");
        saveBitmapToJpeg(StringToBitmap(getBlob[2]),"main3");
        saveBitmapToJpeg(StringToBitmap(getBlob[3]),"main4");

        // ?????? ???????????? ?????? bitmap ??????
        ArrayList<String> listImage = new ArrayList<>();
        File file = new File(getCacheDir().toString());
        File[] files = file.listFiles();
        for(File tempFile : files){
            if(tempFile.getName().contains("main")){
                listImage.add(getCacheDir()+"/"+tempFile.getName());
            }
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager_main);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        // ViewPager???  FragmentAdapter ??????
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setClipToPadding(false);
        //float dpValue = getResources().getDisplayMetrics().widthPixels / 8;
        //float density = getResources().getDisplayMetrics().density;
        //int margin = (int) (dpValue * density / 2);
        float dpValue = getResources().getDisplayMetrics().widthPixels / 4;
        int margin = (int) (dpValue / 2);
        viewPager.setPadding(margin * 3 / 2, 0, margin * 3 / 2, 0);
        viewPager.setPageMargin(margin / 3);

        // FragmentAdapter??? Fragment ??????, Image*1000 ????????? ??????
        for (int i = 0; i < listImage.size() * 1000; i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("imgRes", listImage.get(i % 4));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }
        fragmentAdapter.notifyDataSetChanged();

        LinearLayout viewPagerLayout = (LinearLayout) findViewById(R.id.viewPagerLayout_main);
        viewPagerLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDisplayMetrics().widthPixels * 4 / 5));

        /*
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    flag = true;
                if(event.getAction() == MotionEvent.ACTION_MOVE)
                    flag = false;
                if(event.getAction() == MotionEvent.ACTION_UP && flag == true) {
                    if(x < getResources().getDisplayMetrics().widthPixels / 10) {   //?????? ???????????? ????????? ??????
                        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                    } else if(x < getResources().getDisplayMetrics().widthPixels * 8 / 10) {    //?????? ???????????? ????????? ??????
                        Toast.makeText(getApplicationContext(), viewPager.getCurrentItem() % 4 + 1 + "??? ????????? ??????!", Toast.LENGTH_SHORT).show();
                    } else {    //?????? ???????????? ????????? ??????
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                }
                return false;
            }
        });
         */

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {  //?????? ??? ??????????????? ????????? ????????? ????????? ???????????? ????????? ??? ???????????? ??????, ???????????? ????????? ????????? ????????? ????????? ????????? ????????? ???????????? ?????? ??????????????? ??????.

            }
            @Override
            public void onPageSelected(int position) {  //???????????? ????????? ?????? ??? ???????????? ?????????

                ImageButton firstBtn = (ImageButton)findViewById(R.id.firstBtn_main);
                ImageButton secondBtn = (ImageButton)findViewById(R.id.secondBtn_main);
                ImageButton thirdBtn = (ImageButton)findViewById(R.id.thirdBtn_main);
                ImageButton fourthBtn = (ImageButton)findViewById(R.id.fourthBtn_main);

                switch(position%4) {
                    case 0:
                        firstBtn.setImageResource(R.drawable.blue_point);
                        secondBtn.setImageResource(R.drawable.gray_point);
                        thirdBtn.setImageResource(R.drawable.gray_point);
                        fourthBtn.setImageResource(R.drawable.gray_point);
                        break;
                    case 1:
                        firstBtn.setImageResource(R.drawable.gray_point);
                        secondBtn.setImageResource(R.drawable.blue_point);
                        thirdBtn.setImageResource(R.drawable.gray_point);
                        fourthBtn.setImageResource(R.drawable.gray_point);
                        break;
                    case 2:
                        firstBtn.setImageResource(R.drawable.gray_point);
                        secondBtn.setImageResource(R.drawable.gray_point);
                        thirdBtn.setImageResource(R.drawable.blue_point);
                        fourthBtn.setImageResource(R.drawable.gray_point);
                        break;
                    case 3:
                        firstBtn.setImageResource(R.drawable.gray_point);
                        secondBtn.setImageResource(R.drawable.gray_point);
                        thirdBtn.setImageResource(R.drawable.gray_point);
                        fourthBtn.setImageResource(R.drawable.blue_point);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {   //???????????? ????????? ?????? ??? ???????????? ????????? 0:IDLE(??????) 1:DRAGGING(????????? ?????? ???) 2:SETTING(??????)

            }
        });

        //viewPager ??? ?????????????????? ???????????? ???????????? ?????? handler
        autoSlideHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        };
        /*************************************111111111111111111111111**********************************************************/

        /*************************************333333333333333333333333**********************************************************/

        ImageButton firstBtn = (ImageButton) findViewById(R.id.firstBtn_main);
        ImageButton secondBtn = (ImageButton) findViewById(R.id.secondBtn_main);
        ImageButton thirdBtn = (ImageButton) findViewById(R.id.thirdBtn_main);
        ImageButton fourthBtn = (ImageButton) findViewById(R.id.fourthBtn_main);

        firstBtn.setOnClickListener(movePageListener);
        secondBtn.setOnClickListener(movePageListener);
        thirdBtn.setOnClickListener(movePageListener);
        fourthBtn.setOnClickListener(movePageListener);

        firstBtn.setTag(0);
        secondBtn.setTag(1);
        thirdBtn.setTag(2);
        fourthBtn.setTag(3);

        viewPager.setOffscreenPageLimit(2);

        /*************************************333333333333333333333333**********************************************************/

        /*************************************5555555555555555555555555**********************************************************/

        ImageButton recommendBtn = (ImageButton) findViewById(R.id.recommendBtn_main);
        ImageButton manageBtn = (ImageButton) findViewById(R.id.manageBtn_main);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        // ????????????
        alertDialogBuilder.setTitle("???????????? ????????? ??????????????????.");
        // AlertDialog ??????
        alertDialogBuilder
                .setMessage("????????? ???????????????????")
                .setCancelable(false)
                .setPositiveButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("??????",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        final AlertDialog alertDialog = alertDialogBuilder.create();

        recommendBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID != -1) {
                    Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog.show();
                }
            }
        });

        manageBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID != -1) {
                    Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                    startActivity(intent);
                } else {
                    alertDialog.show();
                }
            }
        });
        /*************************************5555555555555555555555555**********************************************************/
        homeImage = (ImageView) findViewById(R.id.homeImage_drawer);
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
                scrollView.smoothScrollTo(0,0);
            }
        });

        loginImage = findViewById(R.id.loginImage_drawer);
        loginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        correctionImage = findViewById(R.id.correctionImage_drawer);
        correctionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CorrectionActivity.class);
                startActivity(intent);
            }
        });

        logoutImage = findViewById(R.id.logoutImage_drawer);
        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = -1;
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        closeImage = (ImageView) findViewById(R.id.closeImage_drawer);
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                drawerLayout.closeDrawers();
            }
        });


        /*************************************?????? ????????? ??????**********************************************************/
        interestAreaList = (ListView) findViewById(R.id.interestAreaList_drawer);
        targetList = (ListView) findViewById(R.id.targetList_drawer);
        hostList = (ListView) findViewById(R.id.hostList_drawer);
        awardsList = (ListView) findViewById(R.id.awardsList_drawer);

        interestAreaArrayList = new ArrayList<Menu>();
        targetArrayList = new ArrayList<Menu>();
        hostArrayList = new ArrayList<Menu>();
        awardsArrayList = new ArrayList<Menu>();

        interestAreaAdapter = new MenuListAdapter(getApplicationContext(), interestAreaArrayList);
        targetAdapter = new MenuListAdapter(getApplicationContext(), targetArrayList);
        hostAdapter = new MenuListAdapter(getApplicationContext(), hostArrayList);
        awardsAdapter = new MenuListAdapter(getApplicationContext(), awardsArrayList);

        moreInterestAreaImage = (ImageView) findViewById(R.id.moreInterestAreaImage_drawer);
        moreTargetImage = (ImageView) findViewById(R.id.moreTargetImage_drawer);
        moreHostImage = (ImageView) findViewById(R.id.moreHostImage_drawer);
        moreAwardsImage = (ImageView) findViewById(R.id.moreAwardsImage_drawer);

        interestArea = (RelativeLayout) findViewById(R.id.interestArea_drawer);
        target = (RelativeLayout) findViewById(R.id.target_drawer);
        host = (RelativeLayout) findViewById(R.id.host_drawer);
        awards = (RelativeLayout) findViewById(R.id.awards_drawer);

        interestAreaLayout = (LinearLayout) findViewById(R.id.interestAreaLayout_drawer);
        targetLayout = (LinearLayout) findViewById(R.id.targetLayout_drawer);
        hostLayout = (LinearLayout) findViewById(R.id.hostLayout_drawer);
        awardsLayout = (LinearLayout) findViewById(R.id.awardsLayout_drawer);

        //ListView??? Adapter??????
        interestAreaList.setAdapter(interestAreaAdapter);
        targetList.setAdapter(targetAdapter);
        hostList.setAdapter(hostAdapter);
        awardsList.setAdapter(awardsAdapter);

        //ItemClickListener
        interestAreaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                //       Toast.makeText(getApplicationContext(),selected_item,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                intent.putExtra("key", "field "+selected_item);
                startActivity(intent);
                drawerLayout.closeDrawer(drawerView);
            }
        });
        targetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                //  Toast.makeText(getApplicationContext(),selected_item,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                intent.putExtra("key", "application "+selected_item);
                startActivity(intent);
                drawerLayout.closeDrawer(drawerView);
            }
        });
        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                // Toast.makeText(getApplicationContext(),selected_item,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                intent.putExtra("key", "host "+selected_item);
                startActivity(intent);
                drawerLayout.closeDrawer(drawerView);
            }
        });
        awardsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                //  Toast.makeText(getApplicationContext(),selected_item,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                intent.putExtra("key", "awards "+selected_item);
                startActivity(intent);
                drawerLayout.closeDrawer(drawerView);
            }
        });

        //moreImageClickListener
        interestArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked_interestArea == false) {
                    interestAreaOpen();
                    targetClose();
                    hostClose();
                    awardsClose();
                }
                else {
                    interestAreaClose();
                }
            }
        });
        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCLicked_target == false) {
                    interestAreaClose();
                    targetOpen();
                    hostClose();
                    awardsClose();
                } else {
                    targetClose();
                }
            }
        });
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked_host == false) {
                    interestAreaClose();
                    targetClose();
                    hostOpen();
                    awardsClose();
                } else {
                    hostClose();
                }
            }
        });
        awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked_awards == false) {
                    interestAreaClose();
                    targetClose();
                    hostClose();
                    awardsOpen();
                } else {
                    awardsClose();
                }
            }
        });

        /***************************PHP ?????? ???*******************************************************/
        // ???????????? ??????
        conn = new URLConnector(curtcompUrl);
        conn.start();
        try {
            conn.join();
        } catch(InterruptedException e) {

        }
        result = conn.getResult();
        // ???????????? ???
        try {
            JSONObject root = new JSONObject(result);
            JSONArray comInfo = root.getJSONArray("compresult");     //????????????, ?????????, ????????? ?????? json ??????

            for (int i = comInfo.length()-1; i >comInfo.length()-6 ; i--) {
                JSONObject jsonObject = comInfo.getJSONObject(i);
                // Pulling items from the array
                String item1 = jsonObject.getString("title");
                String item2 = jsonObject.getString("period_start");
                String item3 = jsonObject.getString("period_end");
                String item4 = jsonObject.getString("field");

                int item5 = jsonObject.getInt("competition_id");

                String dday=calDDay(item3);
                compArrayList.add(new Comp(item1, item4, item2 , item3, dday, item5));
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawerLayout.closeDrawers();
        if(searchWindow != null)
            searchWindow.removeAllViews();

        //????????? ????????? ??? ?????? ?????? ????????? ???????????? ??? ?????? ?????? ????????????.
        scrollView = findViewById(R.id.scrollView_main);
        scrollView.smoothScrollTo(0,0);

        compList.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        viewPager.setCurrentItem(40);

        slideThread = new autoSlideRunnable();
        autoSlideThread = new Thread(slideThread);
        autoSlideThread.start();

        if(userID == -1) {
            //loginImage width = wrap, logoutImage width = 0
            viewParams = (ViewGroup.LayoutParams) loginImage.getLayoutParams();
            viewParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            loginImage.setLayoutParams(viewParams);
            viewParams = (ViewGroup.LayoutParams) logoutImage.getLayoutParams();
            viewParams.width = 0;
            logoutImage.setLayoutParams(viewParams);
        } else {
            //loginImage width = 0, logoutImage width = wrap
            viewParams = (ViewGroup.LayoutParams) loginImage.getLayoutParams();
            viewParams.width = 0;
            loginImage.setLayoutParams(viewParams);
            viewParams = (ViewGroup.LayoutParams) logoutImage.getLayoutParams();
            viewParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            logoutImage.setLayoutParams(viewParams);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        slideThread.isRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    class autoSlideRunnable implements Runnable {
        boolean isRunning = true;

        @Override
        public void run() {
            while(isRunning) {
                try {
                    //2.5?????????
                    Thread.sleep(2500);
                    //handler??? ?????? ??? ????????? ??????
                    autoSlideHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void menuOpen() {
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    }

    void menuClose() {
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 0;
        params.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
    }

    void interestAreaOpen() {
        menuOpen();
        interestAreaLayout.setLayoutParams(params);
        addInterestArea();                                           //???????????? ????????? ??????
        interestAreaAdapter.notifyDataSetChanged();                           //????????? ??????
        moreInterestAreaImage.setImageResource(R.drawable.ic_expand_less_black_24dp);  //+?????? ???????????? - ????????????
        isClicked_interestArea = true;
    }
    void interestAreaClose() {
        menuClose();
        interestAreaLayout.setLayoutParams(params);
        interestAreaArrayList.clear();
        interestAreaAdapter.notifyDataSetChanged();
        moreInterestAreaImage.setImageResource(R.drawable.ic_expand_more_black_24dp);
        isClicked_interestArea = false;
    }

    void targetOpen() {
        menuOpen();
        targetLayout.setLayoutParams(params);
        addTarget();          //???????????? ????????? ??????
        targetAdapter.notifyDataSetChanged();         //????????? ??????
        moreTargetImage.setImageResource(R.drawable.ic_expand_less_black_24dp); //+?????? ???????????? - ????????????
        isCLicked_target = true;
    }
    void targetClose() {
        menuClose();
        targetLayout.setLayoutParams(params);
        targetArrayList.clear();
        targetAdapter.notifyDataSetChanged();
        moreTargetImage.setImageResource(R.drawable.ic_expand_more_black_24dp);
        isCLicked_target = false;
    }

    void hostOpen() {
        menuOpen();
        hostLayout.setLayoutParams(params);
        addHost();                                               //???????????? ????????? ??????
        hostAdapter.notifyDataSetChanged();                     //????????? ??????
        moreHostImage.setImageResource(R.drawable.ic_expand_less_black_24dp);  //+?????? ???????????? - ????????????
        isClicked_host = true;
    }
    void hostClose() {
        menuClose();
        hostLayout.setLayoutParams(params);
        hostArrayList.clear();
        hostAdapter.notifyDataSetChanged();
        moreHostImage.setImageResource(R.drawable.ic_expand_more_black_24dp);
        isClicked_host = false;
    }

    void awardsOpen() {
        menuOpen();
        awardsLayout.setLayoutParams(params);
        addAwards();                  //???????????? ????????? ??????
        awardsAdapter.notifyDataSetChanged();  //????????? ??????
        moreAwardsImage.setImageResource(R.drawable.ic_expand_less_black_24dp);     //+?????? ???????????? - ????????????
        isClicked_awards = true;
    }
    void awardsClose() {
        menuClose();
        awardsLayout.setLayoutParams(params);
        awardsArrayList.clear();
        awardsAdapter.notifyDataSetChanged();
        moreAwardsImage.setImageResource(R.drawable.ic_expand_more_black_24dp);
        isClicked_awards = false;
    }

    void addInterestArea() {            //???????????? ????????? ??????
        interestAreaArrayList.add(new Menu("??????/????????????"));
        interestAreaArrayList.add(new Menu("??????/?????????"));
        interestAreaArrayList.add(new Menu("??????/?????????"));
        interestAreaArrayList.add(new Menu("??????/UCC/??????"));
        interestAreaArrayList.add(new Menu("?????????/?????????/??????"));
        interestAreaArrayList.add(new Menu("???/?????????/?????????"));
        interestAreaArrayList.add(new Menu("??????/???????????????"));
        interestAreaArrayList.add(new Menu("??????/??????"));
        interestAreaArrayList.add(new Menu("??????/???/????????????"));
        interestAreaArrayList.add(new Menu("??????/??????/????????????"));
        interestAreaArrayList.add(new Menu("?????????/?????????"));
        interestAreaArrayList.add(new Menu("?????????/??????/??????"));
        interestAreaArrayList.add(new Menu("????????????/????????????"));
        interestAreaArrayList.add(new Menu("????????????"));
        interestAreaArrayList.add(new Menu("??????/??????"));
        interestAreaArrayList.add(new Menu("??????"));
    }
    void addTarget() {              //??????????????? ????????? ??????
        targetArrayList.add(new Menu("??????"));
        targetArrayList.add(new Menu("????????????"));
        targetArrayList.add(new Menu("?????????"));
        targetArrayList.add(new Menu("?????????"));
        targetArrayList.add(new Menu("?????????"));
        targetArrayList.add(new Menu("?????????"));
        targetArrayList.add(new Menu("??????"));
    }
    void addHost() {                  //????????? ????????? ??????
        hostArrayList.add(new Menu("??????"));
        hostArrayList.add(new Menu("??????/????????????"));
        hostArrayList.add(new Menu("?????????"));
        hostArrayList.add(new Menu("?????????"));
        hostArrayList.add(new Menu("??????/??????/??????"));
        hostArrayList.add(new Menu("???????????????"));
        hostArrayList.add(new Menu("??????/??????/????????????"));
        hostArrayList.add(new Menu("?????????/??????/??????"));
        hostArrayList.add(new Menu("??????"));
        hostArrayList.add(new Menu("??????"));
    }
    void addAwards() {                //???????????? ????????? ??????
        awardsArrayList.add(new Menu("??????"));
        awardsArrayList.add(new Menu("5???????????????"));
        awardsArrayList.add(new Menu("5?????????~3?????????"));
        awardsArrayList.add(new Menu("3?????????~1?????????"));
        awardsArrayList.add(new Menu("1???????????????"));
        awardsArrayList.add(new Menu("????????? ??????"));
        awardsArrayList.add(new Menu("??????"));
    }

    static public String calDDay( String endDate) {

        Calendar today=Calendar.getInstance();
        long calDateDays=0;
        String day="";

        try { // String Type??? Date Type?????? ?????????????????? ????????? ????????? ?????? ????????? ???????????? ????????? ????????? ?????????????????? ????????? ???????????? ???????????? ??? ??? ??????.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // date1, date2 ??? ????????? parse()??? ?????? Date????????? ??????.
            Date FirstDate = new Date(today.getTimeInMillis());
            Date SecondDate = format.parse(endDate);

            // Date??? ????????? ??? ????????? ????????? ??? ??? ??????????????? long type ????????? ????????? ?????? ??????.
            // ???????????? -950400000. long type ?????? return ??????.
            long calDate = SecondDate.getTime()/ ( 24*60*60*1000) - FirstDate.getTime()/ ( 24*60*60*1000);

            // Date.getTime() ??? ??????????????? ????????????1970??? 00:00:00 ?????? ??? ?????? ??????????????? ???????????????.
            // ?????? 24*60*60*1000(??? ???????????? ?????? ?????????) ??? ???????????? ????????? ?????????.
            calDateDays = calDate;
            //Toast.makeText(getApplicationContext(),format.format(FirstDate),Toast.LENGTH_SHORT).show();
        } catch(ParseException e) {
            // ?????? ??????
        }

        if(calDateDays>0)
            day="D-"+Long.toString(calDateDays);
        else if(calDateDays==0)
            day="D-day";
        else {
            calDateDays=Math.abs(calDateDays);
            day="D+"+Long.toString(calDateDays);
        }
        return day;
    }

    private Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void saveBitmapToJpeg(Bitmap bitmap, String name){
        File storage = getCacheDir();
        String fileName = name + ".jpg";
        File tempFile = new File(storage,fileName);
        try{
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile,false);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.close();
        }catch (FileNotFoundException e){
            Log.e("MyTag","FileNotFoundException : " + e.getMessage());
        }catch (IOException e) {
            Log.e("MyTag","IOException : " + e.getMessage());
        }
    }

    /*************************************444444444444444444444444444**********************************************************/
    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentNum = viewPager.getCurrentItem()%4;
            int tag = 0;
            if((int)v.getTag() == currentNum)
                tag = viewPager.getCurrentItem();
            else if((int)v.getTag() == (currentNum+1)%4)
                tag = viewPager.getCurrentItem()+1;
            else if((int)v.getTag() == (currentNum+2)%4)
                tag = viewPager.getCurrentItem()+2;
            else //if((int)v.getTag() == (currentNum+3)%4)
                tag = viewPager.getCurrentItem()-1;
            viewPager.setCurrentItem(tag);
        }
    };

    /*************************************444444444444444444444444444**********************************************************/

    /******************************************2222222222222222222222*****************************************************/
    class FragmentAdapter extends FragmentPagerAdapter {

        // ViewPager??? ????????? Fragment?????? ?????? ?????????
        private ArrayList<Fragment> fragments = new ArrayList<>();

        // ?????? ?????????
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // List??? Fragment??? ?????? ??????
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    }
    /**********************************************22222222222222222222222222222*************************************************/

    public void showNegativeDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        negativeDialog = builder.setMessage(msg)
                .setNegativeButton("??????", null)
                .create();
        negativeDialog.show();
        return;
    }

    private void checkInternetState(){
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        if(!(connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected())){
            new AlertDialog.Builder(this)
                    .setMessage("????????? ?????? ?????? ?????? ?????????????????? ??????????????????.")
                    .setCancelable(false)
                    .setPositiveButton("??????",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            finishAffinity();
                        }
                    }).show();
        }
    }
}