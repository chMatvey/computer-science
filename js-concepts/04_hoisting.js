// console.log(sum(1, 2)) // ReferenceError
console.log(a) // undefiend
//console.log(sum1(1, 2)) // sum1 is not a function
// console.log(b) // ReferenceError
// console.log(c) // ReferenceError
console.log(sum2(1, 2))
//console.log(sum3(1, 2)) // sum1 is not a function

const sum = (a, b) => a + b
var sum1 = (a, b) => a + b
var a = 1
const b = 1
let c = 1

function sum2(a, b) {
    return a + b
}

var sum3 = function(a, b) {
    return a + b;
}

console.log(sum1(1, 2))

