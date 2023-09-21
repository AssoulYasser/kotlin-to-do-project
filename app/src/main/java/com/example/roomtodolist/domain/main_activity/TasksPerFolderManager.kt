package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateMapOf
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

class TasksPerFolderManager {

    val tasksPerFolder = mutableStateMapOf<FolderTable, MutableList<TaskTable>>()
    var isInitialized = false

    fun updateTasksPerFolderKeyState(folder: FolderTable, operation: Operation) {
        when (operation) {
            Operation.ADD -> {
                tasksPerFolder[folder] = mutableListOf()
            }
            Operation.CHANGE -> {
                for (eachFolder in tasksPerFolder.keys) {
                    if (eachFolder.id == folder.id) {
                        val list = tasksPerFolder[eachFolder]!!
                        tasksPerFolder.remove(eachFolder)
                        tasksPerFolder[folder] = list
                        return
                    }
                }
            }
            Operation.DELETE -> {
                tasksPerFolder.remove(folder)
            }
        }
    }

    fun updateTasksPerFolderValueState(task: TaskTable, folder: FolderTable, operation: Operation) {
        when (operation) {
            Operation.ADD -> {
                tasksPerFolder[folder]!!.add(task)
            }
            Operation.CHANGE -> {
                val list = tasksPerFolder[folder]!!
                for (index in list.indices) {
                    if (list[index].id == task.id) {
                        if (list[index].folder == task.folder)
                            tasksPerFolder[folder]!![index] = task
                        else
                            tasksPerFolder[folder]!!.remove(list[index])
                        return
                    }
                }
                tasksPerFolder[folder]!!.add(task)
            }
            Operation.DELETE -> {
                tasksPerFolder[folder]!!.remove(task)
            }
        }
    }

}