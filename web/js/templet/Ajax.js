function ajaxRequest(url,callback,data){
    xmlHttp=new XMLHttpRequest();
    xmlHttp.onreadystatechange=function(){
        if(xmlHttp.readyState===4&&xmlHttp.status===200){
            return callback(xmlHttp.responseText);
        }
    }
    xmlHttp.open("post",url,true);
    xmlHttp.setRequestHeader("Content-Type","application/json");
    xmlHttp.send(data);
}