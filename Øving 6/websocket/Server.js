const net = require('net');

// Simple HTTP server responds with a simple WebSocket client test
const httpServer = net.createServer((connection) => {
  connection.on('data', () => {
    let content = `<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
  </head>
  <body>
    WebSocket test page
    <script>
      let ws = new WebSocket('ws://localhost:3001');
      ws.onmessage = event => alert('Message from server: ' + event.data);
      ws.onopen = () => ws.send('hello');
    </script>
  </body>
</html>
`;
    connection.write('HTTP/1.1 200 OK\r\nContent-Length: ' + content.length + '\r\n\r\n' + content);
  });
});
httpServer.listen(3000, () => {
  console.log('HTTP server listening on port 3000');
});

// Complete WebSocket server
const wsServer = net.createServer((socket) => {
  console.log('Client connected');

  socket.on('data', (data) => {
    console.log('Data received from client: ', data.toString());
    if (isWebSocketHandshake(data)) {
      const acceptHeader = generateAcceptHeader(data);
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

wsServer.on('error', (error) => {
  console.error('Error: ', error);
});

wsServer.listen(3001, () => {
  console.log('WebSocket server listening on port 3001');
});

// Helper functions
function isWebSocketHandshake(data) {
  const header = data.toString();
  if (header.indexOf('Upgrade: websocket') > -1 && header.indexOf('Connection: Upgrade') > -1) {
    return true;
  }
  return false;
}

const crypto = require('crypto');

function generateAcceptHeader(key) {
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

