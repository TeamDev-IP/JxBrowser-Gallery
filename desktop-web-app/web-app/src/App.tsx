/*
 *  Copyright (c) 2025 TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

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
import {ThemeProvider} from "@/components/theme-provider.tsx";

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
    );
}

export default App;
