package ait.hu.cubbylist.ui.components.cards

import ait.hu.cubbylist.R
import ait.hu.cubbylist.data.shopList.ShopList
import ait.hu.cubbylist.ui.theme.fontFamily
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListCard(
    list: ShopList,
    onNavigateToList: (ShopList) -> Unit,
    onDelete: (ShopList) -> Unit,
    onListEdit: (ShopList?) -> Unit
) {
    var expanded by rememberSaveable {mutableStateOf(false)}

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.copperwood),
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .clickable{
                onNavigateToList(list)
            }
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = list.listName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color =colorResource(R.color.cornsilk),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Text(
                    text = list.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 15.sp,
                    color = colorResource(R.color.cornsilk),
                    modifier = Modifier.fillMaxWidth(0.8f)
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
                    tint = colorResource(R.color.sunlit_clay),
                    modifier = Modifier.size(100.dp)
                )
            }

        }

        if (expanded) {
            Column(
                modifier = Modifier.
                fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button (
                    onClick = {
                        onListEdit(list)

                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.sunlit_clay),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp, 10.dp, 1.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit list",
                        tint = colorResource(R.color.cornsilk)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text="Edit list",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp)

                }

                Button(
                    onClick = {
                        onDelete(list)
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.sunlit_clay),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 1.dp, 10.dp, 10.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete list",
                        tint = colorResource(R.color.delete_bright)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text="Delete list",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )

                }
            }
        }


    }

}

