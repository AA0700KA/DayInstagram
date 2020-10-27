package java.devcolibri.itvdn.com.day3instagram.screens.add_friends

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.devcolibri.itvdn.com.day3instagram.data.common.map
import java.devcolibri.itvdn.com.day3instagram.data.FeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.data.UsersRepository
import java.devcolibri.itvdn.com.day3instagram.models.User

class AddFriendsViewModel(private val onFailureListener: OnFailureListener,
                          private val usersRepo: UsersRepository,
                          private val feedPostsRepo: FeedPostsRepository
) : ViewModel() {

    val userAndFriends: LiveData<Pair<User, List<User>>> =
        usersRepo.getUsers().map { allUsers ->
            val (userList, otherUsersList) = allUsers.partition {
                it.uid == usersRepo.currentUid()
            }
            userList.first() to otherUsersList
        }

    fun setFollow(currentUid: String, uid: String, follow: Boolean): Task<Void> {
        return (if (follow) {
            Tasks.whenAll(
                usersRepo.addFollow(currentUid, uid),
                usersRepo.addFollower(currentUid, uid),
                feedPostsRepo.copyFeedPosts(postsAuthorUid = uid, uid = currentUid))
        } else {
            Tasks.whenAll(
                usersRepo.deleteFollow(currentUid, uid),
                usersRepo.deleteFollower(currentUid, uid),
                feedPostsRepo.deleteFeedPosts(postsAuthorUid = uid, uid = currentUid))
        }).addOnFailureListener(onFailureListener)
    }

}