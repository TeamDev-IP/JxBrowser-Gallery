import {ThemeProvider} from "@/components/theme-provider";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import {Tasks} from "@/components/tasks.tsx";
import {TaskForm} from "@/components/task-form.tsx";

function App() {
    return (
        <Router>
            <ThemeProvider>
                <div className="container flex flex-col mt-16">
                    <Routes>
                        <Route path={"/"} element={<Tasks/>}>
                        </Route>
                        <Route path={"/new"} element={<TaskForm/>}>
                        </Route>
                    </Routes>
                </div>
            </ThemeProvider>
        </Router>
    )
}

export default App
