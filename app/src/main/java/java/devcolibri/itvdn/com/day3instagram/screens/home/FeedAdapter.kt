package java.devcolibri.itvdn.com.day3instagram.screens.home

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.feed_item.view.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.FeedPost
import java.devcolibri.itvdn.com.day3instagram.models.FeedPostLikes
import java.devcolibri.itvdn.com.day3instagram.screens.common.loadImage
import java.devcolibri.itvdn.com.day3instagram.screens.common.setCaptionText
import java.devcolibri.itvdn.com.day3instagram.screens.common.showToast

class FeedAdapter(private val listener: Listener)
: RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val posts: MutableList<FeedPost> = mutableListOf()
    private var postLikes: Map<Int, FeedPostLikes> = emptyMap()
    private val defaultPostLikes = FeedPostLikes(0, false)

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun toggleLike(postId: String)
        fun loadLikes(postId: String, position: Int)
        fun openComments(postId: String)
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
            view.comment_image.setOnClickListener { listener.openComments(post.id) }
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

    fun updatePosts(feedPosts : List<FeedPost>) {
        posts.addAll(feedPosts)
        notifyDataSetChanged()
    }


    override fun getItemCount() = posts.size

}
