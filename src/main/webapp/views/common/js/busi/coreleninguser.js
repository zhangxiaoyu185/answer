$(function () {
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });
    //下拉选择框
    $(".select-item").unbind().on('click', function () {
        var thisinput = $(this);
        var thisul = $(this).parent().find("ul");
        if (thisul.css("display") == "none") {
            if (thisul.height() > 150) {
                thisul.css({height: "150" + "px", "overflow-y": "scroll"});
            }
            ;
            thisul.fadeIn("100");
            thisul.hover(function () {
            }, function () {
                thisul.fadeOut("100");
            });
            thisul.find(".searchit").focus();
            thisul.find('.searchit').on('keyup', function () {
                var searchText = $(this).val();
                thisul.find("li").each(function () {
                    if ($(this).text().indexOf(searchText) > -1) {
                        $(this).show();
                    }
                    else {
                        $(this).hide();
                    }
                });
            });
            thisul.find("li").unbind().click(function () {
                var ids = "";
                var str_val = "";
                if ($(this).find('input').is(':checked')) {
                    $(this).find("input").attr("checked", '');
                    $(this).find("input").removeAttr("checked");
                }
                else {
                    $(this).find("input").attr("checked", 'checked');
                }
                $(this).parent().find(":checkbox").each(function (i) {
                    if ($(this).attr("checked")) {
                        ids += $(this).parent("li").attr("data-id") + "|";
                        str_val += $(this).parent("li").text() + "|";
                    }
                });
                thisinput.val(str_val);

                thisinput.attr("data-ids", ids);
                thisinput.css('color', '#000');
                //thisul.fadeOut("100");
            }).hover(function () {
                    $(this).addClass("select-hover");
                },
                function () {
                    $(this).removeClass("select-hover");
                });
        }
        else {
            thisul.fadeOut("fast");
        }
    });
});

//初始化
function initModify() {
    $(".crusrUuid").val(getQueryString("id"));
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'user=' + encodeURIComponent(id);
    getOData(str, "coreUser/find/all/by/user", {
        fn: function (oData) {
            if (oData.code == 1) {
                var leningUuids = "";
                var leningNames = "";
                var dataArray = oData.data;
                for(var i=0;i<dataArray.length;i++) {
                    leningUuids=leningUuids+dataArray[i].crlngUuid+"|";
                    leningNames=leningNames+dataArray[i].crlngName+"|";
                }
                $(".crlurLenings").find(".select-item").attr("data-ids", leningUuids);
                $(".crlurLenings").find(".select-item").val(leningNames);
                getLengings(leningUuids);
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//获取下拉框里的值
function getLengings(leningUuids) {
    var str = '';
    getOData(str, "coreLening/find/all", {
        fn: function (oData) {
            if (oData.code == 1) {
                var strhtml_selectUl = '';
                strhtml_selectUl += '<input type="text" style="width:400px;" class="searchit"></input>';
                var arrData = oData.data;
                var ln = arrData.length;
                var ids = leningUuids.split("|");
                for (var i = 0; i < ln; i++) {
                    var ischecked = false;
                    for (var j = 0; j < ids.length; j++) {
                        if (ids[j] == arrData[i].crlngUuid) {
                            ischecked = true;
                            continue;
                        }
                    }
                    if (ischecked) {
                        strhtml_selectUl += '<li data-id="' + arrData[i].crlngUuid + '"><input type="checkbox" name="checkbox" checked="true"/>' + arrData[i].crlngName + '</li>';
                    } else {
                        strhtml_selectUl += '<li data-id="' + arrData[i].crlngUuid + '"><input type="checkbox" name="checkbox"/>' + arrData[i].crlngName + '</li>';
                    }

                }
                $('.crlurLenings').find('.select-ul').html(strhtml_selectUl);
            } else {
                alert(data.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    var r = confirm("是否确认开通？");
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
    var crlurUser = getQueryString("id");
    var crlurLenings = $(".crlurLenings").find(".select-item").attr("data-ids");
    var str = 'crlurUser=' + encodeURIComponent(crlurUser)
        + '&crlurLenings=' + encodeURIComponent(crlurLenings);
    getOData(str, "coreUser/add/coreLeningUser", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("开通教辅成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}