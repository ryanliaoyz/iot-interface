<#import "/common/macro.ftl" as mc/>
<@mc.mHeader/>
<#include "/common/globle.ftl"/>
<@mc.mMenu $reqSysMenu/>

<@mc.mTop/>
<div class="wrapper">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>用户<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>管理系统</a></li>
                <li class="active">用户</li>
            </ol>
        </section>

        <div class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="col-sm-8">
                                <input type="hidden"   name="_ss" value="${RequestParameters['_ss']!}" id="_ss" />
                                <input type="hidden"   name="_uri" value="${rc.requestUri!}" id="_uri" />
                                <input type="hidden"   name="_tab" value="${(oldParams['_tab'])!''}" id="_tab" />
                                <input type="hidden" name="p_uri" id="p_uri" value="${(oldParams['p_uri'])!''}" />
                                <button class="btn btn-primary" type="button" id="iotUserSaveBtn"><i class="fa fa-check ">保存</i></button>
                                <button id="iotUsereditCancleBtn" type="button" class="btn btn-primary"><i class="fa fa-reply"> 返回 </i></button>
                            </div>
                        </div>
                    </div> <!-- panel -->
                </div> <!-- col -->
            </div><!-- row -->
            <div class="nav-tabs-custom">
                <ul class="nav nav-tabs panel-heading" role="tablist">
                    <#if (oldParams['_tab'])??>
                    <li >
                        <#else>
                        <li class="active">
                    </#if>
                    <a href="#tab_iotUser" data-toggle="tab" data-loadflag="false" data-content="">用户</a></li>
                    <#if actionType?? && actionType!='create'>
                    <#if (oldParams['_tab'])?? &&oldParams['_tab']=='userService'>
                    <li class="active">
                        <#else><li>
                    </#if>
                    <a href="#tab_iotUser_userService" data-toggle="tab" data-loadflag="false" data-content="userService">服务</a></li>
                    <#if (oldParams['_tab'])?? &&oldParams['_tab']=='iotDevice'>
                    <li class="active">
                        <#else><li>
                    </#if>
                    <a href="#tab_iotUser_iotDevice" data-toggle="tab" data-loadflag="false" data-content="iotDevice">设备</a></li>
                    </#if>

                </ul>

                <div class="tab-content">
                    <#if (oldParams['_tab'])??>
                    <div class="tab-pane " id="tab_iotUser">
                        <#else>
                        <div class="tab-pane active" id="tab_iotUser">
                    </#if>
                    <div class="panel-body">
                        <form id="iotUserEditForm" class="form-horizontal" role="form" enctype="multipart/form-data" autocomplete="off">
                            <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>
                            <input type="hidden" name="iotUserId" id="iotUserId" value="${(iotUser.iotUserId)!''}"/>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>用户名</label>
                                <div class="col-sm-6">
                                    <input name="userName" id="userName" class="form-control"  value="${(iotUser.userName)!}"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>密码</label>
                                <div class="col-sm-6">
                                    <input type="password" name="userPassword" id="userPassword" class="form-control" value="${(iotUser.userPassword)!}"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>密钥</label>
                                <div class="col-sm-6">
                                    <input name="userToken" id="userToken" class="form-control"  value="${(iotUser.userToken)!}"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>邮箱</label>
                                <div class="col-sm-6">
                                    <input name="userEmail" id="userEmail" class="form-control"  value="${(iotUser.userEmail)!}"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>显示名</label>
                                <div class="col-sm-6">
                                    <input name="userDisplayName" id="userDisplayName" class="form-control"  value="${(iotUser.userDisplayName)!}"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>激活</label>
                                <div class="col-sm-6">
                                    <input name="userValid" id="userValid" class="form-control"  value="${(iotUser.userValid)!}"/>
                                </div>
                            </div>
                            <input type="hidden" name="userDate" id="userDate" class="form-control" value="${(iotUser.userDate)!''}"/>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label">余额</label>
                                <div class="col-sm-6">
                                    <input name="userBalance" id="userBalance" readonly class="form-control" value="${(iotUser.userBalance)!}"/>
                                </div>
                            </div>
                        </form>
                    </div> <!-- panel-body -->
                        </div>
                        <#if actionType?? && actionType!='create'>
                        <input id="userServiceChecked" type="hidden"></input>
                        <#if (oldParams['_tab'])?? &&oldParams['_tab']=='userService'>
                        <div class="tab-pane active" id="tab_iotUser_userService" >
                            <#else>
                            <div class="tab-pane" id="tab_iotUser_userService" >
                        </#if>
                        <div class="panel-body">
                            <form class="cmxform form-horizontal tasi-form" id="userServiceQuerySearchForm" novalidate="novalidate" style="margin-bottom:0;">
                                <input type="hidden" id="p_userId" name="p_userId" value="${(iotUser.iotUserId)!''}" />
                                <input type="hidden" id="userService_pageStart" value="${(oldParams['start'])!}" />
                                <input type="hidden" id="userService_sort" value="${(oldParams['sort'])!}" />
                                <input type="hidden" id="userService_order" value="${(oldParams['order'])!}" />
                                <input type="hidden" id="userService_pageLength" value="${(oldParams['rows'])!}" />
                            </form>
                            <div class="col-md-12" style="text-align:right;">
                                <button id="userServiceaddBtn" class="btn btn-primary" >添加</button>
                                <button id="userServicedelBtn" class="btn btn-primary" >移除</button>
                            </div>
                            <table id="userServiceManageTable" class="table table-striped table-bordered table-hover table-full-width totemManageTable" style="overflow-x: inherit;" width="100%">
                            </table>
                        </div>
                            </div>

                            <input id="iotDeviceChecked" type="hidden"></input>
                            <#if (oldParams['_tab'])?? &&oldParams['_tab']=='iotDevice'>
                            <div class="tab-pane active" id="tab_iotUser_iotDevice" >
                                <#else>
                                <div class="tab-pane" id="tab_iotUser_iotDevice" >
                            </#if>
                            <div class="panel-body">
                                <form class="cmxform form-horizontal tasi-form" id="iotDeviceQuerySearchForm" novalidate="novalidate" style="margin-bottom:0;">
                                    <input type="hidden" id="deviceBelonging" name="deviceBelonging" value="${(iotUser.iotUserId)!''}" />
                                    <input type="hidden" id="iotDevice_pageStart" value="${(oldParams['start'])!}" />
                                    <input type="hidden" id="iotDevice_sort" value="${(oldParams['sort'])!}" />
                                    <input type="hidden" id="iotDevice_order" value="${(oldParams['order'])!}" />
                                    <input type="hidden" id="iotDevice_pageLength" value="${(oldParams['rows'])!}" />
                                </form>
                                <div class="col-md-12" style="text-align:right;">
                                    <button id="iotDevicecreateBtn" class="btn btn-primary" >新增</button>
                                </div>
                                <table id="iotDeviceManageTable" class="table table-striped table-bordered table-hover table-full-width totemManageTable" style="overflow-x: inherit;" width="100%">
                                </table>
                            </div>
                                </div>

                        </#if>

                            </div>

                        </div>
                    </div><!-- content -->
                </div>

                <!-- Main Footer -->
                <@mc.mFooter/>
            </div>
            <@mc.mJsCommon/>

            <#include "/iot/header.ftl">

            <script type="text/javascript" src="${appsite}assets/js/iot/userservice/grid.js?${.now?date}"></script>
            <script type="text/javascript" src="${appsite}assets/js/iot/userservice/manage.js?${.now?date}"></script>
            <script type="text/javascript" src="${appsite}assets/js/iot/iotdevice/grid.js?${.now?date}"></script>
            <script type="text/javascript" src="${appsite}assets/js/iot/iotdevice/manage.js?${.now?date}"></script>
            <script type="text/javascript" src="${appsite}assets/js/iot/iotuser/validate.js?${.now?date}"></script>
            <script type="text/javascript" src="${appsite}assets/js/iot/iotuser/edit.js?${.now?date}"></script>
            <@mc.mEnd/>
