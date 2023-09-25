package com.example.roomtodolist.domain.main_activity

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import com.example.roomtodolist.R
import com.example.roomtodolist.data.folder.FolderDAO
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskDAO
import com.example.roomtodolist.data.task.TaskTable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class TaskManager(private val folderDAO: FolderDAO,private val taskDAO: TaskDAO) {

    private var hasInitialized = false

    private val completedTasks = hashMapOf<Long, Job>()

    val tasks = mutableStateMapOf<Long, TaskTable>()

    val tasksPerFolder = mutableStateMapOf<FolderTable, SnapshotStateList<TaskTable>>()

    val folders : Map<Long, FolderTable>
        get() {
            val map = mutableMapOf<Long, FolderTable>()
            for (each in tasksPerFolder.keys) {
                map[each.id!!] = each
            }
            return map
        }

    var folderToUpdate: FolderTable? = null
        private set

    var taskToUpdate: TaskTable? = null
        private set

    suspend fun init() {
        if (hasInitialized)
            return

        for (folder in folderDAO.getFolders()) {
            val tasksInFolder = taskDAO.getTasksFromFolder(folder.id!!)
            folder.taskCounts = tasksInFolder.size

            tasksPerFolder[folder] = mutableStateListOf()
            tasksPerFolder[folder]!!.addAll(tasksInFolder)

            for (task in tasksInFolder) {
                tasks [task.id!!] = task
            }
        }

        hasInitialized = true
    }

    private fun getFolderByKey(folderId : Long) : FolderTable? {
        for (folder in tasksPerFolder.keys)
            if (folderId == folder.id) return folder
        return null
    }

    suspend fun addFolder(folder: FolderTable) : FolderTable {
        val generatedId = folderDAO.addFolder(folder)
        val newFolder = folder.copy(id = generatedId)
        tasksPerFolder[newFolder] = mutableStateListOf()
        return newFolder
    }

    fun setFolderToUpdate(folder: FolderTable) {
        folderToUpdate = folder
    }

    fun clearFolderToUpdate() {
        folderToUpdate = null
    }

    suspend fun updateFolder(folder: FolderTable) {
        folderDAO.updateFolder(folder)
        val oldFolder = getFolderByKey(folderId = folder.id!!)
        val oldTaskList = tasksPerFolder[oldFolder]
        tasksPerFolder.remove(oldFolder)
        tasksPerFolder[folder] = oldTaskList ?: mutableStateListOf()
    }

    suspend fun deleteFolder(folder: FolderTable) {
        folderDAO.deleteFolder(folderId = folder.id!!)
        val oldFolder = getFolderByKey(folder.id)
        tasksPerFolder.remove(oldFolder)
    }

    fun getFolderColors() = folderColors

    fun getFolderAssets() = folderAssets

    suspend fun addTask(task: TaskTable) : TaskTable {
        val generatedId = taskDAO.addTask(task)

        val newTask = task.copy(id = generatedId)
        tasks[generatedId] = newTask

        val folder = getFolderByKey(task.folder)
        val oldTasksInFolder = tasksPerFolder[folder]
        oldTasksInFolder!!.add(newTask)
        tasksPerFolder.remove(folder)
        folder!!.taskCounts ++
        tasksPerFolder[folder] = oldTasksInFolder

        return newTask
    }

    fun setTaskToUpdate(task: TaskTable) {
        taskToUpdate = task
    }

    fun clearTaskToUpdate() {
        taskToUpdate = null
    }

    suspend fun updateTask(task: TaskTable) {
        taskDAO.updateTask(task)
        if (tasks[task.id]!!.folder == task.folder) {
            val folder = getFolderByKey(task.folder)
            for (taskIndex in tasksPerFolder[folder]!!.indices) {
                if (tasksPerFolder[folder]!![taskIndex].id == task.id) {
                    tasksPerFolder[folder]!![taskIndex] = task
                    break
                }
            }
        } else {
            val oldFolder = getFolderByKey(tasks[task.id!!]!!.folder)
            val oldList = tasksPerFolder[oldFolder]!!
            tasksPerFolder.remove(oldFolder)
            for (taskIndex in oldList.indices) {
                if (oldList[taskIndex].id == task.id) {
                    oldList.removeAt(taskIndex)
                    oldFolder!!.taskCounts --
                    tasksPerFolder[oldFolder] = oldList
                    break
                }
            }
            val newFolder = getFolderByKey(task.folder)
            val newList = tasksPerFolder[newFolder]!!
            newList.add(task)
            tasksPerFolder.remove(newFolder)
            newFolder!!.taskCounts ++
            tasksPerFolder[newFolder] = newList
        }
        tasks[task.id!!] = task
    }

    suspend fun deleteTask(task: TaskTable) {
        taskDAO.deleteTask(taskId = task.id!!)
        tasks.remove(task.id)

        val folder = getFolderByKey(task.folder)
        val oldTasksInFolder = tasksPerFolder[folder]
        oldTasksInFolder!!.remove(task)
        tasksPerFolder.remove(folder)
        folder!!.taskCounts --
        tasksPerFolder[folder] = oldTasksInFolder
    }

    suspend fun selectTask(task: TaskTable, job: Job) {
        if (completedTasks.containsKey(task.id)) {
            completedTasks[task.id]!!.cancel()
            completedTasks.remove(task.id)
        } else {
            completedTasks[task.id!!] = job
            delay(5000)
            deleteTask(task)
            completedTasks.remove(task.id)
        }
    }


    private val folderColors: List<Color> = mutableListOf(
            Color(0xFFFF0000),
            Color(0xFFFF5100),
            Color(0xFF37FF00),
            Color(0xFF00FFFF),
            Color(0xFF0800FF),
            Color(0xFF9500FF),
            Color(0xFFFF0084),
        )

    private val folderAssets: Map<Int, Int> = hashMapOf(
            R.drawable.yoga_asset to R.drawable.yoga_asset_primary,
            R.drawable.cooking_asset to R.drawable.cooking_asset_primary,
            R.drawable.working_asset to R.drawable.working_asset_primary,
            R.drawable.diy_asset to R.drawable.diy_asset_primary,
            R.drawable.shopping_asset to R.drawable.shopping_asset_primary,
            R.drawable.nature_asset to R.drawable.nature_asset_primary,
            R.drawable.choosing_asset to R.drawable.choosing_asset_primary,
            R.drawable.familly_asset to R.drawable.familly_asset_primary,
            R.drawable.diversity_asset to R.drawable.diversity_asset_primary,
            R.drawable.homework_asset to R.drawable.homework_asset_primary,
        )

}