package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskTable

class TasksPerFolderManager {

    val tasksPerFolder = mutableStateMapOf<FolderTable, SnapshotStateList<TaskTable>>()


    fun initFolders(folders: List<FolderTable>) {
        for (folder in folders) {
            tasksPerFolder[folder] = mutableStateListOf()
        }
    }

    fun initTasks(tasks: List<TaskTable>) {
        for (task in tasks) {
            for (folder in tasksPerFolder.keys) {
                if (folder.id == task.folder)
                    tasksPerFolder[folder]!!.add(task)
            }
        }
    }

    fun addFolder(folder: FolderTable) {
        tasksPerFolder[folder] = mutableStateListOf()
    }

    fun updateFolder(folder: FolderTable) {
        for (eachFolder in tasksPerFolder.keys) {
            if (eachFolder.id == folder.id) {
                val list = tasksPerFolder[eachFolder]!!
                tasksPerFolder.remove(eachFolder)
                tasksPerFolder[folder] = list
                return
            }
        }
    }

    fun deleteFolder(folder: FolderTable) {
        tasksPerFolder.remove(folder)
    }

    fun addTask(folder: FolderTable, task: TaskTable) {
        tasksPerFolder[folder]!!.add(task)
    }

    fun updateTask(folder: FolderTable, task: TaskTable) {
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

    fun deleteTask(folder: FolderTable, task: TaskTable) {
        tasksPerFolder[folder]!!.remove(task)
    }
}