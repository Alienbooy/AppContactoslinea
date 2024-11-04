package com.example.practicomovil4


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contacts = listOf<Contact>()

    fun setContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.textViewName.text = "${contact.name} ${contact.last_name}"
        holder.textViewPhoneNumber.text = contact.phones.firstOrNull()?.number ?: "Sin teléfono"

        Glide.with(holder.itemView.context)
            .load(contact.profile_picture)
            .into(holder.imageViewProfile)
    }

    override fun getItemCount() = contacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.fullNameTextView)
        val textViewPhoneNumber: TextView = itemView.findViewById(R.id.phoneTextView)
        val imageViewProfile: ImageView = itemView.findViewById(R.id.profileImageView)
    }
}

