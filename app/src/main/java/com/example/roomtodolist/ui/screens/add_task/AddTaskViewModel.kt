package com.example.roomtodolist.ui.screens.add_task

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.roomtodolist.data.folder.FolderTable
import com.example.roomtodolist.data.task.TaskPriority
import com.example.roomtodolist.data.task.TaskTable
import com.example.roomtodolist.ui.navigation.NestedRoutes
import com.example.roomtodolist.domain.main_activity.MainViewModel
import java.time.LocalDate
import java.time.LocalTime

class AddTaskViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    var taskTitleState by mutableStateOf("")
        private set
    var taskPriorityState by mutableStateOf(TaskPriority.UNSPECIFIED)
        private set
    var taskDateState by mutableStateOf<LocalDate?>(null)
        private set
    var taskTimeState by mutableStateOf<LocalTime?>(null)
        private set
    var taskFolderState by mutableStateOf<FolderTable?>(null)
        private set

    fun setTaskTitle(title: String) {
        taskTitleState = title
    }

    fun setPriority(priority: TaskPriority) {
        taskPriorityState = priority
    }

    fun setDate(date: LocalDate) {
        taskDateState = date
    }

    fun setTime(time: LocalTime) {
        taskTimeState = time
    }

    fun setFolder(folder: FolderTable) {
        taskFolderState = folder
    }

    fun getFolders() : List<FolderTable> = mainViewModel.folders.values.toList()

    fun navigateToAddFolderScreen() {
        mainViewModel.navigateTo(NestedRoutes.ADD_FOLDER.name)
    }

    fun navigateBack() {
        mainViewModel.navigateBack()
    }

    fun isReadyToSave() = taskTitleState != "" &&
                taskPriorityState != TaskPriority.UNSPECIFIED &&
                taskDateState != null &&
                taskTimeState != null &&
                taskFolderState != null

    fun showErrorMessage(context: Context) {
        if (taskTitleState == "")
            Toast.makeText(context, "insert title please", Toast.LENGTH_SHORT).show()

        else if (taskPriorityState == TaskPriority.UNSPECIFIED)
            Toast.makeText(context, "choose priority please", Toast.LENGTH_SHORT).show()

        else if (taskDateState == null)
            Toast.makeText(context, "choose date please", Toast.LENGTH_SHORT).show()

        else if (taskTimeState == null)
            Toast.makeText(context, "choose time please", Toast.LENGTH_SHORT).show()

        else if (taskFolderState == null)
            Toast.makeText(context, "choose folder please", Toast.LENGTH_SHORT).show()

        else
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()

    }

    fun showSuccessMessage(context: Context) {
        Toast.makeText(context, "the task ${taskTitleState} has been saved successfully", Toast.LENGTH_SHORT).show()
    }

    fun save() {
        mainViewModel.addTask(
            TaskTable(
                title = taskTitleState,
                date = taskDateState!!,
                time = taskTimeState!!,
                priority = taskPriorityState,
                folder = taskFolderState!!.id!!
            )
        )
    }

    fun clear() {
        taskTitleState = ""
        taskPriorityState = TaskPriority.UNSPECIFIED
        taskDateState = null
        taskTimeState = null
        taskFolderState = null
    }
}