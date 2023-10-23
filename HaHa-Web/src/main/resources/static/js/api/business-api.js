function queryArticles(pageIndex,pageSize) {
    
    $(function() {
        var param = new Map(); 
        // param.set("pageIndex","0");
        // param.set("pageSize","20");
        param['pageIndex'] = pageIndex;
        param['pageSize'] = pageSize;
        var sign = getBackendSignature(param);
        param['sign'] = sign;
        
        $.ajax({
            url: req_domain+business_service_port+"/business/article/getArticleList",
            method: "POST",
            data: JSON.stringify(param),
            // headers : {"accessToken":accessToken},
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
                        showList(data.data.list)
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
    
}

function showList(data) {
    // 清空列表容器
    $('#articleList').empty();

    // 遍历数据，生成列表项并插入到列表容器中
    for (var i = 0; i < data.length; i++) {
      var listItem = '<li>' + data[i].title + '</li>';
      $('#articleList').append(listItem);
    }
}