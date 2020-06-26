package java.devcolibri.itvdn.com.day3instagram.activities

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.feed_item.view.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.FeedPostLikes
import java.devcolibri.itvdn.com.day3instagram.utils.FirebaseHelper
import java.devcolibri.itvdn.com.day3instagram.utils.ValueEventListenerAdapter

class HomeActivity : BaseActivity(0), FeedAdapter.Listener {

    private val TAG = "HomeActivity"
    private lateinit var mFirebase : FirebaseHelper
    private lateinit var mAdapter: FeedAdapter
    private var mLikesListeners: Map<String, ValueEventListener> = emptyMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation()
        Log.d(TAG, "onCreate: Home")

        mFirebase = FirebaseHelper(this)


//        sign_out_text.setOnClickListener{
//            mFirebase.auth.signOut()
//        }
        mFirebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
//        auth.signInWithEmailAndPassword("andrew@mail.ru", "password")
//            .addOnCompleteListener{
//                if (it.isSuccessful) {
//                    Log.d(TAG, "signIn: success")
//                } else {
//                    Log.e(TAG, "signIn: failure", it.exception)
//                }
//            }



    }

    override fun onStart() {
        super.onStart()
        if (mFirebase.auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            mFirebase.database.child("feed-posts").child(mFirebase.auth.currentUser!!.uid)
                .addValueEventListener(ValueEventListenerAdapter{
                    val posts = it.children.map { it.getValue(FeedPost::class.java)!!.copy(id = it.key) }
                    mAdapter = FeedAdapter(this, posts)
                    feed_recycler.adapter = mAdapter
                    feed_recycler.layoutManager = LinearLayoutManager(this)
                })
        }
    }

    override fun toggleLike(postId: String) {
        Log.d(TAG, "toggleLike: $postId")
        val reference = mFirebase.database.child("likes").child(postId).child(mFirebase.auth.currentUser!!.uid)
        reference
            .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                reference.setValueTrueOrRemove(!it.exists())
            })
    }

    override fun loadLikes(postId: String, position: Int) {
        fun createListener() =
            mFirebase.database.child("likes").child(postId).addValueEventListener(
                ValueEventListenerAdapter {
                    val userLikes = it.children.map { it.key }.toSet()
                    val postLikes = FeedPostLikes(
                        userLikes.size,
                        userLikes.contains(mFirebase.auth.currentUser!!.uid))
                    mAdapter.updatePostLikes(position, postLikes)
                })

        val createNewListener = mLikesListeners[postId] == null
        Log.d(TAG, "loadLikes: $position $createNewListener")
        if (createNewListener) {
            mLikesListeners += (postId to createListener())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLikesListeners.values.forEach { mFirebase.database.removeEventListener(it) }
    }

}


class FeedAdapter(private val listener: Listener, private val posts: List<FeedPost>)
    : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var postLikes: Map<Int, FeedPostLikes> = emptyMap()
    private val defaultPostLikes = FeedPostLikes(0, false)

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun toggleLike(postId: String)
        fun loadLikes(postId: String, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feed_item, parent, false)
        return ViewHolder(view)
    }

    fun updatePostLikes(position: Int, likes: FeedPostLikes) {
        postLikes += (position to likes)
        notifyItemChanged(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        val likes = postLikes[position] ?: defaultPostLikes
        with(holder) {
            view.user_photo_image.loadImage(post.photo)
            view.username_text.text = post.username
            view.post_image.loadImage(post.image)
//            if (post.likesCount == 0) {
//                view.likes_text.visibility = View.GONE
//            } else {
//                view.likes_text.visibility = View.VISIBLE
//                view.likes_text.text = "${post.likesCount} likes"
//            }

            if (likes.likesCount == 0) {
                view.likes_text.visibility = View.GONE
            } else {
                view.likes_text.visibility = View.VISIBLE
                view.likes_text.text = "${likes.likesCount} likes"
            }
            view.caption_text.setCaptionText(post.username, post.caption)
            view.like_image.setOnClickListener { listener.toggleLike(post.id) }
            view.like_image.setImageResource(
                if (likes.likedByUser) R.drawable.ic_likes_active
                else R.drawable.ic_likes_border)
            listener.loadLikes(post.id, position)
        }

//        val post = posts[position]
//        //val likes = postLikes[position] ?: defaultPostLikes
//        with(holder) {
//            view.user_photo_image.loadUserPhoto(post.photo)
//            view.username_text.text = post.username
//            view.post_image.loadImage(post.image)
////            if (likes.likesCount == 0) {
////                view.likes_text.visibility = View.GONE
////            } else {
////                view.likes_text.visibility = View.VISIBLE
////                view.likes_text.text = "${likes.likesCount} likes"
////            }
//            view.caption_text.setCaptionText(post.username, post.caption)
////            view.like_image.setOnClickListener { listener.toggleLike(post.id) }
////            view.like_image.setImageResource(
////                if (likes.likedByUser) R.drawable.ic_likes_active
////                else R.drawable.ic_likes_border)
//          //  listener.loadLikes(post.id, position)
//        }
    }

    private fun TextView.setCaptionText(username: String, caption: String) {
        val usernameSpannable = SpannableString(username)
        usernameSpannable.setSpan(
            StyleSpan(Typeface.BOLD), 0, usernameSpannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        usernameSpannable.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                widget.context.showToast("Username is clicked")
            }
            override fun updateDrawState(ds: TextPaint?) { }
        }, 0, usernameSpannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        text = SpannableStringBuilder().append(usernameSpannable).append(" ")
            .append(caption)
        movementMethod = LinkMovementMethod.getInstance()
    }

    override fun getItemCount() = posts.size

    private fun ImageView.loadImage(image: String?) {
        Glide.with(this).load(image).into(this)
    }
}
