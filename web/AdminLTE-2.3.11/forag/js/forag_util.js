/**
 * Created by Esong on 2017/5/2.
 */
//解析URL,根据parame取值
function request(strParame) {
    var args = new Object();
    var query = location.search.substring(1);
    var pairs = query.split("&");
    for (var i = 0; i < pairs.length; i++){
        var pos = pairs[i].indexOf('=');
        if (pos == -1){
            continue;
        }
        var argname = pairs[i].substring(0,pos);
        var value = pairs[i].substring(pos+1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args[strParame];
}
function createXMLHttp() {
    //对于大多数浏览器适用
    var xmlHttp;
    if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
    //考虑浏览器的兼容性
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXOject("Microsoft.XMLHTTP");
        if (!xmlHttp) {
            xmlHttp = new ActiveXOject("Msxml2.XMLHTTP");
        }
    }
    return xmlHttp;
}

function reComposing(tabContentId) {
    var tabContents = document.getElementById(tabContentId).children;
    for (var i = 0; i < tabContents.length; i++){
        var colList = tabContents[i].children[0].children;
        //alert(tabContents[i].children[0].innerHTML);
        if (colList.length > 0){
            //保留设置前的class
            var tempDisplay = tabContents[i].className;
            //alert(tempDisplay);
            //统一设置为active,否则会出现没有active class的元素不能够重新排版成功
            tabContents[i].className = "tab-pane active";
            var height = reSetColPosition(colList);
            tabContents[i].style.height = height + "px";
            tabContents[i].className = tempDisplay;
        }
    }
}
function reSetColPosition(colList) {
    var posHeightArr = [0,0];
    var colWidth = (document.documentElement.clientWidth - 40)/2 - 20;
    var startHeight = 50 + document.getElementById("carousel-inner-img").offsetHeight + 20;
    for (var i = 0; i < colList.length; i++){
        var minIndex = getMinOrMaxIndex(posHeightArr,"min");
        //alert("index="+minIndex+",posHeight="+posHeightArr[minIndex]);
        //alert(colList[i].innerHTML);
        colList[i].style.position = "absolute";
        colList[i].style.width = (colWidth - 10) + "px";
        colList[i].style.left = (30 + minIndex * colWidth)+"px";
        colList[i].style.top = (startHeight + posHeightArr[minIndex])+"px";
        posHeightArr[minIndex] += colList[i].offsetHeight;
        //alert("index="+minIndex+",posHeight="+posHeightArr[minIndex]);
    }
    return posHeightArr[getMinOrMaxIndex(posHeightArr,"max")];
}
function getMinOrMaxIndex(posArr,type) {
    var minIndex = 0;
    var maxIndex = 0;
    for (var i = 1; i < posArr.length;i++){
        if (posArr[minIndex] > posArr[i]){
            minIndex = i;
        }
        if (posArr[maxIndex] < posArr[i]){
            maxIndex = i;
        }
    }
    if (type == "min"){
        return minIndex;
    }else if (type == "max"){
        return maxIndex;
    }
}