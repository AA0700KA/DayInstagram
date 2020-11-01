package java.devcolibri.itvdn.com.day3instagram.screens.notifications

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.data.NotificationsRepository
import java.devcolibri.itvdn.com.day3instagram.models.Notification
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseViewModel

class NotificationsViewModel(private val notificationsRepo: NotificationsRepository,
                             onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {

    lateinit var notifications: LiveData<List<Notification>>
    private lateinit var uid: String

    fun init(uid: String) {
        this.uid = uid
        notifications = notificationsRepo.getNotifications(uid)
    }

    fun setNotificationsRead(notifications: List<Notification>) {
        val ids = notifications.filter { !it.read }.map { it.id }
        if (ids.isNotEmpty()) {
            notificationsRepo.setNotificationsRead(uid, ids, true)
                .addOnFailureListener(onFailureListener)
        }
    }

}