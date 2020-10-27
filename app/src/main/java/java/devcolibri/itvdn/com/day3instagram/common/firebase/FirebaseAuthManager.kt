package java.devcolibri.itvdn.com.day3instagram.common.firebase

import java.devcolibri.itvdn.com.day3instagram.common.AuthManager
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.auth

class FirebaseAuthManager : AuthManager {

    override fun signOut() {
        auth.signOut()
    }

}