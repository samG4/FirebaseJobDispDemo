package firebasedispatcherdemo.shadow.com.firebasejobdispatcherdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Lifetime
import com.firebase.jobdispatcher.Trigger
import firebasedispatcherdemo.shadow.com.firebasejobdispatcherdemo.Notification.NotificationService
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    lateinit var dispatcher : FirebaseJobDispatcher
    var dateSelected = 0L
    var timeSelected = 0L
    var futuredate=0L
    var date =0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        date = calendar.date

        calendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, day ->
            tvDate.text = day.toString() + "/" + month + "/" + year
            val c = Calendar.getInstance()
            c.set(year, month, day)
            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)
            dateSelected = c.timeInMillis //this is what you want to use later
        })

        button.setOnClickListener({
            var hours = TimeUnit.HOURS.toMillis(hours.text.toString().toLong())
            var mins = TimeUnit.MINUTES.toMillis(mins.text.toString().toLong())
            timeSelected = hours+mins
            futuredate = dateSelected+timeSelected
            val c = Calendar.getInstance()
            c.timeInMillis=futuredate
            dateSelected = c.timeInMillis
            Log.d("MainActi #OnButtonCLick",""+dateSelected)

            var num = (TimeUnit.MILLISECONDS.toSeconds(futuredate-System.currentTimeMillis())).toInt()
            Log.d("MainActi #time",""+num)
            dispatcher.cancelAll()

            if(num>0) {
                val myJob = dispatcher.newJobBuilder()
                        .setService(NotificationService::class.java)
                        .setTag("notification_job")
                        .setRecurring(false)
                        .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                        .setTrigger(Trigger.executionWindow(num, num + 10))
                        .setReplaceCurrent(false)
                        .build()
                dispatcher.mustSchedule(myJob)
                Toast.makeText(applicationContext, "Job scheduled after "+num,Toast.LENGTH_LONG).show()
            }
        })

    }

}
