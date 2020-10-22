import * as $ from 'jquery'

interface Analytic {
    destroy: () => void,
    getClicks: () => number
}

function createAnalytics(): Analytic {
    let counter = 0
    let isDestroyed  = false

    const listener = (): number => counter++

    $(document).on('click', listener)

    return {
        destroy() {
            $(document).off('click', listener)
            isDestroyed = true
        },

        getClicks(): number {
            if (isDestroyed) {
                return -1
            }
            return counter
        }
    }
}

window['analytics'] = createAnalytics()
