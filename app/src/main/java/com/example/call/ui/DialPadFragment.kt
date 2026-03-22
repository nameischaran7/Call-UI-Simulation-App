package com.example.call.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.call.R
import com.example.call.viewmodel.CallViewModel

class DialPadFragment : Fragment() {

    private lateinit var viewModel: CallViewModel
    private lateinit var display: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_dial_pad, container, false)

        viewModel = ViewModelProvider(requireActivity())[CallViewModel::class.java]
        display = view.findViewById(R.id.numberDisplay)

        val grid = view.findViewById<GridLayout>(R.id.gridLayout)

        // Numbers list
        val buttons = listOf("1","2","3","4","5","6","7","8","9","*","0","#")

        // Assign click listeners dynamically
        for (i in 0 until grid.childCount) {
            val btn = grid.getChildAt(i) as Button
            btn.setOnClickListener {
                display.append(buttons[i])
            }
        }

        // Backspace
        val backBtn = view.findViewById<ImageButton>(R.id.backspaceBtn)
        backBtn.setOnClickListener {
            val text = display.text.toString()
            if (text.isNotEmpty()) {
                display.text = text.dropLast(1)
            }
        }

        // Call Button (REAL + UI FLOW)
        val callBtn = view.findViewById<ImageButton>(R.id.callBtn)

        callBtn.setOnClickListener {
            val number = display.text.toString()

            if (number.isEmpty()) {
                Toast.makeText(context, "Enter number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Start your UI flow
            viewModel.startCall(number)

            // After 2 seconds → trigger real call
            Handler(Looper.getMainLooper()).postDelayed({

                if (isAdded) {   // 🔥 VERY IMPORTANT
                    checkPermissionAndCall(number)
                }

            }, 2000)
        }

        // Fake Incoming Call Button (bonus)
        val fakeIncoming = view.findViewById<Button>(R.id.fakeIncomingBtn)
        fakeIncoming?.setOnClickListener {
            viewModel.incomingCall("9876543210")
        }

        return view
    }

    // 🔐 Permission check
    private fun checkPermissionAndCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            makeCall(number)
        }
    }

    // 📞 Real call
    private fun makeCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    // 🔁 Permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            val number = display.text.toString()
            makeCall(number)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}