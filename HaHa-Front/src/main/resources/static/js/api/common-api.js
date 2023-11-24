//全局变量
var req_domain = "http://localhost:";

var user_service_port = "20011";
var file_service_port = "20012";
var business_service_port = "20013";
var front_service_port = "30000";

var context_user = "/user";
var context_business = "/business";
var context_file = "/file";

var base_url_user = req_domain+user_service_port+context_user;
var base_url_view = req_domain+front_service_port;
var base_url_business = req_domain+business_service_port+context_business;
var base_url_file = req_domain+file_service_port+context_file;

var response_status_success = 1000;

// 全局方法，用于获取后端签名
function getBackendSignature(obj) {
  
    var jsonString = JSON.stringify(obj);
    
    var filteredString = jsonString.replace(/[^\u4E00-\u9FA5a-zA-Z0-9]/g, '');
    
    var sortedFilterString = Array.from(filteredString).sort().join('');
    
    var base64String = Base64.encode(sortedFilterString);

    var sortedString = Array.from(base64String).sort().join('');
    
    var secretKey = '84B2J2hvk2jdnvk4#@#';
    var hmac = CryptoJS.HmacMD5(sortedString, secretKey).toString();
    
    return hmac;
}

function encryptPwd(obj) {
    var secretKey = '84B2J2hvk2jdnvk4#@#';
    var hmac = CryptoJS.HmacMD5(obj, secretKey).toString();
    
    return hmac;
}

function assembleSignParam(param) {
    var sign = getBackendSignature(param);
    param['sign'] = sign;
    
    return param;
}