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
            url: base_url_user+"/getUserInfo",
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
                     window.location.href=req_domain+front_service_port+"/web/login";
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
            var icon = user.imgUrl;
            if(null != icon && '' != icon && undefined != icon){
                $('#userIcon').attr('src',icon);
            }else{
                $('#userIcon').attr('src','../img/user.svg');
            }
            
        }else {
            $(".user").hide();
            $(".header__profile").empty();
            var linkElement = $('<a>').attr('href', 'signin.html').text('登录/注册');
            $(".header__profile").append(linkElement);
            
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

function login() {
     
    event.preventDefault();//阻止页面在提交后刷新
    
    var name = document.forms["loginForm"]["Name"].value;
    if (name == null || name == "") {
        alert("请输入登录名");
        return false;
    }

    var pwd = document.forms["loginForm"]["Password"].value;
    if (pwd == null || pwd == "") {
        alert("请输入密码");
        return false;
    }
    
    var params = new Map();
    // params.set('loginName', 'admin');
    // params.set('password', '123456');
    params.set('loginName', name);
    params.set('password', encryptPwd(pwd));
    var sign = getBackendSignature(params);
    
    params.set('sign',sign);
    var jsonObj = {};
    params.forEach(function(value, key) {
      jsonObj[key] = value;
    });
    
    $.ajax({
        url: req_domain+user_service_port+"/user/login/online",
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
                // window.location.href=req_domain+front_service_port+"/web/index";
                window.location.href= 'index.html';
            }else{
                 // alert(data.message);
                 layer.open({
                      title: '提示',
                      content: data.message
                    });
            }
        },
        error: function (data) {
            console.log("请求错误："+data.statusText);
        }

    });
}	

function register() {
     
    event.preventDefault();//阻止页面在提交后刷新
    
    var name = document.forms["registerForm"]["Username"].value;
    if (name == null || name == "") {
        alert("请输入登录名");
        return false;
    }
    
    var email = document.forms["registerForm"]["Email"].value;
    if (email == null || email == "") {
        alert("请输入邮箱");
        return false;
    }

    var pwd = document.forms["registerForm"]["Password"].value;
    if (pwd == null || pwd == "") {
        alert("请输入密码");
        return false;
    }
    
    var cPwd = document.forms["registerForm"]["Confirm password"].value;
    if(cPwd == null || cPwd == ""){
        alert("请输入确认密码");
        return false;
    }
    
    if(pwd != cPwd){
        alert("两次输入的密码不一致，请确认");
        return false;
    }
    
    var sex = $("input[type='radio']:checked").val();
    
    var params = new Map();
    params.set('userName', "client"+Math.random().toString(18).substr());
    params.set('loginName', name);
    params.set('email', email);
    params.set('loginPassword', encryptPwd(pwd));
    params.set('sex', sex == null ? 1 : sex);
    var sign = getBackendSignature(params);
    
    params.set('sign',sign);
    var jsonObj = {};
    params.forEach(function(value, key) {
      jsonObj[key] = value;
    });
    
    $.ajax({
        url: req_domain+user_service_port+"/user/createUser",
        method: "POST",
        data: JSON.stringify(jsonObj),
        async: true,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        beforeSend: function(XHR) {
            XHR.setRequestHeader("Access-Control-Allow-Origin","*");
        },
        success: function (data) {
            
            if(data.code == 1000){
                // 设置全局参数
                alert("已注册成功，请登录");
                $(document).ready(function() {
                   $('.register-form').hide(); 
                   $('.login-form').show(); 
                });
            }else{
                 alert(data.message);
            }
        },
        error: function (data) {
            console.log("请求错误："+data.statusText);
        }

    });
}	