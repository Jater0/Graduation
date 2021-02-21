var ffi = require('ffi')
const api = new ffi.Library('./goscript.dll', {
    'loginCheck': ['string', ['string', 'string']]
})

console.log(api.loginCheck("1452606865@qq.com", "582965"));