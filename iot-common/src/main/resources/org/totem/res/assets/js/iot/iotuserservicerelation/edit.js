function changeIotUserServiceRelationValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("IotUserServiceRelation_"+parent,data)){
            return;
        }
    }
}
(function(window,$){
    var edit = {
        init:function(){
            //图片
            $('.viewer').viewer({url:'data-original'});

            //变量
            var actionType = $("#actionType").val();
            var id = $("#iotUserServiceRelationId").val();
            var that = this;
            var _uri =$("#_uri").val();
            var _tab =$("#_tab").val();

            if($("#iotUserServiceRelation_picker").length>0){
                uploader();
            }

            //成员新增赋值
            if(actionType=='create'){
                if($("#iotUserServiceRelation_UserId").length>0&&$("#iotUserServiceRelation_UserId").val()!=""&&$("#userId").length>0){
                    var iotUserServiceRelation_UserId = $("#iotUserServiceRelation_UserId").val();
                    $("#userId").val(iotUserServiceRelation_UserId);
                    $("#userId").parent().parent().hide();
                }
                if($("#iotUserServiceRelation_ServiceId").length>0&&$("#iotUserServiceRelation_ServiceId").val()!=""&&$("#serviceId").length>0){
                    var iotUserServiceRelation_ServiceId = $("#iotUserServiceRelation_ServiceId").val();
                    $("#serviceId").val(iotUserServiceRelation_ServiceId);
                    $("#serviceId").parent().parent().hide();
                }
            }

            //初始化控件
            if($("#iotUserServiceRelationEditForm").length>0){
                if($("#iotUserServiceRelationEditForm #userId").length>0){
                    $("#userId").select2({
                        data:$.map($.totemUtils.getJson('iot/iotuser/queryAuthAll','get'), function (o) {
                            return {id: o.iotUserId, text: o.userName}
                        }),
                        placeholder: "请选择",
                        allowClear: true
                    });
                }
                if($("#iotUserServiceRelationEditForm #userId").length>0){
                    $("#userId").on("select2:select",function(e){
                        if(e.params.data!=undefined){
                            changeIotUserServiceRelationValue("userId",e.params.data);
                        }
                    })
                }

                if($("#iotUserServiceRelationEditForm #serviceId").length>0){
                    $("#serviceId").select2({
                        data:$.map($.totemUtils.getJson('iot/userservice/queryAuthAll','get'), function (o) {
                            return {id: o.serviceId, text: o.serviceName}
                        }),
                        placeholder: "请选择",
                        allowClear: true
                    });
                }
                if($("#iotUserServiceRelationEditForm #serviceId").length>0){
                    $("#serviceId").on("select2:select",function(e){
                        if(e.params.data!=undefined){
                            changeIotUserServiceRelationValue("serviceId",e.params.data);
                        }
                    })
                }

            }

            //点击事件
            $("#iotUserServiceRelationeditCancleBtn").on("click",function(){
                that.cancle();
            })
            $("#iotUserServiceRelationSaveBtn").on("click",function(){
                that.save();
            })

        },

        cancle:function () {
            var url = $("#p_uri").val()||document.referrer;
            if(url.indexOf("_ss")>=0){
                window.location.href=url;
            }else if(url.indexOf("?")>=0){
                window.location.href=url+"&_ss=1";
            }else {
                window.location.href=url+"?_ss=1";
            }
        },
        save:function(){
            var that=this;
            var validresult = $("#iotUserServiceRelationEditForm").valid();
            $("#iotUserServiceRelationSaveBtn").attr('disabled', true);
            if(validresult){
                var formData = $("#iotUserServiceRelationEditForm").serializeJSON();
                $.ajax({
                    type: 'POST',
                    url: _appsite + "iot/iotuserservicerelation/update",
                    data: formData,
                    dataType: 'json',
                    async: false,
                    success: function (result) {
                        try{
                            result = eval('(' + result + ')');
                        }catch (e){}

                        if(result.result==true){
                            $.confirm({
                                title: '提示',
                                content: '保存成功',
                                buttons: {
                                    confirm: {
                                        text:"确认",
                                        action: function(){
                                            that.cancle();
                                        }
                                    }
                                }
                            });
                        }else{
                            $("#iotUserServiceRelationSaveBtn").attr('disabled', false);
                            $.alert({title : '提示',content : result.msg||'保存失败'});

                        }
                    },
                    error: function (result) {
                        $.alert({title : '提示',content : '网络异常'});
                        $("#iotUserServiceRelationSaveBtn").attr('disabled', false);
                    }
                });
            }else{
                $("#iotUserServiceRelationSaveBtn").attr('disabled', false);
            }

        },

    }

    $(function(){
        edit.init();
    });

    window.iotUserServiceRelationEdit = edit;

})(window,jQuery)