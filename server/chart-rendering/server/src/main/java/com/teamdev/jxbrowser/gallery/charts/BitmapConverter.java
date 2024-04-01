/*
 *  Copyright 2024, TeamDev. All rights reserved.
 *
 *  Redistribution and use in source and/or binary forms, with or without
 *  modification, must retain the above copyright notice and the following
 *  disclaimer.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
