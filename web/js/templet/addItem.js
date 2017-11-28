//商品名，价格，图片路径，商品ID
function addItem(nameStr,priceStr,imgSrc,itemId){
    var item=document.createElement("div");
    item.className="item";
    //ID
    var a=document.createElement("a");
    a.href="goods-page.html?id="+itemId;
    item.appendChild(a);
    var i=document.createElement("i");
    i.className="fa fa-heart-o fa-2x";
    i.title="收藏";
    item.appendChild(i);
    /*//商品ID
    var idInput=document.createElement("input");
    idInput.type="hidden";
    idInput.value=itemId;
    a.appendChild(idInput);*/

    var itemPicture=document.createElement("div");
    itemPicture.className="item-picture";
    a.appendChild(itemPicture);
    var itemName=document.createElement("div");
    itemName.className="item-name";
    //商品名
    itemName.innerText=nameStr;
    a.appendChild(itemName);
    var img=document.createElement("img");
    //图片路径
    img.src=imgSrc;
    itemPicture.appendChild(img);
    var price=document.createElement("span");
    price.className="price";
    //价格
    price.innerText="￥"+priceStr;
    itemPicture.appendChild(price);
    return item;
}