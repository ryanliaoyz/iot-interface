<#import "/common/macro.ftl" as mc/>
<@mc.mHeader/>
<#include "/common/globle.ftl"/>
<@mc.mMenu $reqSysMenu/>

<@mc.mTop/>
<div class="wrapper">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>设备<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>管理系统</a></li>
                <li class="active">设备</li>
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
                                <button id="iotDeviceeditCancleBtn" type="button" class="btn btn-primary"><i class="fa fa-reply"> 返回 </i></button>
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
                    <a href="#tab_iotDevice" data-toggle="tab" data-loadflag="false" data-content="">设备</a></li>

                </ul>

                <div class="tab-content">
                    <#if (oldParams['_tab'])??>
                    <div class="tab-pane " id="tab_iotDevice">
                        <#else>
                        <div class="tab-pane active" id="tab_iotDevice">
                    </#if>
                    <div class="panel-body">
                        <form id="iotDeviceEditForm" class="form-horizontal" role="form" enctype="multipart/form-data" autocomplete="off">
                            <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>
                            <input type="hidden" name="deviceId" id="deviceId" value="${(iotDevice.deviceId)!''}"/>
                            <input type="hidden" name="iotDevice_DeviceType" id="iotDevice_DeviceType" value="${iotDevice_DeviceType!}"/>
                            <input type="hidden" name="iotDevice_DeviceService" id="iotDevice_DeviceService" value="${iotDevice_DeviceService!}"/>
                            <input type="hidden" name="iotDevice_DeviceBelonging" id="iotDevice_DeviceBelonging" value="${iotDevice_DeviceBelonging!}"/>
                        </form>
                    </div> <!-- panel-body -->
                        </div>

                    </div>

                </div>
            </div><!-- content -->
        </div>

        <!-- Main Footer -->
        <@mc.mFooter/>
    </div>
    <@mc.mJsCommon/>

    <#include "/iot/header.ftl">

    <script type="text/javascript" src="${appsite}assets/js/iot/iotdevice/validate.js?${.now?date}"></script>
    <script type="text/javascript" src="${appsite}assets/js/iot/iotdevice/edit.js?${.now?date}"></script>
    <@mc.mEnd/>
