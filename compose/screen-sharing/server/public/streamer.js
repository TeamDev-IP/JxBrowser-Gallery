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

const STREAMER_PEER_ID = 'streamer';
const RECEIVER_PEER_ID = 'receiver';

const socket = io();

let streamerPeer;
let connection;

connectToServer();

/**
 * Connects the streamer browser to the WebRTC server.
 */
function connectToServer() {
    streamerPeer = new Peer(STREAMER_PEER_ID);
    streamerPeer.on('open', () => {
        socket.emit('join-streamer');
    });

    socket.on('receiver-disconnected', () => {
        if (connection) {
            connection.close();
        }
    });
}

/**
 * Starts a screen sharing session between the streamer and the receiver.
 */
function startScreenSharing() {
    navigator.mediaDevices.getDisplayMedia({
        video: {
            cursor: 'always'
        },
        audio: {
            echoCancellation: true,
            noiseSuppression: true
        }
    }).then(stream => {
        if (streamerPeer) {
            connection = streamerPeer.call(RECEIVER_PEER_ID, stream);
        }
        stream.getVideoTracks()[0].onended = closeRemoteSharing;
    });
}

/**
 * Notifies the server that screen sharing is stopped.
 */
function closeRemoteSharing() {
    socket.emit('screen-sharing-stopped');
    connection.close();
}
