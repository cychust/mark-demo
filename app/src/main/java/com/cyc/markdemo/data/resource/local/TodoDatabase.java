package com.cyc.markdemo.data.resource.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cyc.markdemo.data.Task;

/**
 * Created by cyc20 on 2017/12/27.
 */

@Database(entities = {Task.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {
    private static TodoDatabase INSTANCE;

    public abstract TaskDao taskDao();

    private static final Object sLock = new Object();

    private static TodoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TodoDatabase.class, "Task.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
