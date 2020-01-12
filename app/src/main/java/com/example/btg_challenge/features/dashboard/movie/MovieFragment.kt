package com.example.btg_challenge.features.dashboard.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.R
import com.example.btg_challenge.features.dashboard.DashboardViewModel
import com.example.btg_challenge.features.dashboard.movie.adapter.MoviesAdapter
import com.example.btg_challenge.models.MoviesResponseModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    //    companion object{
//        fun newInstance(moviesResponseModel: MoviesResponseModel) : MovieFragment{
//            return MovieFragment()
//        }
//    }
    private lateinit var moviesResponseModel: MoviesResponseModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = DataBindingUtil.inflate<ViewDataBinding>(
//            inflater,
//            R.layout.fragment_movie,
//            container,
//            false
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
//        )
        if (arguments != null) {
            if (arguments!!.containsKey(DashboardViewModel.MOVIES_KEY)) {
                moviesResponseModel =
                    arguments!!.getSerializable(DashboardViewModel.MOVIES_KEY) as MoviesResponseModel
            }
        }
//        setupRecyclerView()
//        return view.root
        val recyclerView = view.findViewById(R.id.fragmentMovieListRecyclerView) as RecyclerView
        val adapter = MoviesAdapter()
        recyclerView.adapter = adapter
        adapter.setMoviesResponseModel(moviesResponseModel)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        return view
    }

    private fun setupRecyclerView() {
        val recyclerView = fragmentMovieListRecyclerView
        val adapter = MoviesAdapter()
        recyclerView.adapter = adapter
        adapter.setMoviesResponseModel(moviesResponseModel)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
    }
}