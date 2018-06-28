function changeUserServiceValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("UserService_"+parent,data)){
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
            var id = $("#serviceId").val();
            var that = this;
            var _uri =$("#_uri").val();
            var _tab =$("#_tab").val();

            if($("#userService_picker").length>0){
                uploader();
            }

            //成员新增赋值
            if(actionType=='create'){}

            //初始化控件
            if($("#userServiceEditForm").length>0){}

            //点击事件
            $("#userServiceeditCancleBtn").on("click",function(){
                that.cancle();
            })
            $("#userServiceSaveBtn").on("click",function(){
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
            var validresult = $("#userServiceEditForm").valid();
            $("#userServiceSaveBtn").attr('disabled', true);
            if(validresult){
                var formData = $("#userServiceEditForm").serializeJSON();
                $.ajax({
                    type: 'POST',
                    url: _appsite + "iot/userservice/update",
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
                            $("#userServiceSaveBtn").attr('disabled', false);
                            $.alert({title : '提示',content : result.msg||'保存失败'});

                        }
                    },
                    error: function (result) {
                        $.alert({title : '提示',content : '网络异常'});
                        $("#userServiceSaveBtn").attr('disabled', false);
                    }
                });
            }else{
                $("#userServiceSaveBtn").attr('disabled', false);
            }

        },

    }

    $(function(){
        edit.init();
    });

    window.userServiceEdit = edit;

})(window,jQuery)