package java.devcolibri.itvdn.com.day3instagram.screens

import android.app.Application
import java.devcolibri.itvdn.com.day3instagram.common.firebase.FirebaseAuthManager
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseFeedPostsRepository
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseNotificationsRepository
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseSearchRepository
import java.devcolibri.itvdn.com.day3instagram.data.firebase.FirebaseUsersRepository
import java.devcolibri.itvdn.com.day3instagram.screens.notifications.NotificationsCreator
import java.devcolibri.itvdn.com.day3instagram.screens.search.SearchPostsCreator

class InstagramApp : Application() {
    val usersRepo by lazy { FirebaseUsersRepository() }
    val feedPostsRepo by lazy { FirebaseFeedPostsRepository() }
    val notificationsRepo by lazy { FirebaseNotificationsRepository() }
    val authManager by lazy { FirebaseAuthManager() }
    val searchRepo by lazy { FirebaseSearchRepository() }

    override fun onCreate() {
        super.onCreate()
        NotificationsCreator(notificationsRepo, usersRepo, feedPostsRepo)
        SearchPostsCreator(searchRepo)
    }

}