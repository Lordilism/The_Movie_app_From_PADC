package com.example.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_actor.view.*

class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(actors: ActorsVO){
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${actors.profilePath}")
            .into(itemView.ivBestActor)

        itemView.tvActorName.text = actors.name
    }
}