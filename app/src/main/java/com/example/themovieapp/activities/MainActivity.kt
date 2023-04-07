package com.example.themovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themovieapp.R
import com.example.themovieapp.adapters.BannerAdapter
import com.example.themovieapp.adapters.ShowcaseAdapter
import com.example.themovieapp.data.vos.ActorsVO
import com.example.themovieapp.data.vos.GenreVO
import com.example.themovieapp.data.vos.MovieVO
import com.example.themovieapp.mvp.presenters.MainPresenter
import com.example.themovieapp.mvp.presenters.MainPresenterImpl
import com.example.themovieapp.mvp.views.MainView
import com.example.themovieapp.router.navigateToMovieDetails
import com.example.themovieapp.router.navigateToMovieSearch
import com.example.themovieapp.viewpods.ActorListViewPods
import com.example.themovieapp.viewpods.MovieListViewPods
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MainView, BaseActivity() {

    //ViewPods
    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPods
    lateinit var mMoviesByGenreViewPod: MovieListViewPods
    lateinit var mActorsListViewPods: ActorListViewPods

    //Models
//    private val mMovieModels: MovieModels = MovieModelsImpl

    //Presenter
    private lateinit var mPresenter: MainPresenter

    //Genres
//    private var mGenres: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()

        setUpToolbar()
        setUpViewPager()
        //setUpGenreTabLayout()
        setUpListeners()
        setUpShowcaseRecyclerView()
        setUpViewPods()

        //MovieDataAgentImpl.getNowPlayingMovies()
        //OkHttpDataAgentImpl.getNowPlayingMovies()
        //RetrofitDataAgentImpl.getNowPlayingMovies()

//        requestData()
        mPresenter.onUiReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        (mPresenter as MainPresenterImpl).initView(this)
    }


    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
        mBannerAdapter.setNewData(nowPlayingMovies)
    }

    override fun showPopularMovies(popularMovies: List<MovieVO>) {
        mBestPopularMovieListViewPod.setData(popularMovies)
    }

    override fun showTopRatedMovies(topRatedMovies: List<MovieVO>) {
        mShowcaseAdapter.setNewData(topRatedMovies)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genreList)
    }

    override fun showMoviesByGenre(movieByGenre: List<MovieVO>) {
        mMoviesByGenreViewPod.setData(movieByGenre)
    }

    override fun showActors(actors: List<ActorsVO>) {
        mActorsListViewPods.setData(actors)
    }

    override fun navigateToMovieDetailsScreen(movieId: Int) {
        navigateToMovieDetails(movieId)
    }

    override fun showError(errorString: String) {

    }


    private fun setUpViewPods() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPods
        mBestPopularMovieListViewPod.setUpMovieListViewPod(mPresenter)

        mMoviesByGenreViewPod = vpMovieByGenre as MovieListViewPods
        mMoviesByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mActorsListViewPods = vpActorsHomeScreen as ActorListViewPods
    }

    private fun setUpListeners() {
        //Genre TabLayout
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mPresenter.onTapGenre(tab?.position ?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setUpViewPager() {
        mBannerAdapter = BannerAdapter(mPresenter)
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
        mShowcaseAdapter = ShowcaseAdapter(mPresenter)
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
                navigateToMovieSearch()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }


}