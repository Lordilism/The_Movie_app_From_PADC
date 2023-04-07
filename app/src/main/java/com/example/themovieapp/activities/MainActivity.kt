package com.example.themovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapters.BannerAdapter
import com.example.themovieapp.adapters.ShowcaseAdapter
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.delegates.BannerViewHolderDelegate
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.example.themovieapp.mvvm.MainViewModel
import com.example.themovieapp.network.dataagents.MovieDataAgent
import com.example.themovieapp.viewpods.ActorListViewPods
import com.example.themovieapp.viewpods.MovieListViewPods
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate{


    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPods
    lateinit var mMoviesByGenreViewPod: MovieListViewPods
    lateinit var mActorsListViewPods: ActorListViewPods

    //Models
    private lateinit var mViewModel : MainViewModel

    //Genres
//    private var mGenres: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()


        setUpToolbar()
        setUpViewPager()
        //setUpGenreTabLayout()
        setUpListeners()
        setUpShowcaseRecyclerView()
        setUpViewPods()

        //MovieDataAgentImpl.getNowPlayingMovies()
        //OkHttpDataAgentImpl.getNowPlayingMovies()
        //RetrofitDataAgentImpl.getNowPlayingMovies()

       observeLiveData()
    }

    private fun observeLiveData() {
        mViewModel.nowPlayingMovieLiveData?.observe(this,mBannerAdapter::setNewData)
        mViewModel.popularMovieLiveData?.observe(this,mBestPopularMovieListViewPod::setData)
        mViewModel.topRatedMovieLiveData?.observe(this, mShowcaseAdapter::setNewData)
        mViewModel.genreLiveData.observe(this,this::setUpGenreTabLayout)
        mViewModel.movieByGenreLiveData.observe(this,mMoviesByGenreViewPod::setData)
        mViewModel.actorsLiveData.observe(this,mActorsListViewPods::setData)

    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mViewModel.getInitialData()
    }


    private fun showError(failure: String) {
        Toast.makeText(this, failure, Toast.LENGTH_SHORT).show()

    }



    private fun setUpViewPods() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPods
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenreViewPod = vpMovieByGenre as MovieListViewPods
        mMoviesByGenreViewPod.setUpMovieListViewPod(this)

        mActorsListViewPods = vpActorsHomeScreen as ActorListViewPods
    }

    private fun setUpListeners() {
        //Genre TabLayout
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mViewModel.getMovieByGenre(tab?.position?:0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpViewPager() {
        mBannerAdapter = BannerAdapter(this)
        viewPagerBanner.adapter = mBannerAdapter

        dotsindicatorBanner.attachTo(viewPagerBanner)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun setUpGenreTabLayout(genreList: List<GenreVO>) {
        genreList.forEach {
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }

        }
    }

    private fun setUpShowcaseRecyclerView() {
        mShowcaseAdapter = ShowcaseAdapter(this)
        rvShowCases.adapter = mShowcaseAdapter
        rvShowCases.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actionSearch -> {
                startActivity(MovieSearchActivity.newIntent(this))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }


    override fun onTapMovieFromBanner(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))

    }

    override fun onTapMovieFromShowcase(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }



}