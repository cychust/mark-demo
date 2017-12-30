package com.cyc.markdemo.main;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.cyc.markdemo.R;
import com.cyc.markdemo.Utils.ActivityUtil;
import com.cyc.markdemo.Utils.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        MainFragment mainFragment=(MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFragment);
        if (mainFragment==null){
            mainFragment=MainFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),mainFragment,R.id.contentFragment);
        }
        new MainPresenter(Injection.provideTaskRepository(getApplicationContext()),mainFragment);
    }
}
