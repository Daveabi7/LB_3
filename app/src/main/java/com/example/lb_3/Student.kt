package com.example.lb_3

class Student(name: String, age: Int, grades: List<Int>) {
    constructor(name: String) : this(name, 0, emptyList()) {
        this.name = name
    }

    init {
        println("Створено нового студента: ${name.trim()}")
    }

    var name: String = name
        set(value) {
            field = value.trim().replaceFirstChar { it.uppercase() }
        }

    var age: Int = age
        set(value) {
            field = if (value >= 0) value else throw IllegalArgumentException("Вік не може бути від'ємним")
        }

    var grades: List<Int> = grades
        get() = field
        private set

    val isAdult: Boolean
        get() = age >= 18

    val status: String by lazy {
        if (isAdult) "Дорослий" else "Неповнолітній"
    }

    fun getAverage(): Double {
        return if (grades.isEmpty()) 0.0 else grades.average()
    }

    fun processGrades(operation: (Int) -> Int) {
        grades = grades.map(operation)
    }

    fun updateGrades(newGrades: List<Int>) {
        grades = newGrades
    }

    operator fun plus(other: Student): Student {
        return Student(name, age, grades + other.grades)
    }

    operator fun times(multiplier: Int): Student {
        return Student(name, age, grades.map { it * multiplier })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Student) return false
        return name == other.name && getAverage() == other.getAverage()
    }

    override fun hashCode(): Int {
        return name.hashCode() + getAverage().hashCode()
    }

    override fun toString(): String {
        return """
            |════════════════════════════
            |👤 Студент: ${name.trim()}
            |├─ Вік: $age років
            |├─ Статус: $status
            |├─ Оцінки: ${grades.joinToString(", ")}
            |└─ Середній бал: ${"%.2f".format(getAverage())}
            |════════════════════════════
        """.trimMargin()
    }
}