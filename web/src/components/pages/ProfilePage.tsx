export function ProfilePage() {
    return (
        <div className="space-y-2 max-w-lg">
            <h2 className="text-2xl font-bold">Profile</h2>
            <p className="text-muted-foreground">
                Profile details will be shown here.
            </p>

            <div className="rounded-md border p-4 bg-background">
                <p><strong>Name: </strong>John Doe</p>
                <p><strong>Email: </strong>jdoe@example.com</p>
            </div>
        </div>
    )
}