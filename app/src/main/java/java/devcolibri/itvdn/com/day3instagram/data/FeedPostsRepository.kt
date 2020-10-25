package java.devcolibri.itvdn.com.day3instagram.data

import com.google.android.gms.tasks.Task

interface FeedPostsRepository {

    fun copyFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>
    fun deleteFeedPosts(postsAuthorUid: String, uid: String): Task<Unit>

}