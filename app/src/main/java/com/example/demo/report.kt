
package com.example.demo
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.DayAdapter
import com.github.mikephil.charting.animation.Easing
import java.text.SimpleDateFormat
import java.util.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ReportActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private var progressStatus = 0
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val textView3: TextView = findViewById(R.id.textView3)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.circularProgressBar)

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)
        textView3.text = "Today $currentDate"

        val days = generateDaysOfMonth()
        val adapter = DayAdapter(days)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val backImageView: ImageView = findViewById(R.id.back)
        backImageView.setOnClickListener {
            openMainActivity()
        }

        startProgressBarAnimation()

        setupLineChart()
    }

    private fun generateDaysOfMonth(): List<String> {
        val days = mutableListOf<String>()
        val calendar = Calendar.getInstance()

        for (i in 1..31) { // Display 8 days starting from Monday (adjust as needed)
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(calendar.time)
            days.add("$dayOfWeek $i")
        }

        return days
    }
    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startProgressBarAnimation() {
        val progressTextView: TextView = findViewById(R.id.progressTextView) // Change this to the actual ID of your TextView

        Thread(Runnable {
            while (progressStatus < 70) {
                progressStatus += 1

                runOnUiThread {
                    progressBar.progress = progressStatus
                    progressTextView.text = "$progressStatus%" // Update the progress text
                }

                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
    }

    private fun setupLineChart() {
        val lineChart: LineChart = findViewById(R.id.lineChart)

        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 500.2f)) // Example data for Sunday
        entries.add(Entry(2f, 500.3f))
        entries.add(Entry(3f, 500.4f))
        entries.add(Entry(4f, 500.5f))
        entries.add(Entry(5f, 500.4f))
        entries.add(Entry(6f, 500.3f))
        entries.add(Entry(7f, 500.2f))
        // Example data for Monday
        // Add data entries for each day of the week

        val dataSet = LineDataSet(entries, "Calories")
        dataSet.color = resources.getColor(R.color.black)

        val data = LineData(dataSet)
        lineChart.data = data
        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))

        val description = Description()
        description.text = "Weekly Calories"
        lineChart.description = description

        // Set line chart animation
        lineChart.animateX(10000, Easing.EaseInOutQuart) // Animation duration and easing

        lineChart.invalidate()
    }

}
