package java.devcolibri.itvdn.com.day3instagram.screens.share

import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Tasks
import java.devcolibri.itvdn.com.day3instagram.data.FeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.data.UsersRepository
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseViewModel


class ShareViewModel(private val usersRepo: UsersRepository,
                     private val feedPostsRepository: FeedPostsRepository,
                     onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {
    val user = usersRepo.getUser()

    fun share(user: User, imageUri: Uri?, caption: String) {
        if (imageUri != null) {
            usersRepo.uploadUserImage(user.uid, imageUri).onSuccessTask { downloadUrl ->
                Tasks.whenAll(
                    usersRepo.setUserImage(user.uid, downloadUrl!!),
                    feedPostsRepository.createFeedPost(user.uid, mkFeedPost(user, caption,
                        downloadUrl.toString()))
                )
            }.addOnFailureListener(onFailureListener)
        }
    }

    private fun mkFeedPost(user: User, caption: String, imageDownloadUrl: String): FeedPost {
        return FeedPost(
            uid = user.uid,
            username = user.username,
            image = imageDownloadUrl,
            caption = caption,
            photo = user.photo
        )
    }
}