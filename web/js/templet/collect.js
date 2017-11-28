//页面引入
(function () {
    document.writeln("<link rel=\'stylesheet\' href=\'../css/templet/collect.css\'>");
    document.writeln("   <link rel=\'stylesheet\' href=\'../css/font-awesome.css\'>");
    document.writeln("<div id=\'collect-container\'>");
    document.writeln("   <i id=\'close-collect\' class=\'fa fa-times-circle\'></i>");
    document.writeln("        <div class=\'collect-content\'>");
    document.writeln("            <div class=\'collect-title\'>");
    document.writeln("                <i class=\'fa fa-envelope-open-o fa-2x\'></i>");
    document.writeln("                <h2>收藏夹</h2>");
    document.writeln("                <div class=\'item-list-head\'>");
    document.writeln("                    <label>");
    document.writeln("                        <input type=\'checkbox\' id=\'select-all\'/>全选</label>");
    document.writeln("                    <p>价格</p>");
    document.writeln("                </div>");
    document.writeln("            </div>");
    document.writeln("            <div class=\'item-list\' id=\'item-list-collect\'>");
    document.writeln("                <div class=\'item\'>");
    document.writeln("                    <div class=\'in-center\'><input type=\'checkbox\'></div>");
    document.writeln("                    <div class=\'in-center item-picture\'><a href=\'goods-page.html?id=100\'><img src=\'../img/goods/1.jpg\'></a></div>");
    document.writeln("                    <div class=\'in-center item-name\'><a href=\'goods-page.html?id=100\'>商品名</a></div>");
    document.writeln("                    <div class=\'in-center item-price\'><p>$20</p></div>");
    document.writeln("                    <input type=\'hidden\'>");
    document.writeln("                </div>");
    document.writeln("            </div>");
    document.writeln("            <button id=\'delete-button\'>删除</button>");
    document.writeln("        </div>");
    document.writeln("    </div>");
})();

(function (window) {
    var collectContainer = document.querySelector("#collect-container");
    //收藏夹对象
    var collect = {
        //打开收藏夹
        openCollect: function () {
            collectContainer.style.zIndex = 1000;
            collectContainer.style.display = "block";
        },
        //关闭收藏夹
        closeCollect: function () {
            collectContainer.style.zIndex = 0;
            collectContainer.style.display = "none";
        }
    };
    var closeCollect = document.querySelector("#close-collect");
    closeCollect.onclick = function () {
        collect.closeCollect();
    }
    //商品名，价格，图片路径，商品ID
    function addCollectitem(nameStr,priceStr,imgSrc,itemId){
        var item=document.createElement("div");
        item.className="item";

        var check=document.createElement("div");
        check.className="in-center";
        var checkBox=document.createElement("input");
        checkBox.type="checkbox";
        check.appendChild(checkBox);
        item.appendChild(check);

        var itemPicture =document.createElement("div");
        itemPicture.className="in-center item-picture";
        var a1=document.createElement("a");
        //ID
        a1.href="goods-page.html?id="+itemId;
        var img=document.createElement("img");
        //图片路径
        img.src=imgSrc;
        a1.appendChild(img);
        itemPicture.appendChild(a1);
        item.appendChild(itemPicture);

        var itemName=document.createElement("div");
        itemName.className="in-center item-name";
        var a2=document.createElement("a");
        //ID
        a2.href="goods-page.html?id="+itemId;
        a2.innerText=nameStr;
        itemName.appendChild(a2);
        item.appendChild(itemName);
        //价格
        var itemPrice=document.createElement("div");
        itemPrice.className="in-center item-price";
        var price=document.createElement("p");
        price.innerText="￥"+priceStr;
        itemPrice.appendChild(price);
        item.appendChild(itemPrice);

        //保存商品id
        var inputId=document.createElement("input");
        inputId.type="hidden";
        inputId.value=itemId;

        var itemList=document.querySelector("#item-list-collect");
        itemList.appendChild(item);
    }

    //全选
    var selectAll = document.querySelector("#select-all");
    selectAll.onclick = function () {
        var selectGroup = document.querySelectorAll("#collect-container .item input");
        var a = selectAll.checked === true ? true : false;
        for (let i = 0; i < selectGroup.length; i++) {
            //避免选中已经删除的
            if(selectGroup[i].style.display!=="none"){
                selectGroup[i].checked = a;
            }
        }
    }
    var itemList = document.querySelector("#item-list-collect");
    var deleteButton = document.querySelector("#delete-button");
    //存储被删除的商品
    var deleteList = [];
    deleteButton.onclick = function () {
        var items = document.querySelectorAll("#collect-container #item-list-collect .item");
        //选中的个数
        var selectNum=0;
        for (let i = 0; i < items.length; i++) {
            if (items[i].firstElementChild.firstElementChild.checked === true) {
                selectNum++;
                deleteList.push(items[i].lastChild.value);
                itemList.removeChild(items[i]);
                //删除后文档流还存在
                items[i].firstElementChild.firstElementChild.checked=false;
                items[i].firstElementChild.firstElementChild.style.display="none";
                dialog.showDialog("删除成功");
                setTimeout(dialog.closeDialog,750);
            }
        }
        if(selectNum===0){
            dialog.showDialog("未选择");
            setTimeout(dialog.closeDialog,1000);
        }
    }
    //将收藏夹对象改为全局变量
    window.collect = collect;
    window.addCollectitem=addCollectitem;
})(window);