package com.example.mad_practical8_21012011118

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mad_practical8_21012011118.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.createAlarmbutton.setOnClickListener {
            showTimerDialog()
            //If you visible here then before set the time it will be visible
           // binding.cardView2.visibility = View.VISIBLE
        }

        binding.cancelAlarmbutton.setOnClickListener {
            stop()
            binding.cardView2.visibility = View.GONE
        }
    }
    private fun showTimerDialog()
    {
        val cal: Calendar = Calendar.getInstance()
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cal.get(Calendar.MINUTE)

        //tp=Time picker dialog

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
        val day: Int = alarmCalender.get(Calendar.DATE)
        alarmCalender.set(year, month, day, hour, minute, 0)
        binding.tv6.text = SimpleDateFormat("hh:mm:ss a").format(alarmCalender.time)
        binding.cardView2.visibility = View.VISIBLE
        setAlarm(alarmCalender.timeInMillis, "START")
    }

    fun stop(){
        setAlarm(-1,"STOP")
    }

    private fun setAlarm(milliTime:Long, action:String)
    {
        val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 2345, intentAlarm, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if(action == AlarmBroadcastReceiver.ALARM_START)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliTime, pendingIntent)
            Toast.makeText(this, "Start Alarm", Toast.LENGTH_LONG).show()
        }
        else if(action == AlarmBroadcastReceiver.ALARM_STOP)
        {
            alarmManager.cancel(pendingIntent)
            //stop the ringtone
            val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
            intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
            sendBroadcast(intentAlarm)
            Toast.makeText(this, "Stop Alarm", Toast.LENGTH_LONG).show()
        }
    }
}