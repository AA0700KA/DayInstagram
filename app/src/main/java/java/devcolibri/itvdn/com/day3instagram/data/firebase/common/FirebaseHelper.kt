package java.devcolibri.itvdn.com.day3instagram.data.firebase.common

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseHelper(private val activity: Activity) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    val storage: StorageReference = FirebaseStorage.getInstance().reference

    fun currentUserReference(): DatabaseReference =
        database.child("Users").child(auth.currentUser!!.uid)

}