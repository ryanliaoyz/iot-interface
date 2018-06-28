function changeIotDeviceValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("IotDevice_"+parent,data)){
            return;
        }
    }
}
(function(window,$){
    var iotDeviceChecked=[];

    var manage = {
        tabledata:undefined,
        init:function(){
            var that = this;

            //初始化表格

            if($("#actionId").length<=0||$("#actionId").val()==''){
                jQuery.iotDeviceGrid.column.push({"data": "deviceId","title":"操作","sWidth":"4em", "orderable":false,"createdCell": function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html("<a href='javascript:;' class='on-default edit-row' onclick='iotDeviceManage.edit(\""+sData+"\")' >编辑</a>"+
                    "&nbsp;&nbsp;&nbsp;" +
                    "<a href='javascript:;' class='on-default edit-row' onclick='iotDeviceManage.remove(\""+sData+"\")' >删除</a>");
                }})
            }

            //初始化setValue

            if($("#p_uri").length>0){
                var referrer = document.referrer||'';
                if(referrer.indexOf("uri")<0&&referrer!=''){
                    $("#p_uri").val(referrer);
                }
            }

            //初始化控件
            if($("#iotDeviceQuerySearchForm").length>0){}

            //点击事件
            $("#iotDeviceQueryBtn").on("click",function(){
                that.search();
            })
            $("#iotDevicecreateBtn").on("click",function(){
                that.create();
            })
            $("#iotDevicebackBtn").on("click",function(){
                var url = $("#p_uri").val()||document.referrer;
                if(url.indexOf("_ss")>=0){
                    window.location.href=url;
                }else if(url.indexOf("?")>=0){
                    window.location.href=url+"&_ss=1";
                }else {
                    window.location.href=url+"?_ss=1";
                }
            })
            $("#iotDeviceClearBtn").on("click",function(){
                $("#iotDeviceQuerySearchForm")[0].reset();
            })

            that.dataTable(false);
        },

        dataTable:function(flag){
            //排序,页长
            var iotDeviceOrder = [];
            var iotDevice_sort = $("#iotDevice_sort").val()||'';
            var iotDevice_order= $("#iotDevice_order").val()||'';
            var iotDevice_pageLength= $("#iotDevice_pageLength").val()||10;
            if(iotDevice_sort!=''){
                var sortArray = iotDevice_sort.split(",");
                var orderArray= iotDevice_order.split(",");
                $.each(sortArray,function (index,item) {
                    $.each(jQuery.iotDeviceGrid.column,function (i,v) {
                        if(v.data==item){
                            iotDeviceOrder.push([i,orderArray[index]]);
                            return true;
                        }
                    })
                })
            }

            //获取查询参数
            this.tabledata = $("#iotDeviceManageTable").DataTable({
                displayStart:+$("#iotDevice_pageStart").val() ||0,
                order: iotDeviceOrder,
                pageLength: +iotDevice_pageLength,
                destroy:flag || false,
                "ajax": {
                    "url": _appsite + "iot/iotdevice/query",
                    "type":"post",
                    "data": function (d) {
                        if($("#actionId").length>0){
                            var actionId = $("#actionId").val();
                        }
                        d.offset = d.start;
                        d.rows = d.length;
                        var querySearchForm = $("#iotDeviceQuerySearchForm").serializeJSON();
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
                "columns": jQuery.iotDeviceGrid.column,
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
            if ($("#deviceType").length>0&&$("#deviceType").val()!=""){
                param += "&iotDevice_DeviceType="+$("#deviceType").val();
            }
            if ($("#deviceService").length>0&&$("#deviceService").val()!=""){
                param += "&iotDevice_DeviceService="+$("#deviceService").val();
            }
            if ($("#deviceBelonging").length>0&&$("#deviceBelonging").val()!=""){
                param += "&iotDevice_DeviceBelonging="+$("#deviceBelonging").val();
            }

            return param;

        },
        search:function(){
            //获取查询参数
            this.tabledata.ajax.reload();
        },
        create:function(){
            var param =this.getPara("create");

            window.location.href= _appsite +"iot/iotdevice/edit/"+param;

        },
        check:function (id) {
            var actionType = $("#actionType").val();
            if(actionType == "radio"){
                $("#iotDeviceChecked").val(id);
            }else{
                if(!$("#"+id).is(':checked')){
                    $.each(iotDeviceChecked,function (index,item) {
                        if(iotDeviceChecked[index]==id){
                            iotDeviceChecked.splice(index,1)
                        }
                    })
                }else {
                    iotDeviceChecked.push(id);
                }
                $("#iotDeviceChecked").val(iotDeviceChecked.join(","));
            }

        },
        edit:function(id){
            var param =this.getPara(id);
            window.location.href= _appsite +"iot/iotdevice/edit/"+param;
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
                                url:_appsite+"iot/iotdevice/delete",
                                data:{id:id},
                                success:function(result){
                                    if(result.result==true) {
                                        iotDeviceManage.dataTable(true);
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

    }

    $(function(){
        manage.init();
    });

    var iotDeviceChildFunction = function childFunction() {
        var datas = $("#iotDeviceChecked").val();
        return datas;

    }

    window.iotDeviceChildManage = iotDeviceChildFunction;

    window.iotDeviceManage = manage;

})(window,jQuery)