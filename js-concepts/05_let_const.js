let a = 5
let b = 7;

{
    let b = a

    {
        let c = a + b
    }

    let d = a + b;
    // let d = a + b + c // ReferenceError: c is not defined

    console.log(d)
}

console.log(a)
console.log(b)

const obj = {}
obj.name = "name"
obj.age = 1
delete obj.age

console.log(obj)
