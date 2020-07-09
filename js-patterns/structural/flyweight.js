class Car {
    constructor(name, model, price) {
        this.name = name
        this.model = model
        this.price = price
    }
}

class Model {
    constructor(name) {
        this.name = name
    }
}

class CarFactory {
    constructor() {
        this.models = []
        this.cars = []
    }

    create(name, modelName, price) {
        const candidate = this.getCar(name)
        if (candidate) {
            return candidate
        }

        let model = this.models.find(m => m.name === modelName)
        if (!model) {
            model = new Model(modelName)
            this.models.push(model)
        }

        const newCar = new Car(name, model, price)
        this.cars.push(newCar)
        return newCar
    }

    getCar(name) {
        return this.cars.find(car => car.name === name)
    }
}

const carFactory = new CarFactory()

const bmwX5 = carFactory.create('X5', 'bmw', 85000)
const mercedesE200 = carFactory.create('E200', 'mercedes', 50000)
const bmwX6 = carFactory.create('X6', 'bmw', 95000)

console.log(bmwX5)
console.log(mercedesE200)
console.log(bmwX6)
