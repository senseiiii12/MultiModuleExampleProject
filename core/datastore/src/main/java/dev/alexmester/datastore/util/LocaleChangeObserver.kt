package dev.alexmester.datastore.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import dev.alexmester.datastore.UserPreferencesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LocaleChangeObserver(
    private val context: Context,
    private val deviceLocaleProvider: DeviceLocaleProvider,
    private val preferencesDataSource: UserPreferencesDataSource,
    private val scope: CoroutineScope,
) {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
                Log.d("LocaleObserver", "System locale changed")
                scope.launch {
                    val prefs = preferencesDataSource.userPreferences.first()
                    if (!prefs.isLocaleManuallySet) {
                        preferencesDataSource.initLocaleFromDevice(
                            country = deviceLocaleProvider.getCountry(),
                            language = deviceLocaleProvider.getLanguage(),
                        )
                    }
                }
            }
        }
    }

    fun register() {
        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
        Log.d("LocaleObserver", "Registered")
    }

    fun unregister() {
        context.unregisterReceiver(receiver)
        Log.d("LocaleObserver", "Unregistered")
    }

    fun checkAndUpdate() {
        scope.launch {
            val prefs = preferencesDataSource.userPreferences.first()
            if (prefs.isLocaleManuallySet) return@launch

            val currentCountry = deviceLocaleProvider.getCountry()
            val currentLanguage = deviceLocaleProvider.getLanguage()

            if (prefs.defaultCountry != currentCountry ||
                prefs.defaultLanguage != currentLanguage
            ) {
                Log.d("LocaleObserver", "Locale mismatch detected on resume, updating...")
                preferencesDataSource.initLocaleFromDevice(
                    country = currentCountry,
                    language = currentLanguage,
                )
            }
        }
    }
}