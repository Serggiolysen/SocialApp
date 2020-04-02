package com.lysenko.jcom.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lysenko.jcom.R
import com.lysenko.jcom.models.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val friendsList = arrayListOf<FriendModel>()
    private val sourceList = arrayListOf<FriendModel>()

    fun setupFriends(list: ArrayList<FriendModel>) {
        sourceList.clear()
        sourceList.addAll(list)
        filter("")
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        friendsList.clear()
        sourceList.forEach {
            if (it.name.contains(query, ignoreCase = true) ||
                it.surname.contains(query, ignoreCase = true) ||
                it.city.contains(query, ignoreCase = true)
            ) {
                friendsList.add(it)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendsViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.cell_friend,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return friendsList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(friendsList[position])
        }
    }

    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar = itemView.findViewById<CircleImageView>(R.id.friend_civ_avatar)
        private val userName = itemView.findViewById<TextView>(R.id.friend_txt_username)
        private val city = itemView.findViewById<TextView>(R.id.friend_txt_city)
        private val isonline = itemView.findViewById<View>(R.id.friend_img_online)

        fun bind(friendModel: FriendModel) {

            Picasso.get()
                .load(friendModel.avatar)
                .into(avatar)

//            friendModel.avatar.let {
//                Picasso.get()
//                    .load(it)
//                    .into(avatar)
//            }

            userName.text = "${friendModel.name}  ${friendModel.surname}"

            city.text = itemView.context.getString(R.string.no_city)
            friendModel.city.let { city.text = it }

            if (friendModel.isOnline) {
                isonline.visibility = View.VISIBLE
            } else {
                isonline.visibility = View.GONE
            }
        }
    }
}