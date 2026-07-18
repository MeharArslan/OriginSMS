package com.meharenterprises.originsms.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.data.db.BlockedNumberEntity

class BlockedNumberAdapter(
    private val onUnblock: (BlockedNumberEntity) -> Unit
) : ListAdapter<BlockedNumberEntity, BlockedNumberAdapter.ViewHolder>(DIFF) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNumber: TextView = itemView.findViewById(R.id.txtNumber)
        val btnUnblock: ImageButton = itemView.findViewById(R.id.btnUnblock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blocked_number, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.txtNumber.text = item.displayNumber
        holder.btnUnblock.setOnClickListener { onUnblock(item) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<BlockedNumberEntity>() {
            override fun areItemsTheSame(oldItem: BlockedNumberEntity, newItem: BlockedNumberEntity) =
                oldItem.normalizedNumber == newItem.normalizedNumber

            override fun areContentsTheSame(oldItem: BlockedNumberEntity, newItem: BlockedNumberEntity) =
                oldItem == newItem
        }
    }
}
