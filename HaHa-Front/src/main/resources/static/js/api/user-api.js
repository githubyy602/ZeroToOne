/**
 * 获取用户信息
 */
function getUserInfo(){
    //localStorage存储和取值key得用单引号
    var userId = localStorage.getItem('userId');
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
                        return true;
                        
                    }else{
                        localStorage.removeItem('userId');
                        localStorage.removeItem('accessToken');
                        localStorage.removeItem('userInfo');
                    }
                    
                }else if(data.code == 2002 || data.code == 2003){
                    layer.msg('登录信息已失效',{time:2000});
                    localStorage.removeItem('userId');
                    localStorage.removeItem('accessToken');
                    localStorage.removeItem('userInfo');
                }
                return true;
            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
                return true;
            }

        });
}

/**
 * 设置界面的用户信息
 */
function setAccountInfo() {
    
    $(function() {
        var storeInfo = localStorage.getItem('userInfo');
        // console.log(user);
        var user = JSON.parse(storeInfo);
        
        if(null != user && undefined != user){
            $(".user").show();
            $('#userName').html(user.userName);
            $('#userNameLeft').html(user.userName);
            $('#articleNum').html(user.anum);
            $('#loginNum').html(user.lnum);

            var icon = user.imgUrl;
            var title = user.userTitle;
            if(null != icon && '' != icon && undefined != icon){
                if(icon.indexOf("http") == -1){
                    icon = base_url_file + Base64.decode(icon);
                }
                
                $('#userIcon').attr('src',icon);
                $('#userLeftImage').attr('src',icon);
                $('#userImage').attr('src',icon);
                
            }else{
                $('#userIcon').attr('src','../img/user.svg');
                $('#userLeftImage').attr('src','../img/user.svg');
            }
            
            if(null != title && '' != title && undefined != title){
                $('#userTitleLeft').html(title);
            }else{
                $('#userTitleLeft').html('无敌开发工程师');
            }
            
            //detail
            $('#slogin').html(user.loginName);
            $('#sname').html(user.userName);
            $('#semail').html(user.email);
            $('#sphone').html(user.phone);
            if(user.sex == 1){
                $('#sex').html("男");
            }else{
                $('#sex').html("女");
            }
            
        }else {
            $(".user").hide();
            $(".header__profile").empty();
            var linkElement = $('<a>').attr('href', 'signin.html').css('color','white').text('登录/注册');
            $(".header__profile").append(linkElement);
            
        }
        
    });
}

function verifyUser() {
    var storeInfo = localStorage.getItem('userInfo');
    var user = JSON.parse(storeInfo);
    var access = false;
    
    if(null != user && undefined != user){
        //token校验
        var accessToken = localStorage.getItem('accessToken');
        var userId = localStorage.getItem('userId');
    
        if(null != accessToken && undefined != accessToken && null != userId && undefined != userId){
            var param = new Map(); 
            param['userId'] = userId;
            
            $.ajax({
            url: base_url_user+"/checkAuth",
            method: "POST",
            data:JSON.stringify(param),
            headers : {"accessToken":accessToken},
			async: false,
            timeout : 5000,
    		dataType: "json",
    		contentType: "application/json;charset=UTF-8",
			beforeSend: function(XHR) {
                XHR.withCredentials = true;
			},
            success: function (data) {
                
                if(data.code == 1000){
                    
                    access = true;
                    setAccountInfo();

                }else {
                   access = false;
                }

            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
                return false;
            }

            });

        }else{
            access = false;
        }

    }else {
        access = false;
    }
    
    if(access == false){
        //未登录
        layer.msg('请登录后操作',{time : 2000},function(){
            localStorage.removeItem('userId');
            localStorage.removeItem('accessToken');
            localStorage.removeItem('userInfo');
            window.location.href= 'index.html';
        });
    }
    return access;
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
        url: base_url_user+"/login/online",
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
                //获取用户信息
                getUserInfo();
                window.location.href= 'index.html';
            }else{
                 // alert(data.message);
                 layer.open({
                      title: '提示',
                      closeBtn :2,
                      anim:1,
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
        layer.open({
          title: '提示',
          time:3000,
          content: '请输入登录名'
        });
        return false;
    }
    
    var email = document.forms["registerForm"]["Email"].value;
    if (email == null || email == "") {
        layer.open({
          title: '提示',
          time:3000,
          content: '请输入邮箱'
        });
        return false;
    }

    var pwd = document.forms["registerForm"]["Password"].value;
    if (pwd == null || pwd == "") {
        layer.open({
          title: '提示',
          time:3000,
          content: '请输入密码'
        });
        return false;
    }
    
    var cPwd = document.forms["registerForm"]["Confirm password"].value;
    if(cPwd == null || cPwd == ""){
        layer.open({
          title: '提示',
          time:3000,
          content: '请输入确认密码'
        });
        return false;
    }
    
    if(pwd != cPwd){
        layer.open({
          title: '提示',
          time:3000,
          content: '两次输入的密码不一致，请重新输入'
        });
        return false;
    }
    
    
    var params = new Map();
    params.set('userName', "user."+Math.random().toString(36).substr(2,5));
    params.set('loginName', name);
    params.set('email', email);
    params.set('loginPassword', encryptPwd(pwd));
    var sign = getBackendSignature(params);
    
    params.set('sign',sign);
    var jsonObj = {};
    params.forEach(function(value, key) {
      jsonObj[key] = value;
    });
    
    $.ajax({
        url: base_url_user+"/createUser",
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
                layer.open({
                      title: '提示',
                      closeBtn :2,
                      time:3000,
                      anim:1,
                      content: data.message+'(3秒后自动跳转登录)',
                      cancel: function(index, layero){ 
                           window.location.href= 'signin.html';
                        },
                      end: function(){
                           window.location.href= 'signin.html';
                        }
                    });
            }else{
                 layer.open({
                      title: '提示',
                      closeBtn :2,
                      anim:1,
                      content: data.message
                    });
            }
        },
        error: function (data) {
            console.log("请求错误："+data.statusText);
        }

    });
}

function logout() {
    
    var userId = localStorage.getItem('userId');
    var accessToken = localStorage.getItem('accessToken');
    if(null == userId || null == accessToken ){
        localStorage.removeItem('userInfo');
        window.location.href= 'index.html';
        return;
    }
    
    var param = new Map(); 
    param['userId'] = userId;
    
    param = assembleSignParam(param);
    
    $.ajax({
        url: base_url_user+"/login/out",
        method: "POST",
        data:JSON.stringify(param),
        headers : {"accessToken":accessToken},
        async: false,
        timeout : 5000,
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        beforeSend: function(XHR) {
            XHR.withCredentials = true;
        },
        success: function (data) {
            
            if(data.code == 1000){
                localStorage.removeItem('userId');
                localStorage.removeItem('accessToken');
                localStorage.removeItem('userInfo');
                window.location.href= 'index.html';
            }else {
               layer.msg(data.message,{time:2000});
            }
            
        },
        error: function (data) {
            console.log("请求错误："+data.statusText);
            return true;
        }

    });
    
    
}

function userDetailUpload() {
    $(document).ready(function() {
      $('#uploadInput').on('change', function() {
        var accessToken = localStorage.getItem('accessToken');
        var userId = localStorage.getItem('userId');
        if(verifyUser() == false){
            return;
        }
          
        var fileInput = document.getElementById('uploadInput');
        var files = fileInput.files;
        if(null == files || undefined == files){
            return;
        }
        
        var formData = new FormData();
        formData.append("fileList", files[0]);
        formData.append('userId',userId);
          
        $.ajax({
          url: base_url_file+'/upload', // 替换为你的后端接口地址
          type: 'POST',
          data: formData,
          headers : {"accessToken":accessToken},
          cache: false,
          processData: false,   // 告诉jquery要传输data对象
          contentType: false,   // 告诉jquery不需要增加请求头对于contentType的设置,
          beforeSend: function(XHR) {
            XHR.withCredentials = true;
          },
          success: function(response) {
            // 处理后端返回的响应
            var code = response.code;
            if(code == response_status_success){
                 // $('#userLeftImage').attr('src', base_url_file+Base64.decode(response.data[0].path));
                $('#userLeftImage').get(0).src = base_url_file+Base64.decode(response.data[0].path)
                 $('#fileInput').val(response.data[0].id);
            }else {
                $('#uploadInput').val('');
                layer.msg('上传失败：'+response.message,{time:2000});
            }
          },
          error: function(xhr, status, error) {
            // 处理错误
            layer.msg('上传失败！');
            $('#uploadInput').val('');
          }
        });
      });
    });
}

function userDetailUpdate() {
    $(document).ready(function() {
       var accessToken = localStorage.getItem('accessToken');
       var userId = localStorage.getItem('userId');
       if(verifyUser() == false){
            return;
        }
        
       var loginName = $('#loginName').val();
       var email = $('#email').val();
       var name = $('#name').val();
       var phone = $('#phone').val();
       var imgId = $('#fileInput').val();
       var sex = $('input[name="sex"]:checked').val();
       
       var param = new Map(); 
       param['loginName'] = loginName;
       param['email'] = email;
       param['userName'] = name;
       param['phone'] = phone;
       param['imgId'] = imgId;
       param['sex'] = sex;
       param['userId'] = userId;
       
       if( (null == loginName || loginName == "" )
           && (null == email ||  email == "")
           && (null == name || name == "")
           && (null == phone ||  phone == "")
           && (null == imgId || imgId == 0 || imgId == "" ) ){
           layer.msg('请填写要更新的用户信息',{
                        time:2000
                    });
           return;
       }
       
       
       var sign = getBackendSignature(param);
        param['sign'] = sign;
    
        $.ajax({
                url: base_url_user+"/updateUserInfo",
                method: "POST",
                data: JSON.stringify(param),
                headers : {"accessToken":accessToken},
                async: false,
                timeout : 5000,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                beforeSend: function(XHR) {
                    XHR.withCredentials = true;
                },
                success: function (data) {
                    
                    if(data.code == 1000){
                        if(null != data.data && '' != data.data){
                            layer.msg('用户信息更新成功',{time:2000},function () {
                                getUserInfo();
                                location.reload();    
                            });
                            return;
                        }
                    }
                    
                    layer.msg('修改用户信息失败',{
                        time:2000
                    });
                },
                error: function (data) {
                    console.log("请求错误："+data.statusText);
                    return true;
                }
    
            });
    });
}

///updateUserPwd
function userModifyPwd() {
    $(document).ready(function() {
       var accessToken = localStorage.getItem('accessToken');
       var userId = localStorage.getItem('userId');
       if(verifyUser() == false){
            return;
       }
        
       var oldpass = $('#oldpass').val();
       if(null == oldpass || '' == oldpass){
           layer.msg('请填写原密码',{
                        time:2000
                    });
           return;
       }
       
       var newpass = $('#newpass').val();
       if(null == newpass || '' == newpass){
           layer.msg('请填写新密码',{
                        time:2000
                    });
           return;
       }
       
       if(newpass == oldpass){
           layer.msg('原密码与新密码一样',{
                        time:2000
                    });
           return;
       }
       
       var confirmpass = $('#confirmpass').val();
       if(null == confirmpass || '' == confirmpass){
           layer.msg('请填写确认密码',{
                        time:2000
                    });
           return;
       }
       
       if(newpass != confirmpass){
           layer.msg('新密码与确认密码不一致',{
                        time:2000
                    });
           return;
       }
       
       var param = new Map(); 
       param['pwd'] = encryptPwd(oldpass);
       param['newPwd'] = encryptPwd(newpass);
       param['userId'] = userId;
       
       var sign = getBackendSignature(param);
        param['sign'] = sign;
    
        $.ajax({
                url: base_url_user+"/updateUserPwd",
                method: "POST",
                data: JSON.stringify(param),
                headers : {"accessToken":accessToken},
                async: false,
                timeout : 5000,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                beforeSend: function(XHR) {
                    XHR.withCredentials = true;
                },
                success: function (data) {
                    
                    if(data.code == 1000){
                        layer.msg('密码修改成功,请重新登录',{
                            time:2000
                        },function () {
                            logout();
                        });
                    }else{
                        layer.msg(data.message,{
                            time:2000
                        });
                    }
                    
                },
                error: function (data) {
                    console.log("请求错误："+data.statusText);
                    return true;
                }
    
            });
    });
}

$(function() {
    
    //退出登录
    $("#logout").click(function () {
        logout();
    });
    
});

