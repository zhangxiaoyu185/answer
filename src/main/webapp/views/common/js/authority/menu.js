$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '';
    $(".searchContent").html(strhtml_searchContent);
    //是否显示查询条件
    showSearchBox(false);

    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, 1);

    //新增
    $('.addit').on('click', function () {
        layer.open({
            type: 2,
            title: '新增',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../authority/menu_add.html'
        });
    });
    //修改
    $("body").delegate('.modifyit', 'click', function () {
        layer.open({
            type: 2,
            title: '修改',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../authority/menu_modify.html?id=' + $(this).attr("data-id")
        });
    });

    /*//全选
    $("body").delegate("input[name='checkboxAll']","click",function(){
        if($(this).attr("checked")){
            $("input[name='checkbox']").each(function(){
                $(this).attr("checked",true);
            });
        }else{
            $("input[name='checkbox']").each(function(){
                $(this).removeAttr("checked");
            });
        }

    });

    //批量删除
    $("body").delegate(".delthese","click",function(){
        var ids = '';
        $("input[name='checkbox']").each(function(){
            if($(this).attr("checked")){
                ids += $(this).attr("data-id")+"|";
            }
        });
        if(ids == ""){
            alert("未选择删除对象！");
        }else{
            var r=confirm("是否确认删除所选的记录？");
            if (r==true){
                var str = 'crfntUuids='+ids+'&crfntStatus='+$(this).attr("data-status");
                getOData(str,"coreFunction/delete/batch",{fn:function(oData){
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = $(".curpage").text();
                        showList(obj,pagenum);
                        alert("删除成功！");
                    }}
                );
            }
        }
    });*/

    //删除
    $("body").delegate(".delit", "click", function () {
        if ($(this).attr("data-status") == 0) {
            var r = confirm("是否确认禁用？");
        } else if ($(this).attr("data-status") == 1) {
            var r = confirm("是否确认启用？");
        }

        if (r == true) {
            var str = 'crfntUuid=' + encodeURIComponent($(this).attr("data-id")) + '&crfntStatus=' + encodeURIComponent($(this).attr("data-status"));
            getOData(str, "coreFunction/delete/one", {
                    fn: function (oData) {
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = $(".curpage").text();
                        showList(obj, pagenum);
                        alert("操作成功！");
                    }
                }
            );
        }

    });
    //tr高亮显示
    $("body").delegate(".trHighLight", "mouseenter", function () {
        $(this).find("td").css("background-color", "#c1ebff");
    });
    $("body").delegate(".trHighLight", "mouseleave", function () {
        $(this).find("td").css("background-color", "#fff");
    });

    //查询
    $("body").delegate(".searchBtn", "click", function () {
        var obj = {};//查询条件对象
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
    var aData = [/*{name:"<input type='checkbox' name='checkboxAll' value='checkbox' />",percent:"5"},*/
        {name: "菜单名", percent: "20"},
        {name: "源地址", percent: "30"},
        {name: "状态", percent: "10"},
        {name: "上级菜单", percent: "20"},
        {name: "操作", percent: "20"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=20';
    getOData(str, "coreFunction/menuPage", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var status = "";
                    if (arrData[i].crfntStatus == 0) {
                        status = "禁用";
                    } else if (arrData[i].crfntStatus == 1) {
                        status = "启用";
                    }

                    strhtml_list += '<tr class="trHighLight">'
                        //						+'<td>'+'<input type="checkbox" name="checkbox" value="checkbox" data-id="'+arrData[i].crfntUuid+'"/>'+'</td>'
                        + '<td>' + arrData[i].crfntFunName + '</td>'
                        + '<td>' + arrData[i].crfntResource + '</td>'
                        + '<td>' + status + '</td>';
                    if (arrData[i].crfntFatherName == undefined) {
                        strhtml_list += '<td></td>';
                    } else {
                        strhtml_list += '<td>' + arrData[i].crfntFatherName + '</td>';
                    }
                    strhtml_list += '<td>'
                        + '<a  class="p-edit modifyit" data-id="' + arrData[i].crfntUuid + '">修改</a>';
                    if (arrData[i].crfntStatus == 0) {
                        strhtml_list += '<a  class="p-edit delit" data-id="' + arrData[i].crfntUuid + '" data-status="1">启用</a>';
                    } else if (arrData[i].crfntStatus == 1) {
                        strhtml_list += '<a  class="p-edit delit" data-id="' + arrData[i].crfntUuid + '" data-status="0">禁用</a>';
                    }

                    strhtml_list += '</td>'
                        + '</tr>';
                }
                $(".tb-body").html(strhtml_list);
                setTableFoot(oData.data, aData.length);
            }
        }
    );
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {

}