package java.devcolibri.itvdn.com.day3instagram.screens.share

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_share.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.screens.common.CameraTaker
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupAuthGuard
import java.devcolibri.itvdn.com.day3instagram.screens.common.showToast

class ShareActivity : BaseActivity() {

    private lateinit var mCameraTaker: CameraTaker
    private lateinit var mViewModel: ShareViewModel
    private lateinit var mUser: User
    private lateinit var mImageUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        Log.d(TAG, "onCreate: Share")

        setupAuthGuard {
            mViewModel = initViewModel()

            mCameraTaker = CameraTaker(this)
            mCameraTaker.takeCameraPicture()

            back_image.setOnClickListener { finish() }
            share_text.setOnClickListener { share() }

            mViewModel.user.observe(this, Observer {
                it?.let {
                    mUser = it
                }
            })
        }

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
        mViewModel.share(mUser, mImageUri, caption_input.text.toString())
        finish()
    }


    companion object {
        const val TAG = "ShareActivity"
    }

}
