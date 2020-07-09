// one to many
// other name publisher, subscriber, dispatcher, listener

class Subject {
    constructor() {
        this.observers = []
    }

    // in rx js observer - function
    subscribe(observer) {
        this.observers.push(observer)
    }

    unsubscribe(observer) {
        this.observers = this.observers.filter(obs => obs !== observer)
    }

    // emit(changes)
    fire(action) {
        this.observers.forEach(obs => {
            obs.update(action)
        })
    }
}

class Observer {
    constructor(state = 0) {
        this.state = state
        this.initialState = state
    }

    update(action) {
        switch (action.type) {
            case 'INCREMENT': {
                this.state++
                break
            }
            case 'DECREMENT': {
                this.state--
                break
            }
            case 'ADD': {
                this.state += action.payload
                break
            }
            default: {
                this.state = this.initialState
            }
        }
    }
}

const stream$ = new Subject()

const obs1 = new Observer(1)
const obs2 = new Observer(2)

stream$.subscribe(obs1)
stream$.subscribe(obs2)

stream$.fire({type: 'INCREMENT'})
stream$.fire({type: 'INCREMENT'})
stream$.fire({type: 'DECREMENT'})
stream$.fire({type: 'ADD', payload: 5})

console.log(obs1.state)
console.log(obs2.state)

