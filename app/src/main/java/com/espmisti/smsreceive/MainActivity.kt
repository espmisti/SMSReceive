package com.espmisti.smsreceive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permissions = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it) {
                Toast.makeText(this, "Good!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "The application needs permission to work to read SMS", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        permissions.launch(android.Manifest.permission.RECEIVE_SMS)
    }
}