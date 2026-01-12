/*
 *  Copyright (c) 2024 TeamDev
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

import {PeerServer} from "peer";
import {Command} from 'commander';

// PeerJs implementation of a WebRTC signaling server.
PeerServer({
    port: portFromArgs()
});

/**
 * Extracts the passed `--port` value from the passed arguments.
 *
 * Specifying the port is mandatory. The methods throws without an explicitly
 * passed port.
 *
 * @returns {number}
 */
function portFromArgs() {
    const command = new Command();
    command.option('-p, --port <value>');
    command.parse(process.argv);

    const options = command.opts();
    const port = options.port
    if (port == null) {
        throw new Error('The used port should be explicitly provided: `--port 3000`.');
    }

    return port;
}
