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
import org.totem.iot.model.IotDevice;
import org.totem.iot.service.IotDeviceService;
import org.totem.iot.dao.IotDeviceDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
* IotDevice 服务实现层
*/

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class IotDeviceServiceImpl implements IotDeviceService {
    @Autowired
    private IotDeviceDao dao;

    @Autowired
    private CachedService cachedService;

    @Autowired
    private AuthorityService authService;

    private boolean isUpdate = false;

    @SuppressWarnings("unchecked")
    public IotDevice updateIotDevice(IotDevice model) {
        if( StringUtils.isEmpty(model.getDeviceId()) ) {
            model.setDeviceId(UUIDUtils.getShortUuid());
            dao.addIotDevice(model);
        } else {
            isUpdate = true;
            dao.updateIotDevice(model);
            cachedService.delKey("IOTDEVICE" + model.getDeviceId());
        }

        deleteCache();
        return getById(model.getDeviceId());
    }

    public int addIotDevices(List<IotDevice> models){
        if(CollectionUtils.isEmpty(models)){
            return 0;
        }
        for (IotDevice model:models){
            model.setDeviceId(UUIDUtils.getShortUuid());
        }

        deleteCache();
        return dao.addIotDevices(models);
    }

    public void deleteCache(){
        List<String> moduleNames = new ArrayList<String>();
        moduleNames.add("IOTDEVICE");
        moduleNames.add("IOTUSER");
        authService.deleteCache(moduleNames);
    }

    public int deleteIotDevice(String deviceId){
        cachedService.delKey("IOTDEVICE" + deviceId);
        deleteCache();
        return dao.deleteIotDevice(deviceId);
    }

    public int deleteIotDevices(Map<String, Object> map){
        if (getCounts(map)==getCounts(new HashMap<String,Object>())){
            return 0;
        }
        deleteCache();
        map.remove("orderBy");
        return dao.deleteIotDevices(map);
    }

    public IotDevice getById(String deviceId){
        if (StringUtils.isBlank(deviceId)){
            return null;
        }
        String key = "IOTDEVICE" +"DEVICEID"+ deviceId;
        String keyMem = cachedService.getKey("IOTDEVICEMAPKEY");
        if (StringUtils.contains(keyMem, key)){
            IotDevice model = cachedService.getKey(key);
            if (null!=model){
                return model;
            }
        }
        IotDevice model = dao.getById(deviceId);
        if (null==model){
            return null;
        }

        cachedService.setKey(key ,0,model);
        cachedService.setKey("IOTDEVICEMAPKEY",0,keyMem+","+key);
        return model;
    }

    public List<IotDevice> selectByDeviceManufa(String deviceManufa){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceManufa",deviceManufa);
        List<IotDevice> list = queryIotDevices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotDevice> selectByDeviceType(String deviceType){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceType",deviceType);
        List<IotDevice> list = queryIotDevices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotDevice> selectByDeviceService(String deviceService){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceService",deviceService);
        List<IotDevice> list = queryIotDevices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotDevice> selectByDeviceName(String deviceName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceName",deviceName);
        List<IotDevice> list = queryIotDevices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotDevice> selectByDeviceBelonging(String deviceBelonging){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceBelonging",deviceBelonging);
        List<IotDevice> list = queryIotDevices(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public int getCounts(Map< String, Object > map){
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        map.put("appCode", appCode);
        String keyMap = "IOTDEVICECOUNT";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTDEVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        int returnValue =  dao.getCounts(map);
        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTDEVICEMAPKEY",0,keyMem+","+keyMap);
        return returnValue;
    }

    //按查询条件获取带分页的数据集
    public List< IotDevice> queryByQueryStr(String queryStr, PageBounds pageBounds){
        String keyMap = "IOTDEVICEMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("IOTDEVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotDevice> returnValue =  dao.queryByQueryStr(queryStr, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTDEVICEMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    //按查询条件获取数据集
    public List< IotDevice> queryByQueryStr(String queryStr){
        String keyMap = "IOTDEVICEMAPS";
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("IOTDEVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotDevice> returnValue =  dao.queryByQueryStr(queryStr);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTDEVICEMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<IotDevice> queryIotDevices(Map<String, Object> map, PageBounds pageBounds) {
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
        String keyMap = "IOTDEVICEMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTDEVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotDevice> returnValue =  dao.queryIotDevices(queryMap, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTDEVICEMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<IotDevice> queryIotDevices(Map<String, Object> map) {
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

        String keyMap = "IOTDEVICEMAPS";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTDEVICEMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotDevice> dataList =  dao.queryIotDevices(queryMap);

        cachedService.setKey(keyMap,0,dataList);
        cachedService.setKey("IOTDEVICEMAPKEY",0,keyMem+","+keyMap);
        return dataList;

    }

    private void validatePara(Map<String,Object> map){
        if (map.get("noDeviceManufas") instanceof String && StringUtils.isNotBlank((String)map.get("noDeviceManufas"))){
            map.put("noDeviceManufas",StringUtils.split((String)map.get("noDeviceManufas"),","));
        }
        if (map.get("noDeviceTypes") instanceof String && StringUtils.isNotBlank((String)map.get("noDeviceTypes"))){
            map.put("noDeviceTypes",StringUtils.split((String)map.get("noDeviceTypes"),","));
        }
        if (map.get("noDeviceServices") instanceof String && StringUtils.isNotBlank((String)map.get("noDeviceServices"))){
            map.put("noDeviceServices",StringUtils.split((String)map.get("noDeviceServices"),","));
        }
        if (map.get("noDeviceNames") instanceof String && StringUtils.isNotBlank((String)map.get("noDeviceNames"))){
            map.put("noDeviceNames",StringUtils.split((String)map.get("noDeviceNames"),","));
        }
        if (map.get("noDeviceBelongings") instanceof String && StringUtils.isNotBlank((String)map.get("noDeviceBelongings"))){
            map.put("noDeviceBelongings",StringUtils.split((String)map.get("noDeviceBelongings"),","));
        }
    }
    public String getOrder(String sort,String order){
        String[] sorts = StringUtils.split(sort,",");
        String[] orders = StringUtils.split(order,",");
        List<String> returnValue = new ArrayList<String>();
        for (int i=0;i<sorts.length;i++){
            if (StringUtils.equals("deviceId",sorts[i])){
                returnValue.add("A.DEVICE_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceManufa",sorts[i])){
                returnValue.add("A.DEVICE_MANUFA "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceType",sorts[i])){
                returnValue.add("A.DEVICE_TYPE "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceTypeShowLabel",sorts[i])){
                returnValue.add("A.DEVICE_TYPE "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceService",sorts[i])){
                returnValue.add("A.DEVICE_SERVICE "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceServiceShowLabel",sorts[i])){
                returnValue.add("A.DEVICE_SERVICE "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceName",sorts[i])){
                returnValue.add("A.DEVICE_NAME "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceBelonging",sorts[i])){
                returnValue.add("A.DEVICE_BELONGING "+orders[i]);
                continue;
            }
            if (StringUtils.equals("deviceBelongingShowLabel",sorts[i])){
                returnValue.add("A.DEVICE_BELONGING "+orders[i]);
                continue;
            }

        }
        return StringUtils.join(returnValue,",");
    }
}