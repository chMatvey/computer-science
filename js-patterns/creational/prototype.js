const car = {
    wheels: 4,

    init() {
        console.log(`${this.wheels} wheels`)
    }
}

const carWithOwner = Object.create(car, {
    owner: {
        value: 'Vladimir'
    }
})

console.log(carWithOwner.__proto__ === car)
carWithOwner.init()
