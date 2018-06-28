package org.totem.iot.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.totem.common.service.CachedService;
import org.totem.components.data.json.JsonUtils;
import org.totem.common.service.AuthorityService;
import org.totem.components.cryptor.md5.MD5Utils;
import org.totem.components.data.DateUtils;
import org.totem.components.data.UUIDUtils;
import org.totem.iot.utils.CommonsUtil;
import org.totem.iot.model.IotUserServiceRelation;
import org.totem.iot.service.IotUserServiceRelationService;
import org.totem.iot.dao.IotUserServiceRelationDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
* IotUserServiceRelation 服务实现层
*/

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class IotUserServiceRelationServiceImpl implements IotUserServiceRelationService {
    @Autowired
    private IotUserServiceRelationDao dao;

    @Autowired
    private CachedService cachedService;

    @Autowired
    private AuthorityService authService;

    private boolean isUpdate = false;

    @SuppressWarnings("unchecked")
    public IotUserServiceRelation updateIotUserServiceRelation(IotUserServiceRelation model) {
        if( StringUtils.isEmpty(model.getIotUserServiceRelationId()) ) {
            model.setIotUserServiceRelationId(UUIDUtils.getShortUuid());
            dao.addIotUserServiceRelation(model);
        } else {
            isUpdate = true;
            dao.updateIotUserServiceRelation(model);
            cachedService.delKey("IOTUSERSERVICERELATION" + model.getIotUserServiceRelationId());
        }

        deleteCache();
        return getById(model.getIotUserServiceRelationId());
    }

    public int addIotUserServiceRelations(List<IotUserServiceRelation> models){
        if(CollectionUtils.isEmpty(models)){
            return 0;
        }
        for (IotUserServiceRelation model:models){
            model.setIotUserServiceRelationId(UUIDUtils.getShortUuid());
        }

        deleteCache();
        return dao.addIotUserServiceRelations(models);
    }

    public void deleteCache(){
        List<String> moduleNames = new ArrayList<String>();
        moduleNames.add("IOTUSERSERVICERELATION");
        moduleNames.add("IOTUSER");
        moduleNames.add("USERSERVICE");
        authService.deleteCache(moduleNames);
    }

    public int deleteIotUserServiceRelation(String iotUserServiceRelationId){
        cachedService.delKey("IOTUSERSERVICERELATION" + iotUserServiceRelationId);
        deleteCache();
        return dao.deleteIotUserServiceRelation(iotUserServiceRelationId);
    }

    public int deleteIotUserServiceRelations(Map<String, Object> map){
        if (getCounts(map)==getCounts(new HashMap<String,Object>())){
            return 0;
        }
        deleteCache();
        map.remove("orderBy");
        return dao.deleteIotUserServiceRelations(map);
    }

    public IotUserServiceRelation getById(String iotUserServiceRelationId){
        if (StringUtils.isBlank(iotUserServiceRelationId)){
            return null;
        }
        String key = "IOTUSERSERVICERELATION" +"IOTUSERSERVICERELATIONID"+ iotUserServiceRelationId;
        String keyMem = cachedService.getKey("IOTUSERSERVICERELATIONMAPKEY");
        if (StringUtils.contains(keyMem, key)){
            IotUserServiceRelation model = cachedService.getKey(key);
            if (null!=model){
                return model;
            }
        }
        IotUserServiceRelation model = dao.getById(iotUserServiceRelationId);
        if (null==model){
            return null;
        }

        cachedService.setKey(key ,0,model);
        cachedService.setKey("IOTUSERSERVICERELATIONMAPKEY",0,keyMem+","+key);
        return model;
    }

    public List<IotUserServiceRelation> selectByUserId(String userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        List<IotUserServiceRelation> list = queryIotUserServiceRelations(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUserServiceRelation> selectByServiceId(String serviceId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceId",serviceId);
        List<IotUserServiceRelation> list = queryIotUserServiceRelations(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUserServiceRelation> selectByServiceDescription(String serviceDescription){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceDescription",serviceDescription);
        List<IotUserServiceRelation> list = queryIotUserServiceRelations(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public int getCounts(Map< String, Object > map){
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        map.put("appCode", appCode);
        String keyMap = "IOTUSERSERVICERELATIONCOUNT";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTUSERSERVICERELATIONMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        int returnValue =  dao.getCounts(map);
        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERSERVICERELATIONMAPKEY",0,keyMem+","+keyMap);
        return returnValue;
    }

    //按查询条件获取带分页的数据集
    public List< IotUserServiceRelation> queryByQueryStr(String queryStr, PageBounds pageBounds){
        String keyMap = "IOTUSERSERVICERELATIONMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("IOTUSERSERVICERELATIONMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUserServiceRelation> returnValue =  dao.queryByQueryStr(queryStr, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERSERVICERELATIONMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    //按查询条件获取数据集
    public List< IotUserServiceRelation> queryByQueryStr(String queryStr){
        String keyMap = "IOTUSERSERVICERELATIONMAPS";
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("IOTUSERSERVICERELATIONMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUserServiceRelation> returnValue =  dao.queryByQueryStr(queryStr);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERSERVICERELATIONMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<IotUserServiceRelation> queryIotUserServiceRelations(Map<String, Object> map, PageBounds pageBounds) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        for (String key:map.keySet()){
            queryMap.put(key,map.get(key));
        }
        validatePara(queryMap);
        if (null==queryMap.get("orderBy")){
            queryMap.put("orderBy",getOrder("",""));
        }
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        queryMap.put("appCode", appCode);
        String keyMap = "IOTUSERSERVICERELATIONMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTUSERSERVICERELATIONMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUserServiceRelation> returnValue =  dao.queryIotUserServiceRelations(queryMap, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERSERVICERELATIONMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<IotUserServiceRelation> queryIotUserServiceRelations(Map<String, Object> map) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        for (String key:map.keySet()){
            queryMap.put(key,map.get(key));
        }
        validatePara(queryMap);
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        queryMap.put("appCode", appCode);

        if (null==queryMap.get("orderBy")){
            queryMap.put("orderBy",getOrder("",""));
        }

        String keyMap = "IOTUSERSERVICERELATIONMAPS";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTUSERSERVICERELATIONMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUserServiceRelation> dataList =  dao.queryIotUserServiceRelations(queryMap);

        cachedService.setKey(keyMap,0,dataList);
        cachedService.setKey("IOTUSERSERVICERELATIONMAPKEY",0,keyMem+","+keyMap);
        return dataList;

    }

    private void validatePara(Map<String,Object> map){
        if (map.get("noUserIds") instanceof String && StringUtils.isNotBlank((String)map.get("noUserIds"))){
            map.put("noUserIds",StringUtils.split((String)map.get("noUserIds"),","));
        }
        if (map.get("noServiceIds") instanceof String && StringUtils.isNotBlank((String)map.get("noServiceIds"))){
            map.put("noServiceIds",StringUtils.split((String)map.get("noServiceIds"),","));
        }
        if (map.get("noServiceDescriptions") instanceof String && StringUtils.isNotBlank((String)map.get("noServiceDescriptions"))){
            map.put("noServiceDescriptions",StringUtils.split((String)map.get("noServiceDescriptions"),","));
        }
    }
    public String getOrder(String sort,String order){
        String[] sorts = StringUtils.split(sort,",");
        String[] orders = StringUtils.split(order,",");
        List<String> returnValue = new ArrayList<String>();
        for (int i=0;i<sorts.length;i++){
            if (StringUtils.equals("iotUserServiceRelationId",sorts[i])){
                returnValue.add("A.IOT_USR_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userId",sorts[i])){
                returnValue.add("A.USER_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userIdShowLabel",sorts[i])){
                returnValue.add("A.USER_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("serviceId",sorts[i])){
                returnValue.add("A.SERVICE_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("serviceIdShowLabel",sorts[i])){
                returnValue.add("A.SERVICE_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("serviceDescription",sorts[i])){
                returnValue.add("A.SERVICE_DESCRIPTION "+orders[i]);
                continue;
            }

        }
        return StringUtils.join(returnValue,",");
    }
}