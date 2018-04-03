// JavaScript Document
$(function () {
	$(".cur_postion").html("当前位置： [考试统计] - [试卷学生]");

	//查询条件
	var strhtml_searchContent = '<div class="inline-block margin">'
		+'<span>所属教辅:</span>'
		+'<select class="inputStyle_condition strLening"></select>'
		+'</div>';

    $(".searchContent").html(strhtml_searchContent);

	getOData('',"coreLening/find/all",{fn:function(oData){
		if(oData.code == 1) {
			var strhtml_select = '';
			var arrData = oData.data;
			var ln = arrData.length;
			for(var i=0;i<ln;i++){
				strhtml_select += '<option value="'+arrData[i].crlngUuid+'">'+arrData[i].crlngName+'</option>';
			}
			$('.strLening').append(strhtml_select);
		} else {
			alert(data.errMsg);
		}
	}},true);

	//是否显示查询条件
	showSearchBox(true);

	var obj = {};//查询条件对象
	searchContent(obj);
	showList(obj,1);

    //导出Excel
    $('.toExcel').on('click', function () {
    	window.location = urlfile + "/gradeTotal/excel/export/papersuser?strLening="+$('.strLening').val();
    });

	//全选
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
	//tr高亮显示
	$("body").delegate(".trHighLight","mouseleave",function(){
		$(this).find("td").css("background-color","#fff");
	});
	//tr高亮显示并显示图
	$("body").delegate(".trHighLight","mouseenter",function(){
		$(this).find("td").css("background-color","#c1ebff");
	});
	//查询
	$("body").delegate(".searchBtn","click",function(){
		var obj = {};//查询条件对象
		searchContent(obj);
		showList(obj,1);
	});
	//上一页
	$('.manageBox').delegate(".backpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//下一页
	$('.manageBox').delegate(".nextpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//首页
	$('.manageBox').delegate(".firstpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//末页
	$('.manageBox').delegate(".lastpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//跳转至
	$('.manageBox').delegate(".jumppage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($('.jumppagetext').val());
		if(pagenum>0 && pagenum <=parseInt($(this).attr("data-pagemax"))){
			showList(obj,pagenum);
		}else{
			alert("查无此页！");
		}
	});

});

function showList(obj,pagenum){
	var aData =[{name:"序号",percent:"5"},
				{name:"试卷名称",percent:"35"},
				{name:"未做人数",percent:"20"},
				{name:"已做未满分人数",percent:"20"},
				{name:"已做满分人数",percent:"20"}];
	setTableHead(aData);
	var str = 'strLening='+ obj.strLening;
	getOData(str,"gradeTotal/papers/user",{fn:function(oData){
		var strhtml_list = "";
		var arrData = oData.data;
		var ln = arrData.length;
		for(var i=0;i<ln;i++){
			strhtml_list += '<tr class="trHighLight">'
				+'<td>'+(i+1)+'</td>'
				+'<td>'+(arrData[i].columnObj || "")+'</td>'
				+'<td><a  class="p-edit seeDes" onclick="seeDes(\'未做人姓名\',\''+arrData[i].hwzValue+'\')">'+(arrData[i].hwzKey || 0)+'</a></td>'
				+'<td><a class="p-edit seeDes" onclick="seeDes(\'已做未满分姓名\',\''+arrData[i].hyzwmfValue+'\')">'+(arrData[i].hyzwmfKey || 0)+'</a></td>'
				+'<td><a class="p-edit seeDes" onclick="seeDes(\'已做满分姓名\',\''+arrData[i].hyzmfValue+'\')">'+(arrData[i].hyzmfKey || 0)+'</a></td>'
				+'</tr>';
		}
		$(".tb-body").html(strhtml_list);
		}}
	);
}

function seeDes(title, data) {
	layer.open({
		type: 1,
		title: title,
		scrollbar: false,
		maxmin: true,
		shadeClose: false, //点击遮罩关闭层
		area : ['600px' , '390px'],
		content: '<div style="font-size: 15px;padding: 10px;">' + data + '</div>'
	});
}

function refreshList(){
	var obj = {};//查询条件对象
	searchContent(obj);
	showList(obj,parseInt($(".curpage").text()));
	layer.closeAll();
}

function searchContent(obj){
	obj.strLening = $(".strLening").val();
}