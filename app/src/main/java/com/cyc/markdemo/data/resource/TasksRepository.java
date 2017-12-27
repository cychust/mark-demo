package com.cyc.markdemo.data.resource;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cyc.markdemo.data.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        if (mCachedTask!=null&&mCachedIsDirty){
            callback.onTasksLoaded(new ArrayList<>(mCachedTask.values()));
            return;
        }
        if (mCachedIsDirty){
            return;
        }else {
                mLocalTasksDataSource.getTasks(new LoadTasksCallback() {
                    @Override
                    public void onTasksLoaded(List<Task> tasks) {
                        refreshCache(tasks);
                        callback.onTasksLoaded(new ArrayList<>(mCachedTask.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        //Todo
                    }
                });
        }
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mLocalTasksDataSource.saveTask(task);

        if (mCachedTask==null){
            mCachedTask=new LinkedHashMap<>();
        }
        mCachedTask.put(task.getId(),task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        checkNotNull(task);
        mLocalTasksDataSource.completeTask(task);
        Task completedTask = new Task(task.getTitle(), task.getDescription(), task.getId(), true);
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        checkNotNull(taskId);
        completeTask(getTaskWithId(taskId));
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
    private void refreshCache(List<Task> tasks) {
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.clear();
        for (Task task : tasks) {
            mCachedTask.put(task.getId(), task);
        }
        mCachedIsDirty = false;
    }
    @Nullable
    private Task getTaskWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedTask == null || mCachedTask.isEmpty()) {
            return null;
        } else {
            return mCachedTask.get(id);
        }
    }
}
