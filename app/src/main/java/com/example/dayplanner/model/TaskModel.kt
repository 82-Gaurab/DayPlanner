package com.example.dayplanner.model

import android.os.Parcel
import android.os.Parcelable

class TaskModel (
    var taskId: String = "",
    var taskTitle: String = "",
    var taskDesc: String = "",
    var taskTime: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskId)
        parcel.writeString(taskTitle)
        parcel.writeString(taskDesc)
        parcel.writeString(taskTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }
}