import { Button } from "./ui/button";
import { Label } from "./ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Input } from "./ui/input";
import type { FormEvent } from "react";

export function LoginForm() {

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault()
    }
    return (
        <Card className="w-full max-w-sm">
            <CardHeader>
                <CardTitle>Login</CardTitle>
                <CardDescription>
                    Enter your email and password to Login
                </CardDescription>
            </CardHeader>

            <CardContent>
                <form className="space-y-4">
                    <div className="space-y-2">
                        <Label htmlFor="email">Email</Label>
                        <Input
                            id="email"
                            type="email"
                            placeholder="Email"
                            required />

                        <Label htmlFor="email">Password</Label>
                        <Input
                            id="password"
                            type="password"
                            required />
                    </div>

                    <Button type="submit" className="w-full" onSubmit={handleSubmit}>Login</Button>
                </form>
            </CardContent>
        </Card>
    )
}