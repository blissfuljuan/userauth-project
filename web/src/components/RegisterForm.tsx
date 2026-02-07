import { useState, type FormEvent } from "react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "./ui/card";
import { Label } from "./ui/label";
import { Input } from "./ui/input";
import { Checkbox } from "./ui/checkbox";
import { Button } from "./ui/button";

export function RegisterForm() {
    const [password, setPassword] = useState("")
    const [confirmPassword, setConfirmPassword] = useState("")
    const [acceptedTerms, setAcceptedTerms] = useState(false)

    const passwordsMatch = password === confirmPassword

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault()

        if (!passwordsMatch) {
            alert("Passwords do not match")
            return
        }

        if (!acceptedTerms) {
            alert("You must accept the terms and conditions")
            return
        }

        // submit to API
        console.log("Registration submitted")
    }

    return (
        <Card className="w-full max-w-md">
            <CardHeader>
                <CardTitle>Create an account</CardTitle>
                <CardDescription>Fill in the details below to register</CardDescription>
            </CardHeader>

            <CardContent>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div className="space-y-2">
                        <Label htmlFor="firstname">First name</Label>
                        <Input id="firstname" required />
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="lastname">Last name</Label>
                        <Input id="lastname" required />
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="middlename">Middle name</Label>
                        <Input id="middlename" />
                    </div>

                    <div className="space-y-2">
                        <Label htmlFor="email">Email</Label>
                        <Input
                            id="email"
                            type="email"
                            placeholder="Email"
                            required />
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="password">Password</Label>
                        <Input
                            id="password"
                            type="password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    <div className="space-y-2">
                        <Label htmlFor="confirmPassword">Confirm Password</Label>
                        <Input
                            id="confirmPassword"
                            type="password"
                            required
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)} />
                        {!passwordsMatch && confirmPassword && (
                            <p className="text-sm text-destructive">
                                Passwords do not match
                            </p>
                        )}
                    </div>

                    <div className="flex items-start space-x-2">
                        <Checkbox
                            id="terms"
                            checked={acceptedTerms}
                            onCheckedChange={(value) => setAcceptedTerms(Boolean(value))} />
                        <Label htmlFor="terms" className="text-sm leading-snug">
                            I accept the{" "}
                            <span className="underline cursor-pointer">
                                terms and conditions
                            </span>
                        </Label>
                    </div>

                    <Button type="submit" className="w-full">
                        Register
                    </Button>
                </form>
            </CardContent>
        </Card>
    )
}