import {useEffect, useState} from "react";
import {Button} from "@/components/ui/button.tsx";
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow
} from "@/components/ui/table.tsx";
import {useNavigate} from "react-router-dom";
import {Task} from "../gen/task_pb.js";
import {TaskService} from "@/gen/task_service_pb.ts";
import {createCallbackClient} from "@connectrpc/connect";
import {createGrpcWebTransport} from "@connectrpc/connect-web";

// A port for RPC communication passed obtained from the server side via
// the JxBrowser Java-Js bridge.
declare const rpcPort: Number

const transport = createGrpcWebTransport({
    baseUrl: `http://localhost:${rpcPort}`,
});
export const tasksClient = createCallbackClient(TaskService, transport);

export function Tasks() {
    const [tasks, setTasks] = useState<Task[]>([]);
    const navigate = useNavigate();
    useEffect(() => {
        tasksClient.getTasks({}, (_err, res) => {
            setTasks(res.tasks)
        });
    }, []);

    return (
        <div>
            <div>
                <h2 className="text-2xl font-bold tracking-tight">Welcome back!</h2>
                <p className="text-muted-foreground">
                    Here&apos;s a list of your tasks for this month!
                </p>
            </div>
            <Table>
                <TableHeader>
                    <TableRow>
                        <TableHead className="w-[100px]">ID</TableHead>
                        <TableHead>Title</TableHead>
                        <TableHead>Type</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead className="text-right">Priority</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {tasks.map((task) => (
                        <TableRow key={task.id}>
                            <TableCell className="font-medium">{task.id}</TableCell>
                            <TableCell>{task.title}</TableCell>
                            <TableCell>{task.type}</TableCell>
                            <TableCell>{task.status}</TableCell>
                            <TableCell className="text-right">{task.priority}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            <Button onClick={() => navigate("/new")}>Add new task</Button>
        </div>
    )
}