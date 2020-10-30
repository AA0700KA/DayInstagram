package java.devcolibri.itvdn.com.day3instagram.screens.common

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnFailureListener
import java.devcolibri.itvdn.com.day3instagram.common.firebase.FirebaseAuthManager
import java.devcolibri.itvdn.com.day3instagram.screens.add_friends.AddFriendsViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.edit_profile.EditProfileViewModel
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseFeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseUsersRepository
import java.devcolibri.itvdn.com.day3instagram.screens.home.HomeViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.login.LoginViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.profile.ProfileViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.profilesettings.ProfileSettingsViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.register.RegisterViewModel
import java.devcolibri.itvdn.com.day3instagram.screens.share.ShareViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val app: Application,
                       private val commonViewModel: CommonViewModel,
                       private val onFailureListener: OnFailureListener) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val usersRepo by lazy { FirebaseUsersRepository() }
        val feedPostsRepo by lazy { FirebaseFeedPostsRepository() }
        val authManager by lazy { FirebaseAuthManager() }

        if (modelClass.isAssignableFrom(AddFriendsViewModel::class.java)) {
            return AddFriendsViewModel(onFailureListener, usersRepo, feedPostsRepo) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(onFailureListener, usersRepo) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(onFailureListener, feedPostsRepo) as T
        } else if (modelClass.isAssignableFrom(ProfileSettingsViewModel::class.java)) {
            return ProfileSettingsViewModel(authManager) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authManager, app, commonViewModel, onFailureListener) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(usersRepo) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(commonViewModel, app, usersRepo) as T
        } else if (modelClass.isAssignableFrom(ShareViewModel::class.java)) {
            return ShareViewModel(usersRepo, onFailureListener) as T
        } else {
            error("Unknown view model class $modelClass")
        }

    }

}