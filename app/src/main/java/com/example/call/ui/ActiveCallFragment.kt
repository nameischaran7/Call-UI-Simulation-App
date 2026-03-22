package com.example.call.ui

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.call.R
import com.example.call.viewmodel.CallViewModel

class ActiveCallFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_active_call, container, false)

        val vm = ViewModelProvider(requireActivity())[CallViewModel::class.java]
        val timer = view.findViewById<TextView>(R.id.timerText)

        vm.callTime.observe(viewLifecycleOwner) {
            val min = it / 60
            val sec = it % 60
            timer.text = String.format("%02d:%02d", min, sec)
        }

        view.findViewById<ImageButton>(R.id.endCallBtn).setOnClickListener {
            vm.endCall()
        }

        return view
    }
}