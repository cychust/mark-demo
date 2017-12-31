package com.cyc.markdemo.taskdetail;


import android.support.annotation.Nullable;

import com.cyc.markdemo.data.Task;
import com.cyc.markdemo.data.resource.TasksDataSource;
import com.cyc.markdemo.data.resource.TasksRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by cyc20 on 2017/12/27.
 */

public class TaskDetailPresenter implements TaskDetailContract.Presenter {

    TaskDetailContract.View mView;
    private TasksRepository mTasksRepository;


    @Nullable
    private String taskId;
    public TaskDetailPresenter(@Nullable String taskId, TasksRepository tasksRepository, TaskDetailContract.View view){
        //TODO:
        this.taskId=taskId;
        this.mTasksRepository=checkNotNull(tasksRepository);
        mView=view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mTasksRepository.getTask(taskId, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                mView.showTaskUi(task.getTitle(),task.getDescription());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

    }

    @Override
    public void showAddEditTask() {
        mView.showAddEditTask(taskId);
    }

    @Override
    public void deleteTask() {
        mView.deleteTask(taskId);
        mTasksRepository.deleteTask(taskId);
    }
}
