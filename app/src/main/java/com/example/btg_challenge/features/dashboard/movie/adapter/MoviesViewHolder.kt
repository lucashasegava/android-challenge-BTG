package com.example.btg_challenge.features.dashboard.movie.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.R
import kotlinx.android.synthetic.main.rv_movie_details.view.*

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
     val viewDataBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
     val imageView = itemView.findViewById<ImageView>(R.id.rvMovieDetailsPosterImageView)
}