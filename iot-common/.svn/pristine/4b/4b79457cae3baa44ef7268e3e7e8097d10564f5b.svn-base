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
import org.totem.iot.model.UserService;
import org.totem.iot.service.UserServiceService;
import org.totem.iot.dao.UserServiceDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
* UserService 服务实现层
*/

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class UserServiceServiceImpl implements UserServiceService {
    @Autowired
    private UserServiceDao dao;

    @Autowired
    private CachedService cachedService;

    @Autowired
    private AuthorityService authService;

    private boolean isUpdate = false;

    @SuppressWarnings("unchecked")
    public UserService updateUserService(UserService model) {
        if( StringUtils.isEmpty(model.getServiceId()) ) {
            model.setServiceId(UUIDUtils.getShortUuid());
            dao.addUserService(model);
        } else {
            isUpdate = true;
            dao.updateUserService(model);
            cachedService.delKey("USERSERVICE" + model.getServiceId());
        }

        deleteCache();
        return getById(model.getServiceId());
    }

    public int addUserServices(List<UserService> models){
        if(CollectionUtils.isEmpty(models)){
            return 0;
        }
        for (UserService model:models){
            model.setServiceId(UUIDUtils.getShortUuid());
        }

        deleteCache();
        return dao.addUserServices(models);
    }

    public void deleteCache(){
        List<String> moduleNames = new ArrayList<String>();
        moduleNames.add("USERSERVICE");
        moduleNames.add("IOTUSER");
        authService.deleteCache(moduleNames);
    }

    public int deleteUserService(String serviceId){
        cachedService.delKey("USERSERVICE" + serviceId);
        deleteCache();
        return dao.deleteUserService(serviceId);
    }

    public int deleteUserServices(Map<String, Object> map){
        if (getCounts(map)==getCounts(new HashMap<String,Object>())){
            return 0;
        }
        deleteCache();
        map.remove("orderBy");
        return dao.deleteUserServices(map);
    }

    public UserService getById(String serviceId){
        if (StringUtils.isBlank(serviceId)){
            return null;
        }
        String key = "USERSERVICE" +"SERVICEID"+ serviceId;
        String keyMem = cachedService.getKey("USERSERVICEMAPKEY");
        if (StringUtils.contains(keyMem, key)){
            UserService model = cachedService.getKey(key);
            if (null!=model){
                return model;
            }
        }
        UserService model = dao.getById(serviceId);
        if (null==model){
            return null;
        }

        cachedService.setKey(key ,0,model);
        cachedService.setKey("USERSERVICEMAPKEY",0,keyMem+","+key);
        return model;
    }

    public List<UserService> selectByServiceName(String serviceName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceName",serviceName);
        List<UserService> list = queryUserServices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<UserService> selectByServiceType(String serviceType){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceType",serviceType);
        List<UserService> list = queryUserServices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<UserService> selectByServiceDescription(String serviceDescription){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("serviceDescription",serviceDescription);
        List<UserService> list = queryUserServices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public int getCounts(Map< String, Object > map){
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        map.put("appCode", appCode);
        String keyMap = "USERSERVICECOUNT";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("USERSERVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        int returnValue =  dao.getCounts(map);
        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("USERSERVICEMAPKEY",0,keyMem+","+keyMap);
        return returnValue;
    }

    //按查询条件获取带分页的数据集
    public List< UserService> queryByQueryStr(String queryStr, PageBounds pageBounds){
        String keyMap = "USERSERVICEMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("USERSERVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<UserService> returnValue =  dao.queryByQueryStr(queryStr, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("USERSERVICEMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    //按查询条件获取数据集
    public List< UserService> queryByQueryStr(String queryStr){
        String keyMap = "USERSERVICEMAPS";
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("USERSERVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<UserService> returnValue =  dao.queryByQueryStr(queryStr);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("USERSERVICEMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<UserService> queryUserServices(Map<String, Object> map, PageBounds pageBounds) {
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
        String keyMap = "USERSERVICEMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("USERSERVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<UserService> returnValue =  dao.queryUserServices(queryMap, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("USERSERVICEMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<UserService> queryUserServices(Map<String, Object> map) {
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

        String keyMap = "USERSERVICEMAPS";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("USERSERVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<UserService> dataList =  dao.queryUserServices(queryMap);

        cachedService.setKey(keyMap,0,dataList);
        cachedService.setKey("USERSERVICEMAPKEY",0,keyMem+","+keyMap);
        return dataList;

    }

    private void validatePara(Map<String,Object> map){
        if (map.get("noServiceNames") instanceof String && StringUtils.isNotBlank((String)map.get("noServiceNames"))){
            map.put("noServiceNames",StringUtils.split((String)map.get("noServiceNames"),","));
        }
        if (map.get("noServiceTypes") instanceof String && StringUtils.isNotBlank((String)map.get("noServiceTypes"))){
            map.put("noServiceTypes",StringUtils.split((String)map.get("noServiceTypes"),","));
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
            if (StringUtils.equals("serviceId",sorts[i])){
                returnValue.add("A.SERVICE_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("serviceName",sorts[i])){
                returnValue.add("A.SERVICE_NAME "+orders[i]);
                continue;
            }
            if (StringUtils.equals("serviceType",sorts[i])){
                returnValue.add("A.SERVICE_TYPE "+orders[i]);
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