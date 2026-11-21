package ait.hu.cubbylist.ui.screen.mainScreenPage
import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.ui.components.cards.ListCard
import ait.hu.cubbylist.ui.dialogs.GeneralItemDialog
import ait.hu.cubbylist.ui.dialogs.ListDialog
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToList: (Int) -> Unit
) {
    var showListDialog by rememberSaveable{ mutableStateOf(false)}
    var showItemDialog by rememberSaveable{ mutableStateOf(false)}
    var listEdit: ShopList? by remember {mutableStateOf(null)}
    var itemEdit: ShopItem? by remember {mutableStateOf(null)}
    var buttonExpanded by remember {mutableStateOf(false)}
    var confirmExpanded by remember {mutableStateOf(false)}
    var listCount = viewModel.getAllListCount().collectAsState(
        initial = 0
    )
    var shopLists = viewModel.getAllLists().collectAsState(
        initial = emptyList()
    )
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
                            text = stringResource(R.string.title),
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.cornsilk),
                            fontSize = 32.sp,
                            fontFamily = fontFamily
                        )
                        Text(
                            text = if (listCount.value == 1) {
                                "1 list"
                            } else "${listCount.value} lists",
                            color = colorResource(R.color.cornsilk),
                            fontSize = 15.sp,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Light

                        )
                    }},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.black_forest)
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (shopLists.value.isNotEmpty()) confirmExpanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.deleteLists),
                            modifier = Modifier.size(75.dp),
                            tint = colorResource(R.color.delete_bright)
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier
                            .background(colorResource(R.color.black_forest)),
                        expanded = confirmExpanded,
                        onDismissRequest = { confirmExpanded = false },
                        shape = RoundedCornerShape(20.dp),
                        offset = DpOffset(x = (-10).dp, y=20.dp),
                        border = BorderStroke(5.dp, colorResource(R.color.olive_leaf))
                    ) {
                        DropdownMenuItem(
                            modifier = Modifier
                                .padding(10.dp),
                            text = {
                                Text(
                                    text = stringResource(R.string.confirmDeleteLists),
                                    fontFamily = fontFamily) },
                            onClick = {
                                viewModel.clearLists()
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
            Box {
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
                        containerColor = colorResource(R.color.black_forest),
                    ),
                    onClick = {
                        buttonExpanded = true
                        listEdit = null
                    }) {
                    Text(
                        text = "+",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                DropdownMenu(
                    modifier = Modifier
                        .background(colorResource(R.color.black_forest)),
                    expanded = buttonExpanded,
                    onDismissRequest = { buttonExpanded = false },
                    shape = RoundedCornerShape(20.dp),
                    offset = DpOffset(x = 0.dp, y=15.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.createList),
                                fontFamily = fontFamily) },
                        onClick = {
                            showListDialog = true
                            buttonExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = stringResource(R.string.list)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = stringResource(R.string.enter)
                            )
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = colorResource(R.color.cornsilk),
                            trailingIconColor = colorResource(R.color.olive_leaf),
                            leadingIconColor = colorResource(R.color.olive_leaf)
                        )
                    )
                    DropdownMenuItem(
                        text = { Text(
                            text = stringResource(R.string.addItem),
                            fontFamily = fontFamily)
                               },
                        onClick = {
                            showItemDialog = true
                            buttonExpanded = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CheckBox,
                                contentDescription = stringResource(R.string.item)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = stringResource(R.string.enter)
                            )
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = colorResource(R.color.cornsilk),
                            trailingIconColor = colorResource(R.color.olive_leaf),
                            leadingIconColor = colorResource(R.color.olive_leaf)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            if (shopLists.value.isEmpty()) {
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
                       contentDescription = stringResource(R.string.no_lists),
                       modifier= Modifier.size(250.dp)
                       )
                   }
                    item {
                        Text(
                            stringResource(R.string.empty_list),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(R.color.copperwood)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(0.dp, 10.dp)
                ) {
                    items(shopLists.value) {
                        ListCard(
                            list = it,
                            onNavigateToList = { list ->
                                onNavigateToList(list.id)
                            },
                            onDelete = { shopList ->
                                viewModel.deleteList(shopList)
                            },
                            onListEdit = { list ->
                                listEdit = list
                                showListDialog = true
                            }

                        )
                    }
                }

            }
        }

    }

    if (showListDialog) {
        ListDialog(
            onListCreate = { newList ->
                viewModel.addList(newList)
                showListDialog = false
            },
            onListEdit = { editedList ->
                viewModel.editList(editedList)
                showListDialog = false
            },
            onCancel = {showListDialog = false},
            listEdit = listEdit,
            snackbarHostState = snackbarHostState
        )
    }

    if (showItemDialog) {
        GeneralItemDialog(
            onItemCreate = { newItem ->
                viewModel.addItem(newItem)
                showItemDialog = false
            },
            onCancel = { showItemDialog = false },
            itemEdit = itemEdit,
            shopLists = shopLists.value,
            snackbarHostState = snackbarHostState
        )
    }

}
