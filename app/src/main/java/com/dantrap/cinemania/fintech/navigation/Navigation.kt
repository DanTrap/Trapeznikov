package com.dantrap.cinemania.fintech.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dantrap.cinemania.fintech.navigation.graphs.appGraph
import com.dantrap.cinemania.fintech.utils.CustomNavHost
import com.dantrap.cinemania.fintech.utils.Destination

@Composable
internal fun Navigation(navHostController: NavHostController, modifier: Modifier = Modifier) {
    CustomNavHost(
        navController = navHostController,
        startDestination = Destination.AppGraph,
        modifier = modifier
    ) {
        appGraph(navHostController)
    }
}
