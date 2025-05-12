package com.example.lb_3

class Group(private vararg val students: Student) {
    operator fun get(index: Int): Student {
        return students[index]
    }

    fun getTopStudent(): Student? {
        return students.maxByOrNull { it.getAverage() }
    }

    override fun toString(): String {
        val header = """
            |════════════════════════════
            |       🎓 ГРУПА СТУДЕНТІВ       
            |════════════════════════════
        """.trimMargin()

        val footer = """
            |════════════════════════════
            |🏆 Найкращий студент: ${getTopStudent()?.name ?: "немає"}
            |════════════════════════════
        """.trimMargin()

        return header + "\n" + students.joinToString("\n") { it.toString() } + "\n" + footer
    }
}