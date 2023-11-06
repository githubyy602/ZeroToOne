//全局变量
var req_domain = "http://localhost:";
var user_service_port = "20011";
var file_service_port = "20012";
var business_service_port = "20013";
var web_service_port = "30000";

// 全局方法，用于获取后端签名
function getBackendSignature(obj) {
  
    var jsonString = JSON.stringify(obj);
    // console.log(jsonString);
    
    var filteredString = jsonString.replace(/[^\u4E00-\u9FA5a-zA-Z0-9]/g, '');
    // console.log(filteredString);
    
    
    var base64String = btoa(filteredString);
    // console.log(base64String);

    var sortedString = Array.from(base64String).sort().join('');
    // console.log(sortedString);

    
    var secretKey = '84B2J2hvk2jdnvk4#@#';
    var hmac = CryptoJS.HmacMD5(sortedString, secretKey).toString();
    
    return hmac;
}

function encryptPwd(obj) {
    var secretKey = '84B2J2hvk2jdnvk4#@#';
    var hmac = CryptoJS.HmacMD5(obj, secretKey).toString();
    
    return hmac;
}
