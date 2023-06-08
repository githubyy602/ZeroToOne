// 全局方法，用于获取后端签名
function getBackendSignature(obj) {
  var map = obj;
  
  // Object.entries(obj).forEach(function(entry) {
  //     var key = entry[0];
  //     var value = entry[1];
  //   console.log(key + ': ' + value);
  //   map.set(key, value);
  // });
  
    var jsonObj = {};
    map.forEach(function(value, key) {
      jsonObj[key] = value;
    });
  
  var jsonString = JSON.stringify(jsonObj);
    console.log(jsonString);
    
    
    
    var filteredString = jsonString.replace(/[^\u4E00-\u9FA5a-zA-Z0-9]/g, '');
    console.log(filteredString);
    
    
    var base64String = btoa(filteredString);
    console.log(base64String);

    var sortedString = Array.from(base64String).sort().join('');
    console.log(sortedString);

    
    var secretKey = '84B2J2hvk2jdnvk4#@#';
    var hmac = CryptoJS.HmacMD5(sortedString, secretKey).toString();
    // console.log(hmac);
    // debugger;
    // // 将 Uint8Array 转换为 Hex 字符串
    // var hexString = byteArrayToHexString(hmac);
    
    return hmac;
}
