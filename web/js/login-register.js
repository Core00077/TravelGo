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
    var login = function () {
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
        if (phoneReg.test(phoneNumber.value) && password.value.length >= 6) {
            //md5Password.value = toMD5(password.value);
            var form = new FormData();
            form.append("phoneNumber",phoneNumber.value);
            form.append("password",password.value);
            var p=ajaxRequest("post","/login",form);
            p.then(function(text){
                var result = JSON.parse(text);
                if(result.status==="ok"){
                    window.location.href="main.html";
                }
            }).catch(function (status) { // 如果AJAX失败，获得响应代码
                console.log(status);
            });
        }
    }
    loginButton.onclick = login;
    //注册响应事件
    var register = function () {
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
        if (userNamereg.test(userName.value) && phoneReg.test(phoneNumber.value) && password.value.length >= 6) {
            md5Password.value = toMD5(password.value);
            registerForm.submit();
        }
    }
    registerButton.onclick = register;

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
    inputBox[0].onclick = function () {
        remove(0);
    }
    inputBox[1].onclick = function () {
        remove(1);
    }
    inputBox[2].onclick = function () {
        remove(2);
    }
    inputBox[3].onclick = function () {
        remove(3);
    }
    inputBox[4].onclick = function () {
        remove(4);
    }
})(window);