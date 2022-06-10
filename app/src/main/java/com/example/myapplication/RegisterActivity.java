package com.example.myapplication;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText pswd1;
    private EditText pswd2;
    private Button register;

    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        initView();
        //创建或打开数据库
        db = openOrCreateDatabase("musicplayer.db",MODE_PRIVATE,null);
        //在数据库中创建数据表users
        db.execSQL("create table if not exists users(name varchar(50),pswd varchar(50),primary key(name))");
    }

    private void initView() {
        name = findViewById(R.id.editTextTextPersonName);
        pswd1 = findViewById(R.id.editTextTextPassword);
        pswd2 =findViewById(R.id.editTextTextPassword2);
        register = findViewById(R.id.button);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //当点击在注册按钮，则提交信息，此时调用submit方法
                submit();
                break;
        }
    }

    private void submit() {
        String me=name.getText().toString().trim();
        if (TextUtils.isEmpty(me)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String paswd1= pswd1.getText().toString().trim();
        if (TextUtils.isEmpty(paswd1)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String paswd2 =pswd2.getText().toString().trim();
        if (TextUtils.isEmpty(paswd2)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!paswd1.equals(paswd2)){
            Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }else{
            try {

                // 向表中添加一条用户数据
                db.execSQL("insert into users(name,pswd) values(?,?)", new String[]{me, paswd1});
                Intent data = getIntent();
                data.putExtra("name",me);
                data.putExtra("pswd",paswd1);
                setResult(RESULT_OK,data);
                finish();

            }catch (Exception e){
                Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    protected void onDestroy() {
        //关闭数据库
        db.close();
        super.onDestroy();
    }


}
