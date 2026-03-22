package com.example.call

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.call.viewmodel.CallViewModel
import com.example.call.utils.CallState
import com.example.call.ui.DialPadFragment
import com.example.call.ui.OutgoingCallFragment
import com.example.call.ui.IncomingCallFragment
import com.example.call.ui.ActiveCallFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {   // ✅ override added
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[CallViewModel::class.java]

        // Observe state changes
        viewModel.callState.observe(this) { state ->
            when (state) {
                CallState.IDLE -> loadFragment(DialPadFragment())
                CallState.CALLING -> loadFragment(OutgoingCallFragment())
                CallState.RINGING -> loadFragment(IncomingCallFragment())
                CallState.ACTIVE -> loadFragment(ActiveCallFragment())
                CallState.ENDED -> loadFragment(DialPadFragment())
            }
        }
    }

    // Function to switch fragments
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}