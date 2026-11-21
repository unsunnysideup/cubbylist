package ait.hu.cubbylist.ui.components.spinners

import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp


@Composable
fun ListSpinner(
    list: List<ShopList>,
    preselected: ShopList?,
    onSelectionChanged: (myList: ShopList?) -> Unit,
    modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) } // initial value
    OutlinedCard(
        modifier = modifier
            .padding(vertical = 5.dp)
            .clickable {
                expanded = !expanded
            },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.sunlit_clay),
            contentColor = colorResource(R.color.cornsilk)
        ),
        border = BorderStroke(1.dp,colorResource(R.color.copperwood))

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = selected?.listName ?: stringResource(R.string.no_list_selected),
                fontFamily = fontFamily,
                color = colorResource(R.color.cornsilk),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(
                Icons.Outlined.ArrowDropDown, null, modifier =
                    Modifier.padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                offset = DpOffset(x = 0.dp, y = 10.dp),
                onDismissRequest = { expanded = false },
                containerColor = colorResource(R.color.copperwood),
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .heightIn(max = 100.dp)


            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry.listName,
                                fontFamily = fontFamily,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = colorResource(R.color.cornsilk)
                        )
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally),
                        color = colorResource(R.color.sunlit_clay),
                        thickness = 1.dp

                    )
                }
            }
        }
    }
}

