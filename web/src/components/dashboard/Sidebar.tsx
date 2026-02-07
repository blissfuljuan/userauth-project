import type { ReactNode } from "react";
import { Button } from "../ui/button";
import { Separator } from "../ui/separator";
import type { Page } from "./DashboardLayout";

interface SidebarProps {
    page: Page
    onNavigate: (page: Page) => void
}

export function Sidebar({ page, onNavigate }: SidebarProps) {
    return (
        <aside className="w-56 bg-background border-r p-4 flex flex-col">
            <nav className="space-y-1">
                <SidebarButton
                    active={page === "dashboard"}
                    onClick={() => onNavigate("dashboard")}>
                    Dashboard
                </SidebarButton>

                <SidebarButton
                    active={page === "profile"}
                    onClick={() => onNavigate("profile")}>
                    Profile
                </SidebarButton>

                <Separator className="my-4" />

                <SidebarButton
                    onClick={() => alert("Logged out")}>
                    Logout
                </SidebarButton>
            </nav>
        </aside>
    )
}

function SidebarButton({
    active,
    destructive,
    children,
    onClick,
}: {
    active?: boolean
    destructive?: boolean
    children: ReactNode
    onClick: () => void
}) {
    return (
        <Button
            variant={
                destructive ? "ghost" :
                    active ? "secondary" : "ghost"}
            className={`w-full justify-start ${
                destructive ? "text-destructive hover:text-destructive" : ""}`}
            onClick={onClick}>
            {children}
        </Button>
    )
}