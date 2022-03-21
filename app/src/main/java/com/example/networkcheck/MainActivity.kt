package com.example.networkcheck

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val networkMonitor = NetworkMonitorUtil(this)
    var wifiAnimation: AnimationDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.setBackgroundResource(R.drawable.internet_anim)
        wifiAnimation = imageView.background as AnimationDrawable?
        wifiAnimation?.start()

        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                internet_status.text = getString(R.string.wifi)
                                imageView.visibility = View.GONE
                                internet_status.visibility = View.VISIBLE
                                wifiAnimation?.stop()
                            }
                            ConnectionType.Cellular -> {
                                internet_status.text = getString(R.string.cellular)
                                imageView.visibility = View.GONE
                                internet_status.visibility = View.VISIBLE
                                wifiAnimation?.stop()


                            }
                            else -> { }
                        }
                    }
                    false -> {
                        //internet_status.text = getString(R.string.no_connection)
                        imageView.setBackgroundResource(R.drawable.internet_anim)
                        imageView.visibility = View.VISIBLE
                        internet_status.visibility = View.GONE
                        wifiAnimation?.start()
                    }
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }
}