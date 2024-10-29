package com.teamdev.jxbrowser.task;

import com.teamdev.jxbrowser.TaskOuterClass.Task;

/**
 * A utility for creating {@link Task}.
 */
public final class Tasks {

    private Tasks() {

    }

    public static Task createTask(String id, String title, String status, String type, int priority) {
        return Task.newBuilder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setStatus(status)
                .setPriority(priority)
                .build();
    }
}
