package com.example.medlookup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.medlookup.navigation.AppNavigation
import com.example.medlookup.ui.theme.MedLookupTheme
import com.example.medlookup.ui.search.SearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedLookupTheme {
                AppNavigation()
            }
        }
    }
}

