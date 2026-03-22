package com.example.call.ui

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.call.R
import com.example.call.viewmodel.CallViewModel

class IncomingCallFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_incoming_call, container, false)

        val vm = ViewModelProvider(requireActivity())[CallViewModel::class.java]

        view.findViewById<ImageButton>(R.id.acceptBtn).setOnClickListener {
            vm.acceptCall()
        }

        view.findViewById<ImageButton>(R.id.rejectBtn).setOnClickListener {
            vm.endCall()
        }

        return view
    }
}