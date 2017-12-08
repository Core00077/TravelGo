//搜索请求
function requestSearch(key){
    var data="city="+key;

    //搜索结果请求
    var searchRequest=ajaxRequest("post","/findGoodByCity",data);
    searchRequest.then(function(responseText){
        result=JSON.parse(responseText);
       //请求成功，添加商品列表
       if(result.status==="success"){
        var itemList=result.data;
        var itemContainer=document.querySelector("#item-list-search");
        for(let i=0;i<itemList.length;i++){
         //addItem(nameStr,priceStr,imgSrc,itemId)
            var itemTitle=decodeURI(itemList[i].name)+"["+decodeURI(itemList[i].route);
            itemContainer.appendChild(addItem(itemTitle,itemList[i].price,itemList[i].pictures[0],itemList[i].id));
        }
       }
    }).catch(function(errorText){
        dialog.showDialog("网络错误");
        setTimeout(dialog.closeDialog,1000);
    });
}

(function(window){
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
    var searchInput=document.querySelector("#search-wrap input");
    var searchButton=document.querySelector("#search-wrap button");
    var key=decodeURI(window.location.href.split("?")[1].substring(4));
    searchInput.value=key;
    requestSearch(key);
    //从url获取关键字
    searchButton.onclick=function(){
        if(searchInput.value===""){
            dialog.showDialog("请输入");
            setTimeout(dialog.closeDialog,1000);
        }
        else{
            window.location.href="search.html?key="+searchInput.value;
        }
    }
})(window);