import OSS from 'ali-oss'
import co from 'co'

const OSSConfig = {
    uploadHost: 'objective-forum-oss.oss-cn-shenzhen.aliyuncs.com',
    type: 'scs',
    ossParams: {
        region: 'oss-cn-shenzhen',
        success_action_status: '200',
        accessKeyId: '',
        accessKeySecret: '',
        bucket: 'objective-forum-oss',
    }
}

function randomw_string(len) {
    len = len || 32
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'
    var maxPos = chars.length
    var pwd = ''
    for (let i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos))
    }
    return pwd
}

function getID(length) {
    return Number(Math.random().toString().substring(3, length) + Date.now()).toString(36)
}

function uploadOSS(event, dir = 'forum-content/') {
    return new Promise((resolve, reject) => {
        var client = new OSS({
            region: OSSConfig.ossParams.region,
            accessKeyId: OSSConfig.ossParams.accessKeyId,
            accessKeySecret: OSSConfig.ossParams.accessKeySecret,
            bucket: OSSConfig.ossParams.bucket,
        });
        var file = event;
        let randomName = `${dir}adm${randomw_string(4)}${getID(8)}`
        co(function* () {
            var result = yield client.multipartUpload(randomName, file);
            resolve(`https://${OSSConfig.uploadHost}/${result.name}`)
        }).catch((err) => {
            reject(err)
        })
    })
}

export {uploadOSS}