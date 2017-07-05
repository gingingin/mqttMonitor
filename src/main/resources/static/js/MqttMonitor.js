/**
 * Created by gin on 2017/6/26.
 */
$(function () {

    var obj = new webMessage();

    $("#mqttSub").on('click', function () {
        obj.mqttSub()
    });

    $('#mqttPub').on('click', function () {
        obj.mqttPub()
    });

    $('#mqttConnect').on('click', function () {
        obj.mqttConnect()
    });
})


var websocket = null;

//判断当前浏览器是否支持WebSocket
var buildWebSocket = function () {
    if ('WebSocket' in window) {
        websocket = new WebSocket(document.getElementById('websocketurl').value);
    }
    else {
        alert('当前浏览器 Not support websocket')
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
    };
    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    };
    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    };
};


//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    closeWebSocket();
};

//将消息显示在网页上
var flag = true;
var boxHeight = $(".msgBox").height();
//    alert(boxHeight);
var msgHeight = 0;
var disHeight = -398;
$(".msgBox").mouseover(function () {
    flag = false;
//        alert("划上去暂停看的东西");
});
$(".msgBox").mouseleave(function () {
    flag = true;
});

function setMessageInnerHTML(innerHTML) {
    document.getElementById('message1').innerHTML += innerHTML + '<br/>';

    msgHeight = $("#message1").height();
    disHeight = msgHeight - boxHeight;
//        alert("msgHeight:"+msgHeight);
    var scrollToTop = 20;
       // alert(disHeight);
    scrollToTop += disHeight;
    if (flag) {
        if (disHeight > 0) {

            $(".msgBox").scrollTop(scrollToTop);
        }
    }
}

//关闭WebSocket连接
function closeWebSocket() {
    if (websocket != null){
        websocket.close();

    }
}

//发送消息
function send() {


    var obj = {
        "message": document.getElementById('messagePub').value,
        "url": document.getElementById('host').value,
//             "repeat":document.getElementById('isrepeat').checked==true? "1":"0",
        "clientId": timestamp.toString(),
        "subTopic": document.getElementById('subTopic').value,
        "pubTopic": document.getElementById('pubTopic').value
    };
    websocket.send(JSON.stringify(obj));
}

var clearDiv = function () {
    document.getElementById('message1').innerHTML = '';
    document.getElementById('message2').innerHTML = '';
};


function webMessage() {




    //从而保证创建该对象的实例时，属性的方法不会被重复创建
    if (typeof webMessage._getJsString == "undefined") {
        webMessage.prototype.getJsString = function () {
            return JSON.stringify(this);
        };
        webMessage._getJsString = true;
    }

    if (typeof webMessage._mqttSub == "undefined") {
        webMessage.prototype.mqttSub = function () {
            this.operation = "mqttSub";
            this.subTopic = getInfo();
            if(this.subTopic == null){
                return null;
            }

            var timestamp = Date.parse(new Date());
            timestamp = timestamp / 1000;
            this.clientId = timestamp.toString();
            this.mqttURL = document.getElementById('host').value.toString();
            websocket.send(JSON.stringify(this));
        };
        webMessage._mqttSub = true;
    }

    if (typeof webMessage._mqttPub == "undefined") {
        webMessage.prototype.mqttPub = function () {
            this.operation = "mqttPub";
            this.pubTopic = document.getElementById('pubTopic').value.toString();
            this.message = document.getElementById('messagePub').value.toString();
            this.qos = document.getElementById('Qos').value.toString();
            return websocket.send(JSON.stringify(this));
        };
        webMessage._mqttPub = true;
    }

    if (typeof webMessage._mqttConnect == "undefined") {
        webMessage.prototype.mqttConnect = function () {
            this.operation = "mqttConnect";
            var timestamp = Date.parse(new Date());
            timestamp = timestamp / 1000;
            this.clientId = timestamp.toString();
            this.mqttURL = document.getElementById('host').value.toString();
            return websocket.send(JSON.stringify(this));
        };
        webMessage._mqttConnect = true;
    }


}


//    获取inpnt框的值
var subTopicVal = $("#subTopic").val();
//    获取acceptSub框中的li标签
var subLia = document.getElementsByClassName("subLi");
var subBoxHeight;
//    点击添加函数
var addSub = function () {
    //  获取input框的值，检查值的内容是否已经变化
    subTopicVal = $("#subTopic").val().trim();
    if (subTopicVal.length == 0) {
        return;
    }
    if (subLia.length == 0) {
        var subTopicTag = $("<li class='label label-xl label-warning subLi'></li>").text(subTopicVal);
        $("#subUl").append(subTopicTag);
    } else if (subLia.length > 0) {
        var flag = true;
        for (var i = 0; i < subLia.length; i++) {
            if (subTopicVal == subLia[i].innerHTML) {
                flag = false;
                alert("已经存在相同元素");
                break;
            }
        }
        if (flag) {
            subTopicTag = $("<li class='label label-warning subLi' style='font-size:14px'></li>").text(subTopicVal);
            $("#subUl").append(subTopicTag);
            subBoxHeight = $("#subUl").height();
//                alert(subBoxHeight);
//                alert($("#subUl").scrollTop());
            if (subBoxHeight > 77) {
//                    alert($("#subUl").scrollTop());
                subBoxHeight += 300;
                $("#subUl").scrollTop(subBoxHeight);
            }
        }
    }

    $(".subLi").on("dblclick", function () {
//            for(var i=0;i<=subLi.length;i++){
//                (function (i) {
//                    subLi[i].ondblclick = function(){
//                        alert($(this).html());
        $(this).remove();
//                        this.parentNode.removeChild(this);
//                    }
//                }(i));
//            }
    });

};
var getInfo = function () {
    var subTopic = "";
    if (subLia.length == 0) {
        alert("请添加要监听的topic");
        return null;
    } else {
        for (var i = 0; i < subLia.length; i++) {
            if (i == subLia.length - 1) {
                //末尾特殊处理不需要逗号
                subTopic = subTopic.concat(subLia[i].innerHTML);
            } else {
                subTopic = subTopic.concat(subLia[i].innerHTML + ",");

            }
        }
    }
    return subTopic;
}

function banInputSapce(e) {
    var keynum;
    if (window.event) // IE
    {
        keynum = e.keyCode
    }
    else if (e.which) // Netscape/Firefox/Opera
    {
        keynum = e.which
    }
    if (keynum == 32) {
        return false;
    }
    return true;
}
