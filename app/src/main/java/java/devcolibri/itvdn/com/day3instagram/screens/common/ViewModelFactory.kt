package java.devcolibri.itvdn.com.day3instagram.screens.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.common.firebase.FirebaseAuthManager
import java.devcolibri.itvdn.com.day3instagram.screens.add_friends.AddFriendsViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.edit_profile.EditProfileViewModel
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseFeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseUsersRepository
import java.devcolibri.itvdn.com.day3instagram.screens.home.HomeViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.profilesettings.ProfileSettingsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val onFailureListener: OnFailureListener) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFriendsViewModel::class.java)) {
            return AddFriendsViewModel(onFailureListener, FirebaseUsersRepository(),
                FirebaseFeedPostsRepository()) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel(onFailureListener, FirebaseUsersRepository()) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(onFailureListener, FirebaseFeedPostsRepository()) as T
        } else if (modelClass.isAssignableFrom(ProfileSettingsViewModel::class.java)) {
            return ProfileSettingsViewModel(FirebaseAuthManager()) as T
        } else {
            error("Unknown view model class $modelClass")
        }
    }

}