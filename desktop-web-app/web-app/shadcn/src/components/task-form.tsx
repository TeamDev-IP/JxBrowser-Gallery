import {Button} from "@/components/ui/button.tsx";
import {useNavigate} from "react-router-dom";
import taskService from "@/components/tasks.tsx";
import {TaskSchema} from "../gen/task_pb.js";
import {create, toBinary} from "@bufbuild/protobuf";
import {Input} from "@/components/ui/input.tsx";
import {useState} from "react";

export function TaskForm() {
    const navigate = useNavigate();
    const [title, setTitle] = useState("");
    const [type, setType] = useState("");
    const [priority, setPriority] = useState(1);

    return (
        <div>
            <Input id={"Title"} placeholder={"Title"} onChange={(e) => setTitle(e.target.value)}/>
            <br/>
            <Input id={"Type"} placeholder={"Type"} onChange={(e) => setType(e.target.value)}/>
            <br/>
            <Input id={"Priority"} placeholder={"Priority"} onChange={(e) => setPriority(Number(e.target.value))}/>
            <br/>
            <Button onClick={() => {
                const newTask = create(TaskSchema, {
                    title,
                    type,
                    status: "To Do",
                    priority
                });
                const bytes: Uint8Array = toBinary(TaskSchema, newTask);
                taskService.addTask(bytes.slice().buffer);

                navigate("/");
            }}>Submit</Button>
        </div>
    )
}