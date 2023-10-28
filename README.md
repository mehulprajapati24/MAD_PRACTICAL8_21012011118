# MAD_PRACTICAL8_21012011118

Study: BroadcastReceiver, Service, TextClock, TimePickerDialog, Calendar Class, SimpleDateFormat Class, PendingIntent, AlarmManager, getSystemService() method, sendBroadcast() method, MediaPlayer, startService(), stopService() method, Intent.getStringExtra(), Intent.putStringExtra(), MaterialCardView

AIM: What is BroadcastReceiver? Difference between Service and BroadcastReceiver. Create an Alarm application by using service & BroadcastReceiver by following below instructions.

Create MainActivity according to below UI design.

Create AlarmBroadcastReceiver class

Create AlarmService Class

Add android.permission.SCHEDULE_EXACT_ALARM Permission in Manifest file

ANSWER:

BroadcastReceiver:
A BroadcastReceiver is a component in Android that allows the system or other apps to send messages (called broadcasts) to your app.
It listens for specific broadcast messages or events and responds to them.
It's like a messenger that receives notifications and triggers actions based on them.
It typically has a short lifespan and is used for handling events or notifications in the background.

Service:
A Service is another component in Android that performs operations in the background without a user interface.
It's like a worker that can run independently of the UI, allowing tasks to continue even when the app is not in the foreground.
Services are used for long-running operations, such as playing music, downloading files, or performing network operations.
They can continue running even when the app is not in the foreground, making them suitable for tasks that require ongoing processing.

Difference:
Purpose: BroadcastReceiver is used for responding to system-wide events or notifications, while Service is used for long-running tasks that need to run in the background even if the app is not in the foreground.
Lifespan: BroadcastReceiver typically has a short lifespan, while Service can run for a long time.
User Interface: BroadcastReceiver doesn't have a user interface. It's mainly used to trigger actions or respond to events. Service also doesn't have a user interface, but it performs tasks in the background.
Triggering: BroadcastReceiver is triggered by system events or explicit broadcasts, while Service is started by an application component and runs independently.
Concurrency: BroadcastReceiver runs on the main thread by default, so it should complete quickly to avoid blocking the UI. Service runs on a separate thread, so it can handle long-running tasks without affecting the UI responsiveness.
Communication: BroadcastReceiver is used for one-way communication (receiving events), while Services can be used for both one-way (background tasks) and two-way communication (interacting with other components in the app).
