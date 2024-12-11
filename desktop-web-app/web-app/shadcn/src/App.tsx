import {ThemeProvider} from "@/components/theme-provider";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {
    Sidebar,
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarMenu,
    SidebarProvider
} from "@/components/ui/sidebar.tsx";
import {Account} from "@/components/account.tsx";
import {General} from "@/components/general.tsx";
import {Appearance} from "@/components/appearance.tsx";
import {NavigationItem} from "@/components/navigation-item.tsx";
import {Bell, Settings, SquareUser, SunMoon} from "lucide-react";
import {Notifications} from "@/components/notifications.tsx";

function App() {
    return (
        <Router>
            <ThemeProvider>
                <SidebarProvider className="space-y-4 space-x-4" >
                    <Sidebar side="left">
                        <SidebarContent className="p-4">
                            <SidebarGroup/>
                            <SidebarGroupContent>
                                <SidebarMenu>
                                    <NavigationItem title={"Account"} url={"/"} icon={SquareUser}/>
                                </SidebarMenu>
                            </SidebarGroupContent>
                            <SidebarGroupLabel>Preferences</SidebarGroupLabel>
                            <SidebarGroupContent>
                                <SidebarMenu>
                                    <NavigationItem title={"General"} url={"/prefs/general"}
                                                    icon={Settings}/>
                                    <NavigationItem title={"Appearance"} url={"/prefs/appearance"}
                                                    icon={SunMoon}/>
                                    <NavigationItem title={"Notifications"} url={"/prefs/notifications"}
                                                    icon={Bell}/>
                                </SidebarMenu>
                            </SidebarGroupContent>
                            <SidebarGroup/>
                        </SidebarContent>
                    </Sidebar>
                    <main className="w-full p-8">
                        <div className="w-full">
                            <Routes>
                                <Route path={"/"} element={<Account/>}/>
                                <Route path={"/prefs/general"} element={<General/>}/>
                                <Route path={"/prefs/appearance"} element={<Appearance/>}/>
                                <Route path={"/prefs/notifications"} element={<Notifications/>}/>
                            </Routes>
                        </div>
                    </main>
                </SidebarProvider>
            </ThemeProvider>
        </Router>
    )
}

export default App
