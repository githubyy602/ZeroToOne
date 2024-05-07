//查询文章
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

//下拉加载更多
let isLoading = false;
let currentPage = 0;
function showMore(){

    // 滚动事件监听
    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 50) {
            loadMore(currentPage,isLoading);
        }
    });

}

// 加载更多内容
function loadMore(currentPage,isLoading) {
    if (isLoading) return;
    isLoading = true;
    currentPage++;

    var param = new Map();
    param['pageIndex'] = currentPage;
    param['pageSize'] = 5;

    var sign = getBackendSignature(param);
    param['sign'] = sign;

    $.ajax({
        url: base_url_business+'/article/getArticleList',
        type: 'POST',
        data: JSON.stringify(param),
        contentType: 'application/json;charset=UTF-8',
        success: function(data) {
            if(data.code == response_status_success){
                // 假设返回的数据是JSON数组，每个元素都有content等属性
                $.each(data.data.list, function(i, item) {
                    // 创建新的元素并添加到contentList中
                    createPostElement(item);
                });

                isLoading = false;
            }else {
                layer.msg("加载失败："+data.message,{time:2000});
            }
        },
        error: function(error) {
            console.error('Error loading more content:', error);
            isLoading = false;
        }
    });
}

// 创建新的帖子元素（根据返回的数据结构调整）
function createPostElement(item) {
    // 这里是创建DOM元素的代码，需要根据实际的数据结构来构建
    let newArea = $('<div>').addClass('post');

    // 添加帖子的头部,包括头像、用户名和时间
    let postHead = $('<div>').addClass('post__head');
    let postImage = $('<a>').attr('href', '#').addClass('post__head-img').append(
        $('<img>').attr('src', null != item.imageUrl && '' != item.imageUrl ? item.imageUrl : 'img/user.svg')
    );
    let postUser = $('<div>').addClass('post__head-title').append(
        $('<h5>').append(
            $('<a>').attr('href', '#').append(
                $('<font>').css('vertical-align','inherit').text(item.userName)
            )
        )
        ,
        $('<p>').append($('<font>').css('vertical-align','inherit').text('2024-01-01'))
    );

    postHead.append(postImage, postUser);
    // console.log("postHead:\n"+postHead.html());

    // 添加帖子的标题
    let postTitle = $('<h2>').addClass('post__title').attr('value',item.id).append(
        $('<font>').css('vertical-align','inherit').text(item.title)
    );

    // 添加帖子的正文
    let postDescription = $('<div>').addClass('post__description').append(
        $('<p>').append(
            $('<font>').css('vertical-align','inherit').text(item.content.substring(0, 100))
        )
    );

    // 添加帖子的统计信息
    let postStats = $('<div>').addClass('post__stats').append(
        $('<div>').append(
            $('<a>').addClass('post__likes').append(
                $('<i>').addClass('icon ion-ios-heart'),
                $('<span>').append(
                    $('<font>').css('vertical-align','inherit').text(0)
                )
            ),
            $('<i>').addClass('icon ion-ios-eye'),
            $('<span>').append(
                $('<font>').css('vertical-align','inherit').text(1)
            )
        )
    );

    newArea.append(postHead,postTitle,postDescription,postStats);
    // console.log("newArea:\n"+newArea.html());
    $('#contentListArea').append(newArea);
}

//
// function showList(data) {
//     // 清空列表容器
//     $('#articleList').empty();
//
//     // 遍历数据，生成列表项并插入到列表容器中
//     for (var i = 0; i < data.length; i++) {
//       var link = document.createElement("a");
//       link.href = req_domain+front_service_port+'/web/detail/'+data[i].id;
//       link.textContent = data[i].title;
//       link.style.color = 'white';
//
//       var listItem = document.createElement("li");
//       listItem.appendChild(link);
//       $('#articleList').append(listItem);
//     }
// }

//创建文章
function createArticle(title,content,coverId) {
    $(function() {
        var param = new Map(); 
        param['title'] = title;
        param['content'] = content;
        param['creator'] = 1;
        if(null != coverId && undefined != coverId && coverId >0 ){
            param['coverImgId'] = coverId;
        }
        
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

//编辑文章
function updateArticle(id,title,content,coverId) {
    $(function() {
        var param = new Map();
        param['id'] = id;
        param['title'] = title;
        param['content'] = content;
        if(null != coverId && undefined != coverId && coverId >0 ){
            param['coverImgId'] = coverId;
        }
        
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

//文章详情上传封面
function uploadCoverImage(){
    $(function () {
        $('#articleIcon').on('change', function() {
            var accessToken = localStorage.getItem('accessToken');
            var userId = localStorage.getItem('userId');
            if(verifyUser() == false){
                return;
            }

            var fileInput = document.getElementById('articleIcon');
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
                        
                        $('#coverImg').attr('src', response.data[0].path);
                        $('#coverInput').val(response.data[0].id);
                    }else {
                        $('#articleIcon').val('');
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