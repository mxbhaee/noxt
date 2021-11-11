package com.noxt.repositories

import com.noxt.model.NoteCategory
import com.noxt.model.Task
import com.noxt.utils.LiveRealmData
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmResults
import io.realm.Sort

class TaskRepo {
    var realm: Realm = Realm.getDefaultInstance()

    fun getTasks():LiveRealmData<Task>{
        realm = Realm.getDefaultInstance()
        return realm.where(Task::class.java).sort( "checked", Sort.ASCENDING, "date", Sort.DESCENDING).findAllAsync().asLiveData()
    }


    fun addTask(task: Task){
        realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            it.copyToRealm(task)
            it.commitTransaction()
        }
    }

    fun updateTask(task: Task){
        realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            it.copyToRealmOrUpdate(task)
            it.commitTransaction()
        }
    }

    fun deleteTask(task: Task){
        realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            task.deleteFromRealm()
            it.commitTransaction()
        }
    }

    fun addCategoryToTask(task: Task, noteCategory: NoteCategory){
        realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            task.noteCategories.add(noteCategory)
            it.commitTransaction()
        }
    }

    fun checkTask(task: Task, check: Boolean){
        realm = Realm.getDefaultInstance()
        realm.use {
            it.beginTransaction()
            task.checked = check
            it.commitTransaction()
        }
    }


    private fun <T: RealmModel> RealmResults<T>.asLiveData() = LiveRealmData<T>(this)
}