#!/bin/bash

# Check if mkcert is installed
MKCERT_INSTALLED=$(which mkcert)

# Install mkcert if not installed
if [ -z "$MKCERT_INSTALLED" ]; then 
    echo "Please install mkcert manually from https://github.com/FiloSottile/mkcert/releases"
    exit 1
fi

# Create server.js file
cat <<EOT >> server.mjs
const { createServer } = require('https');
const { parse } = require('url');
const next = require('next');
const fs = require('fs');

const hostname = 'localhost';
const port = 3000;

const dev = process.env.NODE_ENV !== 'production';
const app = next({ dev, hostname, port });
const handle = app.getRequestHandler();

const httpsOptions = {
  key: fs.readFileSync('./localhost-key.pem'),
  cert: fs.readFileSync('./cert.pem'),
};

app.prepare().then(() => {
  createServer(httpsOptions, async (req, res) => {
    try {
      const parsedUrl = parse(req.url, true);
      await handle(req, res, parsedUrl);
    } catch (err) {
      console.error('Error occurred handling', req.url, err);
      res.statusCode = 500;
      res.end('internal server error');
    }
  }).listen(port, (err) => {
    if (err) throw err;
    console.log(\`> Ready on https://\${hostname}:\${port}\`);
  });
});
EOT

# Install mkcert CA
mkcert -install

# Create localhost certificate
mkcert localhost
