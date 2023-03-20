### Service
This is a demo app on how to implement BoundService.

### Key Points of Service:

* Bound Services in Android are used when we want to establish a connection between an Activity and a Service, in order to allow them to communicate with each other. Bound Services provide a client-server interface that enables multiple components to bind to it and access its functionality through an interface.
* Bound Services are closely tied to the lifecycle of the Activity that bound to them. When the bound Activity is destroyed, the Service is automatically unbound and stopped. Therefore, Bound Services are not designed to run in the background after the app is killed or the Activity that bound to them is destroyed.
* Bound Services in Android can communicate with only one client at a time. When a client binds to the service, the service replaces any existing client with the new one. This means that only one component can communicate with a Bound Service at any given time.
* If you include the BIND_AUTO_CREATE flag as a parameter when calling bindService(), Android will automatically create the service if it does not already exist. This means that you do not need to explicitly start the service using startService().  its android.app.Service.onStartCommand method will still only be called due to an explicit call to startService.
* You cannot directly call public methods in a Service from an Activity without using a Binder. The Binder is used to create a connection between the Activity and the Service, allowing the Activity to communicate with the Service and call its public methods.

Here are some use cases where Bound Services are commonly used:

* Music Players: Music players often use Bound Services to allow Activities to play, pause, or skip songs. 
* Messaging Applications: Messaging apps use Bound Services to send and receive messages in the background and notify the user when new messages arrive. 
* Location Tracking: Location tracking apps use Bound Services to continuously monitor the user's location and send updates to the application's server. 
* Downloading Data: Apps that download large files or data sets use Bound Services to download data in the background, allowing the user to continue using the app while the download is in progress.

### Start service

```
serviceConnection = object : ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, bidner: IBinder?) {
                mService = (bidner as MyBoundService.MyBinder).getMyService()
            }
            override fun onServiceDisconnected(name: ComponentName?) { }
        }
        Intent(this, MyBoundService::class.java).also {
            startService(it)
            bindService(it, serviceConnection as ServiceConnection, Context.BIND_AUTO_CREATE)
        }
```

### Stop service

* To stop a running service in Android, you can use the stopService() method or the stopSelf() method if you are using a bound service.
```
val intent = Intent(this, MyService::class.java)
stopService(intent)

```
* To stop a thread that is running within a service in Android, you can use a boolean flag to control the loop of the thread. 
```
myThread.interrupt()

```
