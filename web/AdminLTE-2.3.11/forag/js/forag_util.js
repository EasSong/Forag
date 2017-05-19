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