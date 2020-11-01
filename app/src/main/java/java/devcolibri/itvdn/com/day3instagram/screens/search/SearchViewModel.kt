package java.devcolibri.itvdn.com.day3instagram.screens.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.data.SearchRepository
import java.devcolibri.itvdn.com.day3instagram.models.SearchPost
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseViewModel

class SearchViewModel(searchRepo: SearchRepository,
                      onFailureListener: OnFailureListener
) : BaseViewModel(onFailureListener) {
    private val searchText = MutableLiveData<String>()

    val posts: LiveData<List<SearchPost>> = Transformations.switchMap(searchText) { text ->
        searchRepo.searchPosts(text)
    }

    fun setSearchText(text: String) {
        searchText.value = text
    }

}