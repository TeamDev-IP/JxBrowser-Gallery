package com.teamdev.jxbrowser.gallery.charts;

import com.teamdev.jxbrowser.ui.Bitmap;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;

import static java.awt.image.Raster.createInterleavedRaster;

/**
 * A converter of {@link Bitmap} instances into {@code BufferedImage}.
 *
 * <p>Performs the image conversion "by hand". Alternatively, you may add the
 * "JxBrowser JavaFX"/"JxBrowser Swing"/"JxBrowser SWT" dependency to the project
 * and use the {@code BitmapImage.toToolkitImage()} method. The resulting image
 * will belong to the corresponding UI toolkit.
 */
final class BitmapConverter {

    /**
     * Converts the specified {@link Bitmap} to a {@link BufferedImage}.
     *
     * @param bitmap the {@link Bitmap} to convert
     * @return the {@link BufferedImage} instance
     */
    BufferedImage toBufferedImage(Bitmap bitmap) {
        var size = bitmap.size();
        var width = size.width();
        var height = size.height();
        var raster = createInterleavedRaster(
                0, width, height, width * 4, 4, new int[]{2, 1, 0, 3}, null
        );
        var colorModel = new ComponentColorModel(
                ColorSpace.getInstance(1000), new int[]{8, 8, 8, 8}, true, true, 3, 0
        );
        var dataBuffer = (DataBufferByte) raster.getDataBuffer();
        var pixels = bitmap.pixels();
        System.arraycopy(pixels, 0, dataBuffer.getData(), 0, pixels.length);
        return new BufferedImage(colorModel, raster, true, null);
    }
}