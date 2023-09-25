package com.example.mad_practical8_21012011118

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createAlramBtn:Button = findViewById(R.id.createAlarmbutton)
        val cardView2: MaterialCardView = findViewById(R.id.cardView2)
        val cancelAlarmBtn:Button = findViewById(R.id.cancelAlarmbutton)

        createAlramBtn.setOnClickListener {
//            if(cardView2.visibility==View.GONE)
//            {
//                cardView2.visibility = View.VISIBLE
//            }
            cardView2.visibility = View.VISIBLE
        }

        cancelAlarmBtn.setOnClickListener {
//            if(cardView2.visibility==View.VISIBLE)
//            {
//                cardView2.visibility = View.GONE
//            }
            cardView2.visibility = View.GONE
        }
    }

    fun setAlarm(milliTime:Long, action:String)
    {
        val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 2345, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == AlarmBroadcastReceiver.ALARM_START)
        {
            manager.setExact(AlarmManager.RTC_WAKEUP, milliTime, pendingIntent)
        }
    }
}