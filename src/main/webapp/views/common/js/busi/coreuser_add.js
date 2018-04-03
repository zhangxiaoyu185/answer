$(function () {
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });
});
//检查提交
function checkAdd() {
    if ($.trim($(".crusrCode").val()) == "") {
        alert("真实姓名不能为空，请填写完再提交！");
        $(".crusrCode").focus();
        return false;
    }
    if ($.trim($(".crusrPassword").val()) == "") {
        alert("登录密码不能为空，请填写完再提交！");
        $(".crusrPassword").focus();
        return false;
    }
    if ($.trim($(".crusrMobile").val()) == "") {
        alert("手机号码不能为空，请填写完再提交！");
        $(".crusrMobile").focus();
        return false;
    }
    if ($.trim($(".crusrGender").val()) == "") {
        alert("请选择性别！");
        return false;
    }
    var reg = new RegExp("^[0-9]*$");
    if(!reg.test($.trim($(".crusrMobile").val()))){
        alert("手机号格式不正确!");
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
    var crusrCode = $(".crusrCode").val();
    var crusrPassword = $(".crusrPassword").val();
    var crusrMobile = $.trim($(".crusrMobile").val());
    var crusrGender = $(".crusrGender").val();
    var crusrClass = $(".crusrClass").val();
    var crusrSchool = $(".crusrSchool").val();
    var str = '';
    str += '&crusrCode=' + encodeURIComponent(crusrCode)
    str += '&crusrPassword=' + encodeURIComponent(crusrPassword)
    str += '&crusrMobile=' + encodeURIComponent(crusrMobile)
    str += '&crusrGender=' + encodeURIComponent(crusrGender)
    str += '&crusrClass=' + encodeURIComponent(crusrClass)
    str += '&crusrSchool=' + encodeURIComponent(crusrSchool);
    getOData(str, "coreUser/add/coreUser", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！学号为："+oData.data.crusrName);
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}