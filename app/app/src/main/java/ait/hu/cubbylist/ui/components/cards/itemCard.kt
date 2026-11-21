package ait.hu.cubbylist.ui.components.cards

import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopItem.ShopItem
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ItemCard(
    item: ShopItem,
    onItemDelete: (ShopItem) -> Unit,
    onItemChecked: (ShopItem) -> Unit,
    onItemEdit: (ShopItem) -> Unit
) {
    var expanded by rememberSaveable {mutableStateOf(false)}

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.black_forest),
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically


            ) {
                Checkbox(
                    checked = item.isDone,
                    onCheckedChange = {
                        onItemChecked(item)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.olive_leaf),
                        uncheckedColor = colorResource(R.color.olive_leaf),
                        checkmarkColor = colorResource(R.color.cornsilk)
                    ),
                    modifier = Modifier.scale(1.5f)

                )
                Image(
                    painter = painterResource(id = item.category.getIcon()),
                    contentDescription = item.category.name,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(5.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                ) {
                    Text(
                        item.itemName,
                        fontFamily = fontFamily,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontSize = 15.sp,
                        color = colorResource(R.color.cornsilk),
                        modifier = Modifier.fillMaxWidth(.8f)
                    )
                    Text(
                        item.description,
                        fontFamily = fontFamily,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 10.sp,
                        color = colorResource(R.color.cornsilk),
                        modifier = Modifier.fillMaxWidth(.8f)
                    )
                    Text(
                        text = "$${"%.2f".format(item.price)}",
                        fontFamily = fontFamily,
                        color = colorResource(R.color.cornsilk),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth(.8f)
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropDown
                        else Icons.Filled.ArrowDropDown,
                        contentDescription = if (expanded) {
                            "Less"
                        } else {
                            "More"
                        },
                        tint = colorResource(R.color.olive_leaf),
                        modifier = Modifier.size(100.dp)
                    )
                }

            }

        }

        if (expanded) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onItemEdit(item)

                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.olive_leaf),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Item",
                        tint = colorResource(R.color.cornsilk)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Edit list",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )

                }

                Button(
                    onClick = {
                        onItemDelete(item)
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.olive_leaf),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 1.dp, 10.dp, 10.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Item",
                        tint = colorResource(R.color.cornsilk)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Delete item",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )

                }
            }
        }

    }

}
