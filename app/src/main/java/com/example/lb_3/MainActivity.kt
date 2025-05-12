package com.example.lb_3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var outputTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        outputTextView = findViewById(R.id.outputTextView)
        val output = StringBuilder()

        output.appendLine("""
            |════════════════════════════
            |     🆕 СТВОРЕННЯ СТУДЕНТІВ     
            |════════════════════════════
        """.trimMargin())

        val student1 = Student("  ivan petrov  ", 20, listOf(85, 90, 78))
        val student2 = Student(name = "Olena", age = 17, grades = listOf(92, 88, 95))
        val student3 = Student("Anna")

        output.appendLine(student1.toString())
        output.appendLine(student2.toString())
        output.appendLine(student3.toString())

        output.appendLine("""
            |════════════════════════════
            |     📊 ДОДАВАННЯ ОЦІНОК      
            |════════════════════════════
        """.trimMargin())

        student3.updateGrades(listOf(75, 80, 85))
        output.appendLine("Оновлені оцінки для Anna: ${student3.grades}")

        output.appendLine("""
            |════════════════════════════
            |     ➕✖️ ОПЕРАТОРИ      
            |════════════════════════════
        """.trimMargin())

        val combinedStudent = student1 + student2
        output.appendLine("Об'єднані оцінки: ${combinedStudent.grades}")

        val multipliedStudent = student1 * 2
        output.appendLine("Оцінки помножені на 2: ${multipliedStudent.grades}")

        output.appendLine("""
            |════════════════════════════
            |     🎓 РОБОТА З ГРУПОЮ      
            |════════════════════════════
        """.trimMargin())

        val group = Group(student1, student2, student3)
        output.appendLine(group.toString())

        output.appendLine("""
            |════════════════════════════
            |     ⏳ ОНОВЛЕННЯ ОЦІНОК      
            |════════════════════════════
            |Запит до сервера...
        """.trimMargin())

        launch {
            val newGrades = async(Dispatchers.IO) { fetchGradesFromServer() }.await()
            student3.updateGrades(newGrades)
            output.appendLine("""
                |✅ Отримано нові оцінки:
                |${student3.grades.joinToString(", ")}
                |Новий середній бал: ${"%.2f".format(student3.getAverage())}
            """.trimMargin())
            outputTextView.text = output.toString()
        }

        outputTextView.text = output.toString()
    }

    private suspend fun fetchGradesFromServer(): List<Int> {
        delay(2000)
        return listOf(90, 95, 85, 88, 92)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}