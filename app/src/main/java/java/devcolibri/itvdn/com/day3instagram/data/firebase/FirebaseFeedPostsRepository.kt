package java.devcolibri.itvdn.com.day3instagram.data.firebase

import com.google.android.gms.tasks.Task
import java.devcolibri.itvdn.com.day3instagram.common.task
import java.devcolibri.itvdn.com.day3instagram.data.FeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.common.TaskSourceOnCompleteListener
import java.devcolibri.itvdn.com.day3instagram.common.ValueEventListenerAdapter
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.database
import java.devcolibri.itvdn.com.day3instagram.common.toUnit

class FirebaseFeedPostsRepository: FeedPostsRepository {

    override fun copyFeedPosts(postsAuthorUid: String, uid: String): Task<Unit> =
        task { taskSource ->
            database.child("feed-posts").child(postsAuthorUid)
                .orderByChild("uid")
                .equalTo(postsAuthorUid)
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    val postsMap = it.children.map { it.key to it.value }.toMap()
                    database.child("feed-posts").child(uid).updateChildren(postsMap)
                        .toUnit()
                        .addOnCompleteListener(
                            TaskSourceOnCompleteListener(
                                taskSource
                            )
                        )
                })
        }

    override fun deleteFeedPosts(postsAuthorUid: String, uid: String): Task<Unit> =
        task { taskSource ->
            database.child("feed-posts").child(uid)
                .orderByChild("uid")
                .equalTo(postsAuthorUid)
                .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                    val postsMap = it.children.map { it.key to null }.toMap()
                    database.child("feed-posts").child(uid).updateChildren(postsMap)
                        .toUnit()
                        .addOnCompleteListener(
                            TaskSourceOnCompleteListener(
                                taskSource
                            )
                        )
                })
        }

}