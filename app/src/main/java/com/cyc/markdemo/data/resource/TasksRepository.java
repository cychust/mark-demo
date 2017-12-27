package com.cyc.markdemo.data.resource;

import android.support.annotation.NonNull;

import com.cyc.markdemo.data.Task;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by cyc20 on 2017/12/27.
 */

public class TasksRepository implements TasksDataSource {
    private static TasksRepository INSTANCE = null;
    private final TasksDataSource mLocalTasksDataSource;
    // TODO private final TasksDataSource mRemoteTasksDataSource;
    Map<String, Task> mCachedTask;
    boolean mCachedIsDirty = false;

    private TasksRepository(@NonNull TasksDataSource localTasksDataSource) {
        mLocalTasksDataSource = localTasksDataSource;
    }

    public static TasksRepository getInstance(TasksDataSource localTasksDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(localTasksDataSource);
        }
        return INSTANCE;
    }

    public void DestroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {
        checkNotNull(callback);

    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull Task task) {

    }

    @Override
    public void completeTask(@NonNull String taskId) {

    }

    @Override
    public void activateTask(@NonNull Task task) {

    }

    @Override
    public void activateTask(@NonNull String taskId) {

    }

    @Override
    public void clearCompletedTasks() {

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
