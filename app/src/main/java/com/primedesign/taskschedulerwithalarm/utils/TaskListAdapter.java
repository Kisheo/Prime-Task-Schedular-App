package com.primedesign.taskschedulerwithalarm.utils;

import com.primedesign.taskschedulerwithalarm.db.Task;

import java.util.Collections;
import java.util.List;

public class TaskListAdapter {

    private List<Task> mTask = Collections.emptyList();

    public void setTask(List<Task> task) {
        mTask = task;
    }

    // getItemCount() is called many times, and when it is first called,
// mWords has not been updated (means initially, it's null, and we can't return null).
    public int getItemCount() {
        if (mTask != null)
            return mTask.size();
        else return 0;
    }
}