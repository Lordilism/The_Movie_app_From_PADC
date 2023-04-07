package com.example.themovieapp.mvp.presenters

import com.example.themovieapp.delegates.BannerViewHolderDelegate
import com.example.themovieapp.delegates.MovieViewHolderDelegate
import com.example.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.example.themovieapp.mvp.views.MainView

interface MainPresenter: BannerViewHolderDelegate,ShowCaseViewHolderDelegate,MovieViewHolderDelegate,IBasePresenter {
    fun initView(view: MainView)
    fun onTapGenre(genrePosition: Int)
}