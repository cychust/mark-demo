package com.cyc.markdemo.Utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cyc.markdemo.data.resource.TasksRepository;
import com.cyc.markdemo.data.resource.local.TaskLocalDataResource;
import com.cyc.markdemo.data.resource.local.TodoDatabase;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by cyc20 on 2017/12/30.
 */

public class Injection {
    public static TasksRepository provideTaskRepository(@NonNull Context context){
        checkNotNull(context);
        TodoDatabase database=TodoDatabase.getInstance(context);
        return TasksRepository.getInstance(TaskLocalDataResource.getInstance(new AppExecutors(),database.taskDao()));
    }
}
