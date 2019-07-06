package com.app.bookkeeping;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.Adapt.AccountAdapt;
import com.app.Adapt.BillAdapt;
import com.app.dao.Dao;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    final static int MIAN_ACTIVITY = 1;
    final static int LIST_ACTIVITY = 2;
    final static int SPECAIL_ACTIVITY = 3;

    private int the_activity = 1;

    BottomNavigationView bottomNavigationView;
    FloatingActionButton btn_add_a_bill;
    View main_activuty,list_activity,specail_activity;
    TextView add_card;
    ListView AssetList,BillList,AllBills;
    AccountAdapt accountAdapt;
    BillAdapt billAdapt,AllBillAdapt;
    AssetsEntify allOftheAsset;
    private void init(){

        if(the_activity == MIAN_ACTIVITY){
            Dao dao = new Dao();
            List<AssetsEntify> ListOfAssets;
            List<BillEntify> ListOfBills;

            accountAdapt = new AccountAdapt(MainActivity.this);
            billAdapt = new BillAdapt(MainActivity.this);


            ListOfAssets = dao.getAssets(MainActivity.this);
            ListOfBills = dao.getBills(MainActivity.this,10);

            if(ListOfAssets.size() != 0){
                allOftheAsset = ListOfAssets.get(0);
                ListOfAssets.remove(0);
            }

            accountAdapt.setList(ListOfAssets);
            billAdapt.setList(ListOfBills);
            billAdapt.setAssetList(ListOfAssets);


            AssetList.setAdapter(accountAdapt);
            BillList.setAdapter(billAdapt);
            setListViewHeightBasedOnChildren(AssetList);
            setListViewHeightBasedOnChildren(BillList);
        }else if(the_activity == LIST_ACTIVITY){
            Dao dao = new Dao();
            List<AssetsEntify> ListOfAssets;
            List<BillEntify> ListOfAllBills;
            AllBillAdapt = new BillAdapt(MainActivity.this);
            ListOfAssets = dao.getAssets(MainActivity.this);
            ListOfAllBills = dao.getBills(MainActivity.this,0);
            ListOfAssets.remove(0);
            AllBillAdapt.setList(ListOfAllBills);
            AllBillAdapt.setAssetList(ListOfAssets);

            AllBills.setAdapter(AllBillAdapt);
        }

    }




    public void setListen(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        main_activuty.setVisibility(View.VISIBLE);
                        list_activity.setVisibility(View.GONE);
                        specail_activity.setVisibility(View.GONE);
                        the_activity = MIAN_ACTIVITY;
                        btn_add_a_bill.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_list:
                        main_activuty.setVisibility(View.GONE);
                        list_activity.setVisibility(View.VISIBLE);
                        specail_activity.setVisibility(View.GONE);
                        the_activity = LIST_ACTIVITY;
                        init();
                        btn_add_a_bill.setVisibility(View.VISIBLE);
                        break;
                    case R.id.navigation_special:
                        main_activuty.setVisibility(View.GONE);
                        list_activity.setVisibility(View.GONE);
                        specail_activity.setVisibility(View.VISIBLE);
                        the_activity = SPECAIL_ACTIVITY;
                        init();
                        btn_add_a_bill.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });

        AssetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountAdapt accountAdapt = (AccountAdapt)AssetList.getAdapter();
                AssetsEntify assetsEntify = accountAdapt.getItem(position);
                Intent intent = new Intent(MainActivity.this,AssetDetail.class);
                intent.putExtra("Asset",assetsEntify);
                startActivity(intent);
            }
        });


        BillList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillAdapt billAdapt = (BillAdapt)BillList.getAdapter();
                BillEntify billEntify = billAdapt.getItem(position);
                AssetsEntify assetsEntify = billAdapt.getAsset(position);
                Intent intent = new Intent(MainActivity.this,MyDetail.class);
                intent.putExtra("Bill",billEntify);
                if(billEntify.getFrom() != 0){
                    intent.putExtra("Asset",assetsEntify);
                }
                startActivity(intent);
            }
        });

        AllBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BillAdapt billAdapt = (BillAdapt)AllBills.getAdapter();
                BillEntify billEntify = billAdapt.getItem(position);
                AssetsEntify assetsEntify = billAdapt.getAsset(position);
                Intent intent = new Intent(MainActivity.this,MyDetail.class);
                intent.putExtra("Bill",billEntify);
                if(billEntify.getFrom() != 0){
                    intent.putExtra("Asset",assetsEntify);
                }
                startActivity(intent);
            }
        });

        btn_add_a_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickAdd();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


        add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnClickNewAsset();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_activuty = findViewById(R.id.include_main);
        list_activity = findViewById(R.id.include_list);
        specail_activity = findViewById(R.id.include_special);
        bottomNavigationView = findViewById(R.id.navigation);
        btn_add_a_bill = findViewById(R.id.btn_add_a_bill);
        add_card = findViewById(R.id.txt_add_card);
        AssetList = findViewById(R.id.AssetList);
        BillList = findViewById(R.id.BillList);
        AllBills = findViewById(R.id.Billlist_of_all);
        init();
        setListen();
        Dao dao = new Dao();
        dao.Creat(MainActivity.this);
        Intent intent = new Intent(this, AutoReceiver.class);
        intent.setAction("VIDEO_TIMER");
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // AlarmManager.ELAPSED_REALTIME_WAKEUP表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用相对时间
        // SystemClock.elapsedRealtime()表示手机开始到现在经过的时间
//        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime(), 10 * 1000, sender);
        long triggerAtTime = System.currentTimeMillis() + 10 * 1000;
        manager.set(AlarmManager.RTC_WAKEUP, triggerAtTime, sender);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void OnClickAdd(){
        Intent intent = new Intent(MainActivity.this,NewBill.class);
        startActivity(intent);
    }

    public void OnClickList(){
        Intent intent = new Intent(MainActivity.this,MyDetail.class);
        startActivity(intent);
    }

    public void OnClickNewAsset(){
        Intent intent = new Intent(MainActivity.this,NewAsset.class);
        startActivity(intent);

    }

    public void OnClickAssetDetial(){
        Intent intent = new Intent(MainActivity.this,AssetDetail.class);
        startActivity(intent);

    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }




}
