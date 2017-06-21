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
            tabContents[i].style.height = (height+20) + "px";
            tabContents[i].children[1].style.marginTop = (height-10)+"px";
            tabContents[i].className = tempDisplay;
        }
    }
}
function reSetColPosition(colList) {
    //alert(colList.length);
    var windowWidth = document.documentElement.clientWidth;
    var posHeightArr = new Array(parseInt(windowWidth/500+1));
    initArr(posHeightArr);
    var colWidth = windowWidth / posHeightArr.length - 10;
    var offsetWidth = (windowWidth - posHeightArr.length * colWidth)/(posHeightArr.length + 1);
    var startHeight = 50 + document.getElementById("carousel-inner-img").offsetHeight + document.getElementById("top-tag-nav-ul").offsetHeight - 20;
    for (var i = 0; i < colList.length; i++){
        var minIndex = getMinOrMaxIndex(posHeightArr,"min");
        //alert("index="+minIndex+",posHeight="+posHeightArr[minIndex]);
        //alert(colList[i].innerHTML);
        colList[i].style.position = "absolute";
        colList[i].style.width = colWidth + "px";
        colList[i].style.left = (offsetWidth + minIndex * colWidth)+"px";
        colList[i].style.top = (startHeight + posHeightArr[minIndex])+"px";
        posHeightArr[minIndex] += colList[i].offsetHeight;
        //alert("index="+minIndex+",posHeight="+posHeightArr[minIndex]);
    }
    return posHeightArr[getMinOrMaxIndex(posHeightArr,"max")];
}
function initArr(arr) {
    for (var i = 0; i < arr.length; i++){
        arr[i] = 0;
    }
    return arr;
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
//增加数组的删除功能
function removeByValue(val,arr) {
    for(var i=0; i<arr.length; i++) {
        if(arr[i] == val) {
            arr.splice(i, 1);
            break;
        }
    }
    return arr;
}
//增加数组检查重复的功能
function contains(needle,arr) {
    for (var i in arr) {
        if (arr[i] == needle) return true;
    }
    return false;
}
//top 导航栏搜索功能
function submitSearch(inputId) {
    var inputSearch = document.getElementById(inputId);
    //alert(inputId.value);
    getHotTag(inputSearch.value);
}
function getHotTag(value) {
    document.getElementById("search-tip-list").style.display = "none";
    $.ajax({
        type:"POST",
        url:"/LoadMoreMsg",
        dataType:"json",
        data:"tagName="+value+"&offset=0&len=10&uId=-1",
        success:function (msgData) {
            var tableSearch = document.getElementById("search-table");
            if (msgData.count<=0){
                tableSearch.innerHTML = "<tr style='width: 100%'><td style='text-align: center;width: 100%'>没有搜索到标签\""+inputSearch.value+"\"下的文章</td></tr>"
            }else {
                var msg = msgData.data;
                var tempHtml = "";
                for (var i = 0; i < msgData.count; i++){
                    if (i > 7){
                        break;
                    }
                    tempHtml += "<tr style='width: 100%'><td style='padding-left: 20px;width: 100%'><a href='article.html?mId=" + msg[i].mId + "' target='_blank'>"+msg[i].mTitle+"</a></td></tr>";
                }
                tableSearch.innerHTML = tempHtml;
            }
            $('#searchModal').modal('show');
        }
    });
}
//top 导航栏搜索提示下拉
function setSearchTipList(obj) {
    //alert(obj.value);
    var searchValue = obj.value;
    var searchTipListTable = document.getElementById("search-tip-list");
    if (searchValue == ""){
        searchTipListTable.style.display = "none";
    } else {
        $.ajax({
            type:"POST",
            url:"/TagEdit",
            dataType:"json",
            data:"type=search&tagName="+(obj.value).trim(),
            success:function (resultData) {
                var length = resultData.length;
                var tipList = document.getElementById("search-tip-list");
                var temp = "";
                if (length>0){
                    for (var i = 0; i < length; i++){
                        if (i > 7){
                            break;
                        }
                        temp += "<tr style='width: 100%;'><td style='width: 100%;' class='btn'  onclick='getHotTag(\""+resultData[i]+"\")'><span class='pull-left'>"+resultData[i]+"</span></td></tr>";
                    }
                    searchTipListTable.style.display = "table";
                    tipList.innerHTML = temp;
                }else {
                    //temp = "<tr><td><a href='#' class='disabled'>"+resultData[i]+"</a></td></tr>";
                    searchTipListTable.style.display = "none";
                }
            }
        });
    }
}
function setTipListShow() {
    //alert(1);
    //document.getElementById("top-input-search").focus();
    var focusedTagId = document.activeElement.id;
   // alert(document.activeElement.tagName);
    if (focusedTagId != "top-input-search"){
        document.getElementById("search-tip-list").style.display = "none";
    }
}