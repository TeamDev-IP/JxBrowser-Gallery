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
