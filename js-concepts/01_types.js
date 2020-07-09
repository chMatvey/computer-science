// null, undefined, boolean, number, bigint string, objects, symbol

console.log(typeof null)
console.log(typeof undefined)
console.log(typeof true)
console.log(typeof 1)
console.log(typeof 1n)
console.log(typeof "js")
console.log(typeof {})
console.log(typeof Symbol(1))

console.log(typeof Math)
console.log(typeof function () {})
console.log(typeof NaN)

let variable = "Variable";
if (variable) { // false: "", 0, null, undefined, NaN
    console.log(variable)
}

console.log(Number.POSITIVE_INFINITY / Number.POSITIVE_INFINITY)

console.log(1 + '2')
console.log('' + 1 + 0)
console.log('' - 1)
console.log('5' - 1)
console.log('3' * '1')
console.log(3 + 2 + 'a')
console.log(undefined + 3)
console.log(null + 3)

console.log(false == '')
console.log(false == [])
console.log(false == {})
console.log('' == 0)
console.log('' == null)
console.log(null == 0)

console.log(false == [])
if ([]) { // false: "", 0, null, undefined, NaN
    console.log(variable)
}
