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
    param['userId'] = userId;

    var sign = getBackendSignature(param);
    param['sign'] = sign;

    $.ajax({
            url: req_domain+user_service_port+"/user/getUserInfo",
            method: "POST",
            data: JSON.stringify(param),
            headers : {"accessToken":accessToken},
			async: false,
            timeout : 5000,
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
                     window.location.href=req_domain+web_service_port+"/web/login";
				}
            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
            }

        });
}

function setAccountInfo() {
    getUserInfo();
    $(function() {
        var storeInfo = localStorage.getItem('userInfo');
        // console.log(user);
        var user = JSON.parse(storeInfo);
        
        if(null != user && undefined != user){
            // alert(user.userName);
            $('#userName').html(user.userName); 
            
        }else {
            var linkElement = $('<a>').attr('href', req_domain+web_service_port+'/web/login').text('登录/注册');
            $('#accInfo').remove();
            $('.navbar-account').append(linkElement);
            $("#logout").hide();
        }
        
        //退出登录
        $("#logout").click(function () {
            localStorage.removeItem('userId');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('userInfo');
            location.reload();
        });
        
        
    });
}

