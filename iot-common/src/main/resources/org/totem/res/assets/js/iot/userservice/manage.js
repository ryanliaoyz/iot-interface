function changeUserServiceValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("UserService_"+parent,data)){
            return;
        }
    }
}
(function(window,$){
    var userServiceChecked=[];

    var manage = {
        tabledata:undefined,
        init:function(){
            var that = this;

            //初始化表格

            if($("#actionId").length<=0||$("#actionId").val()==''){
                jQuery.userServiceGrid.column.push({"data": "serviceId","title":"操作","sWidth":"4em", "orderable":false,"createdCell": function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html("<a href='javascript:;' class='on-default edit-row' onclick='userServiceManage.edit(\""+sData+"\")' >编辑</a>"+
                    "&nbsp;&nbsp;&nbsp;" +
                    "<a href='javascript:;' class='on-default edit-row' onclick='userServiceManage.remove(\""+sData+"\")' >删除</a>");
                }})
            }

            //初始化setValue
            if($("#userServiceQuerySearchForm #userService_ServiceName").length>0){
                var param = $("#userService_ServiceName").data("param");
                $("#userService_ServiceName").val(param);
            };

            if($("#p_uri").length>0){
                var referrer = document.referrer||'';
                if(referrer.indexOf("uri")<0&&referrer!=''){
                    $("#p_uri").val(referrer);
                }
            }

            //初始化控件
            if($("#userServiceQuerySearchForm").length>0){}

            //点击事件
            $("#userServiceQueryBtn").on("click",function(){
                that.search();
            })
            $("#userServicecreateBtn").on("click",function(){
                that.create();
            })
            $("#userServicebackBtn").on("click",function(){
                var url = $("#p_uri").val()||document.referrer;
                if(url.indexOf("_ss")>=0){
                    window.location.href=url;
                }else if(url.indexOf("?")>=0){
                    window.location.href=url+"&_ss=1";
                }else {
                    window.location.href=url+"?_ss=1";
                }
            })
            $("#userServiceClearBtn").on("click",function(){
                $("#userServiceQuerySearchForm")[0].reset();
            })

            that.dataTable(false);
        },

        dataTable:function(flag){
            //排序,页长
            var userServiceOrder = [];
            var userService_sort = $("#userService_sort").val()||'';
            var userService_order= $("#userService_order").val()||'';
            var userService_pageLength= $("#userService_pageLength").val()||10;
            if(userService_sort!=''){
                var sortArray = userService_sort.split(",");
                var orderArray= userService_order.split(",");
                $.each(sortArray,function (index,item) {
                    $.each(jQuery.userServiceGrid.column,function (i,v) {
                        if(v.data==item){
                            userServiceOrder.push([i,orderArray[index]]);
                            return true;
                        }
                    })
                })
            }

            //获取查询参数
            this.tabledata = $("#userServiceManageTable").DataTable({
                displayStart:+$("#userService_pageStart").val() ||0,
                order: userServiceOrder,
                pageLength: +userService_pageLength,
                destroy:flag || false,
                "ajax": {
                    "url": _appsite + "iot/userservice/query",
                    "type":"post",
                    "data": function (d) {
                        if($("#actionId").length>0){
                            var actionId = $("#actionId").val();
                            d.p_noUserId=actionId;
                        }
                        d.offset = d.start;
                        d.rows = d.length;
                        var querySearchForm = $("#userServiceQuerySearchForm").serializeJSON();
                        $.extend(d,querySearchForm);
                        if(d.order.length>0){
                            d.sort = d.columns[d.order[0].column].data;
                            d.order = d.order[0].dir;
                        }
                        delete d.columns;
                    },
                    "dataSrc":function(json){
                        json.recordsTotal = json.total;
                        json.recordsFiltered = json.total;
                        return json.rows;
                    }
                },
                "columns": jQuery.userServiceGrid.column,
            });
        },
        getPara : function(param){
            if(param=='create'){
                param+="?actionType=create";
            }else{
                param+="?actionType=edit";
            }
            if($("#_ss").length>0){
                var _ss=$("#_ss").val();
                if(_ss!=""){
                    param+="&_ss=1";
                }
            }
            if($("#_uri").length>0){
                var _uri=$("#_uri").val();
                if(_uri!=""){
                    var uri = encodeURIComponent(_uri);
                    param+="&_uri="+uri;
                }
            }
            if($("#_tab").length>0){
                var _tab=$("#_tab").val();
                if(_tab!=""){
                    param+="&_tab="+_tab;
                }
            }
            if($("#p_uri").length>0){
                var p_uri=$("#p_uri").val();
                if(p_uri!=""){
                    var encodeURI = encodeURIComponent($("#p_uri").val());
                    param+="&p_uri="+encodeURI;
                }
            }

            return param;

        },
        search:function(){
            //获取查询参数
            this.tabledata.ajax.reload();
        },
        create:function(){
            var param =this.getPara("create");

            window.location.href= _appsite +"iot/userservice/edit/"+param;

        },
        check:function (id) {
            var actionType = $("#actionType").val();
            if(actionType == "radio"){
                $("#userServiceChecked").val(id);
            }else{
                if(!$("#"+id).is(':checked')){
                    $.each(userServiceChecked,function (index,item) {
                        if(userServiceChecked[index]==id){
                            userServiceChecked.splice(index,1)
                        }
                    })
                }else {
                    userServiceChecked.push(id);
                }
                $("#userServiceChecked").val(userServiceChecked.join(","));
            }

        },
        edit:function(id){
            var param =this.getPara(id);
            window.location.href= _appsite +"iot/userservice/edit/"+param;
        },

        remove:function(id){
            $.confirm({
                title: '提示',
                content: '是否确认删除？',
                buttons: {
                    confirm: {
                        text:"确认",
                        action: function(){
                            $.ajax({
                                type:'POST',
                                url:_appsite+"iot/userservice/delete",
                                data:{id:id},
                                success:function(result){
                                    if(result.result==true) {
                                        userServiceManage.dataTable(true);
                                    }else{
                                        $.alert({title : '提示',content : result.msg||'删除失败'});
                                    }
                                },
                                error:function(result){
                                    $.alert({title : '提示',content : result.msg||'网络异常'});
                                }
                            });
                        }
                    },
                    cancel:{
                        text:"取消"
                    }
                }
            });

        },

        iotUserDel:function(memberId,modelId){
            $.confirm({
                title: '提示',
                content: '是否确认移除？',
                buttons: {
                    confirm: {
                        text:"确认",
                        action: function(){
                            $.ajax({
                                type:'POST',
                                async: false,
                                url:_appsite+"iot/iotuserservicerelation/deletes",
                                data:{"userId":modelId,"serviceId":memberId},
                                success:function(result){
                                    if(result.result==true) {
                                        userServiceManage.dataTable(true);
                                    }else{
                                        $.alert({title : '提示',content : result.msg||'移除失败'});
                                    }
                                },
                                error:function(result){
                                    $.alert({title : '提示',content : result.msg||'网络异常'});
                                }
                            });
                        }
                    },
                    cancel:{
                        text:"取消"
                    }
                }
            });

        },
    }

    $(function(){
        manage.init();
    });

    var userServiceChildFunction = function childFunction() {
        var datas = $("#userServiceChecked").val();
        return datas;

    }

    window.userServiceChildManage = userServiceChildFunction;

    window.userServiceManage = manage;

})(window,jQuery)