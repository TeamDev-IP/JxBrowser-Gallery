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

const RECEIVER_PEER_ID = 'receiver';
const socket = io();

connectToServer();

/**
 * Connects the receiver browser to the WebRTC server.
 */
function connectToServer() {
    const receiverPeer = new Peer(RECEIVER_PEER_ID);
    receiverPeer.on('open', () => {
        socket.emit('join-receiver');
    });

    receiverPeer.on('call', (call) => {
        call.answer();
        call.on('stream', (stream) => {
            addVideoStream(stream);
        });
    });

    socket.on('remove-video', () => {
        removeVideoStream();
    });

    socket.on('streamer-disconnected', () => {
        removeVideoStream();
    });
}

/**
 * Displays the video element with the given stream as the source of the media.
 *
 * @param {MediaStream} stream media stream to be displayed
 */
function addVideoStream(stream) {
    const video = document.querySelector('video');
    video.srcObject = stream;
    video.style.display = 'block';
}

/**
 * Clears the video element and hides it.
 */
function removeVideoStream() {
    const video = document.querySelector('video');
    video.srcObject = null;
    video.style.display = 'none';
}
