package com.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController

class MessageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        val messageEditText = view.findViewById<EditText>(R.id.message_fragment_edit)
        val translateButton = view.findViewById<Button>(R.id.message_fragment_translate_button)

        translateButton.setOnClickListener {
            val message = messageEditText.text.toString()
            val action = MessageFragmentDirections.actionMessageFragmentToConverterFragment(message)
            val navController = view.findNavController()
            navController.navigate(action)
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = MessageFragment()
    }
}