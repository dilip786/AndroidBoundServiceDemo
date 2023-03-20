package com.demo.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.demo.boundservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val Tag ="BoundServiceDemo"
    }
    private var mIsBounded: Boolean = false
    private var mainActivityBinding: ActivityMainBinding? = null
    private var mService: MyBoundService? = null
    private var serviceConnection : ServiceConnection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding?.root)
        bindBoundService()
        initClickListeners()
    }

    private fun initClickListeners() {
        mainActivityBinding?.button?.setOnClickListener {
            if (mIsBounded) {
                mService?.getRandomNumber()?.let {
                    mainActivityBinding?.tvRandomNumber?.text = it.toString()
                }
            }
        }
    }

    private fun bindBoundService(){
        serviceConnection = object : ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, bidner: IBinder?) {
                Log.e(Tag,"onServiceConnected called")
                mService = (bidner as MyBoundService.MyBinder).getMyService()
                mIsBounded = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e(Tag,"onServiceDisconnected called")
                mIsBounded = false
            }
        }

        Intent(this, MyBoundService::class.java).also {
            startService(it)
            bindService(it, serviceConnection as ServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        if(mIsBounded) serviceConnection?.let { unbindService(it) }
        super.onStop()
    }

}
