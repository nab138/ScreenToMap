const screenshot = require('screenshot-desktop')
const sharp = require('sharp')
const WebSocket = require('ws')
const wss = new WebSocket.Server({ port: 8080 })
let interval;
wss.on('connection', function connection(ws) {
    interval = setInterval(async () => {
        ws.send("hb")
    }, 5000)
    ws.on('message', async function incoming(message) {
        console.log(message.toString())
        if(message.toString().toLowerCase() == 'f'){
            ws.send(await ss())
        } else if(message.toString().toLowerCase() == 'i'){
            clearInterval(interval)
            ws.send(await ss())
        }
    })
})
async function ss(){
    return new Promise((resolve, reject) => {
    screenshot().then((img) => {
        sharp(img)
        .resize(384, 216)
        .png()
        .toBuffer()
        .then(async (data) => {
            resolve(data.toString('base64'))
        })
        .catch((err) => {
            console.error(err)
            reject(err)
        });
    }).catch((err) => {
        console.error(err)
        reject(err)
    })
})
}