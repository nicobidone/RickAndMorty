package com.example.data.local.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

val NEXT_PAGE: Preferences.Key<Int> = intPreferencesKey("lastPage")
val LAST_PAGE: Preferences.Key<Int> = intPreferencesKey("nextPage")

const val DEFAULT_PAGE_VALUE = 1
