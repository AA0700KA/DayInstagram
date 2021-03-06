package java.devcolibri.itvdn.com.day3instagram.screens.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.common.SingleLiveEvent
import java.devcolibri.itvdn.com.day3instagram.data.FeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.data.common.map
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.FeedPostLikes
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseViewModel

class HomeViewModel (onFailureListener: OnFailureListener,
                     private val feedPostsRepo: FeedPostsRepository
) : BaseViewModel(onFailureListener) {
    lateinit var uid: String
    lateinit var feedPosts: LiveData<List<FeedPost>>
    private var loadedLikes = mapOf<String, LiveData<FeedPostLikes>>()

    private val _goToCommentsScreen = SingleLiveEvent<String>()
    val goToCommentsScreen = _goToCommentsScreen

    fun init(uid: String) {
        this.uid = uid
        feedPosts = feedPostsRepo.getFeedPosts(uid).map {
            it.sortedByDescending { it.timestampDate() }
        }
    }

    fun toggleLike(postId: String) {
        feedPostsRepo.toggleLike(postId, uid).addOnFailureListener(onFailureListener)
    }

    fun getLikes(postId: String): LiveData<FeedPostLikes>? = loadedLikes[postId]

    fun loadLikes(postId: String): LiveData<FeedPostLikes> {
        val existingLoadedLikes = loadedLikes[postId]
        if (existingLoadedLikes == null) {
            val liveData = feedPostsRepo.getLikes(postId).map { likes ->
                FeedPostLikes(
                    likesCount = likes.size,
                    likedByUser = likes.find { it.userId == uid } != null)
            }
            loadedLikes += postId to liveData
            return liveData
        } else {
            return existingLoadedLikes
        }
    }

    fun openComments(postId: String) {
        _goToCommentsScreen.value = postId
    }

}