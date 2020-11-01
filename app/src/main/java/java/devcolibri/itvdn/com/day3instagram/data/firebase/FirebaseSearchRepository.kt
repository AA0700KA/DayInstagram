package java.devcolibri.itvdn.com.day3instagram.data.firebase

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import java.devcolibri.itvdn.com.day3instagram.common.toUnit
import java.devcolibri.itvdn.com.day3instagram.data.SearchRepository
import java.devcolibri.itvdn.com.day3instagram.data.common.map
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.FirebaseLiveData
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.database
import java.devcolibri.itvdn.com.day3instagram.models.SearchPost

class FirebaseSearchRepository : SearchRepository {
    override fun createPost(post: SearchPost): Task<Unit> =
        database.child("search-posts").push()
            .setValue(post.copy(caption = post.caption.toLowerCase())).toUnit()

    override fun searchPosts(text: String): LiveData<List<SearchPost>> {
        val reference = database.child("search-posts")
        val query = if (text.isEmpty()) {
            reference
        } else {
            reference.orderByChild("caption")
                .startAt(text.toLowerCase()).endAt("${text.toLowerCase()}\\uf8ff")
        }
        return FirebaseLiveData(query).map {
            it.children.map { it.asSearchPost()!! }
        }
    }
}

private fun DataSnapshot.asSearchPost(): SearchPost? =
    getValue(SearchPost::class.java)?.copy(id = key)