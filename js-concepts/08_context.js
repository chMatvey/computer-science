const person = {
    surname: 'Старк',
    knows: function (what, name) {
        console.log(`Ты ${what} знаешь, ${name} ${this.surname}`)
    }
}

const john = {surname: 'Сноу'}

person.knows('все', 'Бран')
person.knows.call(john, 'ничего не', 'Джон')
person.knows.apply(john, ['ничего не', 'Джон'])
person.knows.call(john, ...['ничего не', 'Джон'])
const bound = person.knows.bind(john, 'ничего не', 'Джон')
bound()

function printArgs() {
    // вызов arr.slice() скопирует все элементы из this в новый массив
    const args = [].slice.call(arguments);
    console.log(args.join(', ')); // args - полноценный массив из аргументов
}

printArgs('Привет', 'мой', 'мир'); // Привет, мой, мир

// =========

function Person(name, age) {
    this.name = name
    this.age = age

    console.log(this)
}

const Ivan = new Person('Ivan', 20)

// =========

function logThis() {
    console.log(this)
}

const object = {
    number: 1
}

logThis.apply(object)
logThis.call(object)
logThis.bind(object)()

// ========

const animal = {
    legs: 4,
    logThis: function () {
        console.log(this)
    }
}

animal.logThis()

const cat = {legs: 4}
cat.logThis = animal.logThis.bind(cat)
cat.logThis()

// ========

function Cat(color) {
    this.color = color
    console.log('This', this);
    ( () => console.log('Arrow this', this) )()
}

new Cat('white')
