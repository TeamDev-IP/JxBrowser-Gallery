/*
 *  Copyright 2026, TeamDev
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.teamdev.jxbrowser.gallery.charts;

import com.teamdev.jxbrowser.ui.Bitmap;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;

import static java.awt.image.Raster.createInterleavedRaster;

/**
 * A converter of {@link Bitmap} instances into {@link BufferedImage}.
 *
 * <p>Performs relatively complex image conversion "by hand". Alternatively, you
 * may add the "JxBrowser JavaFX"/"JxBrowser Swing"/"JxBrowser SWT" dependency
 * to the project and use the {@code BitmapImage.toToolkitImage()} method.
 * The resulting image will belong to the corresponding UI toolkit.
 */
final class BitmapConverter {

    /**
     * Converts the specified {@code bitmap} into a {@link BufferedImage} instance.
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
