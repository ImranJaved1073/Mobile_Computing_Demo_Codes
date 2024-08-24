package com.example.crud_web_api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentAdapter(context: Context, private val students: List<Student>) : ArrayAdapter<Student>(context, 0, students) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_student_item, parent, false)

        val student = students[position]

        val rollNoTextView = view.findViewById<TextView>(R.id.rollNoTextView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val ageTextView = view.findViewById<TextView>(R.id.ageTextView)
        val cgpaTextView = view.findViewById<TextView>(R.id.cgpaTextView)

        rollNoTextView.text = student.rollNo
        nameTextView.text = student.name
        ageTextView.text = student.age.toString()
        cgpaTextView.text = student.cgpa.toString()

        return view
    }
}
