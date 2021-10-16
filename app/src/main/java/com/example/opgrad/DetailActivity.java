package com.example.opgrad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DetailActivity extends AppCompatActivity {
    /******************************* drawer 선언 시작 *********************************************************************/
    //Layout의 width, height, weight, margin (=parameters) 저장
    private LinearLayout.LayoutParams params;
    private ViewGroup.LayoutParams viewParams;

    CustomCircleProgressDialog customCircleProgressDialog;

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

    //클릭 여부
    private boolean isClicked_interestArea=false;
    private boolean isCLicked_target=false;
    private boolean isClicked_host=false;
    private boolean isClicked_awards=false;

    //ListView
    private ListView interestAreaList;
    private ListView targetList;
    private ListView hostList;
    private ListView awardsList;

    //ArrayList
    private List<Menu> interestAreaArrayList;
    private List<Menu> targetArrayList;
    private List<Menu> hostArrayList;
    private List<Menu> awardsArrayList;

    //Adapter
    private MenuListAdapter interestAreaAdapter;
    private MenuListAdapter targetAdapter;
    private MenuListAdapter hostAdapter;
    private MenuListAdapter awardsAdapter;

    //more or less ImageView
    private ImageView moreInterestAreaImage;
    private ImageView moreTargetImage;
    private ImageView moreHostImage;
    private ImageView moreAwardsImage;

    //Layout
    private LinearLayout interestAreaLayout;
    private LinearLayout targetLayout;
    private LinearLayout hostLayout;
    private LinearLayout awardsLayout;
    /******************************* drawer 선언 끝 ***********************************************************************/

    private TextView title;
    private TextView favorite;

    //상단 검색창
    private LinearLayout searchWindow;
    private ImageButton searchBtn;
    private ImageButton cancelBtn;
    private EditText searchTitle;
    private String searchKey;

    // 초리초리 시작
    private String myResult;
    private int list_cnt;
    // 초리초리 끝
    int getKey;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        overridePendingTransition(0, 0);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_detail);
        drawerView = (View) findViewById(R.id.drawerLayout_drawer);

        ImageView drawer = (ImageView) findViewById(R.id.drawer_top_bar);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        title = (TextView) findViewById(R.id.title_top_bar);
        title.setText("Detail View");

        favorite = (TextView) findViewById(R.id.favorite_detail);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite.getText().toString().equals("☆")) {
                    favorite.setText("★");

                    InsertData task = new InsertData();
                    task.execute(MainActivity.insertFavoriteUrl,Integer.toString(MainActivity.userID), Integer.toString(getKey));
                } else {
                    favorite.setText("☆");

                    InsertData task = new InsertData();
                    task.execute(MainActivity.deleteFavoriteUrl,Integer.toString(MainActivity.userID), Integer.toString(getKey));
                }
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
                            Toast.makeText(getApplicationContext(), "공모전 명을 입력해주세요", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            Intent intent = new Intent(DetailActivity.this, MoreActivity.class);
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
        /*************************************5555555555555555555555555**********************************************************/

        homeImage = (ImageView) findViewById(R.id.homeImage_drawer);
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loginImage = findViewById(R.id.loginImage_drawer);
        loginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        correctionImage = findViewById(R.id.correctionImage_drawer);
        correctionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, CorrectionActivity.class);
                startActivity(intent);
            }
        });

        logoutImage = findViewById(R.id.logoutImage_drawer);
        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userID = -1;
                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
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

        /*************************************메뉴 리스트 추가**********************************************************/
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

        //ListView에 Adapter연결
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

                Intent intent = new Intent(DetailActivity.this, MoreActivity.class);
                intent.putExtra("key", "field "+selected_item);
                startActivity(intent);
            }
        });
        targetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();

                Intent intent = new Intent(DetailActivity.this, MoreActivity.class);
                intent.putExtra("key", "application "+selected_item);
                startActivity(intent);
            }
        });
        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();

                Intent intent = new Intent(DetailActivity.this, MoreActivity.class);
                intent.putExtra("key", "host "+selected_item);
                startActivity(intent);
            }
        });
        awardsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();

                Intent intent = new Intent(DetailActivity.this, MoreActivity.class);
                intent.putExtra("key", "awards "+selected_item);
                startActivity(intent);
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

        getKey = getIntent().getIntExtra("key", 0);

        dataupdate task = new dataupdate();
        task.execute();

        /***************************************************************************************************************************/
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawerLayout.closeDrawers();
        if(searchWindow != null)
            searchWindow.removeAllViews();

        if(MainActivity.userID == -1) {
            //loginImage width = wrap, logoutImage width = 0
            viewParams = (ViewGroup.LayoutParams) loginImage.getLayoutParams();
            viewParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            loginImage.setLayoutParams(viewParams);
            viewParams = (ViewGroup.LayoutParams) logoutImage.getLayoutParams();
            viewParams.width = 0;
            logoutImage.setLayoutParams(viewParams);

            //favorite ☆ width = 0
            params = (LinearLayout.LayoutParams) favorite.getLayoutParams();
            params.width = 0;
            favorite.setLayoutParams(params);
        } else {
            //loginImage width = 0, logoutImage width = wrap
            viewParams = (ViewGroup.LayoutParams) loginImage.getLayoutParams();
            viewParams.width = 0;
            loginImage.setLayoutParams(viewParams);
            viewParams = (ViewGroup.LayoutParams) logoutImage.getLayoutParams();
            viewParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            logoutImage.setLayoutParams(viewParams);

            //favorite ☆ width = wrap
            params = (LinearLayout.LayoutParams) favorite.getLayoutParams();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            favorite.setLayoutParams(params);
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
        addInterestArea();                                           //리스트뷰 아이템 추가
        interestAreaAdapter.notifyDataSetChanged();                           //데이터 갱신
        moreInterestAreaImage.setImageResource(R.drawable.ic_expand_less_black_24dp);  //+버튼 이미지를 - 이미지로
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
        addTarget();          //리스트뷰 아이템 추가
        targetAdapter.notifyDataSetChanged();         //데이터 갱신
        moreTargetImage.setImageResource(R.drawable.ic_expand_less_black_24dp); //+버튼 이미지를 - 이미지로
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
        addHost();                                               //리스트뷰 아이템 추가
        hostAdapter.notifyDataSetChanged();                     //데이터 갱신
        moreHostImage.setImageResource(R.drawable.ic_expand_less_black_24dp);  //+버튼 이미지를 - 이미지로
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
        addAwards();                  //리스트뷰 아이템 추가
        awardsAdapter.notifyDataSetChanged();  //데이터 갱신
        moreAwardsImage.setImageResource(R.drawable.ic_expand_less_black_24dp);     //+버튼 이미지를 - 이미지로
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

    void addInterestArea() {            //관심분야 리스트 추가
        interestAreaArrayList.add(new Menu("기획/아이디어"));
        interestAreaArrayList.add(new Menu("광고/마케팅"));
        interestAreaArrayList.add(new Menu("논문/리포트"));
        interestAreaArrayList.add(new Menu("영상/UCC/사진"));
        interestAreaArrayList.add(new Menu("디자인/캐릭터/웹툰"));
        interestAreaArrayList.add(new Menu("웹/모바일/플래시"));
        interestAreaArrayList.add(new Menu("게임/소프트웨어"));
        interestAreaArrayList.add(new Menu("과학/공학"));
        interestAreaArrayList.add(new Menu("문학/글/시나리오"));
        interestAreaArrayList.add(new Menu("건축/건설/인테리어"));
        interestAreaArrayList.add(new Menu("네이밍/슬로건"));
        interestAreaArrayList.add(new Menu("예체능/미술/음악"));
        interestAreaArrayList.add(new Menu("대외활동/서포터즈"));
        interestAreaArrayList.add(new Menu("봉사활동"));
        interestAreaArrayList.add(new Menu("취업/창업"));
        interestAreaArrayList.add(new Menu("해외"));
    }
    void addTarget() {              //응시대상자 리스트 추가
        targetArrayList.add(new Menu("전체"));
        targetArrayList.add(new Menu("제한없음"));
        targetArrayList.add(new Menu("일반인"));
        targetArrayList.add(new Menu("대학생"));
        targetArrayList.add(new Menu("청소년"));
        targetArrayList.add(new Menu("어린이"));
        targetArrayList.add(new Menu("기타"));
    }
    void addHost() {                  //주최사 리스트 추가
        hostArrayList.add(new Menu("전체"));
        hostArrayList.add(new Menu("정부/공공기관"));
        hostArrayList.add(new Menu("공기업"));
        hostArrayList.add(new Menu("대기업"));
        hostArrayList.add(new Menu("신문/방송/언론"));
        hostArrayList.add(new Menu("외국계기업"));
        hostArrayList.add(new Menu("중견/중소/벤처기업"));
        hostArrayList.add(new Menu("비영리/협회/재단"));
        hostArrayList.add(new Menu("해외"));
        hostArrayList.add(new Menu("기타"));
    }
    void addAwards() {                //시상내역 리스트 추가
        awardsArrayList.add(new Menu("전체"));
        awardsArrayList.add(new Menu("5천만원이상"));
        awardsArrayList.add(new Menu("5천만원~3천만원"));
        awardsArrayList.add(new Menu("3천만원~1천만원"));
        awardsArrayList.add(new Menu("1천만원이하"));
        awardsArrayList.add(new Menu("다양한 혜택"));
        awardsArrayList.add(new Menu("기타"));
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

    class InsertData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
        }

        @Override
        protected String doInBackground(String... params) {

            int member_id = Integer.parseInt(params[1]);
            int competition_id = Integer.parseInt(params[2]);

            String serverURL = (String)params[0];
            String postParameters = "member_id=" + member_id + "&competition_id=" + competition_id;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setConnectTimeout(50000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }
        }
    }
    class dataupdate extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCircleProgressDialog =new CustomCircleProgressDialog(DetailActivity.this);
            customCircleProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            URLConnector conn = new URLConnector(MainActivity.curtcompUrl);
            conn.start();
            try {
                conn.join();
            } catch(InterruptedException e) {

            }
            MainActivity.result = conn.getResult();
            return  null;
        }

        @Override
        protected void onPostExecute(Void num) {   //UI작업은 onPostExecute에서 함.
            super.onPostExecute(num);
            // 초리초리 시작
            Thread t1 = new Thread(){
                public void run(){
                    myResult = HttpPostData();   // 서버와 자료 주고받기
                    // 요청 받은팀 정보 추가
                }
            };
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            try {
                JSONObject proot = new JSONObject(myResult);
                JSONArray pja = proot.getJSONArray("detail");
                list_cnt = pja.length();
                for (int i = 0; i < list_cnt; i++) {
                    JSONObject jsonObject = pja.getJSONObject(i);
                    if(jsonObject.getString("favorite").equals("yes")){
                        favorite.setText("★");
                    }

                    saveBitmapToJpeg(StringToBitmap(jsonObject.getString("poster_jpg")),"poster");

                    TextView title = (TextView)findViewById(R.id.title_detail);
                    TextView category = (TextView)findViewById(R.id.category_detail);
                    TextView target = (TextView)findViewById(R.id.target_detail);
                    TextView host = (TextView)findViewById(R.id.host_detail);
                    TextView donation = (TextView)findViewById(R.id.donation_detail);
                    TextView period = (TextView)findViewById(R.id.period_detail);
                    TextView totalPrizeMoney = (TextView)findViewById(R.id.totalPrizeMoney_detail);
                    TextView firstPrizeMoney = (TextView)findViewById(R.id.firstPrizeMoney_detail);
                    TextView homePage_detail = (TextView)findViewById(R.id.homePage_detail);
                    ImageView imageView1 = (ImageView) findViewById(R.id.poster_detail);

                    title.setText(jsonObject.getString("title"));
                    category.setText(jsonObject.getString("field"));
                    target.setText(jsonObject.getString("application"));
                    host.setText(jsonObject.getString("host"));
                    donation.setText(jsonObject.getString("support"));
                    period.setText(jsonObject.getString("period_start")+" ~ "+jsonObject.getString("period_end"));
                    totalPrizeMoney.setText(jsonObject.getString("total_money"));
                    firstPrizeMoney.setText(jsonObject.getString("first_place_money"));
                    homePage_detail.setText(jsonObject.getString("homepage"));

                    //하이퍼링크
                    final String homepageUrl = jsonObject.getString("homepage");
                    homePage_detail.setPaintFlags(homePage_detail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    homePage_detail.setOnClickListener(new TextView.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(homepageUrl));
                            startActivity(intent);
                        }
                    });

                    File file = new File(getCacheDir().toString());
                    File[] files = file.listFiles();
                    for(File tempFile : files){
                        if(tempFile.getName().contains("poster")){
                            imageView1.setImageURI(Uri.fromFile(new File((getCacheDir()+"/"+tempFile.getName()))));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // 초리초리 끝
            customCircleProgressDialog.dismiss();
        }

    }
    // 초리초리 시작
    public String HttpPostData() {
        HttpURLConnection http = null;
        String postParameters = "member_id=" + MainActivity.userID + "&competition_id=" + getKey; // 멤버 아이디, 공모전 번호 설정
        StringBuilder builder = new StringBuilder();;
        try {
            //--------------------------
            //   URL 설정하고 접속하기
            //--------------------------
            URL url = new URL(MainActivity.detailUrl);       // URL 설정
            http = (HttpURLConnection) url.openConnection();   // 접속

            http.setReadTimeout(50000);
            http.setConnectTimeout(50000);
            http.setRequestMethod("POST"); // POST 형식으로 설정
            http.connect();

            OutputStream outputStream = http.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            String str;
            while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
            }
        } catch (MalformedURLException e) {
            //
        } catch (IOException e) {
            //
        } // try
        return builder.toString();
    }
    // 초리초리 끝
}