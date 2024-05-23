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

import express from 'express';
import http from 'http';
import {Server as SocketIO} from "socket.io";
import {Command} from 'commander';

const app = express();
const httpServer = http.createServer(app);
const io = new SocketIO(httpServer);
const room = 'room';

io.on('connection', (socket) => {
    socket.on('join-receiver', () => {
        socket.join(room);
        socket.on('disconnect', () => {
            socket.to(room).emit('receiver-disconnected');
        });
    });

    socket.on('join-streamer', () => {
        socket.join(room);
        socket.on('disconnect', () => {
            socket.to(room).emit('streamer-disconnected');
        });
    });

    socket.on('screen-sharing-stopped', () => {
        socket.to(room).emit('remove-video');
    });
});

httpServer.listen(getPortValue());

function getPortValue() {
    const command = new Command();
    command.option('-p, --port <value>', 'port value');
    command.parse(process.argv);

    const options = command.opts();
    const port = options.port
    if (port == null) {
        throw new Error('No port provided!');
    }

    return port;
}
