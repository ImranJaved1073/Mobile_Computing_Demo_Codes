package com.example.crud_web_api

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebServiceListViewDemoActivity : Activity() {

    private lateinit var studentListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_service_list_view_demo)

        studentListView = findViewById(R.id.listView)

        fetchStudents()
    }

    private fun fetchStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val students = RetrofitInstance.api.getAllStudents()

                withContext(Dispatchers.Main) {
                    populateListView(students)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WebServiceListViewDemoActivity, "Failed to fetch students: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun populateListView(students: List<Student>) {
        val adapter = StudentAdapter(this, students)
        studentListView.adapter = adapter
    }
}
