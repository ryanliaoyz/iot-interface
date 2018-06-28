<#import "/common/macro.ftl" as mc/>
<@mc.mHeader/>
<#include "/common/globle.ftl"/>
<@mc.mMenu $reqSysMenu/>

<@mc.mTop/>
<div class="wrapper">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>用户服务关系<small></small></h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i>管理系统</a></li>
                <li class="active">用户服务关系</li>
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
                                <button class="btn btn-primary" type="button" id="iotUserServiceRelationSaveBtn"><i class="fa fa-check ">保存</i></button>
                                <button id="iotUserServiceRelationeditCancleBtn" type="button" class="btn btn-primary"><i class="fa fa-reply"> 返回 </i></button>
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
                    <a href="#tab_iotUserServiceRelation" data-toggle="tab" data-loadflag="false" data-content="">用户服务关系</a></li>

                </ul>

                <div class="tab-content">
                    <#if (oldParams['_tab'])??>
                    <div class="tab-pane " id="tab_iotUserServiceRelation">
                        <#else>
                        <div class="tab-pane active" id="tab_iotUserServiceRelation">
                    </#if>
                    <div class="panel-body">
                        <form id="iotUserServiceRelationEditForm" class="form-horizontal" role="form" enctype="multipart/form-data" autocomplete="off">
                            <input type="hidden" name="actionType" id="actionType" value="${actionType!}"/>
                            <input type="hidden" name="iotUserServiceRelationId" id="iotUserServiceRelationId" value="${(iotUserServiceRelation.iotUserServiceRelationId)!''}"/>
                            <input type="hidden" name="iotUserServiceRelation_UserId" id="iotUserServiceRelation_UserId" value="${iotUserServiceRelation_UserId!}"/>
                            <input type="hidden" name="iotUserServiceRelation_ServiceId" id="iotUserServiceRelation_ServiceId" value="${iotUserServiceRelation_ServiceId!}"/>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>用户ID</label>
                                <div class="col-sm-6">
                                    <input name="userId" id="userId" class="form-control" value="${(iotUserServiceRelation.userId)!}" style="width: 100%;"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-6">
                                <label class="col-sm-4 control-label"><span class="requireLabel"></span>服务ID</label>
                                <div class="col-sm-6">
                                    <input name="serviceId" id="serviceId" class="form-control" value="${(iotUserServiceRelation.serviceId)!}" style="width: 100%;"/>
                                </div>
                            </div>
                            <div class="form-group col-sm-12">
                                <label class="col-sm-2 control-label"><span class="requireLabel"></span>服务描述</label>
                                <div class="col-sm-9">
                                    <textarea class="form-control" name="serviceDescription" id="serviceDescription" style="height: 180px">${(iotUserServiceRelation.serviceDescription)!}</textarea>
                                </div>
                            </div>
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

    <script type="text/javascript" src="${appsite}assets/js/iot/iotuserservicerelation/validate.js?${.now?date}"></script>
    <script type="text/javascript" src="${appsite}assets/js/iot/iotuserservicerelation/edit.js?${.now?date}"></script>
    <@mc.mEnd/>
