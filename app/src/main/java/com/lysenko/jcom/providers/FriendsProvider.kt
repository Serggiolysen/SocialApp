package com.lysenko.jcom.providers

import android.os.Handler
import android.util.JsonReader
import android.util.Log
import com.google.gson.JsonParser
import com.lysenko.jcom.R
import com.lysenko.jcom.models.FriendModel
import com.lysenko.jcom.presenters.FriendsPresenter
import com.vk.sdk.api.*

class FriendsProvider(var presenter: FriendsPresenter) {

    private val TAG = FriendsProvider::class.java.simpleName

    fun testloadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList = arrayListOf<FriendModel>()
            if (hasFriends) {
                val friend1 = FriendModel(
                    name = "Ivan", surname = "Petrov", city = "", isOnline = true,
                    avatar = "https://upload.wikimedia.org/wikipedia/commons/c/c6/Ants_piip.jpg"
                )

                val friend2 = FriendModel(
                    name = "Pavel", surname = "Danov", city = "Gaga", isOnline = true,
                    avatar = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Konstantin_P%C3%A4ts.jpg/150px-Konstantin_P%C3%A4ts.jpg"
                )

                val friend3 = FriendModel(
                    name = "Nadia", surname = "Johnes", city = "Tomsk", isOnline = false,
                    avatar = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Jaan_Tonisson1928.jpg/150px-Jaan_Tonisson1928.jpg"
                )

                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)
            }

            presenter.friendsLoaded(friendsList = friendsList)
        }, 1000)
    }

    fun loadFriends() {
        val request = VKApi.friends()
            .get(
                VKParameters.from(
//                    VKApiConst.COUNT,
//                    40,
                    VKApiConst.FIELDS,
                    "sex,bdate,city,country, photo_100, online"
                )
            )
        request.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val jsonParser = JsonParser()
                val parsed = jsonParser.parse(response!!.json.toString()).asJsonObject
                val friendsList = arrayListOf<FriendModel>()

                parsed.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    val city = if (it.asJsonObject.get("city") == null) {
                        ""
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }

                    val friendModel =
                        FriendModel(
                            name = it.asJsonObject.get("first_name").asString,
                            avatar = it.asJsonObject.get("photo_100").asString,
                            isOnline = it.asJsonObject.get("online").asInt == 1,
                            city = city,
                            surname = it.asJsonObject.get("last_name").asString
                        )
                    friendsList.add(friendModel)
                }
                presenter.friendsLoaded(friendsList)
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.frends_err_loading)
            }

        })
    }
}