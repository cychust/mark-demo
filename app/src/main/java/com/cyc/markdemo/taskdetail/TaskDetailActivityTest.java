package com.cyc.markdemo.taskdetail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;


import com.cyc.markdemo.R;
import com.cyc.markdemo.Utils.Injection;
import com.cyc.markdemo.Utils.Transition;
import com.cyc.markdemo.addtask.AddTaskActivity;

/**
 * Created by cyc20 on 2017/12/31.
 */

public class TaskDetailActivityTest extends AppCompatActivity implements TaskDetailContract.View {

    private boolean finishEnter;
    private CardView cardView;
    private TextView mTitleTV;
    private TextView descriptionTV;
    private Toolbar mToolbar;
    TaskDetailContract.Presenter mPresenter;
    private String taskId;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_taskdetail_main);
        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        cardView = (CardView) findViewById(R.id.card_view);
        mTitleTV = (TextView) cardView.findViewById(R.id.title_tv);
        descriptionTV = (TextView) findViewById(R.id.detail_description_tv);
        taskId = getIntent().getStringExtra("taskId");
        Log.d(taskId, "onCreate: " + taskId);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        long transitionDuration = 800;
        if (savedInstanceState != null) {
            transitionDuration = 0;
        }
        finishEnter = false;
        Transition.enter(
                this,
                transitionDuration,
                new DecelerateInterpolator(),
                new Animator.AnimatorListener() {


                    @Override
                    public void onAnimationEnd(Animator animator) {
                        finishEnter = true;
                        initOtherViews();
                    }

                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }
        );
        new TaskDetailPresenter(taskId, Injection.provideTaskRepository(getApplicationContext()), this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showAddEditTask();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initOtherViews() {
        descriptionTV.setVisibility(View.VISIBLE);
        descriptionTV.setAlpha(0);
        descriptionTV.setTranslationY(50);
        descriptionTV.animate()
                .setDuration(300)
                .alpha(1)
                .translationY(0);
        fab.setVisibility(View.VISIBLE);
        fab.setAlpha(0);
        fab.setTranslationY(50);
        fab.animate()
                .setDuration(300)
                .alpha(1)
                .translationY(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startBackAnim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    private void startBackAnim() {
        // forbidden scrolling
        descriptionTV.animate()
                .setDuration(200)
                .alpha(0)
                .translationY(30)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // transition exit after our anim
                        Transition.exit(TaskDetailActivityTest.this, 800, new DecelerateInterpolator());
                    }
                });
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void deleteTask(String taskId) {

    }

    @Override
    public void showAddEditTask(String taskId) {
        Intent intent = new Intent(TaskDetailActivityTest.this, AddTaskActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showTaskUi(String title, String description) {
        mTitleTV.setText(title);
        descriptionTV.setText(description);
    }
}
