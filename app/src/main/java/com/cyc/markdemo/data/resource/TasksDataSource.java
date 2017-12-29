package com.cyc.markdemo.data.resource;

import android.support.annotation.NonNull;

import com.cyc.markdemo.data.Task;

import java.util.List;


/**
 * Created by cyc20 on 2017/12/27.
 */

public interface TasksDataSource {
    interface LoadTasksCallback {
        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {
        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback);

    void saveTask(@NonNull Task task);

    Task getTaskWithId(@NonNull String id);

    void deleteAllTasks();

    void deleteTask(@NonNull String taskId);
}
