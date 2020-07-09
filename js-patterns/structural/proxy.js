function networkFetch(url) {
    return `${url} - Response from the server`
}

const cache = new Set()
const proxyFetch = new Proxy(networkFetch, {
    apply(target, thisArg, argArray) {
        const url = argArray[0]
        if (cache.has(url)) {
            return `${url} - Response from the cache`
        } else {
            cache.add(url)
            return Reflect.apply(target, thisArg, argArray)
        }
    }
})

console.log(proxyFetch('angular.io'))
console.log(proxyFetch('react.io'))
console.log(proxyFetch('angular.io'))
