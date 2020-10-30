package java.devcolibri.itvdn.com.day3instagram.data.firebase.common


import android.arch.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.devcolibri.itvdn.com.day3instagram.models.Comment
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.User


val auth: FirebaseAuth = FirebaseAuth.getInstance()

val database: DatabaseReference = FirebaseDatabase.getInstance().reference

val storage: StorageReference = FirebaseStorage.getInstance().reference

fun currentUid(): String? = auth.currentUser?.uid

fun DatabaseReference.liveData(): LiveData<DataSnapshot> =
    FirebaseLiveData(this)

fun DatabaseReference.setValueTrueOrRemove(value: Boolean) =
    if (value) setValue(true) else removeValue()

fun DataSnapshot.asUser(): User? =
    getValue(User::class.java)?.copy(uid = key)

fun DataSnapshot.asFeedPost(): FeedPost? =
    getValue(FeedPost::class.java)?.copy(id = key)

fun DataSnapshot.asComment(): Comment? =
    getValue(Comment::class.java)?.copy(id = key)