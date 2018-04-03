$(function () {
    initAdd();
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });
});

//初始化
function initAdd() {
    $(".crcasName").val("");
    goodColumns();
}

//加载模块下拉框内容
function goodColumns(){
    var str = '';
    getOData(str,"coreSchool/find/all",{fn:function(oData){
        var strhtml_goodColumns = '';
        var arrData = oData.data;
        var ln = arrData.length;
        for(var i=0;i<ln;i++){
            strhtml_goodColumns += '<option value ="'+arrData[i].crsclUuid+'" >'+arrData[i].crsclName+'</option>';
        }
        $(".crcasSchool").html(strhtml_goodColumns);
    }});
}

//检查提交
function checkAdd() {
    if ($.trim($(".crcasSchool").val()) == "") {
        alert("所属学校不能为空，请填写完再提交！");
        $(".crcasSchool").focus();
        return false;
    }
    if ($.trim($(".crcasName").val()) == "") {
        alert("班级名称不能为空，请填写完再提交！");
        $(".crcasName").focus();
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
    var crcasSchool = $(".crcasSchool").val();
    var crcasName = $(".crcasName").val();
    var str = '';
    str += '&crcasSchool=' + encodeURIComponent(crcasSchool)
    str += '&crcasName=' + encodeURIComponent(crcasName);
    getOData(str, "coreClass/add/coreClass", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}