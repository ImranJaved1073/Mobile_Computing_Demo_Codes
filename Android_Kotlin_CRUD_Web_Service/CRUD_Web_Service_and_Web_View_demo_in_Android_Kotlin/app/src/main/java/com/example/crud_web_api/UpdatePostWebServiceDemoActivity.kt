package com.example.crud_web_api

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crud_web_api.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdatePostWebServiceDemoActivity : Activity() {
    private lateinit var editTextRollNo: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextCgpa: EditText
    private lateinit var buttonSubmit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_post_web_service_demo)

        val student = Student(
            intent.getStringExtra("rollNo")!!,
            intent.getStringExtra("name")!!,
            intent.getIntExtra("age", 0),
            intent.getDoubleExtra("cgpa", 0.0)
        )

        editTextRollNo = findViewById(R.id.rollno_txt)
        editTextName = findViewById(R.id.name_txt)
        editTextAge = findViewById(R.id.age_txt)
        editTextCgpa = findViewById(R.id.CGPA_txt)
        buttonSubmit = findViewById(R.id.click_btn)

        editTextRollNo.setText(student.rollNo)
        editTextName.setText(student.name)
        editTextAge.setText(student.age.toString())
        editTextCgpa.setText(student.cgpa.toString())

        buttonSubmit.setOnClickListener {
            updateStudent()
        }
    }

    private fun updateStudent()
    {
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
                val response = api.updateStudent(student)

                withContext(Dispatchers.Main) {
                    if (response.success) {
                        Toast.makeText(this@UpdatePostWebServiceDemoActivity, "Student updated successfully!", Toast.LENGTH_LONG).show()
                        finish() // Close the activity after updating
                        val intent = Intent(this@UpdatePostWebServiceDemoActivity,WebServiceListViewDemoActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@UpdatePostWebServiceDemoActivity, "Failed to update student: ${response.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdatePostWebServiceDemoActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}