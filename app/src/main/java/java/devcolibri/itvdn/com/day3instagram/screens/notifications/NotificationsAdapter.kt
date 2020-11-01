package java.devcolibri.itvdn.com.day3instagram.screens.notifications


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.notification_item.view.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.Notification
import java.devcolibri.itvdn.com.day3instagram.models.NotificationType
import java.devcolibri.itvdn.com.day3instagram.screens.common.loadImageOrHide
import java.devcolibri.itvdn.com.day3instagram.screens.common.loadUserPhoto
import java.devcolibri.itvdn.com.day3instagram.screens.common.setCaptionText

class NotificationsAdapter(private val context: Context) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private var notifications = listOf<Notification>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        with(holder) {
            view.user_photo.loadUserPhoto(notification.photo)
            val notificationText = when (notification.type) {
                NotificationType.Comment -> context.getString(R.string.commented, notification.commentText)
                NotificationType.Like -> context.getString(R.string.liked_your_post)
                NotificationType.Follow -> context.getString(R.string.started_following_you)
            }
            view.notification_text.setCaptionText(notification.username, notificationText,
                notification.timestampDate())
            view.post_image.loadImageOrHide(notification.postImage)
        }
    }

    override fun getItemCount(): Int = notifications.size

    fun updateNotifications(newNotifications: List<Notification>) {
        notifications = newNotifications
        notifyDataSetChanged()
    }

}