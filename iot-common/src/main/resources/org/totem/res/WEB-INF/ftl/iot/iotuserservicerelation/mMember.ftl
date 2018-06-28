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
            <h1>用户服务关系<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>用户服务关系管理</a></li>
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
                                <input id="iotUserServiceRelationChecked" type="hidden"></input>
                                <form class="cmxform form-horizontal tasi-form" id="iotUserServiceRelationQuerySearchForm" novalidate="novalidate" style="margin-bottom:0;">

                                <input type="hidden" name="actionId" id="actionId" value="${actionId!}"/>
                                <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>

                                <input type="hidden" id="_ss"  name="_ss" value="${RequestParameters['_ss']!}" />
                                <input type="hidden" id="_uri"  name="_uri" value="${rc.requestUri!}" />
                                <input type="hidden" id="iotUserServiceRelation_pageStart" value="${(oldParams['start'])!}" />
                                <input type="hidden" id="iotUserServiceRelation_pageLength" value="${(oldParams['rows'])!}" />
                                <input type="hidden" id="iotUserServiceRelation_sort" value="${(oldParams['sort'])!''}" />
                                <input type="hidden" id="iotUserServiceRelation_order" value="${(oldParams['order'])!''}" />
                                <input name="userId" id="userId" value="${userId!}" type="hidden" />
                                <input name="noUserId" id="noUserId" value="${noUserId!}" type="hidden" />
                                <input name="serviceId" id="serviceId" value="${serviceId!}" type="hidden" />
                                <input name="noServiceId" id="noServiceId" value="${noServiceId!}" type="hidden" />

                                </form>
                            </div> <!-- row -->
                            <div class="col-md-12" style="text-align:right;">
                                <button id="iotUserServiceRelationQueryBtn" class="btn btn-primary" >查询</button>
                                <button id="iotUserServiceRelationClearBtn" class="btn btn-primary" >清空</button>
                                <#if !actionId??>
                                <button id="iotUserServiceRelationcreateBtn" class="btn btn-primary" >新增</button>
                                <button id="iotUserServiceRelationbackBtn" class="btn btn-primary" >返回</button>
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
                    <div class="panel-heading" style="overflow-y:hidden">用户服务关系</div>
                    <div class="panel-body">
                        <div class="table-responsive1">
                            <table id="iotUserServiceRelationManageTable" class="table table-striped table-bordered totemManageTable" style="overflow-x: inherit;" width="100%">
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

<script type="text/javascript" src="${appsite}assets/js/iot/iotuserservicerelation/grid.js?${.now?date}"></script>
<script type="text/javascript" src="${appsite}assets/js/iot/iotuserservicerelation/manage.js?${.now?date}"></script>
<@mc.mEnd/>
