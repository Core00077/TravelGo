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
    document.writeln("            </div>");
    document.writeln("            <button id=\'delete-button\'>删除</button>");
    document.writeln("        </div>");
    document.writeln("    </div>");
})();

(function (window) {
    var collectContainer = document.querySelector("#collect-container");

    //商品ID
    //itemTitle,itemsResponse[i].pictures[0],itemsResponse[i].price,itemsResponse[i].id
    function addCollectitem(itemTitle, pictureSrc, priceStr, itemId) {
        var item = document.createElement("div");
        item.className = "item";

        var check = document.createElement("div");
        check.className = "in-center";
        var checkBox = document.createElement("input");
        checkBox.type = "checkbox";
        check.appendChild(checkBox);
        item.appendChild(check);

        var itemPicture = document.createElement("div");
        itemPicture.className = "in-center item-picture";
        var a1 = document.createElement("a");
        //ID
        a1.href = "/html/goods-page.html?id=" + itemId;
        var img = document.createElement("img");
        //图片路径
        img.src = pictureSrc;
        a1.appendChild(img);
        itemPicture.appendChild(a1);
        item.appendChild(itemPicture);

        var itemName = document.createElement("div");
        itemName.className = "in-center item-name";
        var a2 = document.createElement("a");
        //ID
        a2.href = "/html/goods-page.html?id=" + itemId;
        a2.innerText = itemTitle;
        itemName.appendChild(a2);
        item.appendChild(itemName);
        //价格
        var itemPrice = document.createElement("div");
        itemPrice.className = "in-center item-price";
        var price = document.createElement("p");
        price.innerText = "￥" + priceStr;
        itemPrice.appendChild(price);
        item.appendChild(itemPrice);

        //保存商品id
        var inputId = document.createElement("input");
        inputId.type = "hidden";
        inputId.value = itemId;
        item.appendChild(inputId);

        return item;
    }

    //收藏夹入口
    function openCollect() {
        collectContainer.style.zIndex = 1000;
        collectContainer.style.display = "block";
        //发送请求
        var dataRequest = ajaxRequest("get", "/love");
        dataRequest.then(function (responseText) {
            var result = JSON.parse(responseText);
            if (result.status === "success") {
                var itemsResponse = result.data;
                var itemList = document.querySelector("#item-list-collect");
                //有数据
                for (let i = 0; i < itemsResponse.length; i++) {
                    //商品名
                    var itemTitle = decodeURI(itemsResponse[i].name) + "[" + decodeURI(itemsResponse[i].route);
                    itemList.appendChild(addCollectitem(itemTitle, itemsResponse[i].pictures[0], itemsResponse[i].price, itemsResponse[i].id));
                }
            } else {
                dialog.showDialog("系统错误");
                setTimeout(dialog.closeDialog, 1000);
            }
        }).catch(function (errorText) {
            dialog.showDialog(errorText);
            setTimeout(dialog.closeDialog, 1000);
        });

        //全选
        var selectAll = document.querySelector("#select-all");
        selectAll.onclick = function () {
            var selectGroup = document.getElementById('item-list-collect').getElementsByClassName('item');
            var a = selectAll.checked === true ? true : false;
            for (let i = 0; i < selectGroup.length; i++) {
                //避免选中已经删除的
                /*if (selectGroup[i].style.display !== "none") {
                    selectGroup[i].checked = a;
                }*/
                selectGroup[i].children[0].children[0].checked = a;
            }
        }

        //删除
        var deleteButton = document.querySelector("#delete-button");
        deleteButton.onclick = function () {
            var items = document.querySelectorAll("#item-list-collect .item");
            var itemList = document.querySelector("#item-list-collect");
            //存储被删除的商品
            var deleteList = [];
            for (let i = 0; i < items.length; i++) {
                if (items[i].children[0].children[0].checked === true) {
                    //将被删除的商品ID添加到数组中
                    deleteList.push(items[i].lastChild.value);
                    itemList.removeChild(items[i]);
                    //删除后文档流还存在
                    //items[i].children[0].children[0].checked = false;
                    //items[i].children[0].children[0].style.display = "none";
                }
            }
            if (deleteList.length === 0) {
                dialog.showDialog("未选择");
                setTimeout(dialog.closeDialog, 1000);
            } else if (deleteList.length > 0) {
                //将删除的商品id拼接为字符串
                var deleteGoodsid = "goodId=".concat(deleteList.join(","));
                var deleteRequest = ajaxRequest("post", "/deleteloves", deleteGoodsid);
                deleteRequest.then(function (responseText) {
                    var result = JSON.parse(responseText);
                    if (result.status === "success") {
                        dialog.showDialog("删除成功");
                        setTimeout(dialog.closeDialog, 750);
                    } else {
                        dialog.showDialog("删除失败");
                        setTimeout(dialog.closeDialog, 750);
                    }
                }).catch(function (errorText) {
                    dialog.showDialog(errorText);
                    setTimeout(dialog.closeDialog, 750);
                });
            }
        }
    }

    //关闭收藏夹
    var closeCollect = document.querySelector("#close-collect");
    closeCollect.onclick = function () {
        var items = document.querySelectorAll("#item-list-collect .item");
        var itemList = document.querySelector("#item-list-collect");
        for (let i = 0; i < items.length; i++) {
                itemList.removeChild(items[i]);
                //删除后文档流还存在
                //items[i].children[0].children[0].checked = false;
                //items[i].children[0].children[0].style.display = "none";
        }
        collectContainer.style.zIndex = 0;
        collectContainer.style.display = "none";
    }
    //将收藏夹对象改为全局变量
    window.openCollect = openCollect;
    window.addCollectitem = addCollectitem;
})(window);