function changeIotServiceValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("IotService_"+parent,data)){
            return;
        }
    }
}
(function(window,$){
    var iotServiceChecked=[];

    var manage = {
        tabledata:undefined,
        init:function(){
            var that = this;

            //初始化表格

            if($("#actionId").length<=0||$("#actionId").val()==''){
                jQuery.iotServiceGrid.column.push({"data": "serviceId","title":"操作","sWidth":"4em", "orderable":false,"createdCell": function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html("<a href='javascript:;' class='on-default edit-row' onclick='iotServiceManage.edit(\""+sData+"\")' >编辑</a>"+
                    "&nbsp;&nbsp;&nbsp;" +
                    "<a href='javascript:;' class='on-default edit-row' onclick='iotServiceManage.remove(\""+sData+"\")' >删除</a>");
                }})
            }

            //初始化setValue
            if($("#iotServiceQuerySearchForm #iotService_ServiceName").length>0){
                var param = $("#iotService_ServiceName").data("param");
                $("#iotService_ServiceName").val(param);
            };

            if($("#p_uri").length>0){
                var referrer = document.referrer||'';
                if(referrer.indexOf("uri")<0&&referrer!=''){
                    $("#p_uri").val(referrer);
                }
            }

            //初始化控件
            if($("#iotServiceQuerySearchForm").length>0){}

            //点击事件
            $("#iotServiceQueryBtn").on("click",function(){
                that.search();
            })
            $("#iotServicecreateBtn").on("click",function(){
                that.create();
            })
            $("#iotServicebackBtn").on("click",function(){
                var url = $("#p_uri").val()||document.referrer;
                if(url.indexOf("_ss")>=0){
                    window.location.href=url;
                }else if(url.indexOf("?")>=0){
                    window.location.href=url+"&_ss=1";
                }else {
                    window.location.href=url+"?_ss=1";
                }
            })
            $("#iotServiceClearBtn").on("click",function(){
                $("#iotServiceQuerySearchForm")[0].reset();
            })

            that.dataTable(false);
        },

        dataTable:function(flag){
            //排序,页长
            var iotServiceOrder = [];
            var iotService_sort = $("#iotService_sort").val()||'';
            var iotService_order= $("#iotService_order").val()||'';
            var iotService_pageLength= $("#iotService_pageLength").val()||10;
            if(iotService_sort!=''){
                var sortArray = iotService_sort.split(",");
                var orderArray= iotService_order.split(",");
                $.each(sortArray,function (index,item) {
                    $.each(jQuery.iotServiceGrid.column,function (i,v) {
                        if(v.data==item){
                            iotServiceOrder.push([i,orderArray[index]]);
                            return true;
                        }
                    })
                })
            }

            //获取查询参数
            this.tabledata = $("#iotServiceManageTable").DataTable({
                displayStart:+$("#iotService_pageStart").val() ||0,
                order: iotServiceOrder,
                pageLength: +iotService_pageLength,
                destroy:flag || false,
                "ajax": {
                    "url": _appsite + "iot/iotservice/query",
                    "type":"post",
                    "data": function (d) {
                        if($("#actionId").length>0){
                            var actionId = $("#actionId").val();
                            d.p_noUserId=actionId;
                        }
                        d.offset = d.start;
                        d.rows = d.length;
                        var querySearchForm = $("#iotServiceQuerySearchForm").serializeJSON();
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
                "columns": jQuery.iotServiceGrid.column,
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

            window.location.href= _appsite +"iot/iotservice/edit/"+param;

        },
        check:function (id) {
            var actionType = $("#actionType").val();
            if(actionType == "radio"){
                $("#iotServiceChecked").val(id);
            }else{
                if(!$("#"+id).is(':checked')){
                    $.each(iotServiceChecked,function (index,item) {
                        if(iotServiceChecked[index]==id){
                            iotServiceChecked.splice(index,1)
                        }
                    })
                }else {
                    iotServiceChecked.push(id);
                }
                $("#iotServiceChecked").val(iotServiceChecked.join(","));
            }

        },
        edit:function(id){
            var param =this.getPara(id);
            window.location.href= _appsite +"iot/iotservice/edit/"+param;
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
                                url:_appsite+"iot/iotservice/delete",
                                data:{id:id},
                                success:function(result){
                                    if(result.result==true) {
                                        iotServiceManage.dataTable(true);
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
                                        iotServiceManage.dataTable(true);
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

    var iotServiceChildFunction = function childFunction() {
        var datas = $("#iotServiceChecked").val();
        return datas;

    }

    window.iotServiceChildManage = iotServiceChildFunction;

    window.iotServiceManage = manage;

})(window,jQuery)