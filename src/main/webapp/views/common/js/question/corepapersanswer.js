$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>所属教辅:</span>'
        + '<select class="inputStyle_condition crpsaLening"></select>'
        + '<span>所属试卷:</span>'
        + '<select class="inputStyle_condition crpsaPaper"></select>'
        + '<span>所属用户:</span>'
        + '<select class="inputStyle_condition crpsaUser"></select>'
        + '</div>';
    $(".searchContent").html(strhtml_searchContent);

    getOData('',"coreLening/find/all",{fn:function(oData){
        if(oData.code == 1) {
            var strhtml_select = '<option value="">全部</option>';
            var arrData = oData.data;
            var ln = arrData.length;
            for(var i=0;i<ln;i++){
                strhtml_select += '<option value="'+arrData[i].crlngUuid+'">'+arrData[i].crlngName+'</option>';
            }
            $('.crpsaLening').append(strhtml_select);
        } else {
            alert(data.errMsg);
        }
    }});
    getOData('',"corePapers/find/all",{fn:function(oData){
        if(oData.code == 1) {
            var strhtml_select = '<option value="">全部</option>';
            var arrData = oData.data;
            var ln = arrData.length;
            for(var i=0;i<ln;i++){
                strhtml_select += '<option value="'+arrData[i].crpesUuid+'">'+arrData[i].crpesName+'</option>';
            }
            $('.crpsaPaper').append(strhtml_select);
        } else {
            alert(data.errMsg);
        }
    }});
    getOData('',"coreUser/find/all",{fn:function(oData){
        if(oData.code == 1) {
            var strhtml_select = '<option value="">全部</option>';
            var arrData = oData.data;
            var ln = arrData.length;
            for(var i=0;i<ln;i++){
                strhtml_select += '<option value="'+arrData[i].crusrUuid+'">'+arrData[i].crusrName+'('+arrData[i].crusrCode+')</option>';
            }
            $('.crpsaUser').append(strhtml_select);
        } else {
            alert(data.errMsg);
        }
    }},true);
    //是否显示查询条件
    showSearchBox(true);

    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, 1);

    //详情
    $("body").delegate('.detailit', 'click', function () {
        layer.open({
            type: 2,
            title: '详情',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../question/corepapersanswer_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //全选
    $("body").delegate("input[name='checkboxAll']", "click", function () {
        if ($(this).attr("checked")) {
            $("input[name='checkbox']").each(function () {
                $(this).attr("checked", true);
            });
        } else {
            $("input[name='checkbox']").each(function () {
                $(this).removeAttr("checked");
            });
        }
    });

    //批量删除
    $("body").delegate(".delthese", "click", function () {
        var ids = '';
        $("input[name='checkbox']").each(function () {
            if ($(this).attr("checked")) {
                ids += $(this).attr("data-id") + "|";
            }
        });
        if (ids == "") {
            alert("未选择删除对象！");
        } else {
            var r = confirm("是否确认删除所选的记录？");
            if (r == true) {
                var str = 'crpsaUuids=' + encodeURIComponent(ids);
                getOData(str, "corePapersAnswer/delete/batch", {
                        fn: function (oData) {
                            var obj = {};//查询条件对象
                            searchContent(obj);
                            pagenum = parseInt($(".curpage").text());
                            isNull(obj, pagenum);
                            alert("删除成功！");
                        }
                    }
                );
            }
        }
    });

    //删除
    $("body").delegate(".delit", "click", function () {
        var r = confirm("是否确认删除？");
        if (r == true) {
            var str = 'crpsaUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "corePapersAnswer/delete/one", {
                    fn: function (oData) {
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = parseInt($(".curpage").text());
                        isNull(obj, pagenum);
                        alert("删除成功！");
                    }
                }
            );
        }
    });
    //tr高亮显示
    $("body").delegate(".trHighLight", "mouseleave", function () {
        $(this).find("td").css("background-color", "#fff");
    });
    //tr高亮显示并显示图
    $("body").delegate(".trHighLight", "mouseenter", function () {
        $(this).find("td").css("background-color", "#c1ebff");
        var imgurl = $(this).attr("data-imageurl");
        if (imgurl != "#") {
            var that = this;
            $("<img/>").attr("src", imgurl).load(function () {
                var width = 150;
                var realWidth = this.width;
                var realHeight = this.height;
                var height = realHeight / realWidth * width;
                if (realWidth >= width) {
                    var bannerimg = '<img id="bannerimg" src="' + imgurl + '" width="' + width + 'px" height="' + height + 'px" />';
                }
                else {//如果小于浏览器的宽度按照原尺寸显示
                    $("#bannerimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    var bannerimg = '<img id="bannerimg" src="' + imgurl + '" width="' + realWidth + 'px" height="' + realHeight + 'px" />';
                }
                layer.tips(bannerimg, that, {
                    tips: 3
                });
            });
        }
    });
    //查询
    $("body").delegate(".searchBtn", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        showList(obj, 1);
    });
    //重置
    $("body").delegate(".resetBtn", "click", function () {
        var obj = {};//查询条件对象
        $(".crpsaLening").val('');
        $(".crpsaPaper").val('');
        $(".crpsaUser").val('');
        searchContent(obj);
        showList(obj, 1);
    });
    //上一页
    $('.manageBox').delegate(".backpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //下一页
    $('.manageBox').delegate(".nextpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //首页
    $('.manageBox').delegate(".firstpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //末页
    $('.manageBox').delegate(".lastpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //跳转至
    $('.manageBox').delegate(".jumppage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($('.jumppagetext').val());
        if (pagenum > 0 && pagenum <= parseInt($(this).attr("data-pagemax"))) {
            showList(obj, pagenum);
        } else {
            alert("查无此页！");
        }
    });

});

function showList(obj, pagenum) {
    var aData = [{name: "<input type='checkbox' name='checkboxAll' value='checkbox' />", percent: "5"},
        {name: "所属教辅", percent: "15"},
        {name: "所属试卷", percent: "15"},
        {name: "学生学号", percent: "15"},
        {name: "学生姓名", percent: "15"},
        {name: "总得分", percent: "10"},
        {name: "创建时间", percent: "15"},
        {name: "操作", percent: "10"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=10';
    if (obj.crpsaLening && obj.crpsaLening != "") {
        str += '&crpsaLening=' + encodeURIComponent(obj.crpsaLening);
    }
    if (obj.crpsaPaper && obj.crpsaPaper != "") {
        str += '&crpsaPaper=' + encodeURIComponent(obj.crpsaPaper);
    }
    if (obj.crpsaUser && obj.crpsaUser != "") {
        str += '&crpsaUser=' + encodeURIComponent(obj.crpsaUser);
    }
    getOData(str, "corePapersAnswer/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].crpsaUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].crlngName || "") + '</td>'
                        + '<td>' + (arrData[i].crpesName || "") + '</td>'
                        + '<td>' + (arrData[i].crusrName || "") + '</td>'
                        + '<td>' + (arrData[i].crusrCode || "") + '</td>'
                        + '<td>' + (arrData[i].crpsaScore || 0) + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].crpsaCdate) || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].crpsaUuid + '">查看</a>'
                        + '<a  class="p-edit delit" data-id="' + arrData[i].crpsaUuid + '">删除</a>'
                        + '</td>'
                        + '</tr>';
                }
                $(".tb-body").html(strhtml_list);
                setTableFoot(oData.data, aData.length);
            }
        }
    );
}

function isNull(obj, pagenum) {
    var str = 'pageNum=' + pagenum + '&pageSize=10';
    if (obj.crpsaLening && obj.crpsaLening != "") {
        str += '&crpsaLening=' + encodeURIComponent(obj.crpsaLening);
    }
    if (obj.crpsaPaper && obj.crpsaPaper != "") {
        str += '&crpsaPaper=' + encodeURIComponent(obj.crpsaPaper);
    }
    if (obj.crpsaUser && obj.crpsaUser != "") {
        str += '&crpsaUser=' + encodeURIComponent(obj.crpsaUser);
    }
    getOData(str, "corePapersAnswer/find/by/cnd", {
        fn: function (oData) {
            var arrData = oData.data.result;
            var ln = arrData.length;
            if (ln == 0) {
                if (oData.data.totalCount != 0 && pagenum != 1) {
                    showList(obj, pagenum - 1);
                } else {
                    showList(obj, 1);
                }
            } else {
                showList(obj, pagenum);
            }
        }
    });
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {
    obj.crpsaLening = $(".crpsaLening").val();
    obj.crpsaPaper = $(".crpsaPaper").val();
    obj.crpsaUser = $(".crpsaUser").val();
}
