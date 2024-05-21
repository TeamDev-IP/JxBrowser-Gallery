/*
 * Copyright 2000-2022 TeamDev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import express from 'express';
import http from 'http';
import { Server as SocketIO } from "socket.io";
import { Command } from 'commander';
import * as url from 'url';

const app = express();
const httpServer = http.createServer(app);
const io = new SocketIO(httpServer);
const room = 'room';
const rootPath = url.fileURLToPath(new URL('.', import.meta.url));

app.use(express.static('public'));
app.get('/streamer', (req, res) => {
    res.sendFile(rootPath + 'public/streamer.html');
});
app.get('/receiver', (req, res) => {
    res.sendFile(rootPath + 'public/receiver.html');
});

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
    const program = new Command();
    const defaultPort = 3000;

    program
        .option('-p, --port <value>', 'port value');
    program.parse(process.argv);

    const options = program.opts();
    return options.port ? options.port : defaultPort;
}
