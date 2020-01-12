package com.example.btg_challenge.features.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.btg_challenge.features.dashboard.movie.MovieFragment
import com.example.btg_challenge.models.MoviesResponseModel

class DashboardViewPagerAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private lateinit var moviesResponseModel: MoviesResponseModel
    private var listFragment = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        args.putSerializable(DashboardViewModel.MOVIES_KEY, moviesResponseModel)
        listFragment[position].arguments = args
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    fun setMoviesResponse(moviesResponseModel: MoviesResponseModel, listFragment : ArrayList<Fragment>) {
        this.moviesResponseModel = moviesResponseModel
        this.listFragment = listFragment
        notifyDataSetChanged()
    }
}