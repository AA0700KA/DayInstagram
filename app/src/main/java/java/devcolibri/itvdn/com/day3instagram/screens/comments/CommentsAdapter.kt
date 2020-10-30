package java.devcolibri.itvdn.com.day3instagram.screens.comments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.comments_item.view.*
import java.devcolibri.itvdn.com.day3instagram.R
import java.devcolibri.itvdn.com.day3instagram.models.Comment
import java.devcolibri.itvdn.com.day3instagram.screens.common.loadUserPhoto
import java.devcolibri.itvdn.com.day3instagram.screens.common.setCaptionText

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private var comments = listOf<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comments_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]
        with(holder) {
            view.photo.loadUserPhoto(comment.photo)
            view.text.setCaptionText(comment.username, comment.text, comment.timestampDate())
        }
    }

    fun updateComments(newComments: List<Comment>) {
        comments = newComments;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = comments.size

}