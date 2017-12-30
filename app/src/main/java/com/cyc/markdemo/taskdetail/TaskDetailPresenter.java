package com.cyc.markdemo.taskdetail;


import android.support.annotation.Nullable;

/**
 * Created by cyc20 on 2017/12/27.
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    TaskDetailContract.View mView;


    @Nullable
    private String taskId;
    public TaskDetailPresenter(@Nullable String taskId){
        //TODO:
        this.taskId=taskId;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
