package com.cyc.markdemo.addtask;

import com.cyc.markdemo.mvp.BasePresenter;
import com.cyc.markdemo.mvp.BaseView;

/**
 * Created by cyc20 on 2017/12/30.
 */

public class AddTaskContract {
    interface View extends BaseView<Presenter> {
        void setTitle(String title);

        void setDescription(String description);

        void showError();

        void showTaskList();
    }

    interface Presenter extends BasePresenter {
        void saveTask(String title, String description);

        void populateTask();
    }
}
