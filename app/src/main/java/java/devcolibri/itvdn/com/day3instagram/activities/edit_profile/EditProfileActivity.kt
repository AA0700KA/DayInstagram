package java.devcolibri.itvdn.com.day3instagram.activities.edit_profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.activities.ViewModelFactory
import java.devcolibri.itvdn.com.day3instagram.activities.loadUserPhoto
import java.devcolibri.itvdn.com.day3instagram.activities.showToast
import java.devcolibri.itvdn.com.day3instagram.activities.toStringOrNull
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
    private lateinit var mViewModel: EditProfileViewModel


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

        mViewModel = ViewModelProviders.of(this, ViewModelFactory())
            .get(EditProfileViewModel::class.java)

        mViewModel.user.observe(this, Observer {
            it?.let {
                mUser = it
                name_input.setText(mUser.name)
                username_input.setText(mUser.username)
                website_input.setText(mUser.website)
                bio_input.setText(mUser.bio)
                email_input.setText(mUser.email)
                phone_input.setText(mUser.phone?.toString())
                profile_image.loadUserPhoto(mUser.photo)
            }
        })

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == mCameraTaker.TAKE_PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {

            val imageUri = data!!.data
            mViewModel.uploadAndSetUserPhoto(imageUri).addOnFailureListener {
                showToast(it.message)
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
        mViewModel.updateUserProfile(currentUser = mUser, newUser = user)
            .addOnFailureListener { showToast(it.message) }
            .addOnSuccessListener {
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
            mViewModel.updateEmail(
                currentEmail = mUser.email,
                newEmail = mPendingUser.email,
                password = password)
                .addOnFailureListener { showToast(it.message) }
                .addOnSuccessListener { updateUser(mPendingUser) }
        } else {
            showToast("You should be write password")
        }
    }

}