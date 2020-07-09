function Cat(name, color) {
    this.name = name;
    this.color = color;
}

const cat = new Cat('Котэ', 'black')
console.log(cat)

function customNew(constructor, ...args) {
    const obj = {}
    Object.setPrototypeOf(obj, constructor.prototype)
    constructor.apply(obj, args)
    return obj
}

const customCat = customNew(Cat, 'Котэ', 'black')
console.log(customCat)


