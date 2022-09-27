package com.example.challengetopic4

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.example.challengetopic4.databinding.ItemNoteBinding
import com.example.challengetopic4.room.DataNote
import com.example.challengetopic4.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class NoteAdapter(var listNote : List<DataNote>): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(var binding : ItemNoteBinding):RecyclerView.ViewHolder(binding.root) {
        fun databinding(itemDataNote: DataNote){
            binding.data = itemDataNote
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NoteAdapter.ViewHolder {
        var view = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    var NoteDB : NoteDatabase? = null
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databinding(listNote[position])

        holder.binding.btnDeleteNote.setOnClickListener{
            NoteDB = NoteDatabase.getInstance(it.context)

            GlobalScope.async {
                NoteViewModel(Application()).deleteNote(listNote[position])
                NoteDB?.noteDao()?.deleteNote(listNote[position])
                val nav = Navigation.findNavController(it)
                nav.run { 
                    navigate(R.id.fragment_Home)
                }

            }
        }

        holder.binding.btnEditNote.setOnClickListener {
            var bund = Bundle()
            bund.putSerializable("note",listNote[position])
            Navigation.findNavController(it).navigate(R.id.action_fragment_Home_to_fragment_add,bund)
        }
    }

    override fun getItemCount(): Int {
        return  listNote.size
    }

    fun setNoteData(listNote: ArrayList<DataNote>)
    {
        this.listNote=listNote
    }
}