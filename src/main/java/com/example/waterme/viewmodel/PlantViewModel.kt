/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.waterme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.waterme.data.DataSource
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit
import androidx.work.WorkManager


class PlantViewModel(private val application: Application): ViewModel() {

    val plants = DataSource.plants

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String,
    ) {
        // TODO: create a Data instance with the plantName passed to it

            val data = Data.Builder()
                data.putString(WaterReminderWorker.nameKey, plantName).build()

            // TODO: Generate a OneTimeWorkRequest with the passed in duration, time unit, and data
            //  instance
            val waterReminderWorker = OneTimeWorkRequestBuilder<WaterReminderWorker>()
                .setInitialDelay(30, TimeUnit.DAYS)
                .setInputData(Data.Builder().build())

            // TODO: Enqueue the request as a unique work request

        WorkManager.getInstance(application).enqueueUniqueWork(
            plantName, ExistingWorkPolicy.REPLACE, OneTimeWorkRequest.from(WaterReminderWorker::class.java))
        }
    }




class PlantViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            PlantViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
