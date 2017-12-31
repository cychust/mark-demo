package com.cyc.markdemo.addtask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.cyc.markdemo.R;
import com.cyc.markdemo.Utils.ActivityUtil;
import com.cyc.markdemo.Utils.Injection;

/**
 * Created by cyc20 on 2017/12/30.
 */

public class AddTaskActivity extends AppCompatActivity {

    private AddTaskPresenter mAddTaskPresenter;
    private AppBarLayout mAppBarLayout;
    private ActionBar mActionBar;
    public AddTaskActivity() {
        super();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar=getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
        AddTaskFragment addTaskFragment=(AddTaskFragment)getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        String taskId=getIntent().getStringExtra("taskId");
        if (addTaskFragment==null){
            addTaskFragment=AddTaskFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),addTaskFragment,R.id.contentFragment);
        }
        mAddTaskPresenter=new AddTaskPresenter(taskId
        , Injection.provideTaskRepository(getApplicationContext()),
                addTaskFragment);
    }


}
