package com.lysenko.jcom.presenters

import android.content.Intent
import android.os.Handler
import com.lysenko.jcom.R
import com.lysenko.jcom.views.LoginView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    fun login(isSuccess: Boolean) {
        viewState.startLoading()
        Handler().postDelayed({
            if (isSuccess) {
                viewState.endLoading()
                viewState.openFriends()
            } else {
                viewState.showError(R.string.login_error_credentials)
            }
        }, 1000)
    }

    fun loginVK(requestCode: Int, resultCode: Int, data: Intent?):Boolean {
        if (!VKSdk.onActivityResult(
                requestCode,
                resultCode,
                data,
                object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken?) {
                        viewState.openFriends()
                    }

                    override fun onError(error: VKError?) {
                        viewState.showError(textResource = R.string.login_error_credentials)
                }

                })
        ){
            return false
        }
        return true
    }
}