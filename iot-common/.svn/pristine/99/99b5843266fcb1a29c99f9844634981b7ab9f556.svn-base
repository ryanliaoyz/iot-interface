function changeIotUserValue(parent,data){
    if ($.t_iot!=undefined&&$.t_iot.onChange!=undefined){
        if (!$.t_iot.onChange("IotUser_"+parent,data)){
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
            var id = $("#iotUserId").val();
            var that = this;
            var _uri =$("#_uri").val();
            var _tab =$("#_tab").val();

            if($("#iotUser_picker").length>0){
                uploader();
            }

            //成员新增赋值
            if(actionType=='create'){}

            //初始化控件
            if($("#iotUserEditForm").length>0){
                if($("#userValid").length>0){
                    $("#userValid").select2({
                        data : [{id:"1",text:"启用"},{id:"0",text:"停用"}],
                        placeholder: "请选择",
                        allowClear: true
                    });
                }
            }

            //点击事件
            $("#iotUsereditCancleBtn").on("click",function(){
                that.cancle();
            })
            $("#iotUserSaveBtn").on("click",function(){
                that.save();
            })

            //成员

            //点击tab，释放
            $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
                var $this= $(this);
                var flag = $this.data("loadflag");
                var tabIndex= $this.data("content");
                $("#_tab").val(tabIndex);
                if (tabIndex=='userService' && !flag &&userServiceManage.dataTable) {
                    userServiceManage.dataTable(true);
                }
                if (tabIndex=='iotDevice' && !flag &&iotDeviceManage.dataTable) {
                    iotDeviceManage.dataTable(true);
                }

            })
            //点击tab完初始化
            $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                var $this= $(this);
                var flag = $this.data("loadflag");
                var tabIndex= $this.data("content");
                $this.data("loadflag",true)
            })

            $(window).resize(function () {
                $('a[data-toggle="tab"]').each(function () {
                    $(this).data("loadflag",false);
                })
            });

            $("#userServiceaddBtn").on("click",function () {
                var para = "?actionId="+id+"&actionType=list";
                user = $.confirm({
                    title: '添加',
                    columnClass: 'col-md-10 col-md-offset-1 col-sm-8 col-sm-offset-4 col-xs-6 col-xs-offset-1',
                    content: '<iframe frameborder="0" src=' + _appsite + 'iot/userservice'+para+' style="height:400px;width:100%;"  id="userServicechildframe"/>',
                    buttons: {
                        confirm: {
                            text: "确认",
                            action: function () {
                                var datas = $("#userServicechildframe").get(0).contentWindow.userServiceChildManage();
                                var serviceIddatas = datas.split(",");
                                $.each(serviceIddatas,function(index,item){
                                    if(item!=""){
                                        $.ajax({
                                            type: 'POST',
                                            url: _appsite + "iot/iotuserservicerelation/update",
                                            data: {"userId":id,"serviceId":item},
                                            dataType: 'json',
                                            async: false,
                                            success: function (result) {
                                                try{
                                                    result = eval('(' + result + ')');
                                                }catch (e){}

                                                if(result.result==true) {
                                                    userServiceManage.dataTable(true);
                                                    //$.alert({title : '提示',content : '添加成功'});
                                                    }else{
                                                        $.alert({title : '提示',content : result.msg||'添加失败'});
                                                }
                                            },
                                            error: function (result) {
                                                $.alert({title : '提示',content : result.msg||'网络异常'});
                                            }
                                        });
                                    }
                                })

                            }
                        },
                        cancel: {
                            text: "取消"
                        }
                    }
                })
            })
            $("#userServicedelBtn").on("click",function () {
                var memberIds = $("#userServiceChecked").val().split(",");
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
                                    data:{"userId":id,"serviceIds":memberIds},
                                    success:function(result){
                                        try{
                                            result = eval('(' + result + ')');
                                        }catch (e){}
                                        if(result.result==true) {
                                            userServiceManage.dataTable(true);
                                            //$.alert({title : '提示',content : '移除成功'});
                                            }else{
                                                $.alert({title : '提示',content : result.msg||'移除失败'});
                                        }
                                    },
                                    error:function(result){
                                        try{
                                            result = eval('(' + result + ')');
                                        }catch (e){}

                                        $.alert({title : '提示',content : result.msg||'网络异常'});
                                    }
                                });
                            }
                        },
                        cancel:{
                            text:"取消"
                        }
                    }
                })
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
            var validresult = $("#iotUserEditForm").valid();
            $("#iotUserSaveBtn").attr('disabled', true);
            if(validresult){
                var formData = $("#iotUserEditForm").serializeJSON();
                $.ajax({
                    type: 'POST',
                    url: _appsite + "iot/iotuser/update",
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
                            $("#iotUserSaveBtn").attr('disabled', false);
                            $.alert({title : '提示',content : result.msg||'保存失败'});

                        }
                    },
                    error: function (result) {
                        $.alert({title : '提示',content : '网络异常'});
                        $("#iotUserSaveBtn").attr('disabled', false);
                    }
                });
            }else{
                $("#iotUserSaveBtn").attr('disabled', false);
            }

        },

    }

    $(function(){
        edit.init();
    });

    window.iotUserEdit = edit;

})(window,jQuery)