function getUserInfo(){
    
    var userId = localStorage.getItem('userId');
    // localStorage.setItem('userId', data.data.userId);
    // localStorage.setItem('accessToken', data.data.accessToken);
    var accessToken = localStorage.getItem('accessToken');
    var param = new Map(); 
    param.set("userId",userId);
    debugger;
    var sign = getBackendSignature(param);
    param.set("userId",userId);
    param = {"userId":userId,"sign":sign};
    debugger;
    $.ajax({
            url: "http://localhost:20001/user/getUserInfo",
            method: "POST",
            data: JSON.stringify(param),
            headers : {"accessToken":accessToken},
			async: true,
    		dataType: "json",
    		contentType: "application/json;charset=UTF-8",
			beforeSend: function(XHR) {
				XHR.setRequestHeader("Access-Control-Allow-Origin","*");
				// XHR.setRequestHeader("userId", encodeURIComponent(userInfo.userId));
			},
            success: function (data) {
                
                if(data.code == 1000){
                    if(null != data.data && '' != data.data){
                        console.log(data.data);
                        var obj = JSON.parse(data.data);
                        return obj;
                    }{
                        return null;
                    }
                    
                }else{
                     alert("请登录");
				}
            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
            }

        });
}


