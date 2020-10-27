package java.devcolibri.itvdn.com.day3instagram.screens.home

import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.feed_item.view.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.FeedPostLikes
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.common.ValueEventListenerAdapter
import java.devcolibri.itvdn.com.day3instagram.data.firebase.common.setValueTrueOrRemove
import java.devcolibri.itvdn.com.day3instagram.screens.LoginActivity
import java.devcolibri.itvdn.com.day3instagram.screens.common.*

class HomeActivity : BaseActivity(), FeedAdapter.Listener {


    private lateinit var mAdapter: FeedAdapter
    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation(0)
        Log.d(TAG, "onCreate: Home")

        mAdapter = FeedAdapter(this)
        feed_recycler.adapter = mAdapter
        feed_recycler.layoutManager = LinearLayoutManager(this)


        setupAuthGuard { uid ->
            mViewModel = initViewModel()
            mViewModel.init(uid)
            mViewModel.feedPosts.observe(this, Observer {
                it?.let {
                    mAdapter.updatePosts(it)
                }
            })
        }



    }

    override fun toggleLike(postId: String) {
        Log.d(TAG, "toggleLike: $postId")
        Log.d(TAG, "toggleLike: $postId")
        mViewModel.toggleLike(postId)
    }

    override fun loadLikes(postId: String, position: Int) {
        if (mViewModel.getLikes(postId) == null) {
            mViewModel.loadLikes(postId).observe(this, Observer {
                it?.let { postLikes ->
                    mAdapter.updatePostLikes(position, postLikes)
                }
            })
        }
    }

    companion object {
        val TAG = "HomeActivity"
    }

}


