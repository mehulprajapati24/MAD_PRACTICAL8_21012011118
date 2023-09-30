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
       // WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  setSupportActionBar(binding.toolbar)
    //    val createAlramBtn:Button = findViewById(R.id.createAlarmbutton)
    //    val cardView2: MaterialCardView = findViewById(R.id.cardView2)
    //    val cancelAlarmBtn:Button = findViewById(R.id.cancelAlarmbutton)

        // It set time but It not continuously changes
//        val currentTime = Calendar.getInstance()
//        val hour = currentTime.get(Calendar.HOUR)
//        val minute = currentTime.get(Calendar.MINUTE)
//        val second = currentTime.get(Calendar.SECOND)
//        val amPm = if (currentTime.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
//        val month = SimpleDateFormat("MMM", Locale.US).format(currentTime.time)
//        val day = currentTime.get(Calendar.DAY_OF_MONTH)
//        val year = currentTime.get(Calendar.YEAR)
//        val formattedTime = String.format("%02d:%02d:%02d %s %s, %d %d", hour, minute, second, amPm, month, day, year)
//        binding.tv4.text = formattedTime

        // Start a coroutine to update the clock
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                updateClock()
                delay(1000) // Delay for 1 second (1000 milliseconds)
            }
        }


        binding.createAlarmbutton.setOnClickListener {
//            if(cardView2.visibility==View.GONE)
//            {
//                cardView2.visibility = View.VISIBLE
//            }
            showTimerDialog()
            //If you visible here then before set the time it will be visible
           // binding.cardView2.visibility = View.VISIBLE
        }

        binding.cancelAlarmbutton.setOnClickListener {
//            if(cardView2.visibility==View.VISIBLE)
//            {
//                cardView2.visibility = View.GONE
//            }
            binding.cardView2.visibility = View.GONE
            //It stop alarm before it play
          //  setAlarm(-1,"STOP")
            val alarmCalender = Calendar.getInstance()
            setAlarm(alarmCalender.timeInMillis, "STOP")
        }
    }

    private fun updateClock()
    {
        val currentTime = System.currentTimeMillis()
        binding.tv4.text = SimpleDateFormat("hh:mm:ss a MMM, dd yyyy", Locale.US).format(currentTime)
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
        //binding.tv6.text = SimpleDateFormat("hh:mm:ss a").format(alarmCalender)
        binding.tv6.text = SimpleDateFormat("hh:mm:ss a", Locale.US).format(alarmCalender.time)
        binding.cardView2.visibility = View.VISIBLE
        setAlarm(alarmCalender.timeInMillis, "START")
    }

    private fun setAlarm(milliTime:Long, action:String)
    {
        val intentAlarm = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        intentAlarm.putExtra(AlarmBroadcastReceiver.ALARM_KEY, action)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 2345, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
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
            sendBroadcast(intent)
            Toast.makeText(this, "Stop Alarm", Toast.LENGTH_LONG).show()

            // Stop the service
            // Extra lines, If you do not write it then Alarm is play it's okay but after then you can not cancel.
            // Alarm continuously playing until Alarm music is stop
            // So after playing alarm music, for to stop that this both lines are used
            val stopServiceIntent = Intent(this, AlarmService::class.java)
            stopService(stopServiceIntent)
        }
    }
}