package java.devcolibri.itvdn.com.day3instagram.screens.common

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

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

private fun View.ifNotDestroyed(block: () -> Unit) {
    if (!(context as Activity).isDestroyed) {
        block()
    }
}

fun Context.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    text?.let { Toast.makeText(this, it, duration).show() }
}