package com.teamdev.jxbrowser.task;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.teamdev.jxbrowser.js.JsAccessible;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.jxbrowser.TaskOuterClass.Task;
import static com.teamdev.jxbrowser.task.Tasks.createTask;

@JsAccessible
public final class TaskService {

    private static final List<Task> TASKS = new ArrayList<>() {
        {
            add(createTask("0001", "Try to calculate the EXE feed, maybe it will index the multi-byte pixel!", "In progress", "Feature", 1));
            add(createTask("0002", "We need to bypass the neural TCP card!", "In progress", "Bug", 1));
            add(createTask("0003", "I'll parse the wireless SSL protocol, that should driver the API panel!", "To Do", "Feature", 3));
            add(createTask("0004", "Use the digital TLS panel, then you can transmit the haptic system!", "Completed", "Bug", 4));
            add(createTask("0005", "We need to program the back-end THX pixel!", "To Do", "Bug", 4));
            add(createTask("0006", "The SAS interface is down, bypass the open-source pixel so we can back", "In progress", "Feature", 2));
            add(createTask("0007", "We need to generate the virtual HEX alarm!", "Completed", "Bug", 2));
            add(createTask("0008", "We need to compress the auxiliary VGA driver!", "In progress", "Bug", 1));
        }
    };

    @SuppressWarnings("unused")
    public List<byte[]> getTasks() {
        return TASKS.stream().map(AbstractMessageLite::toByteArray).toList();
    }

    @SuppressWarnings("unused")
    public void addTask(byte[] task) throws InvalidProtocolBufferException {
        TASKS.add(Task.parseFrom(task));
    }
}