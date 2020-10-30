package java.devcolibri.itvdn.com.day3instagram.screens.profile

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.screens.add_friends.AddFriendsActivity
import java.devcolibri.itvdn.com.day3instagram.screens.edit_profile.EditProfileActivity
import java.devcolibri.itvdn.com.day3instagram.models.User
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.common.ValueEventListenerAdapter
import java.devcolibri.itvdn.com.day3instagram.screens.common.BaseActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.loadUserPhoto
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupAuthGuard
import java.devcolibri.itvdn.com.day3instagram.screens.common.setupBottomNavigation
import java.devcolibri.itvdn.com.day3instagram.screens.profilesettings.ProfileSettingsActivity

class ProfileActivity : BaseActivity() {

    private lateinit var mAdapter: ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation(4)
        Log.d(TAG, "onCreate: Profile")


        edit_profile_btn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        settings_image.setOnClickListener {
            val intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }
        add_friends_image.setOnClickListener {
            val intent = Intent(this, AddFriendsActivity::class.java)
            startActivity(intent)
        }
        images_recycler.layoutManager = GridLayoutManager(this, 3)
        mAdapter = ImagesAdapter()
        images_recycler.adapter = mAdapter

        setupAuthGuard { uid ->
            val viewModel = initViewModel<ProfileViewModel>()
            viewModel.init(uid)
            viewModel.user.observe(this, Observer {
                it?.let {
                    profile_image.loadUserPhoto(it.photo)
                    username_text.text = it.username
                }
            })
            viewModel.images.observe(this, Observer {
                it?.let { images ->
                    mAdapter.updateImages(images)
                }
            })
        }

    }

    companion object {
        const val TAG = "ProfileActivity"
    }

}


