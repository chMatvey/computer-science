const first = () => console.log('First')
const second = () => console.log('Second')
const third = () => console.log('Third')

first()
setTimeout(second, 0)
third()


const array = [
    {a: 1, b: 2},
    {a: 2}
];
const reducer = (acc, curr) => acc + 1;

console.log(
    array.reduce(reducer)
);

