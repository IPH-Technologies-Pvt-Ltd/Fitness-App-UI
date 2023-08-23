package com.example.fitnessapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R

class DayAdapter(private val days: List<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(days[position], position)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(day: String, position: Int) {
            val dayText: TextView = itemView.findViewById(R.id.dayText)

            // Split the day string to separate day and date
            val parts = day.split(" ")
            val dayName = parts[0] // "Mon", "Tue", etc.
            val date = parts[1] // "1", "2", etc.

            // Set combined day and date text
            dayText.text = "$dayName\n$date"

            if (position == selectedPosition) {
                // Change the background to the selected state
                dayText.setBackgroundResource(R.drawable.reactangle_shap_black) // Use the correct resource name
                dayText.setTextColor(Color.WHITE) // Set text color to white
            } else {
                // Change the background to the default state
                dayText.setBackgroundResource(R.drawable.reactangle_shap) // Use the correct resource name
                dayText.setTextColor(Color.BLACK) // Set text color to black
            }

            dayText.setOnClickListener {
                // Update selected position and refresh the adapter
                if (selectedPosition != adapterPosition) {
                    val previousSelected = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousSelected)
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

}

