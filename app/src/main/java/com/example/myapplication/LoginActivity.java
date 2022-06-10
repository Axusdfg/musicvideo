package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name2;
    private EditText password3;
    private Button btn1;
    private Button btn2;
    private CheckBox ch2;

    public static final int REGISTER_CODE = 0;
    int requestCode = -1;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        initView();

        //创建或打开数据库
        db = openOrCreateDatabase("musicplayer.db",MODE_PRIVATE,null);
        //在数据库中创建数据表users
        db.execSQL("create table if not exists users(name varchar(50),pswd varchar(50),primary key(name))");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(requestCode!=REGISTER_CODE){
            readSharedPreferences();
        }

    }

    private void initView() {
        name2 =  findViewById(R.id.editTextTextPersonName2);
        password3 =  findViewById(R.id.editTextTextPassword3);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        ch2 = findViewById(R.id.checkBox2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                //打开注册界面，并传递requestCode
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,0);//REGISTER_CODE
                break;
            case R.id.button2:
                Intent intent_home = new Intent(LoginActivity.this,HomeActivity.class);
               startActivityForResult(intent_home,0);
                // submit();
                break;
        }
    }
    private void submit() {
        // 获取用户输入的用户名、密码，并确认这两个文本框没有留空
        String name = name2.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String pswd = password3.getText().toString().trim();
        if (TextUtils.isEmpty(pswd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }


        try{
            //查询
            Cursor cursor = db.rawQuery("select pswd from users where name=?",new String[]{name});
            String pswd_check = null;
            //如果根据用户名只查询到一行信息，则获取该行保存的密码
            if(cursor.getCount()==1){
                //跳转到第一行
                cursor.moveToFirst();
                //获取该行第一列的String内容
                pswd_check = cursor.getString(0);

                if(pswd_check.equals(pswd)){    //验证成功，则登陆
                    //【记住密码功能 - 写入】
                    writeSharedPreferences(name,pswd);

                    //【跳转到HomeActivity主页面】
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    //Intent intent = new Intent(LoginActivity.this, MineFragment.class);
                    startActivity(intent);
                }else{         //验证失败，通过Toast提示错误信息
                    Toast.makeText(this,"用户名、密码错误",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void writeSharedPreferences(String name, String pswd) {
    }


    //回调方法：当从注册页面点击注册按钮返回本页面时
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        this.requestCode = -1;


        if(resultCode == RESULT_OK){
            //获取传回来的数据，即data
            if (data != null){
                //填充两个输入框为注册页面传回来的用户名、密码
                name2.setText(data.getStringExtra("name"));
                password3.setText(data.getStringExtra("pswd"));

                //如果本页面由注册页面接收了回传的用户名密码，则设置成员变量requestCode的值为REGISTER_CODE
                this.requestCode = requestCode;
            }
        }

    }


    private void readSharedPreferences() {
        //获取SharedPreferences对象
        SharedPreferences preferences = getSharedPreferences("MusicUserRecord", MODE_PRIVATE);

        if (preferences.getBoolean("keep", false)) {
            //获取保存在SharedPreferences中的用户名、密码
            String username = preferences.getString("name", "");
            String password = preferences.getString("pswd", "");
            //设置用户名、密码输入框中，填入preference中存储的用户名、密码
            name2.setText(username);
            password3.setText(password);
            //设置“记住密码”多选框为选中状态
            ch2.setChecked(true);
        } else {         //如果记住密码处于非选中状态，则清空所有文本输入框和复选框内容
            name2.setText("");
            password3.setText("");
            //设置“记住密码”多选框为未选中状态
            ch2.setChecked(false);
        }


    }
    private void checkNeedPermissions(){
        //6.0以上需要动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }



}