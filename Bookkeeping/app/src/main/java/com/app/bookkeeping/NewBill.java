package com.app.bookkeeping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.Adapt.AccountAdapt;
import com.app.Adapt.AimGridAdapt;
import com.app.dao.Dao;
import com.app.entify.AssetsEntify;
import com.app.entify.BillEntify;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class NewBill extends Activity {

    private static TextView txt_year,txt_month,txt_day,txt_hour,txt_minite;

    private View inflate;
    private Dialog dialog;
    CardView choose_bank;
    ImageView img_bank;
    TextView bank_name;
    EditText edit_money;
    int type;
    int aim = 1;
    Button save;
    Button qiut;
    Button delete;
    private GridView gridView;
//    private List<Map<String, Object>> dataList;
//    private SimpleAdapter adapter;
    int from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        choose_bank = findViewById(R.id.bill_bank);
        img_bank = findViewById(R.id.bill_img_bank);
        bank_name = findViewById(R.id.txt_bill_bank_name);
        gridView = findViewById(R.id.aim_list);
        img_bank.setImageResource(R.drawable.bank);
        save = findViewById(R.id.btn_bill_save);
        qiut = findViewById(R.id.btn_bill_qiut);
        delete = findViewById(R.id.btn_bill_delete);
        edit_money = findViewById(R.id.edit_money);
        txt_year = findViewById(R.id.txt_year);
        txt_month = findViewById(R.id.txt_month);
        txt_day = findViewById(R.id.txt_day);
        txt_hour = findViewById(R.id.txt_hour);
        txt_minite = findViewById(R.id.txt_minite);
        getCurDate();
        delete.setVisibility(View.GONE);
        setDialog();
        initData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBill();
            }
        });

        qiut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    public static void getCurDate(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sDateFormat.format(new java.util.Date());

        String dt = time.substring(0,time.indexOf('-'));
        time = time.substring(time.indexOf('-')+1);
        txt_year.setText(dt);
        dt = time.substring(0,time.indexOf('-'));
        time = time.substring(time.indexOf('-')+1);
        txt_month.setText(dt);
        dt = time.substring(0,time.indexOf(' '));
        time = time.substring(time.indexOf(' ')+1);
        txt_day.setText(dt);
        dt = time.substring(0,time.indexOf(':'));
        time = time.substring(time.indexOf(':')+1);
        txt_hour.setText(dt);
        dt = time.substring(0,time.indexOf(':'));
        txt_minite.setText(dt);
    }

    public void SetDate(View view){
        Calendar calendar = Calendar.getInstance();
        showDatePickerDialog(NewBill.this,3,null,calendar);
    }


    public void SetTime(View view){
        Calendar calendar = Calendar.getInstance();
        showTimePickerDialog(NewBill.this,3,null,calendar);
    }

    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity , themeResId,new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
//                Log.d("时间测试",String.valueOf(year));
//                Log.d("时间测试",String.valueOf(monthOfYear+1));
//                Log.d("时间测试",String.valueOf(dayOfMonth));
                txt_year.setText(String.valueOf(year));
                txt_month.setText(String.valueOf(monthOfYear+1));
                txt_day.setText(String.valueOf(dayOfMonth));
            }
        }
                // 设置初始日期
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public static void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
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
                            txt_minite.setText("0"+hourOfDay);
                        }else {
                            txt_minite.setText(String.valueOf(hourOfDay));
                        }
                        if(minute < 10){
                            txt_minite.setText("0"+minute);
                        }else{
                            txt_minite.setText(String.valueOf(minute));
                        }

                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }


    void initData() {
        AimGridAdapt aimGridAdapt = new AimGridAdapt(NewBill.this);
        aimGridAdapt.setList();
        gridView.setAdapter(aimGridAdapt);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AimGridAdapt adapt = (AimGridAdapt) gridView.getAdapter();
                adapt.setLastPosition(position);

                aim = position + 1;
            }
        });
    }




    public void setDialog(){
        choose_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(NewBill.this,R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                inflate = LayoutInflater.from(NewBill.this).inflate(R.layout.choose_list, null);
                //初始化控件
                Dao dao = new Dao();
                List<AssetsEntify> List= dao.getAssets(NewBill.this);
                AccountAdapt accountAdapt = new AccountAdapt(NewBill.this);
                List.remove(0);
                accountAdapt.setList(List);
                final ListView list_of_bank = inflate.findViewById(R.id.choose_list);
                list_of_bank.setAdapter(accountAdapt);
                //将布局设置给Dialog
                dialog.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity( Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 0;//设置Dialog距离底部的距离
                lp.width = dialogWindow.getWindowManager().getDefaultDisplay().getWidth();
                //    将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog.show();//显示对话框
                list_of_bank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AccountAdapt alist = (AccountAdapt) list_of_bank.getAdapter();
                        bank_name.setText(alist.getItem(position).getName());
                        switch (alist.getItem(position).getType()){
                            case 0:
                                img_bank.setImageResource(R.drawable.bank);
                                break;
                            case 1:
                                img_bank.setImageResource(R.drawable.alipay);
                                break;
                            case 2:
                                img_bank.setImageResource(R.drawable.jianshebank);
                                break;
                            case 3:
                                img_bank.setImageResource(R.drawable.chinabank);
                                break;
                            case 4:
                                img_bank.setImageResource(R.drawable.youzhengbank);
                                break;
                            case 5:
                                img_bank.setImageResource(R.drawable.gongshangbank);
                                break;
                        }
                        from = alist.getItem(position).getID();
                        type = position;
                        dialog.cancel();
                    }
                });
            }
        });
    }



//    public Properties getProperty(String filename){
//        Properties properties = new Properties();
//        try {
//            properties.load(new BufferedReader(new InputStreamReader(NewBill.this.getAssets().open(filename))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return properties;
//    }

    public static long getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    //点击确认按钮后添加账单(缺少数据捕获 by王家淇)
    public void addBill(){
        BillEntify bill = new BillEntify();
        Date date = new Date();
        String time = txt_year.getText().toString() + "-" + txt_month.getText().toString() + "-" + txt_day.getText().toString() + " " + txt_hour.getText().toString() + ":" + txt_minite.getText().toString() + ":00";

        bill.setMoney(edit_money.getText().toString());
        bill.setAim(aim);
        bill.setFrom(from);
        date.setTime(getStringToDate(time));
        bill.setDate(date);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = format.format(date);
        bill.setDateString(dateString);
        Log.d("测试点",date.toString());
        Dao dao = new Dao();
        if(dao.addNewBill(NewBill.this,bill) != -1){
            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"添加失败，请重试",Toast.LENGTH_SHORT).show();
        }

    }


}
