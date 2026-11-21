package ait.hu.cubbylist.ui.screen.listScreenPage
import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.ui.components.cards.ItemCard
import ait.hu.cubbylist.ui.dialogs.ItemDialog
import ait.hu.cubbylist.ui.components.items.ShopItemObject
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: ListScreenViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    listId: Int
) {
    var showItemDialog by rememberSaveable { mutableStateOf(false) }
    var itemEdit: ShopItem? by remember{mutableStateOf(null)}
    var itemList = viewModel.getAllItems(listId).collectAsState(
        initial = emptyList()
    )
    var listCost = viewModel.getListCost(listId).collectAsState(
        initial = 0.00
    )
    var confirmExpanded by remember {mutableStateOf(false)}
    val list = viewModel.getList(listId).collectAsState(initial = null)
    val snackbarHostState = remember { SnackbarHostState()}

    Scaffold(
        containerColor = colorResource(R.color.cornsilk),
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(130.dp),
                title = {
                    Column {
                        Text(
                            text = list.value?.listName ?: stringResource(R.string.list),
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.cornsilk),
                            fontSize = 32.sp,
                            fontFamily = fontFamily
                        )
                        Text(
                            text = "Total Cost: $${"%.2f".format(listCost.value)}",
                            color = colorResource(R.color.cornsilk),
                            fontSize = 15.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Light

                        )
                    }},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.copperwood)
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (itemList.value.isNotEmpty()) confirmExpanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.deleteItems),
                            modifier = Modifier.size(75.dp),
                            tint = colorResource(R.color.delete_dark)
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier
                            .background(colorResource(R.color.copperwood)),
                        expanded = confirmExpanded,
                        onDismissRequest = { confirmExpanded = false },
                        shape = RoundedCornerShape(20.dp),
                        offset = DpOffset(x = (-10).dp, y=30.dp),
                        tonalElevation = 10.dp,
                        border = BorderStroke(5.dp, colorResource(R.color.sunlit_clay)),
                    ) {
                        DropdownMenuItem(
                            modifier = Modifier
                                .padding(10.dp),
                            text = {
                                Text(
                                    text = stringResource(R.string.confirmDeleteItems),
                                    fontFamily = fontFamily) },
                            onClick = {
                                viewModel.clearItems(listId)
                                confirmExpanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = colorResource(R.color.cornsilk),
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier
                        .height(125.dp)
                        .width(125.dp)
                        .padding(25.dp)
                        .offset(x = 35.dp, y = (15).dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorResource(R.color.copperwood),
                    ),
                    onClick = {
                        onNavigateToHome()
                    }
                ) {
                    Icon(
                        Icons.Default.Home,
                        stringResource(R.string.go_home),
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)

                    )
                }

                Button(
                    modifier = Modifier
                        .height(125.dp)
                        .width(125.dp)
                        .padding(25.dp)
                        .offset(x = (-5).dp, y = (15).dp),
                    contentPadding = PaddingValues(
                        horizontal = 15.dp,
                        vertical = 15.dp
                    ),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.copperwood),
                    ),
                    onClick = {
                        itemEdit = null
                        showItemDialog = true
                    }) {
                    Text(
                        text = "+",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }



        }

    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (itemList.value.isEmpty()) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-50).dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.empty),
                            contentDescription = stringResource(R.string.no_items),
                            modifier= Modifier.size(250.dp)// Optional: set size
                        )
                    }
                    item {
                        Text(
                        stringResource(R.string.empty_items),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.copperwood)
                    ) }

                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(0.dp, 10.dp)
                ) {
                    items(
                        itemList.value,
                        key = { item -> item.id }) { shopItem ->
                        ShopItemObject(
                            shopItem,
                            itemCard = {
                                ItemCard(
                                    it,
                                    onItemDelete = { item -> viewModel.deleteItem(item) },
                                    onItemChecked = { item ->
                                        viewModel.changeItemState(
                                            shopItem, !item.isDone
                                        )
                                    },
                                    onItemEdit = { item ->
                                        itemEdit = item
                                        showItemDialog = true
                                    }
                                )
                            },
                            onRemove = { item -> viewModel.deleteItem(item) }


                        )

                    }
                }
            }
        }
    }

    if (showItemDialog) {
        ItemDialog(
            onItemCreate = { newItem ->
                viewModel.addItem(newItem)
                showItemDialog = false
            },
            onItemEdit = { editedItem ->
                viewModel.editItem(editedItem)
                showItemDialog = false
            },
            onCancel = {showItemDialog = false},
            itemEdit = itemEdit,
            listId = listId,
            snackbarHostState = snackbarHostState
        )
    }
}

