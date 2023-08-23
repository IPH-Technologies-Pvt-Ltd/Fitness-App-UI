package com.example.demo

import ImageSliderAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.CalendarContract
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager


import com.example.demo.R
import com.example.demo.ReportActivity
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val imageList = arrayListOf(
        R.drawable.botl,
        R.drawable.dumble,
        R.drawable.running
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        imageSliderAdapter = ImageSliderAdapter(this, imageList)
        viewPager.adapter = imageSliderAdapter

        val handler = Handler(Looper.getMainLooper())
        val update = Runnable {
            if (viewPager.currentItem < imageList.size - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                viewPager.currentItem = 0
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2000, 2000) // Change the first 2000 to the desired delay in milliseconds for auto slide

        val dailyReportCardView: CardView = findViewById(R.id.dailyReportCardView)
        dailyReportCardView.setOnClickListener {
            openReportActivity()
        }
    }

    private fun openReportActivity() {
        val intent = Intent(this, ReportActivity::class.java)
        startActivity(intent)
    }

    private fun openCalendar() {
        val calendarIntent = Intent(Intent.ACTION_VIEW, CalendarContract.CONTENT_URI)
        startActivity(calendarIntent)
    }
}
