package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateMapOf
import com.example.roomtodolist.data.task.TaskDAO
import com.example.roomtodolist.data.task.TaskTable

class TaskManager(private val taskDAO: TaskDAO) {

    private var hasInitialized = false
    val tasks = mutableStateMapOf<Long, TaskTable>()

    suspend fun initTasks(onFinish: (List<TaskTable>) -> Unit) {
        if (hasInitialized)
            return

        for (task in taskDAO.getTasks()) {
            tasks[task.id!!] = task
        }
        onFinish(tasks.values.toList())
        hasInitialized = true
    }

    suspend fun addTask(task: TaskTable) : TaskTable {
        val generatedId = taskDAO.addTask(task)
        val newTask = task.copy(id = generatedId)
        tasks[generatedId] = newTask
        return newTask
    }

    suspend fun updateTask(task: TaskTable) {
        taskDAO.updateTask(task)
        tasks[task.id!!] = task
    }

    suspend fun deleteTask(task: TaskTable) {
        taskDAO.deleteTask(taskId = task.id!!)
        tasks.remove(task.id)
    }

}