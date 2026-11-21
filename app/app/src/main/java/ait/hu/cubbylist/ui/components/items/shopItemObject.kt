package ait.hu.cubbylist.ui.components.items

import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.ui.components.backgrounds.DismissBackground
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopItemObject(
    shopItem: ShopItem,
    itemCard: @Composable (ShopItem) -> Unit,
    modifier: Modifier = Modifier,
    onRemove: (ShopItem) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = { DismissBackground(dismissState)},
        content = {
            itemCard(shopItem)
        },
        onDismiss = {
            onRemove(shopItem)
        }
    )
}