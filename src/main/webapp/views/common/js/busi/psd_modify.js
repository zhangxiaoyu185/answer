// JavaScript Document
$(function () {
	initModify();
	//提交
	$(".submit").on("click",function(){
		checkModify();
	});
});
//初始化
function initModify() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
	var str = 'crusrUuid=' + encodeURIComponent(id);
    getOData(str, "coreUser/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".accountName").html(oData.data.crusrCode || "");
                $(".crusrUuid").val(oData.data.crusrUuid || "");
            } else {
                alert(data.errMsg);
            }
        }
    });
}
//检查提交
function checkModify(){
	if($.trim($(".newPsd").val()) == ""){
		alert("新密码不能为空，请填写完再提交！");
		$(".newPsd").focus();
		return false;
	}
	if($.trim($(".surePsd").val()) == ""){
		alert("确认密码不能为空，请填写完再提交！");
		$(".surePsd").focus();
		return false;
	}
	if($(".surePsd").val() != $(".newPsd").val()){
		alert("确认密码不正确，请重新输入后再提交！");
		$(".surePsd").focus();
		return false;
	}
	var r=confirm("是否确认修改？");
	if (r==true){
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
function Modify(msgObject){
	var crusrUuid = $(".crusrUuid").val();
	var newPwd = $(".newPsd").val();
	var confirmPwd = $(".surePsd").val();
	var str = 'crusrUuid='+encodeURIComponent(crusrUuid)+'&newPwd='+encodeURIComponent(newPwd)+'&confirmPwd='+encodeURIComponent(confirmPwd);
	getOData(str,"coreUser/update/pwd",{
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
	});
}
