package ait.hu.cubbylist.ui.dialogs
import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopItem.ItemCategory
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.ui.components.spinners.CategorySpinner
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalLocale

@Composable
fun ItemDialog(
    onItemCreate: (ShopItem) -> Unit,
    onItemEdit: (ShopItem) -> Unit,
    itemEdit: ShopItem? = null,
    onCancel: () -> Unit,
    listId: Int,
    snackbarHostState: SnackbarHostState
) {
    var itemName by remember {
        mutableStateOf(
            value = itemEdit?.itemName ?: ""
        )
    }
    var itemDesc by remember {
        mutableStateOf(
            value = itemEdit?.description ?: ""
        )
    }
    var itemPrice by remember {
        mutableDoubleStateOf(
            itemEdit?.price ?: 0.00
        )
    }
    var improperName by remember { mutableStateOf(false) }
    var itemCategory by remember { mutableStateOf(itemEdit?.category ?: ItemCategory.Miscellaneous) }
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
                        focusedBorderColor = Color(0xffFEFAE0),
                        unfocusedBorderColor = Color(0xffBC6C25),
                        focusedTextColor = Color(0xffFEFAE0),
                        unfocusedTextColor = Color(0xffFEFAE0),
                        cursorColor = Color(0xffFEFAE0),
                        errorBorderColor = Color(0xffFA6767),
                        errorCursorColor = Color(0xffFEFAE0),
                        errorTextColor = Color(0xffFEFAE0)
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
                        cursorColor = colorResource(R.color.cornsilk)
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
                        if (itemEdit == null) {
                            onItemCreate(
                                ShopItem(
                                    0,
                                    itemName,
                                    itemPrice,
                                    listId = listId,
                                    category = itemCategory,
                                    description = itemDesc,
                                    isDone = false
                                )
                            )

                        } else {
                            val editedItem = itemEdit.copy(
                                id = itemEdit.id,
                                itemName = itemName,
                                price = itemPrice,
                                category = itemCategory,
                                isDone = itemEdit.isDone,
                                description = itemDesc
                            )
                            onItemEdit(editedItem)
                        }
                        onCancel()

                    }) {
                        Text(
                            text = if (itemEdit == null) stringResource(R.string.add_item) else stringResource(
                                R.string.edit_item
                            ),
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
            modifier = Modifier.offset(0.dp, 275.dp)
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
