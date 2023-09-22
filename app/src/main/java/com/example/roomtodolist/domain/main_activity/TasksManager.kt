package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateMapOf
import com.example.roomtodolist.data.task.TaskDAO
import com.example.roomtodolist.data.task.TaskTable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class TasksManager(private val taskDAO: TaskDAO) {

    private var hasInitialized = false

    private val completedTasks = hashMapOf<Long, Job>()

    val tasks = mutableStateMapOf<Long, TaskTable>()

    suspend fun initTasks(onFinish : (Map<Long, TaskTable>) -> Unit) : Map<Long, TaskTable> {
        if (hasInitialized)
            return mapOf()

        hasInitialized = true

        for (task in taskDAO.getTasks())
            tasks[task.id!!] = task

        onFinish(tasks.toMap())
        return tasks.toMap()
    }

    suspend fun addTask(task: TaskTable) : TaskTable {
        val id = taskDAO.addTask(task)
        val newTask = task.copy(id = id)
        updateTaskState(newTask, Operation.ADD)
        return newTask
    }

    suspend fun updateTask(task: TaskTable) {
        taskDAO.updateTask(task)
        updateTaskState(task, Operation.CHANGE)
    }

    suspend fun deleteTask(task: TaskTable) {
        taskDAO.deleteTask(taskId = task.id!!)
        updateTaskState(task, Operation.DELETE)
    }

    suspend fun selectTask(task: TaskTable, job: Job) : Boolean {
        return if (completedTasks.containsKey(task.id)) {
            completedTasks[task.id]!!.cancel()
            completedTasks.remove(task.id)
            false
        } else {
            completedTasks[task.id!!] = job
            delay(2000)
            deleteTask(task)
            completedTasks.remove(task.id)
            true
        }
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