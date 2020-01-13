package com.example.btg_challenge.features.dashboard.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btg_challenge.R
import com.example.btg_challenge.features.dashboard.DashboardViewModel
import com.example.btg_challenge.features.dashboard.adapter.FavoriteMoviesAdapter
import com.example.btg_challenge.models.MoviesResponseModel

class FavoriteMoviesFragment : Fragment(), FavoriteMoviesViewModelInterface {

    private lateinit var moviesResponseModel: MoviesResponseModel
    private lateinit var adapter: FavoriteMoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_movies, container, false)

        getMoviesResponseModel()
        val recyclerView = view.findViewById<RecyclerView>(R.id.favoriteMoviesFragmentRecyclerView)
        adapter = FavoriteMoviesAdapter()
        adapter.setMovieResponseModel(moviesResponseModel)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        return view
    }

    private fun getMoviesResponseModel() {
        if (arguments != null) {
            if (arguments!!.containsKey(DashboardViewModel.MOVIES_KEY)) {
                moviesResponseModel =
                    arguments!!.getSerializable(DashboardViewModel.MOVIES_KEY) as MoviesResponseModel
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setMovieResponseModel(moviesResponseModel)
    }

//    private fun setupBroadCastReceiver() {
//
//
//    }
//    class MyBroadcastReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val moviesResponseModel = intent.extras?.getSerializable("") as MoviesResponseModel
//        }
//    }

    fun setMovieResponseModel(moviesResponseModel: MoviesResponseModel) {
        this.moviesResponseModel = moviesResponseModel
    }
}