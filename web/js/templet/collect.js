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
    document.writeln("            <div class=\'item-list\' id=\'item-list\'>");
    document.writeln("                <div class=\'item\'>");
    document.writeln("                    <div class=\'in-center\'><input type=\'checkbox\'></div>");
    document.writeln("                    <div class=\'in-center item-picture\'><a href=\'\'><img src=\'\'>图片</a></div>");
    document.writeln("                    <div class=\'in-center item-name\'><a href=\'\'>商品名</a></div>");
    document.writeln("                    <div class=\'in-center item-price\'><p>$20</p></div>");
    document.writeln("                    <input type=\'hidden\'>");
    document.writeln("                </div>");
    document.writeln("                <div class=\'item\'>");
    document.writeln("                    <div class=\'in-center\'><input type=\'checkbox\'></div>");
    document.writeln("                    <div class=\'in-center item-picture\'><a href=\'\'><img src=\'\'>图片</a></div>");
    document.writeln("                    <div class=\'in-center item-name\'><a href=\'\'><p>商品名</p></a></div>");
    document.writeln("                    <div class=\'in-center item-price\'><p>$20</p></div>");
    document.writeln("                    <input type=\'hidden\'>");
    document.writeln("                </div>");
    document.writeln("                <div class=\'item\'>");
    document.writeln("                    <div class=\'in-center\'><input type=\'checkbox\'></div>");
    document.writeln("                    <div class=\'in-center item-picture\'><a href=\'\'><img src=\'\'>图片</a></div>");
    document.writeln("                    <div class=\'in-center item-name\'><a href=\'\'><p>商品名</p></a></div>");
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

    var selectAll = document.querySelector("#select-all");
    //全选
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
    var itemList = document.querySelector("#item-list")
    var items = document.querySelectorAll("#collect-container #item-list .item");
    var deleteButton = document.querySelector("#delete-button");
    //存储被删除的商品
    var deleteList = [];
    deleteButton.onclick = function () {
        //选中的个数
        var selectNum=0;
        for (let i = 0; i < items.length; i++) {
            if (items[i].firstElementChild.firstElementChild.checked === true) {
                selectNum++;
                deleteList.push(items[i]);
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
})(window);