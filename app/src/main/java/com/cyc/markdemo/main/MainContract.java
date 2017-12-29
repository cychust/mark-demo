package com.cyc.markdemo.main;

import android.view.View;

import com.cyc.markdemo.data.Task;
import com.cyc.markdemo.mvp.BasePresenter;
import com.cyc.markdemo.mvp.BaseView;
import com.cyc.markdemo.taskdetail.TaskDetailContract;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by cyc20 on 2017/12/28.
 */

public class MainContract {
    interface View extends BaseView<MainPresenter> {
        void showTasks(List<Task> tasks);

        void showAddTask();

        void showTaskDetailUi(String taskId, android.view.View view);
    }

    interface Presenter extends BasePresenter {
        void loadTasks();

        void addNewTask();

        void openTaskDetails(Task clickedTask, android.view.View view);
    }
}
