package dev.alexmester.ui.components.list_card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun rememberLaskArticleCardPainter(
    backgroundColor: Color,
    iconTint: Color,
    icon: ImageVector = Icons.Default.Image,
): Painter {
    val density = LocalDensity.current
    val iconPainter = rememberVectorPainter(icon)

    return remember(backgroundColor, iconTint) {
        object : Painter() {
            override val intrinsicSize = Size.Unspecified

            override fun DrawScope.onDraw() {

                drawRect(color = backgroundColor)

                val iconSizePx = with(density) { 28.dp.toPx() }
                val offsetX = (size.width - iconSizePx) / 2f
                val offsetY = (size.height - iconSizePx) / 2f

                translate(left = offsetX, top = offsetY) {
                    with(iconPainter) {
                        draw(
                            size = Size(iconSizePx, iconSizePx),
                            colorFilter = ColorFilter.tint(iconTint),
                        )
                    }
                }
            }
        }
    }
}