function A() {
    let a = 1

    function B() {
        let b = 2

        function C() {
            let c = 3

            console.log('C:', a, b, c)
        }

        C()
        console.log('B:', a, b)
    }

    B()
    console.log('A:', a)
}

function f1() {
    let value = 'a'

    let f2 = new Function('console.log(value)')

    let f3 = a => { return a + value}

    // console.log(f2('1')) ReferenceError: value is not defined
    console.log(f3('1'))
}

A()
f1()
