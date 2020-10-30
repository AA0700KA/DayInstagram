package java.devcolibri.itvdn.com.day3instagram.common.firebase

import com.google.android.gms.tasks.Task
import java.devcolibri.itvdn.com.day3instagram.common.AuthManager
import java.devcolibri.itvdn.com.day3instagram.common.toUnit
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.auth

class FirebaseAuthManager : AuthManager {

    override fun signOut() {
        auth.signOut()
    }

    override fun signIn(email: String, password: String): Task<Unit> =
        auth.signInWithEmailAndPassword(email, password).toUnit()

}