package service

import entity.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

private const val CARDS_FILE = "/cardDeck.png"
private const val STATION_CARDS_FILE = "/cardStation.png"
private const val IMG_HEIGHT = 270
private const val IMG_WIDTH = 270

/**
 * Provides access to the src/main/resources/card_deck.png file that contains all card images
 * in a raster. The returned [BufferedImage] objects of [frontImageFor], [blankImage],
 * and [backImage] are 130x200 pixels.
 */
class CardImageLoader {

    /**
     * The full raster image containing the suits as rows (plus one special row for blank/back)
     * and values as columns (starting with the ace). As the ordering does not correctly reflect
     * the order in which the suits are declared in [CardSuit], mappings via [row] and [column]
     * are required.
     */
    private val image : BufferedImage = ImageIO.read(CardImageLoader::class.java.getResource(CARDS_FILE))

    private val stationsImage : BufferedImage = ImageIO.read(CardImageLoader::class.java.getResource(STATION_CARDS_FILE))

    fun stationImage(color: Color, isConnect: Boolean): BufferedImage {
        return if (isConnect) {
            getImageByCoordinates(stationsImage, color.ordinal, 1)
        } else {
            getImageByCoordinates(stationsImage, color.ordinal, 0)
        }
    }
    /*

    /**
     * Provides the card image for the given [CardSuit] and [CardValue]
     */
    fun frontImageFor(suit: CardSuit, value: CardValue) =
        getImageByCoordinates(value.column, suit.row)


     */

    fun frontImage(tilePos: Int) = getImageByCoordinates(image, (tilePos - 1) % 10, (tilePos - 1) / 10)

    /**
     * Provides a blank (white) card
     */
//    val blankImage : BufferedImage get() = getImageByCoordinates(8, 4)
    /**
     * Provides the back side image of the card deck
     */
    val backImage: BufferedImage get() = getImageByCoordinates(image, 9, 6)

    /**
     * retrieves from the full raster image [image] the corresponding sub-image
     * for the given column [x] and row [y]
     *
     * @param x column in the raster image, starting at 0
     * @param y row in the raster image, starting at 0
     *
     */
    private fun getImageByCoordinates (image: BufferedImage, x: Int, y: Int) : BufferedImage =
        image.getSubimage(
            x * IMG_WIDTH,
            y * IMG_HEIGHT,
            IMG_WIDTH,
            IMG_HEIGHT
        )

}
