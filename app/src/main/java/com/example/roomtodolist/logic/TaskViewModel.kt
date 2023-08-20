package com.example.roomtodolist.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomtodolist.database.task.TaskDAO
import com.example.roomtodolist.database.task.TaskTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val databaseAccessObject: TaskDAO) : ViewModel() {

    val taskListFlow = MutableStateFlow(mutableListOf<TaskTable>())

    init {
        viewModelScope.launch(Dispatchers.Default) {
            taskListFlow.value.addAll(databaseAccessObject.getTasks())
        }
    }

    fun addTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.Default) {
            databaseAccessObject.addTask(task)
            taskListFlow.value.add(task)
        }
    }

    fun updateTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.Default) {
            databaseAccessObject.updateTask(task)
            for (index in taskListFlow.value.indices)
                if (taskListFlow.value[index].id == task.id){
                    taskListFlow.value[index] = task
                    break
                }
        }
    }

    fun deleteTask(task: TaskTable) {
        viewModelScope.launch(Dispatchers.Default) {
            databaseAccessObject.deleteTask(task)
            taskListFlow.value.remove(task)
        }
    }

}