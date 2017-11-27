function ajaxRequest(method,url,data){
    xmlHttp=new XMLHttpRequest();
    return new Promise(function(sucess,failed){
        xmlHttp.onreadystatechange=function(){
            if(xmlHttp.readyState===4){
                if(xmlHttp.status===200){
                    sucess(xmlHttp.responseText);
                }
                else{
                    failed(xmlHttp.status);
                }
            }
        }();
        xmlHttp.open(method,url,true);
        xmlHttp.setRequestHeader("Content-Type","application/ x - www - form - urlencoded");
        xmlHttp.send(data);
    });
}