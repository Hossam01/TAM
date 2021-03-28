package com.example.tam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tam.ViewModelClasses.LoginViewModel
import com.example.tam.ViewModelClasses.MainViewModel
import com.example.tam.adapter.CategoryAdapter
import com.example.tam.adapter.NewAdapter
import com.example.tam.adapter.TrendingAdapter
import com.example.tam.others.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private val mainModel: MainViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter
    private lateinit var newAdapter: NewAdapter
    private lateinit var trendingAdapter: TrendingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = CategoryAdapter()
        trendingAdapter= TrendingAdapter()
        newAdapter= NewAdapter()
        rvcategory.adapter = adapter
        rvnew.adapter=newAdapter
        rvtrending.adapter=trendingAdapter
        lifecycleScope.launchWhenStarted {
            mainModel.getUsers().collect {
                when (it.code) {
                    Status.OK -> {
                        it.item.let { res ->
                            if (res?.code == 200) {
                                res.item?.let { it1 ->

                                 adapter.submitList(it1.category)
                                    val bundle = intent.extras
                                    var s:String? = null
                                    s = bundle!!.getString("key1", "login")
                                    if (s=="login")
                                    {
                                        newAdapter.submitList(it1.whats_new)
                                        trendingAdapter.submitList(it1.trending)}
                                    }

                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Status = false",
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
                            this@MainActivity,
                            it.msg.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }




        }
    }
}