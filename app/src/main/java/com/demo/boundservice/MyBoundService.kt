package com.demo.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.demo.boundservice.MainActivity.Companion.Tag
import java.lang.Thread.sleep
import java.util.*

class MyBoundService : Service() {
    private var randomNumber:Int? = null
    private val mBinder = MyBinder()
    private var numberThread: Thread?= null
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(Tag, "onBind() called")
        return mBinder
    }

    inner class MyBinder : Binder() {
        fun getMyService(): MyBoundService {
            return this@MyBoundService
        }
    }

    fun getRandomNumber(): Int? {
        return randomNumber
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        numberThread = Thread {
            try {
                Log.e(Tag, "onStartCommand() called")
                for (i in 1..100){
                    sleep(1000)
                    randomNumber = Random().nextInt(1000)
                    Log.e(Tag,"Random number: $randomNumber")
                }
            }
            catch (e: InterruptedException) {
                Log.e(Tag, "Thread was interrupted: ${e.message}")
            }
        }
        numberThread?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        Log.e(Tag, "onDestroy() called")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.e(Tag, "onUnbind() called")
        stopSelf()
        numberThread?.interrupt()
        return super.onUnbind(intent)
    }
}