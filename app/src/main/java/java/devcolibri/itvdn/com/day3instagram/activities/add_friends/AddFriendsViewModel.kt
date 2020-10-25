package java.devcolibri.itvdn.com.day3instagram.activities.add_friends

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.devcolibri.itvdn.com.day3instagram.activities.map
import java.devcolibri.itvdn.com.day3instagram.models.User

class AddFriendsViewModel(private val repository: AddFriendsRepository) : ViewModel() {

    val userAndFriends: LiveData<Pair<User, List<User>>> =
        repository.getUsers().map { allUsers ->
            val (userList, otherUsersList) = allUsers.partition {
                it.uid == repository.currentUid()
            }
            userList.first() to otherUsersList
        }

    fun setFollow(currentUid: String, uid: String, follow: Boolean): Task<Void> {
        return if (follow) {
            Tasks.whenAll(
                repository.addFollow(currentUid, uid),
                repository.addFollower(currentUid, uid),
                repository.copyFeedPosts(postsAuthorUid = uid, uid = currentUid))
        } else {
            Tasks.whenAll(
                repository.deleteFollow(currentUid, uid),
                repository.deleteFollower(currentUid, uid),
                repository.deleteFeedPosts(postsAuthorUid = uid, uid = currentUid))
        }
    }

}