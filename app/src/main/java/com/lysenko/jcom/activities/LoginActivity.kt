package com.lysenko.jcom.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.lysenko.jcom.R
import com.lysenko.jcom.presenters.LoginPresenter
import com.lysenko.jcom.views.LoginView
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter
    private val TAG = this@LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin.setOnClickListener {
//            loginPresenter.login(true)
            VKSdk.login(this, VKScope.FRIENDS)
        }

//        val fingerprints: Array<String> =
//            VKUtil.getCertificateFingerprint(this, this.packageName)
//        Log.e(TAG, fingerprints[0])
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if( !loginPresenter.loginVK(requestCode = requestCode, resultCode = resultCode, data = data)){
           super.onActivityResult(requestCode, resultCode, data)
       }

    }

    override fun startLoading() {
        buttonLogin.visibility = View.GONE
        progress_view.visibility = View.VISIBLE
    }

    override fun openFriends() {
        startActivity(Intent(this, FriendsActivity::class.java))
    }

    override fun endLoading() {
        buttonLogin.visibility = View.VISIBLE
        progress_view.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }
}
