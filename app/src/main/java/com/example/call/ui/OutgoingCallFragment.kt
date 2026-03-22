package com.example.call.ui

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.call.R
import com.example.call.viewmodel.CallViewModel

class OutgoingCallFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_outgoing_call, container, false)

        val vm = ViewModelProvider(requireActivity())[CallViewModel::class.java]

        val number = view.findViewById<TextView>(R.id.numberText)
        number.text = vm.phoneNumber.value

        view.findViewById<ImageButton>(R.id.endCallBtn).setOnClickListener {
            vm.endCall()
        }

        return view
    }
}