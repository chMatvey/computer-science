// __proto__
// Object.getPrototypeOf()

function Cat(name, color) {
    this.name = name;
    this.color = color;
}

Cat.prototype.voice = function () {
    console.log(`Cat ${this.name} says meow`)
}

const cat = new Cat('Kot', 'white')
cat.voice()

console.log(Cat.prototype)
console.log(cat)
console.log(cat.__proto__ === Cat.prototype)
console.log(cat.constructor)

function Person() {}
Person.prototype.name = 'Kate'
Person.prototype.age = 20

const person = new Person()
person.language = 'English'

console.log('name' in person)
console.log(person.age)

console.log(person.hasOwnProperty('name'))
console.log(person.hasOwnProperty('language'))

// Object.create()

let proto = { year: 2020 }
const myYear = Object.create(proto)

console.log(myYear.year)
console.log(myYear.hasOwnProperty('year'))
console.log(Object.keys(myYear))
console.log(myYear.__proto__)
console.log(myYear.__proto__ === proto)

proto.year = 2021
console.log(myYear.year)
proto = { month: 12 }
console.log(myYear.month) // undefined
console.log(proto)

let newProto = { day: 1 }
const myDay = Object.create(newProto)
newProto.hour = 23
console.log(myDay.__proto__)

let obj = {}
let obj1 = new Object()
console.log(obj)
console.log(obj1)

console.log(Object.__proto__.toString())
console.log(obj.__proto__ === Object.prototype);
console.log(obj.__proto__);
