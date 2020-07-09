class Light {
    constructor(color) {
        this.color = color
    }
}

class RedLight extends Light {
    constructor() {
        super('Red');
    }

    sign() {
        return 'Stop'
    }
}

class YellowLight extends Light {
    constructor() {
        super('Yellow');
    }

    sign() {
        return 'Prepare'
    }
}

class GreenLight extends Light {
    constructor() {
        super('Green');
    }

    sign() {
        return 'Go'
    }
}

class TrafficLights {
    constructor() {
        this.states = [
            new RedLight(),
            new YellowLight(),
            new GreenLight()
        ]

        this.current = this.states[0]
    }

    change() {
        const total = this.states.length
        let index = this.states.findIndex(light => light === this.current)

        if (index + 1 < total) {
            this.current = this.states[index + 1]
        } else {
            this.current = this.states[0]
        }
    }

    sign() {
        return this.current.sign()
    }
}

const traffic = new TrafficLights()
console.log(traffic.sign())

traffic.change()
console.log(traffic.sign())

traffic.change()
console.log(traffic.sign())

traffic.change()
console.log(traffic.sign())
