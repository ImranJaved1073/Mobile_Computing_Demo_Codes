package com.example.crud_web_api

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SimpleWebServiceDemoActivity : Activity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_web_service_demo)

        textView = findViewById(R.id.textView)

        // Fetch data using coroutines
        GlobalScope.launch {
            try {
                val students = RetrofitInstance.api.getAllStudents()
                val studentInfo = students.joinToString("\n") { student ->
                    "RollNo: ${student.rollNo},Name: ${student.name}, CGPA: ${student.cgpa}, Age: ${student.age}"
                }
                runOnUiThread {
                    textView.text = studentInfo
                }
            } catch (e: Exception) {
                runOnUiThread {
                    textView.text = "Error fetching data"
                }
            }
        }
    }
}