function getUserInfo(){
    //localStorage存储和取值key得用单引号
    var userId = localStorage.getItem('userId');
    // localStorage.setItem('userId', data.data.userId);
    // localStorage.setItem('accessToken', data.data.accessToken);
    var accessToken = localStorage.getItem('accessToken');
    if(null == userId || null == accessToken || undefined == userId || undefined == accessToken){
        console.log("Present user info is null.")
        return;
    }
    
    var param = new Map(); 
    param.set("userId",userId);

    var sign = getBackendSignature(param);
    param.set("userId",userId);
    param = {"userId":userId,"sign":sign};

    $.ajax({
            url: "http://localhost:20001/user/getUserInfo",
            method: "POST",
            data: JSON.stringify(param),
            headers : {"accessToken":accessToken},
			async: false,
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
                        var result = data.data;
                        localStorage.setItem('userInfo',JSON.stringify(result));
                        return result;
                    }{
                        return null;
                    }
                    
                }else{
                     alert("请登录");
                     window.location.href="http://localhost:30000/web/login";
				}
            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
            }

        });
}


