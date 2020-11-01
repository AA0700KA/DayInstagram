package java.devcolibri.itvdn.com.day3instagram.data


import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import java.devcolibri.itvdn.com.day3instagram.models.Notification


interface NotificationsRepository {
    fun createNotification(uid: String, notification: Notification): Task<Unit>
    fun getNotifications(uid: String): LiveData<List<Notification>>
    fun setNotificationsRead(uid: String, ids: List<String>, read: Boolean): Task<Unit>
}