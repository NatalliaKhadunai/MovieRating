var lastModified;
var mark = -1;
var requestForComment;
var requestForMark;

var isLoggedIn;
var statusName;
var userLogin;
var tvseriesName;

function changeColorsOver(callingElement) {
    if (mark == -1 || isNaN(mark)) {
        for (var i = 1; i <= 10; i++) {
            var element = document.getElementById(i);
            element.style.color = "#C9C9C9";
        }
        for (var i = 1; i <= parseInt(callingElement.id); i++) {
            var element = document.getElementById(i);
            element.style.color = "#FFD23F";
        }
    }
}

function putMark(element) {
    if (isLoggedIn == 'false') {
        alert("You're not logged in!");
        return;
    }
    if (statusName.localeCompare('BAN')==0) {
        alert("You're baned!");
        return;
    }
    if (mark != -1 && !isNaN(mark)) {
        alert("You've already put mark for this film!");
        return;
    }
    else {
        var request;
        var v = element.id;
        var url = "/RatingServlet?requestType=putTVSeriesMark&userLogin=" + userLogin + "&tvseriesName=" + tvseriesName + "&mark=" + v;
        mark = parseInt(v);
        if (window.XMLHttpRequest) {
            request = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            request = new ActiveXObject("Microsoft.XMLHTTP");
        }

        try {
            request.onreadystatechange = getInfo;
            request.open("GET", url, true);
            request.send();
        }
        catch (e) {
            alert("Unable to connect to server");
        }
    }
}

function putComment() {
    if (isLoggedIn == 'false') {
        alert("You're not logged in!");
        return;
    }
    if (statusName.localeCompare('BAN')==0) {
        alert("You're baned!");
        return;
    }
    var content = document.getElementById("commentContent").value;
    if (content.replace("\s") == '') {
        alert("Comment should contain text!");
        return;
    }
    else {
        var dateNow = Date.now();
        lastModified = dateNow;
        document.getElementById("commentContent").value = "";
        var url = "/RatingServlet?requestType=putTVSeriesComment&userLogin=" + userLogin + "&tvseriesName=" +
            tvseriesName + "&date=" + dateNow + "&content=" + content + "&lastModified=" + lastModified;

        if (window.XMLHttpRequest) {
            requestForComment = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            requestForComment = new ActiveXObject("Microsoft.XMLHTTP");
        }

        try {
            requestForComment.onreadystatechange = getInfo;
            requestForComment.open("GET", url, true);
            requestForComment.send();
        }
        catch (e) {
            alert("Unable to connect to server");
        }
    }
}

function getInfo(){
    if(requestForComment.readyState==4 && requestForComment.status == 200){
        var dlList = document.getElementById("commentsDL");
        var comments = requestForComment.responseXML.getElementsByTagName("comments")[0];
        var items = comments.getElementsByTagName("comment");
        for (var i=0;i<items.length;i++) {
            var divNode = document.createElement("div");
            divNode.className = "row";
            divNode.style.marginTop = "20px";
            var nodeDT = document.createElement("dt");
            var nodeA = document.createElement("a");
            var nodeAText = document.createTextNode(items[i].getElementsByTagName("userLogin")[0].firstChild.nodeValue);
            nodeA.setAttribute("href","/RatingServlet?requestType=userPage&userLogin=" + items[i].getElementsByTagName("userLogin")[0].firstChild.nodeValue);
            nodeA.appendChild(nodeAText);
            nodeDT.appendChild(nodeA);
            nodeDT.appendChild(document.createTextNode(" on " + items[i].getElementsByTagName("date")[0].firstChild.nodeValue));
            var nodeDD = document.createElement("dd");
            nodeDD.innerHTML = items[i].getElementsByTagName("content")[0].firstChild.nodeValue;
            divNode.appendChild(nodeDT);
            divNode.appendChild(nodeDD);
            dlList.appendChild(divNode);
        }
    }
}

function initialization(isLoggedInVar, statusNameVar, userLoginVar, tvseriesNameVar) {
    isLoggedIn = isLoggedInVar;
    statusName = statusNameVar;
    userLogin = userLoginVar;
    tvseriesName = tvseriesNameVar;
    var element = document.getElementById("1");
    for (var i=2;i<=10;i++) {
        var copyElement = element.cloneNode();
        copyElement.id = i;
        var divElement = document.getElementById("ratingContainer");
        divElement.appendChild(copyElement);
    }
    lastModified = Date.now();
    checkMarkExists();
}

function checkMarkExists() {
    if (isLoggedIn == 'true' && statusName.localeCompare('BAN')!=0) {
        var url = "/RatingServlet?requestType=checkTVSeriesMarkExists&userLogin=" + userLogin + "&tvseriesName=" +
            tvseriesName;
        if (window.XMLHttpRequest) {
            requestForMark = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            requestForMark = new ActiveXObject("Microsoft.XMLHTTP");
        }

        try {
            requestForMark.onreadystatechange = checkMarkExistsResponse;
            requestForMark.open("GET", url, true);
            requestForMark.send();
        }
        catch (e) {
            alert("Unable to connect to server");
        }
    }
}

function checkMarkExistsResponse() {
    if(requestForMark.readyState==4 && requestForMark.status == 200){
        mark = parseInt(requestForMark.responseText);
        if (mark != -1 && !isNaN(mark)) {
            for (var i=1;i<=10;i++) {
                var element = document.getElementById(i);
                element.style.color = "#C9C9C9";
            }
            for (var i=1;i<=mark;i++) {
                var element = document.getElementById(i);
                element.style.color = "#FFD23F";
            }
        }
    }
}
