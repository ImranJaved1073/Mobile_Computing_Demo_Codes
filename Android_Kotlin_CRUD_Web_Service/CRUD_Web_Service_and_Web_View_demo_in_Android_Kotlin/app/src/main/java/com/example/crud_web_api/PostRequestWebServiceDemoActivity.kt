package com.example.crud_web_api

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.crud_web_api.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostRequestWebServiceDemoActivity : Activity() {
    private lateinit var editTextRollNo: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextCgpa: EditText
    private lateinit var buttonSubmit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_request_web_service_demo)

        editTextRollNo = findViewById(R.id.rollno_txt)
        editTextName = findViewById(R.id.name_txt)
        editTextAge = findViewById(R.id.age_txt)
        editTextCgpa = findViewById(R.id.CGPA_txt)
        buttonSubmit = findViewById(R.id.click_btn)

        // Set click listener for the submit button
        buttonSubmit.setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val rollNo = editTextRollNo.text.toString()
        val name = editTextName.text.toString()
        val age = editTextAge.text.toString().toIntOrNull()
        val cgpa = editTextCgpa.text.toString().toDoubleOrNull()

        if (rollNo.isBlank() || name.isBlank() || age == null || cgpa == null) {
            Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(rollNo, name, age, cgpa)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.addStudent(student)
                runOnUiThread {
                    if (response.success) {
                        Toast.makeText(this@PostRequestWebServiceDemoActivity, "Student added successfully!", Toast.LENGTH_SHORT).show()
                        // Optionally, clear the fields or navigate to another activity
                    } else {
                        Toast.makeText(this@PostRequestWebServiceDemoActivity, "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@PostRequestWebServiceDemoActivity, "Failed to add student: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
