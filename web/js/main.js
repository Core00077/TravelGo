(function(window){
    window.onload=function(){
        scrollBanner();
    };

    //请求数据
    var dataRequest=ajaxRequest("get","/findGoods");
    dataRequest.then(function(responseText){
        var result=JSON.parse(responseText);
        //商品数据
        var itemList=result.data;
        for(let i=0;i<itemList.length;i++){
            var contentIndex1=document.querySelector("#content-index-1 .item");
            //添加商品
            contentIndex1.appendChild(addItem(itemList[i].goodsName,itemList[i].price,itemList[i].img,itemList[i].id));
        }
    }).catch(function(errorText){
        dialog.showDialog("网络错误");
        setTimeout(dialog.closeDialog,1500);
    });

    //登录状态请求
    var stateRequest = ajaxRequest("get", "/findUsername");
    stateRequest.then(function (responseText) {
        var result = JSON.parse(responseText);
        if (result.status === "logined") {
            loginState = "logined";
            setHeader(result.data.username);
        } else if (result.status === "unlogin") {
            loginState = "unlogin";
        }
    }).catch(function (errorText) {});

    //为搜索框添加点击事件
    var searchInput=document.querySelector("#search-form input");
    var searchButton=document.querySelector("#search-form button");
    searchButton.onclick=function(){
        if(searchInput.value===""){
            dialog.showDialog("请输入城市");
            setTimeout(dialog.closeDialog,1000);
        }
        else{
            window.open("search.html?key="+searchInput.value);
        }
    };
})(window);


