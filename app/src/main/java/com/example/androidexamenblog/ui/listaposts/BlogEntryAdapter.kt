package com.example.androidexamenblog.ui.listaposts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.databinding.ItemBlogEntryBinding
import com.example.androidexamenblog.utils.Utils

class BlogEntryAdapter(): RecyclerView.Adapter<BlogEntryAdapter.BlogEntryViewHolder>() {

    var postClickListener: ((view: View, item: BlogEntry) -> Unit)? = null
    private val entries = ArrayList<BlogEntry>()

    class BlogEntryViewHolder(val binding: ItemBlogEntryBinding): RecyclerView.ViewHolder(binding.root){

        var postClickListener: ((view: View, item: BlogEntry) -> Unit)? = null

        fun bind(entry: BlogEntry){

            binding.apply {

                tvAutor.text = entry.author
                tvTitulo.text = entry.title
                tvContenido.text = entry.content
                tvFecha.text = Utils.convertDate(entry.date,"dd/MM/yyyy HH:mm")

                root.setOnClickListener {

                    postClickListener?.invoke(it,entry)

                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogEntryViewHolder {
        val binding = ItemBlogEntryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BlogEntryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: BlogEntryViewHolder, position: Int) {
        holder.postClickListener = postClickListener
        holder.bind(entries[position])
    }

    fun setItems(items: ArrayList<BlogEntry>){

        this.entries.clear()
        this.entries.addAll(items)
        notifyDataSetChanged()

    }
}