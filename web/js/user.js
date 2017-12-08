(function (window) {
    var inputGroup = document.querySelectorAll(".content form .input-text");
    window.onload = function () {
        inputGroup[0].focus();
    }

    //昵称
    var userName = document.querySelector("#username");
    //真实姓名
    var trueName = document.querySelector("#truename");
    //家乡
    var home = document.querySelector("#home");
    //性别男
    var man = document.querySelector("#man");
    //性别女
    var women = document.querySelector("#women");

    //请求用户数据
    var userInforequest = ajaxRequest("get", "/findUser");
    userInforequest.then(function (responseText) {
        var result = JSON.parse(responseText);
        //用户数据
        var data = result.data;
        //
        if (data.phoneNumber === "") {
            loginState = "unlogin";
            dialog.showDialog("未登录");
            setTimeout(dialog.closeDialog, 1000);
        } else if (data.phoneNumber !== "") {
            loginState = "logined";
            setHeader(data.name);
            //通过返回的数据填写内容
            userName.value = data.name;
            if(data.realName!==null){
                trueName.value = decodeURI(data.realName);
            }
            if(data.hometown!==null){
                home.value = decodeURI(data.hometown);
            }
            if (data.sex == "male") {
                man.checked = true;
            } else if (data.sex == "female") {
                women.checked = true;
            }
        }
    }).catch(function (errorText) {
        dialog.showDialog(errorText);
        setTimeout(dialog.closeDialog, 1000);
    });

    var sumbitButton = document.querySelector("#change-message-button");
    sumbitButton.onclick = function () {
        //更改信息
        var changeData = {};
        changeData.username = userName.value;
        changeData.realName = trueName.value;
        changeData.hometown = home.value;
        changeData.sex = "";
        if (man.checked) {
            changeData.sex = "male";
        } else if (women.checked) {
            changeData.sex = "female";
        }
        var changeInforequest = ajaxRequest("post", "/changeUser", stringfy(changeData));
        changeInforequest.then(function (responseText) {
            var result = JSON.parse(responseText);
            if (result.status === "success") {
                dialog.showDialog("更改成功");
                setTimeout(dialog.closeDialog, 1000);
            } else {
                dialog.showDialog("更改失败");
                setTimeout(dialog.closeDialog, 1000);
            }
        }).catch(function (errorText) {
            dialog.showDialog(errorText);
            setTimeout(dialog.closeDialog, 1000);
        });
    }
})(window);