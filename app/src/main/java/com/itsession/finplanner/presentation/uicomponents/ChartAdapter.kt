package com.itsession.finplanner.presentation.uicomponents

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itsession.finplanner.databinding.GraphCellBinding
import com.itsession.finplanner.presentation.domain.ChartData

class ChartAdapter(val chartData : List<ChartData>) : RecyclerView.Adapter<ChartAdapter.ChartViewHolder>() {
    companion object{
        const val EACH_BAR_DELAY = 0L
    }

    var currentlySelected = 0

    inner class ChartViewHolder(
        private val binding: GraphCellBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindView(data : ChartData, maxValue : Double, position: Int){
            binding.graphBarCell.setGraphBarValue(data.value, maxValue, data.label, EACH_BAR_DELAY * position)
            binding.graphBarCell.alpha = if(data.selected) 1.0f else 0.5f
            binding.graphBarCell.setOnClickListener {
                chartData[currentlySelected].selected = false
                notifyItemChanged(currentlySelected)
                chartData[position].selected = true
                currentlySelected = position
                notifyItemChanged(currentlySelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view = GraphCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ChartViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {
        chartData.getOrNull(position)?.let {
            holder.bindView(it, chartData.maxOf { it.value }, position)
        }
    }

    override fun getItemCount(): Int {
        return chartData.size
    }
}