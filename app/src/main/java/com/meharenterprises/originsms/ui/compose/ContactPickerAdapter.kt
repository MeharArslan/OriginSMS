package com.meharenterprises.originsms.ui.compose

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meharenterprises.originsms.R
import com.meharenterprises.originsms.core.ContactsHelper

class ContactPickerAdapter(
    private val onContactSelected: (ContactsHelper.PickableContact) -> Unit
) : RecyclerView.Adapter<ContactPickerAdapter.ViewHolder>() {

    private var fullList: List<ContactsHelper.PickableContact> = emptyList()
    private var filteredList: List<ContactsHelper.PickableContact> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAvatar: ImageView = itemView.findViewById(R.id.imgAvatar)
        val txtName: TextView = itemView.findViewById(R.id.txtContactName)
        val txtNumber: TextView = itemView.findViewById(R.id.txtContactNumber)
    }

    fun submitContacts(contacts: List<ContactsHelper.PickableContact>) {
        fullList = contacts
        filteredList = contacts
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredList = if (query.isBlank()) {
            fullList
        } else {
            val normalizedQuery = query.trim().lowercase()
            fullList.filter {
                it.displayName.lowercase().contains(normalizedQuery) ||
                    it.phoneNumber.contains(normalizedQuery)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_picker, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = filteredList[position]
        holder.txtName.text = contact.displayName
        holder.txtNumber.text = contact.phoneNumber

        if (contact.photoUri != null) {
            try {
                val uri = Uri.parse(contact.photoUri)
                holder.itemView.context.contentResolver.openInputStream(uri)?.use { stream ->
                    val bitmap = android.graphics.BitmapFactory.decodeStream(stream)
                    if (bitmap != null) {
                        holder.imgAvatar.setImageBitmap(bitmap)
                        holder.imgAvatar.scaleType = ImageView.ScaleType.CENTER_CROP
                        holder.imgAvatar.setPadding(0, 0, 0, 0)
                    }
                }
            } catch (_: Exception) {
                holder.imgAvatar.setImageResource(R.drawable.ic_person)
            }
        } else {
            holder.imgAvatar.setImageResource(R.drawable.ic_person)
            holder.imgAvatar.scaleType = ImageView.ScaleType.CENTER_INSIDE
            val pad = (6 * holder.itemView.context.resources.displayMetrics.density).toInt()
            holder.imgAvatar.setPadding(pad, pad, pad, pad)
        }

        holder.itemView.setOnClickListener { onContactSelected(contact) }
    }

    override fun getItemCount(): Int = filteredList.size
}
