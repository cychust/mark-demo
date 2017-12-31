package com.cyc.markdemo.taskdetail;

import com.cyc.markdemo.mvp.BasePresenter;
import com.cyc.markdemo.mvp.BaseView;

/**
 * Created by cyc20 on 2017/12/27.
 */

public interface TaskDetailContract {
    interface View extends BaseView<Presenter> {
        void deleteTask(String taskId);

        void showAddEditTask(String taskId);

        void showTaskUi(String title, String description);
    }

    interface Presenter extends BasePresenter {
        void showAddEditTask();

        void deleteTask();
    }


}
