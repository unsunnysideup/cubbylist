package ait.hu.cubbylist.ui.navigation
import kotlinx.serialization.Serializable

@Serializable
object MainScreenRoute
@Serializable
data class ListScreenRoute(
    val listId: Int
)

