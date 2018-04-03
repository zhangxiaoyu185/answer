var subject='';
var strhtml_goodColumns = '';
var arr=[];
$(function () {
    //生成视频目录select框和添加的字段
    genSelectAndSubjectAndInit();

    $("#add-subject").click(function () {
        $("#subject-table tbody").append(subject);
        /*对新增元素添加绑定事件*/
        $(".delete-btn").off().on("click",function () {
            if(confirm("确认删除吗？")){
                $(this).parents("tr").remove();
            }
        });
        $(".next").off().on("click",function () {
            $(this).parents("tr").next("tr").after($(this).parents("tr"));
        });
        $(".prev").off().on("click",function () {
            $(this).parents("tr").prev("tr").before($(this).parents("tr"));
        });
    });
    //提交
    $("#save-btn").click(function () {
        checkAdd();
    });
    $(".delete-btn").on("click",function () {
        if(confirm("确认删除吗？")){
            $(this).parents("tr").remove();
        }
    });
    $(".next").on("click",function () {
        $(this).parents("tr").next("tr").after($(this).parents("tr"));
    });
    $(".prev").on("click",function () {
        $(this).parents("tr").prev("tr").before($(this).parents("tr"));
    });
});

//初始化
function initAdd(id) {
    //获取已添加的题目信息
    var str = 'crpesUuid=' + encodeURIComponent(id);
    getOData(str, "corePapers/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                var crpesQuestion = oData.data.crpesQuestion;
                if(''!=crpesQuestion && undefined!=crpesQuestion){
                    var parsedJson = jQuery.parseJSON(crpesQuestion);
                    var problems = parsedJson.problem;
                    for ( var i in problems) {
                        //渲染已添加的题目
                        var strhtml_list = '<tr class="subject-item">'
                            +'<th scope="row"><input type="text" class="form-control num" style=" width: 65px;"  placeholder="题目编号" value="'+problems[i].name+'"></th>'
                            +'<td> <input type="text" class="form-control answer" placeholder="题目答案" value="'+problems[i].result+'"></td>'
                            +'<td> <input type="number" class="form-control score" style=" width: 65px;"  placeholder="题目分值" value="'+problems[i].oldScore+'"></td>'
                            +'<td> <select class="form-control file" placeholder="视频目录" value="'+problems[i].dirMovie+'">'+strhtml_goodColumns+'</select></td>'
                            +'<td> <input type="text" class="form-control name"  placeholder="视频名称" value="'+problems[i].movie+'"></td>'
                            +'<td class="btn_group"> '
                            +'<button type="button" class="btn btn-warning btn-sm delete-btn">删除</button>'
                            +'<button type="button"  class="btn btn-danger btn-sm prev">上移</button>'
                            +'<button type="button"  class="btn btn-danger btn-sm next">下移</button>'
                            +'</td>'
                            +'</tr>';
                        $("#subject-table tbody").append(strhtml_list);
                    }
                    /*对新增元素添加绑定事件*/
                    $(".delete-btn").off().on("click",function () {
                        if(confirm("确认删除吗？")){
                            $(this).parents("tr").remove();
                        }
                    });
                    $(".next").off().on("click",function () {
                        $(this).parents("tr").next("tr").after($(this).parents("tr"));
                    });
                    $(".prev").off().on("click",function () {
                        $(this).parents("tr").prev("tr").before($(this).parents("tr"));
                    });
                }
            } else {
                alert(data.errMsg);
            }
        }
    });
}

function genSelectAndSubjectAndInit(){
    var str = '';
    getOData(str,"coreDirMovie/find/all",{fn:function(oData){
        var arrData = oData.data;
        var ln = arrData.length;
        for(var i=0;i<ln;i++){
            strhtml_goodColumns += '<option value ="'+arrData[i].crdirName+'" >'+arrData[i].crdirName+'</option>';
        }
        subject = '<tr class="subject-item">'
            +'<th scope="row"><input type="text" class="form-control num"   style=" width: 65px;"  placeholder="编号"></th>'
            +'<td> <input type="text" class="form-control answer" placeholder="答案"></td>'
            +'<td> <input type="number" class="form-control score" style=" width: 65px;"  placeholder="分值"></td>'
            +'<td> <select class="form-control file" placeholder="目录">'+strhtml_goodColumns+'</select></td>'
            +'<td> <input type="text" class="form-control name"  placeholder="名称"></td>'
            +'<td class="btn_group"> '
            +'<button type="button" class="btn btn-warning btn-sm delete-btn">删除</button>'
            +'<button type="button"  class="btn btn-danger btn-sm prev">上移</button>'
            +'<button type="button"  class="btn btn-danger btn-sm next">下移</button>'
            +'</td>'
            +'</tr>';
        initAdd(getQueryString("id"));
    }});
}

function objVal(obj, classname) {
    return $(obj).find(classname).val();
}

//检查提交
function checkAdd() {
    arr=[];
    var flag = false;
    $(".subject-item").each(function () {
        if(objVal(this, ".num")==""||objVal(this, ".answer")==""||objVal(this, ".score")==""||objVal(this, ".file")==""||objVal(this, ".name")==""){
            alert("请将题目填写完整！");
            flag = true;
            return false;
        }
        var num=$(this).find(".num").val();
        var answer=$(this).find(".answer").val();
        var score=$(this).find(".score").val();
        var file=$(this).find(".file").val();
        var name=$(this).find(".name").val();
        var json={
            "name": num,
            "result": answer,
            "oldScore": score,
            "dirMovie": file,
            "movie": name
        };
        arr.push(json);
    });
    if(flag){
        return false;
    }

    var r = confirm("是否确认编辑？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            //do something
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject) {
    var question = {};
    question.problem = arr;
    var crpesQuestion = JSON.stringify(question);
    var crpesUuid = getQueryString("id");
    var str = '';
    str += 'crpesUuid=' + encodeURIComponent(crpesUuid);
    str += '&crpesQuestion=' + encodeURIComponent(crpesQuestion);
    getOData(str, "corePapers/update/question", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("编辑题目成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}