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
            <h1>设备<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>设备管理</a></li>
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
                                <input id="iotDeviceChecked" type="hidden"></input>
                                <form class="cmxform form-horizontal tasi-form" id="iotDeviceQuerySearchForm" novalidate="novalidate" style="margin-bottom:0;">

                                <input type="hidden" name="actionId" id="actionId" value="${actionId!}"/>
                                <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>

                                <input type="hidden" id="_ss"  name="_ss" value="${RequestParameters['_ss']!}" />
                                <input type="hidden" id="_uri"  name="_uri" value="${rc.requestUri!}" />
                                <input type="hidden" id="iotDevice_pageStart" value="${(oldParams['start'])!}" />
                                <input type="hidden" id="iotDevice_pageLength" value="${(oldParams['rows'])!}" />
                                <input type="hidden" id="iotDevice_sort" value="${(oldParams['sort'])!''}" />
                                <input type="hidden" id="iotDevice_order" value="${(oldParams['order'])!''}" />
                                <input name="deviceType" id="deviceType" value="${deviceType!}" type="hidden" />
                                <input name="noDeviceType" id="noDeviceType" value="${noDeviceType!}" type="hidden" />
                                <input name="deviceService" id="deviceService" value="${deviceService!}" type="hidden" />
                                <input name="noDeviceService" id="noDeviceService" value="${noDeviceService!}" type="hidden" />
                                <input name="deviceBelonging" id="deviceBelonging" value="${deviceBelonging!}" type="hidden" />
                                <input name="noDeviceBelonging" id="noDeviceBelonging" value="${noDeviceBelonging!}" type="hidden" />

                                </form>
                            </div> <!-- row -->
                            <div class="col-md-12" style="text-align:right;">
                                <button id="iotDeviceQueryBtn" class="btn btn-primary" >查询</button>
                                <button id="iotDeviceClearBtn" class="btn btn-primary" >清空</button>
                                <#if !actionId??>
                                <button id="iotDevicecreateBtn" class="btn btn-primary" >新增</button>
                                <button id="iotDevicebackBtn" class="btn btn-primary" >返回</button>
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
                    <div class="panel-heading" style="overflow-y:hidden">设备</div>
                    <div class="panel-body">
                        <div class="table-responsive1">
                            <table id="iotDeviceManageTable" class="table table-striped table-bordered totemManageTable" style="overflow-x: inherit;" width="100%">
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

<script type="text/javascript" src="${appsite}assets/js/iot/iotdevice/grid.js?${.now?date}"></script>
<script type="text/javascript" src="${appsite}assets/js/iot/iotdevice/manage.js?${.now?date}"></script>
<@mc.mEnd/>
