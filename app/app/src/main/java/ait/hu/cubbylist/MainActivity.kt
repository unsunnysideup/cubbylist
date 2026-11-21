package ait.hu.cubbylist

import ait.hu.cubbylist.ui.navigation.ListScreenRoute
import ait.hu.cubbylist.ui.navigation.MainScreenRoute
import ait.hu.cubbylist.ui.screen.listScreenPage.ListScreen
import ait.hu.cubbylist.ui.screen.mainScreenPage.MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ait.hu.cubbylist.ui.theme.cubbylistTheme
import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            cubbylistTheme {
                NavGraph(
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController,
        startDestination = MainScreenRoute
    ) {
        composable<MainScreenRoute> {
            MainScreen(
                onNavigateToList = { listId ->
                    navController.navigate(ListScreenRoute(listId))
                }
            )
         }
        composable<ListScreenRoute> { entry ->
            val route = entry.toRoute<ListScreenRoute>()

            ListScreen(
                onNavigateToHome = {
                    navController.navigate(MainScreenRoute)
                },
                listId = route.listId,
            )

        }
}

}