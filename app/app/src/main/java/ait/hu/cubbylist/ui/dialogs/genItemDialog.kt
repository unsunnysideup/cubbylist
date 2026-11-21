package ait.hu.cubbylist.ui.dialogs
import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopItem.ItemCategory
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.ui.components.spinners.CategorySpinner
import ait.hu.cubbylist.ui.components.spinners.ListSpinner
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@Composable
fun GeneralItemDialog(
    onItemCreate: (ShopItem) -> Unit,
    itemEdit: ShopItem? = null,
    shopLists: List<ShopList>,
    onCancel: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    var itemName by remember {
        mutableStateOf(
            value = itemEdit?.itemName ?: ""
        )
    }
    var itemDesc by remember {
        mutableStateOf(
            itemEdit?.description ?: ""
        )
    }
    var itemPrice by remember {
        mutableDoubleStateOf(
            itemEdit?.price ?: 0.00
        )
    }
    var improperName by remember { mutableStateOf(false) }
    var itemCategory by remember { mutableStateOf(itemEdit?.category ?: ItemCategory.Miscellaneous) }
    var itemListId by remember { mutableIntStateOf(itemEdit?.listId ?: 0) }
    val scope = rememberCoroutineScope()


    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(6.dp),
            color = colorResource(R.color.sunlit_clay)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)

            ) {
                Text(
                    text = if (itemEdit == null) stringResource(R.string.new_item) else stringResource(R.string.edit_item),
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    color = colorResource(R.color.cornsilk)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.item_name),
                            color = colorResource(R.color.cornsilk),
                            fontFamily = fontFamily
                        )
                    },
                    value = itemName,
                    onValueChange = { itemName = it },
                    isError = improperName,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.cornsilk),
                        unfocusedBorderColor = colorResource(R.color.copperwood),
                        focusedTextColor = colorResource(R.color.cornsilk),
                        unfocusedTextColor = colorResource(R.color.cornsilk),
                        cursorColor = colorResource(R.color.cornsilk),
                        errorBorderColor = colorResource(R.color.delete_dark),
                        errorCursorColor = colorResource(R.color.cornsilk),
                        errorTextColor = colorResource(R.color.cornsilk)
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.item_description),
                            color = colorResource(R.color.cornsilk),
                            fontFamily = fontFamily
                        )
                    },
                    value = itemDesc,
                    onValueChange = { itemDesc = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.cornsilk),
                        unfocusedBorderColor = colorResource(R.color.copperwood),
                        focusedTextColor = colorResource(R.color.cornsilk),
                        unfocusedTextColor = colorResource(R.color.cornsilk),
                        cursorColor = colorResource(R.color.cornsilk),
                    )
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.item_price),
                            color = colorResource(R.color.cornsilk),
                            fontFamily = fontFamily
                        )
                    },
                    prefix = {
                        Text(
                            text = "$",
                            color = colorResource(R.color.cornsilk),
                            fontFamily = fontFamily
                        )
                    },
                    value = "%.2f".format(itemPrice),
                    onValueChange = { newPrice ->
                        itemPrice = newPrice.toDoubleOrNull() ?: 0.0},
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.cornsilk),
                        unfocusedBorderColor = colorResource(R.color.copperwood),
                        focusedTextColor = colorResource(R.color.cornsilk),
                        unfocusedTextColor = colorResource(R.color.cornsilk),
                        cursorColor = colorResource(R.color.cornsilk),
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                CategorySpinner(
                    list = ItemCategory.entries.map { it.name },
                    preselected = itemCategory.name.lowercase(LocalLocale.current.platformLocale),
                    onSelectionChanged = {
                        itemCategory = ItemCategory.valueOf(it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ListSpinner(
                    list = shopLists.map { it },
                    preselected = null,
                    onSelectionChanged = {
                        itemListId = it?.id ?: 0
                    },
                    modifier = Modifier.fillMaxWidth()
                )


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {

                        if (itemName.isBlank()) {
                            improperName = true
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Please enter a list name!",
                                    actionLabel = "Got it!",
                                    duration = SnackbarDuration.Short)
                            }
                            return@TextButton
                        }
                        if (itemListId == 0) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Pick an existing list or create a new one!",
                                    actionLabel = "Got it!",
                                    duration = SnackbarDuration.Short)
                            }
                            return@TextButton
                        }
                        onItemCreate(
                            ShopItem(
                                0,
                                itemName,
                                itemPrice,
                                listId = itemListId,
                                category = itemCategory,
                                isDone = false,
                                description = itemDesc
                            )
                        )
                        onCancel()

                    }) {
                        Text(
                            text = stringResource(R.string.add_item),
                            fontFamily = fontFamily,
                            color = colorResource(R.color.cornsilk),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        SnackbarHost(
            snackbarHostState,
            modifier = Modifier.offset(0.dp, 315.dp)
        ) {
            Snackbar(
                it,
                containerColor = colorResource(R.color.delete_dark),
                contentColor = colorResource(R.color.cornsilk),
                shape = RoundedCornerShape(10.dp),
                actionColor = colorResource(R.color.cornsilk)

            )
        }




    }

}
