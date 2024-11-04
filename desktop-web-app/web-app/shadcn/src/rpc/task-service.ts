import {createGrpcWebTransport} from "@connectrpc/connect-web";
import {createCallbackClient} from "@connectrpc/connect";
import {TaskService} from "@/gen/task_service_pb.ts";
import {Task} from "@/gen/task_pb.ts";

// A port for RPC communication passed obtained from the server side via
// the JxBrowser Java-Js bridge.
declare const rpcPort: Number

const transport = createGrpcWebTransport({
    baseUrl: `http://localhost:${rpcPort}`,
});
const tasksClient = createCallbackClient(TaskService, transport);

function getTasks(callback: (tasks: Task[]) => void) {
    tasksClient.getTasks({}, (_err, res) => {
        callback(res.tasks)
    });
}

function addTask(newTask: Task, callback: (added: boolean) => void) {
    tasksClient.addTask(newTask, (_err, res) => {
        callback(res.value)
    });
}

export {
    getTasks,
    addTask
}
