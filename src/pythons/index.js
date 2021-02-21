var exec = require('child_process').exec
var username = "123"
var password = "582965"
var login = exec('python test.py ' + username + ' ' + password + '', function(error, stdout, stderr){
        if(stdout.length >1){
            console.log('you offer args:',stdout);
        } else {
            console.log('you don\'t offer args');
        }
        if(error) {
            console.info('stderr : '+stderr);
        }
})

module.exports = {login}