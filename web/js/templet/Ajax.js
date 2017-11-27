function stringfy(data) {
    var result = '';
    for (let i in data) {

        result = result + i + '=' + data[i] + '&';
    }
    return result.substring(0, result.length - 1);
}

function ajaxRequest(method, url, data) {
    var xmlRequest = new XMLHttpRequest();
    return new Promise(function (resolve, reject) {
        xmlRequest.onreadystatechange = function () {
            if (xmlRequest.readyState === 4) {
                if (xmlRequest.status === 200) {
                    resolve(xmlRequest.responseText);
                } else {
                    reject("网路错误");
                }
            }
        };
        xmlRequest.open(method, url, true);
        xmlRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlRequest.send(data);
    });
}