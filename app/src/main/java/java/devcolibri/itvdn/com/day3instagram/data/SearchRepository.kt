package java.devcolibri.itvdn.com.day3instagram.data

import android.arch.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import java.devcolibri.itvdn.com.day3instagram.models.SearchPost

interface SearchRepository {
    fun searchPosts(text: String): LiveData<List<SearchPost>>
    fun createPost(post: SearchPost): Task<Unit>
}