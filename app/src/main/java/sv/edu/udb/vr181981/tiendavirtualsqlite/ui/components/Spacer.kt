package sv.edu.udb.vr181981.tiendavirtualsqlite.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSpacer(height: Dp = 20.dp) {
    Spacer(modifier = Modifier.height(height))
}