package firebasedispatcherdemo.shadow.com.firebasejobdispatcherdemo.Notification

import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

class NotificationService: JobService() {
    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        Log.d("NotificationService", ""+System.currentTimeMillis())
        return false
    }
}