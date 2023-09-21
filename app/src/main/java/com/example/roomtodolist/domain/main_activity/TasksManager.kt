package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateMapOf
import com.example.roomtodolist.data.task.TaskDAO
import com.example.roomtodolist.data.task.TaskTable

class TasksManager(private val taskDAO: TaskDAO) {

    var hasInitialized = false
    val tasks = mutableStateMapOf<Long, TaskTable>()

    suspend fun start() {
        if (!hasInitialized) {
            for (task in taskDAO.getTasks())
                tasks[task.id!!] = task
            hasInitialized = true
        }
    }

    suspend fun addTask(task: TaskTable) {
        val id = taskDAO.addTask(task)
        val newTask = task.copy(id = id)
        updateTaskState(newTask, Operation.ADD)
    }

    suspend fun updateTask(task: TaskTable) {
        taskDAO.updateTask(task)
        updateTaskState(task, Operation.CHANGE)
    }

    suspend fun deleteTask(task: TaskTable) {
        taskDAO.deleteTask(taskId = task.id!!)
        updateTaskState(task, Operation.DELETE)
    }


    private fun updateTaskState(task: TaskTable, operation: Operation) {
        if (task.id == null)
            throw Exception("TASK ID SHOULD NOT BE NULL")
        when (operation) {
            Operation.ADD, Operation.CHANGE -> {
                tasks[task.id] = task
            }
            Operation.DELETE -> {
                tasks.remove(task.id)
            }
        }
    }

}