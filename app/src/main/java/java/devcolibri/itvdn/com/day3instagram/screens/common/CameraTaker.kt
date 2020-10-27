package java.devcolibri.itvdn.com.day3instagram.screens.common

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

class CameraTaker(val activity : Activity) {

    val TAKE_PICTURE_REQUEST_CODE = 1

    fun takeCameraPicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE)
        }
    }

}