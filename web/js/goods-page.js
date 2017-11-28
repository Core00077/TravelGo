(function (window) {
    function addPicture(src) {
        var bigImagescontainer = document.querySelector("#resource-body #big-images");
        var smallImagescontainer = document.querySelector("#resource-body #small-images");
        //添加大图
        var bigImg = document.createElement("img");
        bigImg.src = src;
        bigImagescontainer.appendChild(bigImg);
        //添加小图
        var smallImg = document.createElement("div");
        var img = document.createElement("img");
        img.src = src;
        smallImg.appendChild(img);
        smallImagescontainer.appendChild(smallImg);
    }

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

    //获取商品信息
    var goodsInfo = ajaxRequest("get", "/xxx");
    goodsInfo.then(function (responseText) {
        var result = JSON.parse(responseText);
        //商品详情
        var data = result.data;
        //标题/路线
        var title = document.querySelector("#main-header .item-title .route");
        title.innerText = data.xxx;
        //图片
        var pictures = data.xxx;
        for (let i = 0; i < pictures.length; i++) {
            addPicture(pictures[i]);
        }
        //价格
        var price = document.querySelector("#order-price");
        price.innerText = result.price;

        //城市
        var city = document.querySelector("#order-city");
        city.innerText = result.city;

        //详情图
        var smallImages = document.querySelectorAll("#small-images div");
        var bigImages = document.querySelectorAll("#big-images img");
        smallImages[0].style.border = "3px solid #1bab8d";
        //大小图片
        for (let i = 0; i < smallImages.length; i++) {
            smallImages[i].onclick = function () {
                bigImages[i].style.zIndex = 1;
                bigImages[i].style.visibility = "visible";
                this.style.border = "3px solid #1bab8d";
                for (let j = 0; j < smallImages.length; j++) {
                    if (j !== i) {
                        bigImages[j].style.zIndex = 0;
                        bigImages[j].style.visibility = "hidden";
                        smallImages[j].style.border = 0;
                    }
                }
            }
        }
    }).catch(function (errorText) {
        dialog.showDialog(errorText);
        setTimeout(dialog.closeDialog, 1000);
    });

    //出行人数
    var minusButton = document.querySelector("#number-minus-button");
    var addButton = document.querySelector("#number-add-button");
    var numberSelect = document.querySelector("#number-select");
    minusButton.onclick = function () {
        if (numberSelect.value > 1) {
            numberSelect.value--;
        }
    }
    addButton.onclick = function () {
        numberSelect.value++;
    }

    //提交预定
    var bookButton = document.querySelector("#book-button");
    bookButton.onclick = function () {
        var orderTime = document.querySelector("#order-time-input");
        if (orderTime.value === "") {
            dialog.showDialog("请输入出行时间");
            setTimeout(dialog.closeDialog, 1000);
        }
    }
    window.addPicture = addPicture;
})(window);