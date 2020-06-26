package java.devcolibri.itvdn.com.day3instagram.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.utils.CameraTaker
import java.devcolibri.itvdn.com.day3instagram.utils.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.utils.ValueEventListenerAdapter
import java.devcolibri.itvdn.com.day3instagram.views.PasswordDialog




class EditProfileActivity : AppCompatActivity(), PasswordDialog.Listener {

    private val TAG = "EditProfileActivity"

    private lateinit var mPendingUser: User
    private lateinit var mUser: User
    private lateinit var mCameraTaker: CameraTaker
    private lateinit var mFirebaseHelper: FirebaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate")

        mCameraTaker = CameraTaker(this)

        close_image.setOnClickListener{
            finish()
        }

        save_image.setOnClickListener {
            updateProfile()
        }

        change_photo_text.setOnClickListener {
            mCameraTaker.takeCameraPicture()
        }

        mFirebaseHelper = FirebaseHelper(this)

        mFirebaseHelper.currentUserReference()
            .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                mUser = it.getValue(User::class.java)!!
                name_input.setText(mUser.name)
                username_input.setText(mUser.username)
                website_input.setText(mUser.website)
                bio_input.setText(mUser.bio)
                email_input.setText(mUser.email)
                phone_input.setText(mUser.phone?.toString())
                profile_image.loadUserPhoto(mUser.photo)
            })

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == mCameraTaker.TAKE_PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {

            val imageUri = data!!.data
            mFirebaseHelper.uploadUserPhoto(imageUri) {
                val photoUrl = it.downloadUrl.toString()
                mFirebaseHelper.updateUserPhoto(photoUrl) {
                    mUser = mUser.copy(photo = photoUrl)
                    profile_image.loadUserPhoto(mUser.photo)
                }
            }
        } else {
           showToast("No result")
        }
    }

    private fun updateProfile() {
        mPendingUser = User(
            name = name_input.text.toString(),
            username = username_input.text.toString(),
            website = website_input.text.toStringOrNull(),
            bio = bio_input.text.toStringOrNull(),
            email = email_input.text.toString(),
            phone = phone_input.text.toStringOrNull()
        )
        val error = validate(mPendingUser)
        if (error == null) {
            if (mPendingUser.email == mUser.email) {
                updateUser(mPendingUser)
            } else {
                PasswordDialog().show(supportFragmentManager, "password_dialog")
            }
        } else {
            showToast(error)
        }
    }

    private fun updateUser(user: User) {
        val updatesMap = mutableMapOf<String, Any?>()
        if (user.name != mUser.name) updatesMap["name"] = user.name
        if (user.username != mUser.username) updatesMap["username"] = user.username
        if (user.website != mUser.website) updatesMap["website"] = user.website
        if (user.bio != mUser.bio) updatesMap["bio"] = user.bio
        if (user.email != mUser.email) updatesMap["email"] = user.email
        if (user.phone != mUser.phone) updatesMap["phone"] = user.phone

        mFirebaseHelper.updateUser(updatesMap) {
            showToast("Profile saved")
            finish()
        }
    }

    private fun validate(user: User): String? =
        when {
            user.name.isEmpty() -> "Please enter name"
            user.username.isEmpty() -> "Please enter username"
            user.email.isEmpty() -> "Please enter email"
            else -> null
        }

    override fun onPasswordConfirm(password: String) {
        if (password.isNotEmpty()) {
            val credential = EmailAuthProvider.getCredential(mUser.email, password)
            mFirebaseHelper.reauthenticate(credential) {
                mFirebaseHelper.updateEmail(mPendingUser.email) {
                    updateUser(mPendingUser)
                }
            }
        } else {
            showToast("You should be write password")
        }
    }

}