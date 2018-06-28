<#import "/common/macro.ftl" as mc/>
<@mc.mHeader/>
<#include "/common/globle.ftl" />
<#if (actionType!"edit")!="list">
<@mc.mMenu $reqSysMenu/>
<@mc.mTop/>
</#if>

<div class="wrapper">
    <#if !actionId??>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>服务<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>服务管理</a></li>
            </ol>
        </section>
    </#if>

    <div class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">查询</div>
                    <div class="panel-body">
                        <div class="form">
                            <div class="row">
                                <input id="userServiceChecked" type="hidden"></input>
                                <form class="cmxform form-horizontal tasi-form" id="userServiceQuerySearchForm" novalidate="novalidate" style="margin-bottom:0;">
                                    <input name="iotUserServiceRelationUserId" id="iotUserServiceRelationUserId" value="${iotUserServiceRelationUserId!}" type="hidden" />
                                    <input name="noIotUserServiceRelationUserId" id="noIotUserServiceRelationUserId" value="${noIotUserServiceRelationUserId!}" type="hidden" />
                                    <input name="iotUserServiceRelationUserIds" id="iotUserServiceRelationUserIds" value="${iotUserServiceRelationUserIds!}" type="hidden" />
                                    <input name="noIotUserServiceRelationUserIds" id="noIotUserServiceRelationUserIds" value="${noIotUserServiceRelationUserIds!}" type="hidden" />

                                    <input type="hidden" name="actionId" id="actionId" value="${actionId!}"/>
                                    <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>

                                    <input type="hidden" id="_ss"  name="_ss" value="${RequestParameters['_ss']!}" />
                                    <input type="hidden" id="_uri"  name="_uri" value="${rc.requestUri!}" />
                                    <input type="hidden" id="userService_pageStart" value="${(oldParams['start'])!}" />
                                    <input type="hidden" id="userService_pageLength" value="${(oldParams['rows'])!}" />
                                    <input type="hidden" id="userService_sort" value="${(oldParams['sort'])!''}" />
                                    <input type="hidden" id="userService_order" value="${(oldParams['order'])!''}" />

                                    <div class="form-group col-sm-6">
                                        <label class="col-sm-4 control-label">服务名</label>
                                        <div class="col-sm-6">
                                            <input name="serviceName" id="userService_ServiceName" class="form-control" data-param="${(oldParams['serviceName'])!}" value=""/>
                                        </div>
                                    </div>

                                </form>
                            </div> <!-- row -->
                            <div class="col-md-12" style="text-align:right;">
                                <button id="userServiceQueryBtn" class="btn btn-primary" >查询</button>
                                <button id="userServiceClearBtn" class="btn btn-primary" >清空</button>
                                <#if !actionId??>
                                <button id="userServicecreateBtn" class="btn btn-primary" >新增</button>
                                <button id="userServicebackBtn" class="btn btn-primary" >返回</button>
                                </#if>
                            </div>

                        </div>
                    </div> <!-- panel-body -->
                </div> <!-- panel -->
            </div> <!-- col -->
        </div> <!-- row -->
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading" style="overflow-y:hidden">服务</div>
                    <div class="panel-body">
                        <div class="table-responsive1">
                            <table id="userServiceManageTable" class="table table-striped table-bordered totemManageTable" style="overflow-x: inherit;" width="100%">
                            </table>
                        </div>
                    </div> <!-- panel-body -->
                </div>  <!-- panel -->
            </div> <!-- col -->
        </div> <!-- row -->
    </div>

    <#if !actionId??>
    </div>
    </#if>

    <!-- Main Footer -->
    <#if !actionId?? >
    <@mc.mFooter/>
    </#if>
</div>

<@mc.mJsCommon/>
<#include "/iot/header.ftl">

<script type="text/javascript" src="${appsite}assets/js/iot/userservice/grid.js?${.now?date}"></script>
<script type="text/javascript" src="${appsite}assets/js/iot/userservice/manage.js?${.now?date}"></script>
<@mc.mEnd/>
