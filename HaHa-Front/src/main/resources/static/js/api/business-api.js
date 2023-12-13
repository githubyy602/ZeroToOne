function queryArticles(pageIndex,pageSize) {
    var count = 20;
    $(function() {
        var param = new Map(); 
        // param.set("pageIndex","0");
        // param.set("pageSize","20");
        param['pageIndex'] = pageIndex;
        param['pageSize'] = pageSize;
        var sign = getBackendSignature(param);
        param['sign'] = sign;
        
        $.ajax({
            url: base_url_business+"/article/getArticleList",
            method: "POST",
            data: JSON.stringify(param),
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
                        //分页
                        showList(data.data.list);
                        count = data.data.list.length;
                    }else{
                        $('#articleList').append('暂未内容！');
                    }
                    
                }else{
                     $('#articleList').append('暂未内容！');
				}
            },
            error: function (data) {
                console.log("请求错误："+data.statusText);
            }

        });
        
    });
    
    return count;
}

function showList(data) {
    // 清空列表容器
    $('#articleList').empty();

    // 遍历数据，生成列表项并插入到列表容器中
    for (var i = 0; i < data.length; i++) {
      var link = document.createElement("a");
      link.href = req_domain+front_service_port+'/web/detail/'+data[i].id;
      link.textContent = data[i].title;
      link.style.color = 'white';
      
      var listItem = document.createElement("li");
      listItem.appendChild(link);
      $('#articleList').append(listItem);
    }
}

function createArticle(title,content) {
    $(function() {
        var param = new Map(); 
        param['title'] = title;
        param['content'] = content;
        param['creator'] = 1;
        var sign = getBackendSignature(param);
        param['sign'] = sign;
        
        $.ajax({
            url: base_url_business+"/article/createArticle",
            method: "POST",
            data: JSON.stringify(param),
			async: false,
            timeout : 5000,
    		dataType: "json",
    		contentType: "application/json;charset=UTF-8",
			beforeSend: function(XHR) {
				XHR.setRequestHeader("Access-Control-Allow-Origin","*");
				// XHR.setRequestHeader("userId", encodeURIComponent(userInfo.userId));
			},
            success: function (data) {
                if(data.code == response_status_success){
                    if(null != data.data && '' != data.data){
                        layer.msg('提交成功',{time:2000});
                    }
                    
                }else{
                     layer.msg('提交内容失败：'+data.message,{time:2000});
				}
            },
            error: function (data) {
                // console.log("请求错误："+data.statusText);
                layer.msg('请求异常：'+data.statusText);
            }

        });
        
    });
}


function updateArticle(id,title,content) {
    $(function() {
        var param = new Map();
        param['id'] = id;
        param['title'] = title;
        param['content'] = content;
        var sign = getBackendSignature(param);
        param['sign'] = sign;
        
        $.ajax({
            url: base_url_business+"/article/updateArticle",
            method: "POST",
            data: JSON.stringify(param),
			async: false,
            timeout : 5000,
    		dataType: "json",
    		contentType: "application/json;charset=UTF-8",
			beforeSend: function(XHR) {
				XHR.setRequestHeader("Access-Control-Allow-Origin","*");
				// XHR.setRequestHeader("userId", encodeURIComponent(userInfo.userId));
			},
            success: function (data) {
                if(data.code == response_status_success){
                    if(null != data.data && '' != data.data){
                        layer.msg('更新成功',{time:2000});
                    }
                    
                }else{
                     layer.msg('更新内容失败：'+data.message,{time:2000});
				}
            },
            error: function (data) {
                // console.log("请求错误："+data.statusText);
                layer.msg('请求异常：'+data.statusText);
            }

        });
        
    });
}