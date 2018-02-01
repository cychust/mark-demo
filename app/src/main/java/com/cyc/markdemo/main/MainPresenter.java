package com.cyc.markdemo.main;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.cyc.markdemo.data.Task;
import com.cyc.markdemo.data.resource.TasksDataSource;
import com.cyc.markdemo.data.resource.TasksRepository;
import com.cyc.markdemo.taskdetail.TaskDetailContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by cyc20 on 2017/12/28.
 */

public class MainPresenter implements MainContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final MainContract.View mView;

    public MainPresenter(@NonNull TasksRepository tasksRepository, @NonNull MainContract.View view) {
        mTasksRepository = checkNotNull(tasksRepository, "taskRepository cannot be null");
        mView = checkNotNull(view, "view cannot be null");
        mView.setPresenter(this);
    }

    @Override
    public void loadTasks() {
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
              /*  List<Task> taskList=new ArrayList<>();
                for (Task task:tasks){
                    taskList.add(task);
                }*/
                proccessTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void proccessTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            Log.d("tasks", "proccessTasks: task is empty");
        } else {
            mView.showTasks(tasks);
        }
    }

    @Override
    public void addNewTask() {
        mView.showAddTask();
    }


    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void openTaskDetails(Task clickedTask, View view) {
        checkNotNull(clickedTask, "clickedTask cannot be null");
        mView.showTaskDetailUi(clickedTask.getId(), view);
    }

    @Override
    public void deleteAll() {
        mTasksRepository.deleteAllTasks();
        mView.deleteAll();
    }
}
