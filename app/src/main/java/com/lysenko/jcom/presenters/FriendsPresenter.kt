package com.lysenko.jcom.presenters

import com.lysenko.jcom.R
import com.lysenko.jcom.models.FriendModel
import com.lysenko.jcom.providers.FriendsProvider
import com.lysenko.jcom.views.FriendsView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class FriendsPresenter : MvpPresenter<FriendsView>() {

    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(this).loadFriends()
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmtyList()
            viewState.showError(R.string.txt_friends_no_items)
        }else{
            viewState.setupFriendsList(friendslist = friendsList)
        }
    }

    fun showError(textResource:Int){
        viewState.showError(textResource = textResource)
    }

}