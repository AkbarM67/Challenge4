package com.example.challengetopic4

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import com.example.challengetopic4.databinding.FragmentEditBinding
import com.example.challengetopic4.room.DataNote
import com.example.challengetopic4.room.NoteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class Fragment_edit : Fragment() {

    lateinit var binding: FragmentEditBinding
    var NoteDB: NoteDatabase? = null
    lateinit var notevm : NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_edit, container, false)
        binding = FragmentEditBinding.inflate(layoutInflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        NoteDB = NoteDatabase.getInstance(requireContext())
        notevm = ViewModelProvider(this).get(NoteViewModel::class.java)

        var getData = arguments?.getSerializable("UName")as DataNote
        binding.editTitle.setText(getData.title)
        binding.editNotee.setText(getData.context)

        binding.btnEditNote.setOnClickListener {
            editNote()

            Toast.makeText(context,"edit anda berhasil", Toast.LENGTH_SHORT).show()
        }
    }

    fun editNote(){
        GlobalScope.async {
            val getData = arguments?.getSerializable("UName")as DataNote

            val title = binding.editTitle.text.toString()
            val note = binding.editNotee.text.toString()

            var editNote = DataNote(getData.id, title, note)
            notevm.updateNote(editNote)
        }
    }


}