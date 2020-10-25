package java.devcolibri.itvdn.com.day3instagram.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.database.ServerValue
import kotlinx.android.synthetic.main.activity_share.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.utils.CameraTaker
import java.devcolibri.itvdn.com.day3instagram.utils.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.utils.ValueEventListenerAdapter
import java.util.*

class ShareActivity : BaseActivity(2) {

    private val TAG = "ShareActivity"
    private lateinit var mCameraTaker: CameraTaker
    private lateinit var mFirebase: FirebaseHelper
    private lateinit var mImageUri: Uri
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

//        setupBottomNavigation()
        Log.d(TAG, "onCreate: Share")

        mFirebase = FirebaseHelper(this)

        mCameraTaker = CameraTaker(this)
        mCameraTaker.takeCameraPicture()

        back_image.setOnClickListener { finish() }
        share_text.setOnClickListener{ share() }

        mFirebase.currentUserReference().addValueEventListener(ValueEventListenerAdapter {
            mUser = it.getValue(User::class.java)!!
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == mCameraTaker.TAKE_PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mImageUri = data!!.data
            Glide.with(this).load(mImageUri).into(post_image)
        } else {
            showToast("No result")
            finish()
        }
    }

    private fun share() {
        val imageUri = mImageUri
        if (imageUri != null) {
            val uid = mFirebase.auth.currentUser!!.uid
            mFirebase.storage.child("Users").child(uid).child("images")
                .child(imageUri.lastPathSegment).putFile(imageUri).addOnCompleteListener{
                    if (it.isSuccessful) {
                        val imageDownloadUrl = it.result.downloadUrl!!.toString()
                        mFirebase.database.child("images").child(uid).push()
                            .setValue(imageDownloadUrl)
                            .addOnCompleteListener{
                                if (it.isSuccessful) {
                                    mFirebase.database.child("feed-posts").child(uid)
                                        .push()
                                        .setValue(mkFeedPost(uid, imageDownloadUrl))
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                startActivity(Intent(this,
                                                    ProfileActivity::class.java))
                                                finish()
                                            }
                                        }
                                } else {
                                    showToast(it.exception!!.message!!)
                                }
                            }
                    } else {
                        showToast(it.exception!!.message!!)
                    }
                }
        }
    }

    private fun mkFeedPost(uid: String, imageDownloadUrl: String): FeedPost {
        return FeedPost(
            uid = uid,
            username = mUser.username,
            image = imageDownloadUrl,
            caption = caption_input.text.toString(),
            photo = mUser.photo
        )
    }
}



data class Comment(val uid: String, val username: String, val text: String)