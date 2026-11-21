package ait.hu.cubbylist.ui.dialogs
import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch


@Composable
fun ListDialog(
    onListCreate: (ShopList) -> Unit,
    onListEdit: (ShopList) -> Unit,
    listEdit: ShopList? = null,
    onCancel: () -> Unit,
    snackbarHostState: SnackbarHostState)
{
    var listName by remember { mutableStateOf(
        value = listEdit?.listName ?: ""
    ) }
    var listDesc by remember { mutableStateOf(
        listEdit?.description ?: ""
    ) }
    var improperName by remember {mutableStateOf(false)}
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(6.dp),
            color = colorResource(R.color.olive_leaf)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)

            ) {
                Text(
                    text = if (listEdit == null) stringResource(R.string.new_list) else stringResource(
                        R.string.edit_list
                    ),
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    color = colorResource(R.color.cornsilk)
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    label = { Text(
                        text = stringResource(R.string.new_title),
                        color = colorResource(R.color.cornsilk),
                        fontFamily = fontFamily) },
                    value = listName,
                    onValueChange = { listName = it },
                    isError = improperName,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.cornsilk),
                        unfocusedBorderColor = colorResource(R.color.black_forest),
                        focusedTextColor = colorResource(R.color.cornsilk),
                        unfocusedTextColor = colorResource(R.color.cornsilk),
                        errorTextColor = colorResource(R.color.cornsilk),
                        cursorColor =  colorResource(R.color.cornsilk),
                        errorBorderColor = colorResource(R.color.delete_bright),
                        errorCursorColor = colorResource(R.color.cornsilk)
                    )
                )
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    label = { Text(
                        text = stringResource(R.string.description),
                        color = colorResource(R.color.cornsilk),
                        fontFamily = fontFamily)
                    },
                    value = listDesc,
                    onValueChange = { listDesc = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.cornsilk),
                        unfocusedBorderColor = colorResource(R.color.black_forest),
                        focusedTextColor = colorResource(R.color.cornsilk),
                        unfocusedTextColor = colorResource(R.color.cornsilk),
                        cursorColor =  colorResource(R.color.cornsilk),
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (listName.isBlank()) {
                            improperName = true
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Please enter a list name!",
                                    actionLabel = "Got it!",
                                    duration = SnackbarDuration.Short)
                            }
                            return@TextButton

                        }
                        if (listEdit == null) {
                            onListCreate(
                                ShopList(
                                    0,
                                    listName,
                                    listDesc,
                                    cost = 0.00
                                )
                            )

                        } else {
                            val editedList = listEdit.copy(
                                id = listEdit.id,
                                listName = listName,
                                description = listDesc,
                            )
                            onListEdit(editedList)
                        }
                        onCancel()

                    }) {
                        Text(
                            text = if (listEdit == null) stringResource(R.string.create_list) else stringResource(R.string.edit_list),
                            fontFamily = fontFamily,
                            color = colorResource(R.color.cornsilk),
                            fontWeight = FontWeight.SemiBold)
                    }
                }
            }


        }

        SnackbarHost(
            snackbarHostState,
            modifier = Modifier.offset(0.dp, 160.dp)
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