package com.example.roomtodolist.domain.main_activity

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

class TasksPerFolderManager {

    val tasksPerFolder = mutableStateMapOf<FolderTable, SnapshotStateList<TaskTable>>()

    fun initTasksPerFolder(folders: Map<Long, FolderTable>, tasks: Map<Long, TaskTable>) {
        for (folder in folders) {
            updateTasksPerFolderKeyState(folder.value, Operation.ADD)
        }
        for (task in tasks) {
            folders[task.value.folder]?.let {
                updateTasksPerFolderValueState(
                    task.value,
                    it,
                    Operation.ADD
                )
            }
        }
    }

    fun updateTasksPerFolderKeyState(folder: FolderTable, operation: Operation) {
        when (operation) {
            Operation.ADD -> {
                tasksPerFolder[folder] = mutableStateListOf()
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
                Log.d(TAG, "updateTasksPerFolderValueState: ${tasksPerFolder.toMap()}")
                tasksPerFolder[folder]!!.remove(task)
                Log.d(TAG, "updateTasksPerFolderValueState: ${tasksPerFolder.toMap()}")
            }
        }
    }

}