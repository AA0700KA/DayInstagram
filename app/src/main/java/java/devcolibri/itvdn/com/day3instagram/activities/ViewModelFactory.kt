package java.devcolibri.itvdn.com.day3instagram.activities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import java.devcolibri.itvdn.com.day3instagram.activities.add_friends.AddFriendsViewModel
import java.devcolibri.itvdn.com.day3instagram.activities.add_friends.FirebaseAddFriendsRepository
import java.devcolibri.itvdn.com.day3instagram.activities.edit_profile.EditProfileViewModel
import java.devcolibri.itvdn.com.day3instagram.activities.edit_profile.FirebaseEditProfileRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFriendsViewModel::class.java)) {
            return AddFriendsViewModel(FirebaseAddFriendsRepository()) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel(FirebaseEditProfileRepository()) as T
        } else {
            error("Unknown view model class $modelClass")
        }
    }

}