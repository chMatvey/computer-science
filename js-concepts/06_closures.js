function sayHelloTo(name) {
    const message = "Hello " + name

    return function () {
        console.log(message);
    }
}

const sayHiTo = name => {
    const message = "Hi " + name

    return _ => {
        console.log(message)
    }
}

const f1 = sayHelloTo("JavaScript")
const f2 = sayHiTo("JS")

f1()
f2()
sayHiTo("ECMAScript")()

const printName = (function () {
    function printNameInner(name) {
        console.log(name);
    }

    return printNameInner;
})()

printName("a")

function createMessengerManager() {
    const messengers = ['Facebook', 'VK']

    return {
        getAll: _ => messengers,

        add: messenger => messengers.push(messenger)
    }
}

const manager = createMessengerManager()

manager.add('Telegram')
console.log(manager.getAll().join(' '))

// setTimeout

const fibonacci = [0, 1, 1, 2, 3, 5, 8, 13, 21]

for (var i = 0; i < fibonacci.length; i++) {
    (function (j) {
        setTimeout(function () {
            console.log(`fibonacci[${j}] = ${fibonacci[j]}`)
        }, 1000)
    })(i)
}

for (let i = 0; i < fibonacci.length; i++) {
    setTimeout(function () {
        console.log(`fibonacci[${i}] = ${fibonacci[i]}`)
    }, 1000)
}
