package com.example.tam

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tam.others.Status
import com.example.tam.ViewModelClasses.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint

class LoginActivity : AppCompatActivity() {
    private val mainModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign.setOnClickListener {
            mainModel.getData(phone_edit.text.toString(),pass_edit.text.toString())
            load.visibility = View.VISIBLE
        }

        skip.setOnClickListener {
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("key1","skip")
            intent.putExtras(bundle)
            startActivity(intent)
        }

        if (checkOnlineState()==true) {
            lifecycleScope.launchWhenStarted {
                mainModel.getUsers().collect {
                    when (it.code) {
                        Status.OK -> {
                            load.visibility=View.GONE
                            Log.e("status",it.code.toString())

                            it.item.let {
                                if (it?.code==200) {
                                    intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    val bundle = Bundle()
                                    bundle.putString("key1","login")
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                }
                                else if (it?.code==401)
                                {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        it.msg.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        Status.LOAD -> {
                            Log.e("status",it.code.toString())
                        }
                        Status.ERROR -> {
                            load.visibility = View.GONE
                            Log.e("status",it.code.toString())
                            Toast.makeText(
                                this@LoginActivity,
                                it.msg.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }


    }

    fun checkOnlineState(): Boolean {
        val CManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val NInfo = CManager!!.activeNetworkInfo
        return if (NInfo != null && NInfo.isConnectedOrConnecting) {
            true
        } else false
    }
}