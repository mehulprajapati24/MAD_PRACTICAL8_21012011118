package com.example.mad_practical8_21012011118

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.WindowCompat
import com.example.mad_practical8_21012011118.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
       // WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  setSupportActionBar(binding.toolbar)
    //    val createAlramBtn:Button = findViewById(R.id.createAlarmbutton)
    //    val cardView2: MaterialCardView = findViewById(R.id.cardView2)
    //    val cancelAlarmBtn:Button = findViewById(R.id.cancelAlarmbutton)

        binding.createAlarmbutton.setOnClickListener {
//            if(cardView2.visibility==View.GONE)
//            {
//                cardView2.visibility = View.VISIBLE
//            }
            showTimerDialog()
            binding.cardView2.visibility = View.VISIBLE
        }

        binding.cancelAlarmbutton.setOnClickListener {
//            if(cardView2.visibility==View.VISIBLE)
//            {
//                cardView2.visibility = View.GONE
//            }
            binding.cardView2.visibility = View.GONE
        }
    }

    private fun showTimerDialog()
    {
        val cal: Calendar = Calendar.getInstance()
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cal.get(Calendar.MINUTE)

        //Time picker dialog

        val picker = TimePickerDialog(
            this,
            {tp, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute)},
            hour,
            minutes,
            false
        )
        picker.show()
    }

    private fun sendDialogDataToActivity(hour: Int, minute: Int)
    {
        val alarmCalender = Calendar.getInstance()
        val year: Int = alarmCalender.get(Calendar.YEAR)
        val month: Int = alarmCalender.get(Calendar.MONTH)

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