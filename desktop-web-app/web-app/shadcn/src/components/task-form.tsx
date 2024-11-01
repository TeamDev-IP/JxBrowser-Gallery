import {Button} from "@/components/ui/button.tsx";
import {useNavigate} from "react-router-dom";
// import taskService from "@/components/tasks.tsx";
import {Input} from "@/components/ui/input.tsx";
import {useState} from "react";
import {create} from "@bufbuild/protobuf";
import {TaskSchema} from "@/gen/task_pb.ts";

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
                create(TaskSchema, {
                    title,
                    type,
                    status: "To Do",
                    priority
                });
                navigate("/");
            }}>Submit</Button>
        </div>
    )
}