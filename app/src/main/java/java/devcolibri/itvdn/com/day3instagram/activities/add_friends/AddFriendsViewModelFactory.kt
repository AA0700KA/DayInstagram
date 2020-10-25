package java.devcolibri.itvdn.com.day3instagram.activities.add_friends

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class AddFriendsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddFriendsViewModel(FirebaseAddFriendsRepository()) as T
    }
}