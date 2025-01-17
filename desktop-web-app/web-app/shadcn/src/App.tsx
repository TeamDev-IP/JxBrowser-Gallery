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
import {UserAccount} from "@/components/user-account.tsx";
import {General} from "@/components/general.tsx";
import {Appearance} from "@/components/appearance.tsx";
import {NavigationItem} from "@/components/navigation-item.tsx";
import {Bell, Settings, SquareUser, SunMoon} from "lucide-react";
import {Notifications} from "@/components/notifications.tsx";
import {FontSizeProvider} from "@/components/font-size-provider.tsx";

function App() {
    return (
        <ThemeProvider>
            <FontSizeProvider>
                <SidebarProvider className="space-y-2 space-x-2 h-full">
                    <Sidebar className="!h-auto" collapsible={"none"} side="left">
                        <SidebarContent className="p-4">
                            <SidebarGroup/>
                            <SidebarGroupContent>
                                <SidebarMenu>
                                    <NavigationItem title={"Account"} url={"/"} icon={SquareUser}/>
                                </SidebarMenu>
                            </SidebarGroupContent>
                            <SidebarGroupLabel className={"pt-3"}>Preferences</SidebarGroupLabel>
                            <SidebarGroupContent>
                                <SidebarMenu>
                                    <NavigationItem title={"General"} url={"/prefs/general"}
                                                    icon={Settings}/>
                                    <NavigationItem title={"Appearance"} url={"/prefs/appearance"}
                                                    icon={SunMoon}/>
                                    <NavigationItem title={"Notifications"}
                                                    url={"/prefs/notifications"}
                                                    icon={Bell}/>
                                </SidebarMenu>
                            </SidebarGroupContent>
                            <SidebarGroup/>
                        </SidebarContent>
                    </Sidebar>
                    <main className="w-full p-8">
                        <div className="w-full">
                            <Router>
                                <Routes>
                                    <Route path={"/"} element={<UserAccount/>}/>
                                    <Route path={"/prefs/general"} element={<General/>}/>
                                    <Route path={"/prefs/appearance"} element={<Appearance/>}/>
                                    <Route path={"/prefs/notifications"}
                                           element={<Notifications/>}/>
                                </Routes>
                            </Router>
                        </div>
                    </main>
                </SidebarProvider>
            </FontSizeProvider>
        </ThemeProvider>
    )
}

export default App
