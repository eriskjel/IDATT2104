// Complete WebSocket server
const net = require('net');
const wsServer = net.createServer((socket) => {
    console.log('Client connected');

    let buffer = Buffer.from('');

    socket.on('data', (data) => {
        console.log('Data received from client: ', data.toString());

        buffer = Buffer.concat([buffer, data]);

        if (isWebSocketHandshake(buffer)) {
            const acceptHeader = generateAcceptHeader(buffer.toString());
            socket.write(acceptHeader);
        } else {
            console.log('Invalid WebSocket handshake');
            socket.end();
        }
    });

    socket.on('end', () => {
        console.log('Client disconnected');
    });
});

// Helper functions
function isWebSocketHandshake(data) {
    const header = data.toString();
    if (header.indexOf('Upgrade: websocket') > -1 && header.indexOf('Connection: Upgrade') > -1) {
        return true;
    }
    return false;
}

function generateAcceptHeader(request) {
    const key = request.match(/Sec-WebSocket-Key: (.+)/)[1];
    const sha1 = crypto.createHash('sha1');
    sha1.update(key + '258EAFA5-E914-47DA-95CA-C5AB0DC85B11');
    const acceptKey = sha1.digest('base64');
    const responseHeaders = [
        'HTTP/1.1 101 Switching Protocols',
        'Connection: Upgrade',
        'Upgrade: websocket',
        `Sec-WebSocket-Accept: ${acceptKey}`,
        '\r\n'
    ].join('\r\n');
    return responseHeaders;
}
