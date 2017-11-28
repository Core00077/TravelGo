(function () {

    //界面打开时输入框获取焦点
    window.onload = function () {
        inputGroup[0].focus();
    }

    //登录和注册的变换
    var on = document.querySelectorAll("#choose a");
    on[0].onclick = function () {
        choose("login");
    }
    on[1].onclick = function () {
        choose("register");
    }

    function choose(tagname) {
        var bar = document.querySelector("#slide-bar");
        var login = document.querySelector("#index-login");
        var register = document.querySelector("#index-register");
        if (tagname === "login") {
            on[0].className = "choose";
            bar.style.left = "8px";
            login.style.display = "block";
            on[1].className = "";
            register.style.display = "none";
            inputGroup[0].focus();
        }
        if (tagname === "register") {
            on[0].className = "";
            login.style.display = "none";
            bar.style.left = "69px";
            on[1].className = "choose";
            register.style.display = "block";
            inputGroup[3].focus();
        }
    }

    var registerButton = document.querySelector("#register-button");
    var loginButton = document.querySelector("#login-button");
    //选取所有输入框
    var inputGroup = document.querySelectorAll(".body-login-register input");
    //选取所有提示dom
    var errorLabel = document.querySelectorAll(".body-login-register label");
    //输入框div
    var inputBox = document.querySelectorAll(".body-login-register .input-box");

    //登录响应事件
    loginButton.onclick = function () {
        var phoneNumber = inputGroup[0];
        var password = inputGroup[1];
        var md5Password = inputGroup[2];
        var loginForm = document.querySelector("#login-form");
        var phoneReg = /\d{11}/;
        if (phoneNumber.value === "") {
            moveon(0, "手机号不能为空");
        } else if (!phoneReg.test(phoneNumber.value)) {
            moveon(0, "请输入正确的手机号");
        }
        if (password.value === "") {
            moveon(1, "密码不能为空");
        } else if (password.value.length < 6) {
            moveon(1, "密码错误");
        }
        //前端验证成功，发送ajax请求
        if (phoneReg.test(phoneNumber.value) && password.value.length >= 6) {
            var data={};
            data.phoneNumber=phoneNumber.value;
            data.password=password.value;
            loginRequest(data);
        }
    }

    function loginRequest(data){
        var login=ajaxRequest("post","/login",stringfy(data));
        login.then(function(responseText){
            //请求成功，根据响应内容判断
            var result = JSON.parse(responseText);
            if(result.status==="success"){
                window.location.href="main.html";
            }
            else if(result.status==="phoneNotExist"){
                moveon(0,"用户不存在");
            }
            else if(result.status==="passwordWrong"){
                moveon(1,"密码错误");
            }
            else{
                alert("系统错误");
            }
        }).catch(function (wrongMessage) { // 如果AJAX失败，获得响应代码
            alert(wrongMessage);
        });

    }

    //注册响应事件
    registerButton.onclick = function () {
        var userName = inputGroup[3];
        var phoneNumber = inputGroup[4];
        var password = inputGroup[5];
        var md5Password = inputGroup[6];
        var phoneReg = /\d{11}/;
        var userNamereg = /[a-zA-Z0-9\_]+/;
        var registerForm = document.querySelector("#register-form");
        //提示信息节点
        if (userName.value === "") {
            moveon(2, "用户名不能为空");
        } else if (!userNamereg.test(userName.value)) {
            moveon(2, "用户名不符合规定");
        }
        if (phoneNumber.value === "") {
            moveon(3, "手机号不能为空");
        } else if (!phoneReg.test(phoneNumber.value)) {
            moveon(3, "请输入正确的手机号");
        }
        if (password.value === "") {
            moveon(4, "密码不能为空");
        } else if (password.value.length < 6) {
            moveon(4, "请输入最少六位");
        }
        //前端验证成功，发送ajax请求
        if (userNamereg.test(userName.value) && phoneReg.test(phoneNumber.value) && password.value.length >= 6) {
            var data={};
            data.username=userName.value;
            data.phoneNumber=phoneNumber.value;
            data.password=password.value;
            var registerRequest=ajaxRequest("post","/register",stringfy(data));
            registerRequest.then(function(responseText){
                var result = JSON.parse(responseText);
                var status=result.status;
                if(status==='success'){
                    loginRequest(data);
                }
                else if(status==="nameHasExisted"){
                    moveon(2,"用户名已存在");
                }
                else if(status==="phoneHasExisted"){
                    moveon(3,"手机号已注册");
                }
                else{
                    alert("系统错误");
                }
            }).catch(function(wrongMessage){
                alert(wrongMessage);
            });
        }
    }

    //错误信息提示弹出函数
    var moveon = function (num, errorText) {
        errorLabel[num].innerText = errorText;
        errorLabel[num].style.visibility = "visible";
        errorLabel[num].style.left = "260px";
        errorLabel[num].style.opacity = 1;
    }

    //错误信息提示消失函数
    var remove = function (i) {
        if (errorLabel[i].style.visibility === "visible") {
            errorLabel[i].style.visibility = "hidden";
            errorLabel[i].style.left = "280px";
            errorLabel[i].style.opacity = 0;
        }
    }
    for(let i=0;i<5;i++){
        inputBox[i].onclick=function(){
            remove(i);
        }
    }
})(window);