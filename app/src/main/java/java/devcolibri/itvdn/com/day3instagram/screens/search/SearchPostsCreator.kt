package java.devcolibri.itvdn.com.day3instagram.screens.search

import android.arch.lifecycle.Observer
import android.util.Log
import java.devcolibri.itvdn.com.day3instagram.common.BaseEventListener
import java.devcolibri.itvdn.com.day3instagram.common.Event
import java.devcolibri.itvdn.com.day3instagram.common.EventBus
import java.devcolibri.itvdn.com.day3instagram.data.SearchRepository
import java.devcolibri.itvdn.com.day3instagram.models.SearchPost

class SearchPostsCreator(searchRepo: SearchRepository) : BaseEventListener() {

    init {
        EventBus.events.observe(this, Observer {
            it?.let { event ->
                when (event) {
                    is Event.CreateFeedPost -> {
                        val searchPost = with(event.post) {
                            SearchPost(
                                image = image,
                                caption = caption,
                                postId = id)
                        }
                        searchRepo.createPost(searchPost).addOnFailureListener {
                            Log.d(TAG, "Failed to create search post for event: $event", it)
                        }
                    }
                    else -> {
                    }
                }
            }
        })
    }

    companion object {
        const val TAG = "SearchPostsCreator"
    }

}