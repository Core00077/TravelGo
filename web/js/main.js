(function(window){
    window.onload=function(){
        scrollBanner();
    };

    //请求数据
    var dataRequest=ajaxRequest("get","/findAll");
    dataRequest.then(function(responseText){
        var result=JSON.parse(responseText);
        //商品数据
        var itemList=result.data;
        //爱上旅行
        var contentIndex1=document.querySelector("#content-index-1 .item-list");
        //热门推荐
        var contentIndex2=document.querySelector("#content-index-2 .itemlist");
        //添加到爱上旅行
        for(let i=0;i<itemList.length-2;i++){
            //添加商品
            var itemTitle1=decodeURI(itemList[i].name)+"["+decodeURI(itemList[i].route);
            contentIndex1.appendChild(addItem(itemTitle1,itemList[i].price,itemList[i].pictures[0],itemList[i].id));
        }
        //添加到热门推荐
        for(let i=itemList.length-2;i<itemList.length;i++){
            //添加商品
            var itemTitle2=decodeURI(itemList[i].name)+"["+decodeURI(itemList[i].route);
            contentIndex2.appendChild(addItem(itemTitle2,itemList[i].price,itemList[i].pictures[0],itemList[i].id));
        }
    }).catch(function(errorText){
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
    }).catch(function (errorText) {

    });

    //为搜索框添加点击事件
    var searchInput=document.querySelector("#search-form input");
    var searchButton=document.querySelector("#search-form button");
    searchButton.onclick=function(){
        if(searchInput.value===""){
            dialog.showDialog("请输入城市");
            setTimeout(dialog.closeDialog,1000);
        }
        else{
            window.open("../html/search.html?key="+searchInput.value);
        }
    };
})(window);


