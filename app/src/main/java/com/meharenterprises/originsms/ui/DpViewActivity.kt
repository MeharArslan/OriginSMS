package com.meharenterprises.originsms.ui

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.meharenterprises.originsms.R

class DpViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dp_view)

        val photoUri = intent.getStringExtra("photoUri")
        val displayName = intent.getStringExtra("displayName") ?: ""

        val imgDp = findViewById<ImageView>(R.id.imgDpFull)
        val txtName = findViewById<TextView>(R.id.txtDpName)
        val btnClose = findViewById<ImageView>(R.id.btnDpClose)

        txtName.text = displayName
        btnClose.setOnClickListener { finish() }
        imgDp.setOnClickListener { finish() }

        if (photoUri != null) {
            try {
                imgDp.setImageURI(Uri.parse(photoUri))
            } catch (_: Exception) {
                imgDp.setImageResource(R.drawable.ic_person)
            }
        } else {
            imgDp.setImageResource(R.drawable.ic_person)
        }
    }
}
