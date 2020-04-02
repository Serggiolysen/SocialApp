package com.lysenko.jcom.activities

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.jcom.R
import com.lysenko.jcom.adapters.FriendAdapter
import com.lysenko.jcom.models.FriendModel
import com.lysenko.jcom.presenters.FriendsPresenter
import com.lysenko.jcom.views.FriendsView
import kotlinx.android.synthetic.main.activity_friends.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class FriendsActivity : MvpAppCompatActivity(), FriendsView {

    private lateinit var adapter: FriendAdapter

    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        friendsPresenter.loadFriends()

        adapter = FriendAdapter()

        recycler_friends.adapter = adapter
        recycler_friends.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recycler_friends.setHasFixedSize(true)

        txt_friends_search.addTextChangedListener (object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

        })
    }

    override fun showError(textResource: Int) {
        txt_friends_no_items.text = getString(textResource)

    }

    override fun setupEmtyList() {
        recycler_friends.visibility = View.GONE
        txt_friends_no_items.visibility = View.VISIBLE
    }

    override fun setupFriendsList(friendslist: ArrayList<FriendModel>) {
        recycler_friends.visibility = View.VISIBLE
        txt_friends_no_items.visibility = View.GONE

        adapter.setupFriends(list = friendslist)
    }

    override fun startLoading() {
        recycler_friends.visibility = View.GONE
        txt_friends_no_items.visibility = View.GONE
        progress_view_friends.visibility = View.VISIBLE

    }

    override fun endLoading() {
        progress_view_friends.visibility = View.GONE
    }
}
