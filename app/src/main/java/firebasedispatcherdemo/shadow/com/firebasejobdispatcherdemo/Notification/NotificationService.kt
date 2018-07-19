package firebasedispatcherdemo.shadow.com.firebasejobdispatcherdemo.Notification

import android.app.Notification
import android.app.Notification.DEFAULT_SOUND
import android.app.Notification.DEFAULT_VIBRATE
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import firebasedispatcherdemo.shadow.com.firebasejobdispatcherdemo.MainActivity
import firebasedispatcherdemo.shadow.com.firebasejobdispatcherdemo.R

class NotificationService: JobService() {
    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        Log.d("NotificationService", ""+System.currentTimeMillis())
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, System.currentTimeMillis().toInt(), intent, 0)
        val noti = Notification.Builder(this)
                .setContentTitle("Alert")
                .setContentText("Notification thrown")
                .setSmallIcon(R.mipmap.firefox)
                .setContentIntent(pIntent)
                .setDefaults(DEFAULT_SOUND or DEFAULT_VIBRATE)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build()
        val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // hide the notification after its selected
        noti.flags = noti.flags or Notification.FLAG_AUTO_CANCEL

        notificationManager.notify(0, noti)
        return false
    }
}