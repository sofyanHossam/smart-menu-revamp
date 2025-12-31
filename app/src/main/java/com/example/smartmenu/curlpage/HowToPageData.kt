@file:Suppress("MaxLineLength", "UndocumentedPublicProperty")

package com.example.smartmenu.curlpage

/**
 * The model for data for the page.
 *
 * @property title The title to show on the page.
 * @property message The message to show on the page.
 */
data class HowToPageData(
    val title: String,
    val message: String,
    val link :String
) {

    companion object {
        val simpleHowToPages = listOf(
            HowToPageData(
                "Welcome \uD83D\uDC4B",
                "This is a simple demo of the PageCurl. Swipe to the left to turn the page.",
                "https://marketplace.canva.com/EAGEDq-_tZQ/1/0/1035w/canva-grey-and-beige-minimalist-restaurant-menu-hb5BNMWcQS4.jpg"
            ),
            HowToPageData(
                "Forward & backward",
                "Nice, now try another direction to go backward.",
                "https://static.vecteezy.com/system/resources/previews/013/752/438/large_2x/restaurant-menu-card-template-free-vector.jpg"
            ),
            HowToPageData(
                "Taps",
                "You may also just tap in the right half of the screen to go forward and tap on the left one to go backward.",
                "https://th.bing.com/th/id/R.7f4dc25d2d6966b319fa29999a82f364?rik=3X5HmFPBh2BnvQ&pid=ImgRaw&r=0"
            ),
            HowToPageData(
                "End",
                "That is the last page, you cannot go further \uD83D\uDE09",
                "https://img.freepik.com/premium-photo/restaurant-food-restaurant-menu-photos-menu_830198-782.jpg?w=2000"
            )
        )

    }
}
