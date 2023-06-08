function getUserInfo(){
    
    $.ajax({
            url: "http://localhost:20001/user/getUserInfo",
            method: "POST",
            data: JSON.stringify(jsonObj),
			async: true,
    		dataType: "json",
    		contentType: "application/json;charset=UTF-8",
			beforeSend: function(XHR) {
				XHR.setRequestHeader("Access-Control-Allow-Origin","*");
				// XHR.setRequestHeader("userId", encodeURIComponent(userInfo.userId));
			},
            success: function (data) {
                
                if(data.code == 1000){
                    // 设置全局参数
					localStorage.setItem('userId', data.data.userId);
					localStorage.setItem('accessToken', data.data.accessToken);
                    window.location.href="./index.html";
                }else{
                     alert(data.message);
				}
            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
            }

        });
}


