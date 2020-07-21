// replace extend by aggregation or composition

class Server {
    constructor(ip, port) {
        this.ip = ip
        this.port = port
    }

    get url() {
        return `https://${this.ip}:${this.port}`
    }
}

function awsDecorator(server) {
    server.isAws = true
    server.awsInfo = function () {
        return server.url
    }

    return server
}

function azureDecorator(server) {
    server.isAzure = true
    server.port += 500

    return server
}

const s1 = awsDecorator(new Server('12.34.56.78', 8080))
console.log(s1.isAws)
console.log(s1.awsInfo())

const s2 = azureDecorator(new Server('78.56.43.12', 8090))
console.log(s2.isAzure)
console.log(s2.url)
