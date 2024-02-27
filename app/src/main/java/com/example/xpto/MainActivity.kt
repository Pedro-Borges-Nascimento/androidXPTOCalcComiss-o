package com.example.xpto

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.text.InputType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the button by its ID
        val btnInfo: Button = findViewById(R.id.btnInfo)
        val btnCalc: Button = findViewById(R.id.btnCalc)

        // Find other views by their IDs
        val edtWage: EditText = findViewById(R.id.edtWage)
        val edtKm: EditText = findViewById(R.id.edtKm)
        val lblRes: TextView = findViewById(R.id.lblRes)
        val lblPercRes: TextView = findViewById(R.id.lblPercRes)

        // Set input type for EditText fields to accept only numerical input
        edtWage.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        edtKm.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // Set OnClickListener to the button
        btnInfo.setOnClickListener {
            // Create a dialog
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.custom_dialog_info)

            // Find views in the dialog layout
            val textViewInfo = dialog.findViewById<TextView>(R.id.textViewInfo)
            val btnClose = dialog.findViewById<Button>(R.id.btnClose)

            // Set text to the TextView
            textViewInfo.text = getString(R.string.info_text)

            // Set OnClickListener to the close button
            btnClose.setOnClickListener {
                // Dismiss the dialog when the button is clicked
                dialog.dismiss()
            }

            // Show the dialog
            dialog.show()
        }

        // Set OnClickListener to the calculate button
        btnCalc.setOnClickListener {
            val wageStr = edtWage.text.toString()
            val kmStr = edtKm.text.toString()

            // Check if input is empty or contains only a dot "."
            if (wageStr.isNotEmpty() && wageStr != "." && kmStr.isNotEmpty() && kmStr != ".") {
                val wage = wageStr.toDouble()
                val km = kmStr.toDouble()
                var commission = 0.0

                // Calculate commission based on the distance traveled
                when {
                    km < 500 -> commission = 0.001 * wage * km
                    km in 501.0..1000.0 -> commission = 0.0015 * wage * km
                    km in 1001.0..2000.0 -> commission = 0.0025 * wage * km
                    km > 2000 -> commission = 0.003 * wage * km
                }

                lblRes.text = commission.toString()

                // Calculate and display the commission percentage based on the kilometers
                val commissionPercentage = when {
                    km < 500 -> "Sua porcentagem da comissão é 0.1%"
                    km in 501.0..1000.0 -> "Sua porcentagem da comissão é 0.15%"
                    km in 1001.0..2000.0 -> "Sua porcentagem da comissão é 0.25%"
                    km > 2000 -> "Sua porcentagem da comissão é 0.3%"
                    else -> ""
                }

                lblPercRes.text = commissionPercentage
            } else {
                // Handle case when one or both fields are empty or contain only a dot "."
                lblRes.text = "Por favor, informe tanto o salário quanto os quilômetros."
                lblPercRes.text = ""
            }
        }
    }
}
