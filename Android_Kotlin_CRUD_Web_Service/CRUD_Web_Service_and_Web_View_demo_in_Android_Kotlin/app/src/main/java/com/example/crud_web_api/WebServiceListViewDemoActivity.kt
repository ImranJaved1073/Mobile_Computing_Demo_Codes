package com.example.crud_web_api

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebServiceListViewDemoActivity : Activity() {

    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentListView: ListView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_service_list_view_demo)

        studentListView = findViewById(R.id.listView)
        searchView = findViewById(R.id.searchView)

        fetchStudents()
    }

    private fun fetchStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val students = RetrofitInstance.api.getAllStudents()

                withContext(Dispatchers.Main) {
                    studentAdapter = StudentAdapter(this@WebServiceListViewDemoActivity, students)
                    studentListView.adapter = studentAdapter

                    // Set up the SearchView after the adapter is initialized
                    setupSearchView()

                    // Set an item click listener
                    studentListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        val selectedStudent = students[position]
                        // Open UpdateActivity with selected student data
                        val intent = Intent(
                            this@WebServiceListViewDemoActivity,
                            UpdatePostWebServiceDemoActivity::class.java
                        ).apply {
                            putExtra("rollNo", selectedStudent.rollNo)
                            putExtra("name", selectedStudent.name)
                            putExtra("cgpa", selectedStudent.cgpa)
                            putExtra("age", selectedStudent.age)
                        }
                        finish()
                        startActivity(intent)
                    }

                    studentListView.onItemLongClickListener =
                        AdapterView.OnItemLongClickListener { _, _, position, _ ->
                            val selectedStudent = students[position]
                            AlertDialog.Builder(this@WebServiceListViewDemoActivity).apply {
                                setTitle("Delete Student")
                                setMessage("Are you sure you want to delete ${selectedStudent.name}?")
                                setPositiveButton("Yes") { _, _ ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            val response = RetrofitInstance.api.deleteStudent(selectedStudent.rollNo)

                                            withContext(Dispatchers.Main) {
                                                if (response.success) {
                                                    Toast.makeText(
                                                        this@WebServiceListViewDemoActivity,
                                                        "Student deleted successfully!",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    fetchStudents() // Refresh the list after deletion
                                                } else {
                                                    Toast.makeText(
                                                        this@WebServiceListViewDemoActivity,
                                                        "Failed to delete student: ${response.message}",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                        } catch (e: Exception) {
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(
                                                    this@WebServiceListViewDemoActivity,
                                                    "${e.message}",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                    }
                                }
                                setNegativeButton("No", null)
                            }.show()
                            true
                        }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@WebServiceListViewDemoActivity,
                        "Failed to fetch students: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                studentAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                studentAdapter.filter.filter(newText)
                return true
            }
        })
    }
}
