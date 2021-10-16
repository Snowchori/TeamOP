package com.example.opgrad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MoreActivity extends AppCompatActivity {
    /******************************* drawer 선언 시작 *********************************************************************/
    //Layout의 width, height, weight, margin (=parameters) 저장
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
    private TextView listName;

    //상단 검색창
    private LinearLayout searchWindow;
    private ImageButton searchBtn;
    private ImageButton cancelBtn;

    private ListView compList;
    private CompListAdapter compAdapter;
    private List<Comp> compArrayList;

    // 공모전 표 배열 저장
    int listNum=1;
    int listUnit=10;      //리스트 표에 보여줄 갯수
    int listLength=0;    //총 공모전 수 /리스트 표에 보여줄 갯수
    int listRemain=0;       // listLength의 나머지 수  unit 보다 작음

    Button firstPageBtn;
    Button secondPageBtn;
    Button thirdPageBtn;
    Button fourthPageBtn;
    Button fifthPageBtn;
    Button previousPageBtn;
    Button nextPageBtn;

    EditText searchTitle;

    String getKey;
    CustomCircleProgressDialog customCircleProgressDialog; //로딩화면
    JSONArray compInfo=new JSONArray();
    boolean remain;

    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        overridePendingTransition(0, 0);
        customCircleProgressDialog = new CustomCircleProgressDialog(MoreActivity.this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_more);
        drawerView = (View) findViewById(R.id.drawerLayout_drawer);

        ImageView drawer = (ImageView) findViewById(R.id.drawer_top_bar);
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        getKey = getIntent().getStringExtra("key");

        title = (TextView) findViewById(R.id.title_top_bar);
        title.setText("More List View");

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

                        String searchKey = searchTitle.getText().toString();

                        if ( searchKey.isEmpty() ) {
                            Toast.makeText(getApplicationContext(), "공모전 명을 입력해주세요", Toast.LENGTH_SHORT).show();
                            return true;
                        } else {
                            getKey="search "+searchKey;
                            asyncComlist a = new asyncComlist();
                            a.execute();
                            listName.setText("Search Result by \"" + getKey.split(" ")[1] + "\"");
                            drawerLayout.closeDrawer(drawerView);


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

        homeImage = (ImageView) findViewById(R.id.homeImage_drawer);
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loginImage = findViewById(R.id.loginImage_drawer);
        loginImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        correctionImage = findViewById(R.id.correctionImage_drawer);
        correctionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreActivity.this, CorrectionActivity.class);
                startActivity(intent);
            }
        });

        logoutImage = findViewById(R.id.logoutImage_drawer);
        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.userID = -1;
                Intent intent = new Intent(MoreActivity.this, MoreActivity.class);
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
                getKey="field "+selected_item;
                asyncComlist a = new asyncComlist();
                a.execute();

                listName.setText(getKey.split(" ")[1] + " List");
                drawerLayout.closeDrawer(drawerView);



            }
        });
        targetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {
                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                getKey="application "+selected_item;
                asyncComlist a = new asyncComlist();
                a.execute();

                listName.setText(getKey.split(" ")[1] + " List");
                drawerLayout.closeDrawer(drawerView);


            }
        });
        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                getKey="host "+selected_item;
                asyncComlist a = new asyncComlist();
                a.execute();

                listName.setText(getKey.split(" ")[1] + " List");
                drawerLayout.closeDrawer(drawerView);


            }
        });
        awardsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Menu item = (Menu) adapterView.getItemAtPosition(position);
                String selected_item = item.getItem();
                getKey="awards "+selected_item;
                asyncComlist a = new asyncComlist();
                a.execute();

                listName.setText(getKey.split(" ")[1] + " List");
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


        /************************************666666666666666666666***********************************************************/
        compList = (ListView) findViewById(R.id.compList_more);
        compArrayList = new ArrayList<Comp>();

        View footer = getLayoutInflater().inflate(R.layout.list_footer, null, false);
        View header = getLayoutInflater().inflate(R.layout.list_detail_header, null, false);

        listName = (TextView) header.findViewById(R.id.listName_detail_header);
        if(getKey.split(" ")[0].contains("search")) {
            listName.setText("Search Result by \"" + getKey.split(" ")[1] + "\"");
        } else {
            listName.setText(getKey.split(" ")[1] + " List");
        }
        listName.setOnClickListener(new TextView.OnClickListener() {
            //empty clickListener
            @Override
            public void onClick(View v) {}
        });

        compAdapter = new CompListAdapter(getApplicationContext(), compArrayList);

        compList.setAdapter(compAdapter);
        compList.addFooterView(footer);
        compList.addHeaderView(header);


        firstPageBtn = (Button) footer.findViewById(R.id.firstBtn_list_footer);
        secondPageBtn = (Button) footer.findViewById(R.id.secondBtn_list_footer);
        thirdPageBtn = (Button) footer.findViewById(R.id.thirdBtn_list_footer);
        fourthPageBtn = (Button) footer.findViewById(R.id.fourthBtn_list_footer);
        fifthPageBtn = (Button) footer.findViewById(R.id.fifthBtn_list_footer);
        previousPageBtn = (Button) footer.findViewById(R.id.previousBtn_list_footer);
        nextPageBtn = (Button) footer.findViewById(R.id.nextBtn_list_footer);

        firstPageBtn.setTag("0");
        secondPageBtn.setTag("1");
        thirdPageBtn.setTag("2");
        fourthPageBtn.setTag("3");
        fifthPageBtn.setTag("4");
        previousPageBtn.setTag("back");
        nextPageBtn.setTag("next");

        firstPageBtn.setOnClickListener(PageBtnClickListener);
        secondPageBtn.setOnClickListener(PageBtnClickListener);
        thirdPageBtn.setOnClickListener(PageBtnClickListener);
        fourthPageBtn.setOnClickListener(PageBtnClickListener);
        fifthPageBtn.setOnClickListener(PageBtnClickListener);
        previousPageBtn.setOnClickListener(PageBtnClickListener);
        nextPageBtn.setOnClickListener(PageBtnClickListener);

        handler = new Handler() {
            public void handleMessage(Message msg){
                firstPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
            }
        };

        //공모전 클릭 시
        compList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                Comp item = (Comp) adapterView.getItemAtPosition(position);
                int selected_item_id = item.getcomp_id();

                Intent intent = new Intent(MoreActivity.this, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("key", selected_item_id);
                startActivity(intent);
            }
        });
        /************************************666666666666666666666***********************************************************/

        asyncComlist asyncComlist = new asyncComlist();
        asyncComlist.execute();

        /*************************************5555555555555555555555555**********************************************************/
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

    class asyncComlist extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCircleProgressDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
            setcompList();  // 공모전 페이지 세팅 데이터갱신만
            return  null;
        }

        @Override
        protected void onPostExecute(Void num) {   //UI작업은 onPostExecute에서 함.
            super.onPostExecute(num);
            compArrayList.clear();
            try{
                if(listLength==0){          //공모전이 1페이지미만일 경우 listRemain만큼만 리스트 추가
                    for (int i=0;i<listRemain;i++) {
                        JSONObject jsonObject = compInfo.getJSONObject(i);
                        // Pulling items from the array
                        String item1 = jsonObject.getString("title");
                        String item2 = jsonObject.getString("period_start");
                        String item3 = jsonObject.getString("period_end");
                        String item4 = jsonObject.getString("field");

                        int item5 = jsonObject.getInt("competition_id");
                        String dday = calDDay(item3);
                        compArrayList.add(new Comp(item1, item4, item2, item3, dday,item5));
                    }
                }
                else {
                    for (int i = 0; i < listUnit; i++) {
                        JSONObject jsonObject = compInfo.getJSONObject(i);
                        // Pulling items from the array
                        String item1 = jsonObject.getString("title");
                        String item2 = jsonObject.getString("period_start");
                        String item3 = jsonObject.getString("period_end");
                        String item4 = jsonObject.getString("field");

                        int item5 = jsonObject.getInt("competition_id");
                        String dday = calDDay(item3);
                        compArrayList.add(new Comp(item1, item4, item2, item3, dday, item5));
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            complowpage(); //리스트페이지가 5페이지 이하일 경우 footer UI 변경
            compAdapter.notifyDataSetChanged();
            customCircleProgressDialog.dismiss();
        }

    }



    public String calDDay( String enddate) {

        Calendar today=Calendar.getInstance();
        long calDateDays=0;
        String day="";

        try{ // String Type을 Date Type으로 캐스팅하면서 생기는 예외로 인해 여기서 예외처리 해주지 않으면 컴파일러에서 에러가 발생해서 컴파일을 할 수 없다.
            SimpleDateFormat format = new     SimpleDateFormat("yyyy-MM-dd");
            // date1, date2 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = new Date(today.getTimeInMillis());
            Date SecondDate = format.parse(enddate);

            // Date로 변환된 두 날짜를 계산한 뒤 그 리턴값으로 long type 변수를 초기화 하고 있다.
            // 연산결과 -950400000. long type 으로 return 된다.
            long calDate = SecondDate.getTime()/ ( 24*60*60*1000) - FirstDate.getTime()/ ( 24*60*60*1000);

            // Date.getTime() 은 해당날짜를 기준으로1970년 00:00:00 부터 몇 초가 흘렀는지를 반환해준다.
            // 이제 24*60*60*1000(각 시간값에 따른 차이점) 을 나눠주면 일수가 나온다.
            calDateDays = calDate;
        } catch(ParseException e) {
            // 예외 처리
        }

        if(calDateDays>0)
            day="D-"+Long.toString(calDateDays);
        else if(calDateDays==0)
            day="D-day";
        else{
            calDateDays=Math.abs(calDateDays);
            day="D+"+Long.toString(calDateDays);
        }
        return day;
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

    private void complowpage(){   //리스트페이지가 5페이지 이하일 경우 각 버튼의 보이기 설정
        listNum=1;
        if(listLength<6){
            if(listRemain==0) {
                switch (listLength ) {
                    case 5:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        fourthPageBtn.setText(Integer.toString(listNum + 3));
                        fifthPageBtn.setText(Integer.toString(listNum + 4));

                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.VISIBLE);
                        fifthPageBtn.setVisibility(View.VISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        fourthPageBtn.setText(Integer.toString(listNum + 3));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.VISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.INVISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        firstPageBtn.setText(Integer.toString(listNum));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.INVISIBLE);
                        thirdPageBtn.setVisibility(View.INVISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        firstPageBtn.setVisibility(View.INVISIBLE);
                        secondPageBtn.setVisibility(View.INVISIBLE);
                        thirdPageBtn.setVisibility(View.INVISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                }
            } else {
                switch (listLength ) {
                    case 5:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        fourthPageBtn.setText(Integer.toString(listNum + 3));
                        fifthPageBtn.setText(Integer.toString(listNum + 4));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.VISIBLE);
                        fifthPageBtn.setVisibility(View.VISIBLE);
                        nextPageBtn.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        fourthPageBtn.setText(Integer.toString(listNum + 3));
                        fifthPageBtn.setText(Integer.toString(listNum + 4));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.VISIBLE);
                        fifthPageBtn.setVisibility(View.VISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        fourthPageBtn.setText(Integer.toString(listNum + 3));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.VISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        thirdPageBtn.setText(Integer.toString(listNum + 2));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum + 1));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.INVISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                    case 0:
                        firstPageBtn.setText(Integer.toString(listNum));
                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.INVISIBLE);
                        thirdPageBtn.setVisibility(View.INVISIBLE);
                        fourthPageBtn.setVisibility(View.INVISIBLE);
                        fifthPageBtn.setVisibility(View.INVISIBLE);
                        nextPageBtn.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        }
        else{
            firstPageBtn.setText(Integer.toString(listNum));
            secondPageBtn.setText(Integer.toString(listNum + 1));
            thirdPageBtn.setText(Integer.toString(listNum + 2));
            fourthPageBtn.setText(Integer.toString(listNum + 3));
            fifthPageBtn.setText(Integer.toString(listNum + 4));
            firstPageBtn.setVisibility(View.VISIBLE);
            secondPageBtn.setVisibility(View.VISIBLE);
            thirdPageBtn.setVisibility(View.VISIBLE);
            fourthPageBtn.setVisibility(View.VISIBLE);
            fifthPageBtn.setVisibility(View.VISIBLE);
            nextPageBtn.setVisibility(View.VISIBLE);
        }
    }

    private  void setcompList(){

        try {
            JSONObject root = new JSONObject(MainActivity.result);
            JSONArray comInfo = root.getJSONArray("compresult");     //공모전명, 시작일, 종료일 담긴 json 배열
            //JSONArray compInfo=new JSONArray();
            while (compInfo.length()>0)
                compInfo.remove(0); // 조건에 만족하는 공모전 JSON배열

            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);

            for (int k = comInfo.length()-1; k >=0; k--) {   //조건에 만족하는 모든 공모전을 찾음.
                JSONObject jsonObject = comInfo.getJSONObject(k);
                switch(getKey.split(" ")[0]){
                    default:
                        compInfo.put(jsonObject);
                        break;
                    case "field":
                        if(jsonObject.getString("field").contains(getKey.split(" ")[1])==true)
                            compInfo.put(jsonObject);
                        break;
                    case "application":
                        if(jsonObject.getString("application").contains(getKey.split(" ")[1])==true)
                            compInfo.put(jsonObject);
                        else if(getKey.split(" ")[1].equals("전체"))
                            compInfo.put(jsonObject);
                        break;
                    case "host":
                        if(jsonObject.getString("competition_host").contains(getKey.split(" ")[1])==true){
                            compInfo.put(jsonObject);}
                        else if(getKey.split(" ")[1].equals("전체"))
                            compInfo.put(jsonObject);
                        break;
                    case "awards":
                        int n=0;
                        String[] award={"5천만원이상","5천만원~3천만원","3천만원~1천만원","1천만원이하","다양한 혜택"};
                        for(int i=0;i<5;i++)
                            if(award[i].equals(jsonObject.getString("total_money"))==true)
                                n++;
                        if(jsonObject.getString("total_money").contains(getKey.split(" ")[1])==true)
                            compInfo.put(jsonObject);
                        else if(n==0)
                            compInfo.put(jsonObject);
                        else if(getKey.split(" ")[1].equals("전체"))
                            compInfo.put(jsonObject);
                        break;
                    case "search":
                        if(jsonObject.getString("title").contains(getKey.split(" ")[1])==true)
                            compInfo.put(jsonObject);
                        break;

                }
            }
            listLength=compInfo.length()/listUnit;   //조건에 맞는 공모전 수/ 표에 나타날 공모전 수
            listRemain=compInfo.length()%listUnit;   //listLength의 나머지 수
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    class asyncComlistChange extends AsyncTask<Integer,Integer,Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCircleProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            //Your stuff
            int num = (int)params[0];
            try {
                JSONObject root = new JSONObject(MainActivity.result);
                JSONArray comInfo = root.getJSONArray("compresult");     //공모전명, 시작일, 종료일 담긴 json 배열
                int listnum=0;

                if(num>listLength*listUnit+listRemain) { //리스트에 보일 수가 10개 이하
                    remain=true;
                    num=listLength*listUnit+listRemain;//num이 공모전수보다 크면 num을 공모전 수만큼 으로 설정
                    //  compInfo=new JSONArray();     //조건에 만족하는 공모전 JSON배열
                    while (compInfo.length()>0)
                        compInfo.remove(0);
                    for (int k = comInfo.length()-1; k >=0; k--) {   //조건에 만족하는 모든 공모전을 찾음.
                        JSONObject jsonObject = comInfo.getJSONObject(k);
                        switch(getKey.split(" ")[0]){
                            default:
                                compInfo.put(jsonObject);
                                break;
                            case "field":
                                if(jsonObject.getString("field").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                break;
                            case "application":
                                if(jsonObject.getString("application").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                else if(getKey.split(" ")[1].equals("전체"))
                                    compInfo.put(jsonObject);
                                break;
                            case "host":
                                if(jsonObject.getString("competition_host").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                else if(getKey.split(" ")[1].equals("전체"))
                                    compInfo.put(jsonObject);
                                break;
                            case "awards":
                                int n=0;
                                String[] award={"5천만원이상","5천만원~3천만원","3천만원~1천만원","1천만원이하","다양한 혜택"};
                                for(int i=0;i<5;i++)
                                    if(award[i].equals(jsonObject.getString("total_money"))==true)
                                        n++;
                                if(jsonObject.getString("total_money").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                else if(n==0)
                                    compInfo.put(jsonObject);
                                else if(getKey.split(" ")[1].equals("전체"))
                                    compInfo.put(jsonObject);
                                break;
                            case "search":
                                if(jsonObject.getString("title").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                break;

                        }
                    }

                }
                else{ //리스트에 보일 수가 listUnit만큼 존재
                    remain=false;
                    //JSONArray compInfo=new JSONArray();     //조건에 만족하는 공모전 JSON배열
                    while (compInfo.length()>0)
                        compInfo.remove(0);
                    for (int k = comInfo.length()-1; k >=0; k--) {   //조건에 만족하는 모든 공모전을 찾음.
                        JSONObject jsonObject = comInfo.getJSONObject(k);
                        switch(getKey.split(" ")[0]){
                            default:
                                compInfo.put(jsonObject);
                                break;
                            case "field":
                                if(jsonObject.getString("field").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                break;
                            case "application":
                                if(jsonObject.getString("application").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                else if(getKey.split(" ")[1].equals("전체"))
                                    compInfo.put(jsonObject);
                                break;
                            case "host":
                                if(jsonObject.getString("competition_host").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                else if(getKey.split(" ")[1].equals("전체"))
                                    compInfo.put(jsonObject);
                                break;
                            case "awards":
                                int n=0;
                                String[] award={"5천만원이상","5천만원~3천만원","3천만원~1천만원","1천만원이하","다양한 혜택"};
                                for(int i=0;i<5;i++)
                                    if(award[i].equals(jsonObject.getString("total_money"))==true)
                                        n++;
                                if(jsonObject.getString("total_money").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                else if(n==0)
                                    compInfo.put(jsonObject);
                                else if(getKey.split(" ")[1].equals("전체"))
                                    compInfo.put(jsonObject);
                                break;
                            case "search":
                                if(jsonObject.getString("title").contains(getKey.split(" ")[1])==true)
                                    compInfo.put(jsonObject);
                                break;

                        }
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return num;
        }

        @Override
        protected void onPostExecute(Integer num) {
            super.onPostExecute(num);
            compArrayList.clear();
            try{
                if(remain==true) {
                    for (int i = num - listRemain; i < num; i++)  //listRemain만큼 리스트에 추가
                    {
                        JSONObject jsonObject = compInfo.getJSONObject(i);
                        // Pulling items from the array
                        String item1 = jsonObject.getString("title");
                        String item2 = jsonObject.getString("period_start");
                        String item3 = jsonObject.getString("period_end");
                        String item4 = jsonObject.getString("field");

                        int item5 = jsonObject.getInt("competition_id");
                        String dday = calDDay(item3);

                        compArrayList.add(new Comp(item1, item4, item2, item3, dday, item5));
                        //    compAdapter.notifyDataSetChanged();
                        // compArrayList.set(listnum,new Comp(item1, item4, item2 , item3,dday));
                    }
                }
                else {
                    for (int i = num - listUnit; i < num; i++) {  //listUnit만큼 리스트 추가
                        JSONObject jsonObject = compInfo.getJSONObject(i);
                        // Pulling items from the array
                        String item1 = jsonObject.getString("title");
                        String item2 = jsonObject.getString("period_start");
                        String item3 = jsonObject.getString("period_end");
                        String item4 = jsonObject.getString("field");

                        int item5 = jsonObject.getInt("competition_id");
                        String dday = calDDay(item3);
                        compArrayList.add(new Comp(item1, item4, item2, item3, dday, item5));
                        // compArrayList.set(listnum,new Comp(item1, item4, item2 , item3,dday));

                        // compAdapter.notifyDataSetChanged();
                    }
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            compAdapter.notifyDataSetChanged();
            customCircleProgressDialog.dismiss();
        }
        /*@Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            compAdapter.notifyDataSetChanged();
        }*/
    }


    private void setCompListChange(int num) {          //공모전 리스트 페이지 변경

        asyncComlistChange task=new asyncComlistChange();
        task.execute(num);
    }

    View.OnClickListener PageBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String pbKey = (String) v.getTag();
            switch (pbKey)
            {
                case "0" :
                    setCompListChange(listNum*listUnit);
                    firstPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                    secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    break;

                case "1" :
                    setCompListChange((listNum+1)*listUnit);
                    firstPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    secondPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                    thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    break;

                case "2" :
                    setCompListChange((listNum+2)*listUnit);
                    firstPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    thirdPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                    fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    break;

                case "3" :
                    setCompListChange((listNum+3)*listUnit);
                    firstPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fourthPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                    fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    break;

                case "4" :
                    setCompListChange((listNum+4)*listUnit);
                    firstPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fifthPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                    break;

                case "back" :
                    if(listNum > 1) {
                        listNum -= 5;
                        setCompListChange(listNum*listUnit);
                        firstPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                        secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                        thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                        fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                        fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));

                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum+1));
                        thirdPageBtn.setText(Integer.toString(listNum+2));
                        fourthPageBtn.setText(Integer.toString(listNum+3));
                        fifthPageBtn.setText(Integer.toString(listNum+4));

                        firstPageBtn.setVisibility(View.VISIBLE);
                        secondPageBtn.setVisibility(View.VISIBLE);
                        thirdPageBtn.setVisibility(View.VISIBLE);
                        fourthPageBtn.setVisibility(View.VISIBLE);
                        fifthPageBtn.setVisibility(View.VISIBLE);
                        nextPageBtn.setVisibility(View.VISIBLE);
                    }
                    break;

                case "next" :
                    listNum += 5;
                    firstPageBtn.setBackgroundColor(Color.rgb(204, 204, 204));
                    secondPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    thirdPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fourthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));
                    fifthPageBtn.setBackgroundColor(Color.rgb(221, 221, 221));

                    if(listNum > listLength - 5) {
                        setCompListChange(listNum * listUnit);
                        if(listRemain==0) {
                            switch (listLength - listNum) {
                                case 4:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setText(Integer.toString(listNum + 3));
                                    fifthPageBtn.setText(Integer.toString(listNum + 4));
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 3:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setText(Integer.toString(listNum + 3));
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 2:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setVisibility(View.INVISIBLE);
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 1:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setVisibility(View.INVISIBLE);
                                    fourthPageBtn.setVisibility(View.INVISIBLE);
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 0:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setVisibility(View.INVISIBLE);
                                    thirdPageBtn.setVisibility(View.INVISIBLE);
                                    fourthPageBtn.setVisibility(View.INVISIBLE);
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                            }
                        } else {
                            switch (listLength - listNum) {
                                case 4:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setText(Integer.toString(listNum + 3));
                                    fifthPageBtn.setText(Integer.toString(listNum + 4));
                                    break;
                                case 3:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setText(Integer.toString(listNum + 3));
                                    fifthPageBtn.setText(Integer.toString(listNum + 4));
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 2:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setText(Integer.toString(listNum + 3));
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 1:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setText(Integer.toString(listNum + 2));
                                    fourthPageBtn.setVisibility(View.INVISIBLE);
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case 0:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setText(Integer.toString(listNum + 1));
                                    thirdPageBtn.setVisibility(View.INVISIBLE);
                                    fourthPageBtn.setVisibility(View.INVISIBLE);
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;
                                case -1:
                                    firstPageBtn.setText(Integer.toString(listNum));
                                    secondPageBtn.setVisibility(View.INVISIBLE);
                                    thirdPageBtn.setVisibility(View.INVISIBLE);
                                    fourthPageBtn.setVisibility(View.INVISIBLE);
                                    fifthPageBtn.setVisibility(View.INVISIBLE);
                                    nextPageBtn.setVisibility(View.INVISIBLE);
                                    break;

                            }
                        }
                    } else {
                        setCompListChange(listNum*listUnit);
                        firstPageBtn.setText(Integer.toString(listNum));
                        secondPageBtn.setText(Integer.toString(listNum+1));
                        thirdPageBtn.setText(Integer.toString(listNum+2));
                        fourthPageBtn.setText(Integer.toString(listNum+3));
                        fifthPageBtn.setText(Integer.toString(listNum+4));
                    }
                    break;
            }
        }
    };
}
