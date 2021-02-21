export const getID = (length) => {
    return Number(Math.random().toString().substring(3, length) + Date.now()).toString(36)
}