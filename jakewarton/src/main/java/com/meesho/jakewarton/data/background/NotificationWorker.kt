package com.meesho.jakewarton.data.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.meesho.jakewarton.R
import com.meesho.jakewarton.domain.GetElapsedTime
import com.meesho.jakewarton.domain.GetSession
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect


@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getSession: GetSession,
    private val getElapsedTime: GetElapsedTime,
) : CoroutineWorker(context, workerParams) {

    var notificationBuilder: NotificationCompat.Builder? = null
    var notificationManager: NotificationManager? = null

    init {
        notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override suspend fun doWork(): Result {
        if (getSession.get().session_status) {
            setForeground(createForegroundInfo())
            getElapsedTime.get().collect {
                val duration =
                    "Session is Active  Hour:${it.hour} Minute:${it.minute} Seconds:${it.seconds}"
                showProgress(duration, notificationManager)

            }
        }
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {

//        val contentIntent = PendingIntent.getActivity(
//            applicationContext,
//            0, Intent(applicationContext, MainActivity::class.java), 0
//        )
        createNotificationChannel(notificationManager)

        notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(CHANNEL_NAME)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)


        return ForegroundInfo(NOTIFICATION_ID, notificationBuilder!!.build())
    }

    private fun showProgress(
        session: String,
        notificationManager: NotificationManager?
    ) {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(CHANNEL_NAME)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setOngoing(true)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentText(session)
            .build()
        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel =
                notificationManager?.getNotificationChannel(CHANNEL_ID)
            if (notificationChannel == null) {
                notificationChannel = NotificationChannel(
                    CHANNEL_ID, SESSION_NOTIFICATION, NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }


    companion object {
        const val CHANNEL_NAME = "Meesho Session"
        const val CHANNEL_ID = "Library Seat Booking"
        const val NOTIFICATION_ID = 1
        const val SESSION_NOTIFICATION = "Session Notification"
    }
}
