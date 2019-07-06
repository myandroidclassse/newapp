package com.app.bookkeeping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.Adapt.AccountAdapt;
import com.app.Adapt.BillAdapt;
import com.app.dao.Dao;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static TextView from_year,from_month,from_day,from_hour,from_minite;
    private static TextView to_year,to_month,to_day,to_hour,to_minite;

    final static int MIAN_ACTIVITY = 1;
    final static int LIST_ACTIVITY = 2;
    final static int SPECAIL_ACTIVITY = 3;

    private int the_activity = 1;

    BottomNavigationView bottomNavigationView;
    FloatingActionButton btn_add_a_bill;
    View main_activuty,list_activity,specail_activity;
    TextView add_card,txt_all_money,txt_month_money;
    ListView AssetList,BillList,AllBills;
    AccountAdapt accountAdapt;
    BillAdapt billAdapt,AllBillAdapt;
    AssetsEntify allOftheAsset;
    private void init(){

        Dao dao = new Dao();
        if(the_activity == MIAN_ACTIVITY){

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
            List<AssetsEntify> ListOfAssets;
            List<BillEntify> ListOfAllBills;
            AllBillAdapt = new BillAdapt(MainActivity.this);
            ListOfAssets = dao.getAssets(MainActivity.this);
            ListOfAllBills = dao.getBills(MainActivity.this,0);
            ListOfAssets.remove(0);
            AllBillAdapt.setList(ListOfAllBills);
            AllBillAdapt.setAssetList(ListOfAssets);
            AllBills.setAdapter(AllBillAdapt);
        }else  if (the_activity == SPECAIL_ACTIVITY){
            getCurDateFrom();
            getCurDateTo();

        }
        txt_all_money.setText("¥"+allOftheAsset.getMoney());
        txt_month_money.setText("¥"+dao.monthPay(MainActivity.this));
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
                if (accountAdapt.Empty) return;
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
                if (billAdapt.Empty) return;
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
                if (billAdapt.Empty) return;
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
        Dao dao = new Dao();
        dao.Creat(MainActivity.this);
        main_activuty = findViewById(R.id.include_main);
        list_activity = findViewById(R.id.include_list);
        specail_activity = findViewById(R.id.include_special);
        bottomNavigationView = findViewById(R.id.navigation);
        btn_add_a_bill = findViewById(R.id.btn_add_a_bill);
        add_card = findViewById(R.id.txt_add_card);
        AssetList = findViewById(R.id.AssetList);
        BillList = findViewById(R.id.BillList);
        AllBills = findViewById(R.id.Billlist_of_all);
        txt_month_money = findViewById(R.id.txt_month_money);
        txt_all_money = findViewById(R.id.txt_all_money);
        from_day = findViewById(R.id.from_day);
        from_month = findViewById(R.id.from_month);
        from_year = findViewById(R.id.from_year);
        from_minite = findViewById(R.id.from_minite);
        from_hour = findViewById(R.id.from_hour);
        to_day = findViewById(R.id.to_day);
        to_month = findViewById(R.id.to_month);
        to_year = findViewById(R.id.to_year);
        to_minite = findViewById(R.id.to_minite);
        to_hour = findViewById(R.id.to_hour);
        init();
        setListen();
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

    public static void getCurDateFrom(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sDateFormat.format(new java.util.Date());

        String dt = time.substring(0,time.indexOf('-'));
        time = time.substring(time.indexOf('-')+1);
        from_year.setText(dt);
        dt = time.substring(0,time.indexOf('-'));
        time = time.substring(time.indexOf('-')+1);
        from_month.setText(dt);
        dt = time.substring(0,time.indexOf(' '));
        time = time.substring(time.indexOf(' ')+1);
        from_day.setText(dt);
        dt = time.substring(0,time.indexOf(':'));
        time = time.substring(time.indexOf(':')+1);
        from_hour.setText(dt);
        dt = time.substring(0,time.indexOf(':'));
        from_minite.setText(dt);
    }
    public static void getCurDateTo(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sDateFormat.format(new java.util.Date());

        String dt = time.substring(0,time.indexOf('-'));
        time = time.substring(time.indexOf('-')+1);
        to_year.setText(dt);
        dt = time.substring(0,time.indexOf('-'));
        time = time.substring(time.indexOf('-')+1);
        to_month.setText(dt);
        dt = time.substring(0,time.indexOf(' '));
        time = time.substring(time.indexOf(' ')+1);
        to_day.setText(dt);
        dt = time.substring(0,time.indexOf(':'));
        time = time.substring(time.indexOf(':')+1);
        to_hour.setText(dt);
        dt = time.substring(0,time.indexOf(':'));
        to_minite.setText(dt);
    }

    public void SetDateFrom(View view){
        Calendar calendar = Calendar.getInstance();
        showDatePickerDialogFrom(MainActivity.this,3,null,calendar);
    }

    public void SetTimeFrom(View view){
        Calendar calendar = Calendar.getInstance();
        showTimePickerDialogFrom(MainActivity.this,3,null,calendar);
    }


    public static void showDatePickerDialogFrom(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity , themeResId,new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//                Log.d("时间测试",String.valueOf(year));
//                Log.d("时间测试",String.valueOf(monthOfYear+1));
//                Log.d("时间测试",String.valueOf(dayOfMonth));
                from_year.setText(String.valueOf(year));
                from_month.setText(String.valueOf(monthOfYear+1));
                from_day.setText(String.valueOf(dayOfMonth));
            }
        }
                // 设置初始日期
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void showTimePickerDialogFrom(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Log.d("时间测试",String.valueOf(hourOfDay));
//                        Log.d("时间测试",String.valueOf(minute));
                        if(hourOfDay < 10){
                            from_minite.setText("0"+hourOfDay);
                        }else {
                            from_minite.setText(String.valueOf(hourOfDay));
                        }
                        if(minute < 10){
                            from_minite.setText("0"+minute);
                        }else{
                            from_minite.setText(String.valueOf(minute));
                        }

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

    public void SetDateTo(View view){
        Calendar calendar = Calendar.getInstance();
        showDatePickerDialogTo(MainActivity.this,3,null,calendar);
    }

    public void SetTimeTo(View view){
        Calendar calendar = Calendar.getInstance();
        showTimePickerDialogTo(MainActivity.this,3,null,calendar);
    }

    public static void showDatePickerDialogTo(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity , themeResId,new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//                Log.d("时间测试",String.valueOf(year));
//                Log.d("时间测试",String.valueOf(monthOfYear+1));
//                Log.d("时间测试",String.valueOf(dayOfMonth));
                to_year.setText(String.valueOf(year));
                to_month.setText(String.valueOf(monthOfYear+1));
                to_day.setText(String.valueOf(dayOfMonth));
            }
        }
                // 设置初始日期
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    public static void showTimePickerDialogTo(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Log.d("时间测试",String.valueOf(hourOfDay));
//                        Log.d("时间测试",String.valueOf(minute));
                        if(hourOfDay < 10){
                            to_minite.setText("0"+hourOfDay);
                        }else {
                            to_minite.setText(String.valueOf(hourOfDay));
                        }
                        if(minute < 10){
                            to_minite.setText("0"+minute);
                        }else{
                            to_minite.setText(String.valueOf(minute));
                        }

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }
    public void Select(View view){
        String from = from_year.getText().toString() + "-" + buling(from_month.getText().toString()) + "-" + buling(from_day.getText().toString()) + " " + buling(from_hour.getText().toString()) + ":" + buling(from_minite.getText().toString()) + ":00";
        String to = to_year.getText().toString() + "-" + buling(to_month.getText().toString()) + "-" + buling(to_day.getText().toString()) + " " + buling(to_hour.getText().toString()) + ":" + buling(to_minite.getText().toString()) + ":00";
        int aim = 0;
        double ans = 0;
        Dao dao = new Dao();
        if (aim != 0){
            ans = dao.getMoneyFromDate(MainActivity.this,aim,from,to);
        }else{
            ans = dao.getMoneyFromDate(MainActivity.this,from,to);
        }
        Toast.makeText(this,"ans="+ans,Toast.LENGTH_LONG).show();


    }
    public String buling(String number){
        if(number.length()<2) return "0"+number;
        else return number;
    }


}
