import {useEffect, useState} from "react";
import {Button} from "@/components/ui/button.tsx";
import {Table, TableBody, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table.tsx";
import {useNavigate} from "react-router-dom";
import { TaskSchema, Task } from "../gen/task_pb.js";
import {fromBinary} from "@bufbuild/protobuf";

declare const taskService: {
    getTasks: () => ArrayBuffer[]
    addTask: (t: ArrayBuffer) => void
}

export default taskService;

export function Tasks() {
    const [tasks, setTasks] = useState<Task[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        const tasks: Task[] = taskService.getTasks().map(taskBuffer => {
            return fromBinary(TaskSchema, new Uint8Array(taskBuffer))
        });
        setTasks(tasks)
    }, []);

    return (
        <div>
            <div className="mb-8">
                <h2 className="text-2xl font-bold tracking-tight">Welcome!</h2>
                <p className="text-muted-foreground">
                    Here&apos;s a list of your tasks for this month.
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
            <div className="mt-8">
                <Button onClick={() => navigate("/new")}>Add new task</Button>
            </div>
        </div>
    )
}