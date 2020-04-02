package com.lysenko.jcom.views

import com.lysenko.jcom.models.FriendModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FriendsView : MvpView {

    fun showError(textResource: Int)
    fun setupEmtyList()
    fun setupFriendsList(friendslist: ArrayList<FriendModel>)
    fun startLoading()
    fun endLoading()

}