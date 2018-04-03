$(function () {
    initAdd();
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });
});

//初始化
function initAdd() {
    getOData('',"coreLening/find/all",{fn:function(oData){
        if(oData.code == 1) {
            var strhtml_select = '';
            var arrData = oData.data;
            var ln = arrData.length;
            for(var i=0;i<ln;i++){
                strhtml_select += '<option value="'+arrData[i].crlngUuid+'">'+arrData[i].crlngName+'</option>';
            }
            $('.crpesLening').append(strhtml_select);
        } else {
            alert(oData.errMsg);
        }
    }},true);

    getOData('crcdaUuid=AAAAA',"coreCacheData/views",{fn:function(oData){
        if(oData.code == 1) {
            var arrData = oData.data;
            if(arrData && arrData.crcdaValue) {
                $('.crpesLening').val(arrData.crcdaValue);
            }
        }
    }},true);
}

//检查提交
function checkAdd() {
    if ($.trim($(".crpesLening").val()) == "") {
        alert("请选择所属教辅！");
        return false;
    }
    if ($.trim($(".crpesName").val()) == "") {
        alert("试卷名不能为空，请填写完再提交！");
        $(".crpesName").focus();
        return false;
    }
    if ($.trim($(".crpesExpire").val()) == "") {
        alert("查看视频过期时间（小时）不能为空，请填写完再提交！");
        $(".crpesExpire").focus();
        return false;
    }

    var r = confirm("是否确认增加？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            //do something
            parent.layer.close(index);
        });
        Add(msgObject);
    }
}

//提交
function Add(msgObject) {
    var crpesLening = $(".crpesLening").val();
    var crpesName = $(".crpesName").val();
    var crpesExpire = $(".crpesExpire").val();
    var str = '';
    str += '&crpesLening=' + encodeURIComponent(crpesLening);
    str += '&crpesName=' + encodeURIComponent(crpesName);
    str += '&crpesExpire=' + encodeURIComponent(crpesExpire);
    getOData(str, "corePapers/add/corePapers", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}