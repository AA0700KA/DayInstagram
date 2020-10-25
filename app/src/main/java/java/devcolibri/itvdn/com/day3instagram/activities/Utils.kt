package java.devcolibri.itvdn.com.day3instagram.activities

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.User


fun ImageView.loadUserPhoto(photoUrl: String?) {
    if (!(context as Activity).isDestroyed) {
        Glide.with(this).load(photoUrl).into(this)
    }
}

fun Editable.toStringOrNull(): String? {
    val str = toString()
    return if (str.isEmpty()) null else str
}

fun ImageView.loadImage(image: String?) {
    Glide.with(this).load(image).into(this)
}

fun <T> task(block: (TaskCompletionSource<T>) -> Unit): Task<T> {
    val taskSource = TaskCompletionSource<T>()
    block(taskSource)
    return taskSource.task
}

fun DatabaseReference.setValueTrueOrRemove(value: Boolean) =
    if (value) setValue(true) else removeValue()


fun DataSnapshot.asUser(): User? =
    getValue(User::class.java)?.copy(uid = key)

fun DataSnapshot.asFeedPost(): FeedPost? =
    getValue(FeedPost::class.java)?.copy(id = key)

fun <A, B> LiveData<A>.map(f: (A) -> B): LiveData<B> =
    Transformations.map(this, f)

private fun View.ifNotDestroyed(block: () -> Unit) {
    if (!(context as Activity).isDestroyed) {
        block()
    }
}

fun Context.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    text?.let { Toast.makeText(this, it, duration).show() }
}