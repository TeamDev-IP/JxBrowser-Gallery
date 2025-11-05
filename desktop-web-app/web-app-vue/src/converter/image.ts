/*
 * Copyright (c) 2025 TeamDev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Converts a binary image (Uint8Array) to a Base64 Data URI string.
 *
 * This function splits the byte array into chunks for performance reasons, converts
 * them to a Base64-encoded string, and prefixes it with the standard `data:image/*;base64,` header.
 *
 * @param contentBytes - The image content as a Uint8Array.
 * @returns A Data URI string that can be directly used in an `<img src="...">` tag.
 *
 * @see https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/Data_URIs — About Data URIs.
 * @see https://developer.mozilla.org/en-US/docs/Web/API/WindowOrWorkerGlobalScope/btoa — MDN: `btoa()` function for Base64 encoding.
 * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Uint8Array — Typed array reference.
 */
export function imageToDataUri(contentBytes: Uint8Array): string {
  const chunkSize = 8192
  const chunks: string[] = []

  for (let i = 0; i < contentBytes.length; i += chunkSize) {
    const chunk = contentBytes.subarray(i, i + chunkSize)
    chunks.push(String.fromCharCode(...chunk))
  }

  const base64String = btoa(chunks.join(''))
  return `data:image/*;base64,${base64String}`
}
