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
    // boolean mCachedIsDirty = false;

    private TasksRepository(@NonNull TasksDataSource localTasksDataSource) {
        mLocalTasksDataSource = checkNotNull(localTasksDataSource);
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
        if (mCachedTask != null) {
            callback.onTasksLoaded(new ArrayList<>(mCachedTask.values()));
            return;
        }
        mLocalTasksDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                refreshCache(tasks);
                callback.onTasksLoaded(new ArrayList<>(mCachedTask.values()));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    void refreshCache(List<Task> tasks) {
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.clear();
        for (Task task : tasks) {
            mCachedTask.put(task.getId(), task);
        }
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {
        checkNotNull(taskId);
        checkNotNull(callback);
        Task cacheTask = getTaskWithId(taskId);
        if (cacheTask != null) {
            callback.onTaskLoaded(cacheTask);
            return;
        }
        mLocalTasksDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mCachedTask == null) {
                    mCachedTask = new LinkedHashMap<>();
                }
                mCachedTask.put(task.getId(), task);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public Task getTaskWithId(@NonNull String taskId) {
        checkNotNull(taskId);
        if (mCachedTask == null || mCachedTask.isEmpty()) {
            return null;
        } else {
            return mCachedTask.get(taskId);
        }
    }

    @Override
    public void saveTask(@NonNull Task task) {
        checkNotNull(task);
        mLocalTasksDataSource.saveTask(task);
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(task.getId(), task);
    }

    @Override
    public void deleteAllTasks() {
        mLocalTasksDataSource.deleteAllTasks();
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.clear();
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        checkNotNull(taskId);
        mLocalTasksDataSource.deleteTask(taskId);
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
            return;
        }
        mCachedTask.remove(taskId);

    }
}
