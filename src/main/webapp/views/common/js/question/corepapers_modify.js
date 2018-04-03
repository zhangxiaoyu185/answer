$(function () {
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });
});

//初始化
function initModify() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crpesUuid=' + encodeURIComponent(id);
    getOData(str, "corePapers/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crpesName").val(oData.data.crpesName || "");
                $(".crpesExpire").val(oData.data.crpesExpire || 0);
                getOData('',"coreLening/find/all",{fn:function(oData1){
                    if(oData1.code == 1) {
                        var strhtml_select = '';
                        var arrData = oData1.data;
                        var ln = arrData.length;
                        for(var i=0;i<ln;i++){
                            strhtml_select += '<option value="'+arrData[i].crlngUuid+'">'+arrData[i].crlngName+'</option>';
                        }
                        $('.crpesLening').append(strhtml_select);
                        $(".crpesLening").val(oData.data.crpesLening || "");
                    } else {
                        alert(oData1.errMsg);
                    }
                }});
            } else {
                alert(oData.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
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

    var r = confirm("是否确认修改？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject) {
    var crpesUuid = getQueryString("id");
    var crpesLening = $(".crpesLening").val();
    var crpesName = $(".crpesName").val();
    var crpesExpire = $(".crpesExpire").val();
    var str = 'crpesUuid=' + encodeURIComponent(crpesUuid)
        + '&crpesLening=' + encodeURIComponent(crpesLening)
        + '&crpesName=' + encodeURIComponent(crpesName)
        + '&crpesExpire=' + encodeURIComponent(crpesExpire);
    getOData(str, "corePapers/update/corePapers", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}