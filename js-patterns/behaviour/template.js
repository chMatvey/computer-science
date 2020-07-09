class Employee {
    constructor(name, salary) {
        this.name = name
        this.salary = salary
    }

    responsibilities() {
    }

    work() {
        return `${this.name} do ${this.responsibilities()}`
    }

    getPaid() {
        return `${this.name} have salary ${this.salary}`
    }
}

class Developer extends Employee {
    constructor(name, salary) {
        super(name, salary)
    }

    responsibilities() {
        return `${this.name} do program`
    }
}

class QA extends Employee {
    constructor(name, salary) {
        super(name, salary);
    }

    responsibilities() {
        return `${this.name} test program`
    }
}

const dev = new Developer('Matvey', 100e3)
console.log(dev.responsibilities())
console.log(dev.getPaid())

const qa = new QA('Svetlana', 90e3)
console.log(qa.responsibilities())
console.log(qa.getPaid())
