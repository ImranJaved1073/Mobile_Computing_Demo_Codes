package com.example.crud_web_api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class StudentAdapter(context: Context, private val originalStudents: List<Student>) :
    ArrayAdapter<Student>(context, 0, originalStudents), Filterable {

    private var filteredStudents: List<Student> = ArrayList(originalStudents)

    override fun getCount(): Int = filteredStudents.size

    override fun getItem(position: Int): Student? = filteredStudents.getOrNull(position)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_student_item, parent, false)

        val student = getItem(position)

        val rollNoTextView = view.findViewById<TextView>(R.id.rollNoTextView)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val ageTextView = view.findViewById<TextView>(R.id.ageTextView)
        val cgpaTextView = view.findViewById<TextView>(R.id.cgpaTextView)

        rollNoTextView.text = student?.rollNo
        nameTextView.text = student?.name
        ageTextView.text = student?.age.toString()
        cgpaTextView.text = student?.cgpa.toString()

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint.toString().toLowerCase()
                filteredStudents = if (query.isEmpty()) {
                    originalStudents
                } else {
                    originalStudents.filter { student ->
                        student.name.toLowerCase().contains(query) ||
                                student.rollNo.toLowerCase().contains(query) ||
                                student.age.toString().contains(query) ||
                                student.cgpa.toString().contains(query)
                    }
                }

                return FilterResults().apply { values = filteredStudents }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredStudents = results?.values as? List<Student> ?: listOf()
                notifyDataSetChanged()
            }
        }
    }
}

