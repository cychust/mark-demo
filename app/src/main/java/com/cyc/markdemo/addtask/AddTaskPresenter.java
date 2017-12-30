package com.cyc.markdemo.addtask;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cyc.markdemo.data.Task;
import com.cyc.markdemo.data.resource.TasksDataSource;

/**
 * Created by cyc20 on 2017/12/30.
 */

public class AddTaskPresenter implements AddTaskContract.Presenter ,TasksDataSource.GetTaskCallback{



    @NonNull
    private final TasksDataSource mTasksRepository;

    @NonNull
    private final AddTaskContract.View mAddTaskView;

    @Nullable
    private String mTaskId;

    public AddTaskPresenter(@Nullable String taskId, @Nullable TasksDataSource tasksDataSource,
                            @Nullable AddTaskContract.View view){
        mTaskId=taskId;
        mAddTaskView=view;
        mTasksRepository=tasksDataSource;
        mAddTaskView.setPresenter(this);
    }

    @Override
    public void start() {

        if(mTaskId!=null)
        populateTask();
    }

    @Override
    public void saveTask(String title, String description) {
        if (mTaskId==null) {
            Task task = new Task(title, description);
            if (task.isEmpty()){
                mAddTaskView.showError();
                mAddTaskView.showTaskList();
            }
            else {
                mTasksRepository.saveTask(task);
                mAddTaskView.showTaskList();
            }
        }else {
            mTasksRepository.saveTask(new Task(title,description,mTaskId));
            mAddTaskView.showTaskList();
        }
    }

    @Override
    public void populateTask() {
        mTasksRepository.getTask(mTaskId,this);
    }

    @Override
    public void onTaskLoaded(Task task) {
        mAddTaskView.setTitle(task.getTitle());
        mAddTaskView.setDescription(task.getDescription());
    }

    @Override
    public void onDataNotAvailable() {

    }
}
