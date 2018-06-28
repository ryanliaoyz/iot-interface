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
            <h1>用户<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>用户管理</a></li>
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
                                <input id="iotUserChecked" type="hidden"></input>
                                <form class="cmxform form-horizontal tasi-form" id="iotUserQuerySearchForm" novalidate="novalidate" style="margin-bottom:0;">

                                <input type="hidden" name="actionId" id="actionId" value="${actionId!}"/>
                                <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>

                                <input type="hidden" id="_ss"  name="_ss" value="${RequestParameters['_ss']!}" />
                                <input type="hidden" id="_uri"  name="_uri" value="${rc.requestUri!}" />
                                <input type="hidden" id="iotUser_pageStart" value="${(oldParams['start'])!}" />
                                <input type="hidden" id="iotUser_pageLength" value="${(oldParams['rows'])!}" />
                                <input type="hidden" id="iotUser_sort" value="${(oldParams['sort'])!'userDate'}" />
                                <input type="hidden" id="iotUser_order" value="${(oldParams['order'])!'desc'}" />

                                <div class="form-group col-sm-6">
                                    <label class="col-sm-4 control-label">激活</label>
                                    <div class="col-sm-6">
                                        <input name="userValid" id="userValid" class="form-control" data-param="${(oldParams['userValid'])!}"/>
                                    </div>
                                </div>
                                <div class="form-group col-sm-6">
                                    <label class="col-sm-4 control-label">用户名</label>
                                    <div class="col-sm-6">
                                        <input name="userNameLIK" id="iotUser_UserName" class="form-control" data-param="${(oldParams['userNameLIK'])!}" value=""/>
                                    </div>
                                </div>
                                <div class="form-group col-sm-6">
                                    <label class="col-sm-4 control-label">邮箱</label>
                                    <div class="col-sm-6">
                                        <input name="userEmailLIK" id="iotUser_UserEmail" class="form-control" data-param="${(oldParams['userEmailLIK'])!}" value=""/>
                                    </div>
                                </div>

                                </form>
                            </div> <!-- row -->
                            <div class="col-md-12" style="text-align:right;">
                                <button id="iotUserQueryBtn" class="btn btn-primary" >查询</button>
                                <button id="iotUserClearBtn" class="btn btn-primary" >清空</button>
                                <#if !actionId??>
                                <button id="iotUsercreateBtn" class="btn btn-primary" >新增</button>
                                <button id="iotUserbackBtn" class="btn btn-primary" >返回</button>
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
                    <div class="panel-heading" style="overflow-y:hidden">用户</div>
                    <div class="panel-body">
                        <div class="table-responsive1">
                            <table id="iotUserManageTable" class="table table-striped table-bordered totemManageTable" style="overflow-x: inherit;" width="100%">
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

<script type="text/javascript" src="${appsite}assets/js/iot/iotuser/grid.js?${.now?date}"></script>
<script type="text/javascript" src="${appsite}assets/js/iot/iotuser/manage.js?${.now?date}"></script>
<@mc.mEnd/>
