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
import {getTasks} from "@/rpc/task-service.ts";
import {useNavigate} from "react-router-dom";
import {Task} from "../gen/task_pb.js";

export function Tasks() {
    const [tasks, setTasks] = useState<Task[]>([]);
    const navigate = useNavigate();
    useEffect(() => {
        getTasks(tasks => setTasks(tasks))
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