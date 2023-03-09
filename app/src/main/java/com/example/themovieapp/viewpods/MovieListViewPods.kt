package com.example.themovieapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.adapters.MovieAdapter
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPods @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    lateinit var mMovieadatper:MovieAdapter
    lateinit var mDelegate: MovieViewHolderDelegate

    override fun onFinishInflate() {
        //setUpMovieRecyclerView()
        super.onFinishInflate()
    }

    fun setUpMovieListViewPod(delegate: MovieViewHolderDelegate){
        setUpDelegate(delegate)
        setUpMovieRecyclerView()
    }
    fun setData(movieList:List<MovieVO>){
        mMovieadatper.setNewData(movieList)
    }
    private fun setUpDelegate(delegate: MovieViewHolderDelegate){
        this.mDelegate = delegate
    }

    private fun setUpMovieRecyclerView(){
        mMovieadatper = MovieAdapter(mDelegate)
        rvMovieList.adapter = mMovieadatper
        rvMovieList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }
}