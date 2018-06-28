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
import org.totem.iot.model.IotUser;
import org.totem.iot.service.IotUserService;
import org.totem.iot.dao.IotUserDao;
import org.totem.iot.model.UserService;
import org.totem.iot.service.UserServiceService;
import org.totem.iot.model.IotUserServiceRelation;
import org.totem.iot.service.IotUserServiceRelationService;
import org.totem.iot.model.IotDevice;
import org.totem.iot.service.IotDeviceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
* IotUser 服务实现层
*/

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class IotUserServiceImpl implements IotUserService {
    @Autowired
    private IotUserDao dao;

    @Autowired
    private CachedService cachedService;

    @Autowired
    private AuthorityService authService;

    @Autowired
    private UserServiceService userServiceService;

    @Autowired
    private IotUserServiceRelationService iotUserServiceRelationService;

    @Autowired
    private IotDeviceService iotDeviceService;

    private boolean isUpdate = false;

    @SuppressWarnings("unchecked")
    public IotUser updateIotUser(IotUser model) {
        if( StringUtils.isEmpty(model.getIotUserId()) ) {
            model.setIotUserId(UUIDUtils.getShortUuid());
            model.setUserPassword(MD5Utils.string2MD5(model.getUserPassword()));
            if (StringUtils.isBlank(model.getUserDate())){
                model.setUserDate(null);
            }
            model.setUserDate(DateUtils.getCurrDate());
            dao.addIotUser(model);
        } else {
            isUpdate = true;
            if (null!=model.getUserPassword()){
                IotUser tempModel = getById(model.getIotUserId());
                if (StringUtils.equals(model.getUserPassword(), tempModel.getUserPassword())){
                    model.setUserPassword(null);
                }else{
                    String userPassword = MD5Utils.string2MD5(model.getUserPassword());
                    if (StringUtils.equals(userPassword, tempModel.getUserPassword())){
                        model.setUserPassword(null);
                    }else{
                        model.setUserPassword(userPassword);
                    }
                }
            }
            if (StringUtils.isBlank(model.getUserDate())){
                model.setUserDate(null);
            }
            dao.updateIotUser(model);
            cachedService.delKey("IOTUSER" + model.getIotUserId());
        }

        deleteCache();
        return getById(model.getIotUserId());
    }

    public int addIotUsers(List<IotUser> models){
        if(CollectionUtils.isEmpty(models)){
            return 0;
        }
        for (IotUser model:models){
            model.setIotUserId(UUIDUtils.getShortUuid());
            model.setUserPassword(MD5Utils.string2MD5(model.getUserPassword()));
            if (StringUtils.isBlank(model.getUserDate())){
                model.setUserDate(null);
            }
            model.setUserDate(DateUtils.getCurrDate());
        }

        deleteCache();
        return dao.addIotUsers(models);
    }

    public void deleteCache(){
        List<String> moduleNames = new ArrayList<String>();
        moduleNames.add("IOTUSER");
        moduleNames.add("USERSERVICE");
        moduleNames.add("IOTDEVICE");
        authService.deleteCache(moduleNames);
    }

    public int deleteIotUser(String iotUserId){
        cachedService.delKey("IOTUSER" + iotUserId);
        deleteCache();
        return dao.deleteIotUser(iotUserId);
    }

    public int deleteIotUsers(Map<String, Object> map){
        if (getCounts(map)==getCounts(new HashMap<String,Object>())){
            return 0;
        }
        deleteCache();
        map.remove("orderBy");
        return dao.deleteIotUsers(map);
    }

    public IotUser getById(String iotUserId){
        if (StringUtils.isBlank(iotUserId)){
            return null;
        }
        String key = "IOTUSER" +"IOTUSERID"+ iotUserId;
        String keyMem = cachedService.getKey("IOTUSERMAPKEY");
        if (StringUtils.contains(keyMem, key)){
            IotUser model = cachedService.getKey(key);
            if (null!=model){
                return model;
            }
        }
        IotUser model = dao.getById(iotUserId);
        if (null==model){
            return null;
        }

        //  添加userServiceServiceId的子成员服务
        //	model.setUserServiceServiceIdChilds(getUserServiceServiceIdChilds(model.getIotUserId(),null));
        //  添加iotDeviceDeviceBelonging的子成员设备
        //	model.setIotDeviceDeviceBelongingChilds(getIotDeviceDeviceBelongingChilds(model.getIotUserId(),null));

        cachedService.setKey(key ,0,model);
        cachedService.setKey("IOTUSERMAPKEY",0,keyMem+","+key);
        return model;
    }

    public List<IotUser> selectByUserName(String userName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName",userName);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserPassword(String userPassword){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userPassword",userPassword);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserToken(String userToken){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userToken",userToken);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserEmail(String userEmail){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userEmail",userEmail);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserDisplayName(String userDisplayName){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userDisplayName",userDisplayName);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserValid(String userValid){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userValid",userValid);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserDate(String userDate){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userDate",userDate);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    public List<IotUser> selectByUserBalance(String userBalance){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userBalance",userBalance);
        List<IotUser> list = queryIotUsers(map);
        if (null!=list){
            return list;
        }
        return null;
    }

    //获取成员<服务>
    public List<UserService> getUserServiceServiceIdChilds(String iotUserId,String authUser){
        Map<String, Object> map = new HashMap<String, Object>();
        List<IotUserServiceRelation> relas = iotUserServiceRelationService.selectByUserId(iotUserId);
        if (null!=relas&&relas.size()>0){
            String memberStr = "";
            for (IotUserServiceRelation rela:relas){
                memberStr += rela.getServiceId()+",";
            }
            map.put("serviceIds",StringUtils.split(memberStr,","));
        }else{
            map.clear();
        }
        if (map.size()>0){
            return userServiceService.queryUserServices(map);
        }else{
            return new ArrayList<UserService>();
        }
    }

    //获取成员<设备>
    public List<IotDevice> getIotDeviceDeviceBelongingChilds(String iotUserId,String authUser){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deviceBelonging",iotUserId);
        return iotDeviceService.queryIotDevices(map);
    }

    public int getCounts(Map< String, Object > map){
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        map.put("appCode", appCode);
        String keyMap = "IOTUSERCOUNT";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTUSERMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        int returnValue =  dao.getCounts(map);
        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERMAPKEY",0,keyMem+","+keyMap);
        return returnValue;
    }

    //按查询条件获取带分页的数据集
    public List< IotUser> queryByQueryStr(String queryStr, PageBounds pageBounds){
        String keyMap = "IOTUSERMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("IOTUSERMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUser> returnValue =  dao.queryByQueryStr(queryStr, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    //按查询条件获取数据集
    public List< IotUser> queryByQueryStr(String queryStr){
        String keyMap = "IOTUSERMAPS";
        keyMap = MD5Utils.string2MD5(keyMap+queryStr);
        String keyMem = cachedService.getKey("IOTUSERMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUser> returnValue =  dao.queryByQueryStr(queryStr);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<IotUser> queryIotUsers(Map<String, Object> map, PageBounds pageBounds) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        for (String key:map.keySet()){
            queryMap.put(key,map.get(key));
        }
        validatePara(queryMap);
        if (null==queryMap.get("userValid")){
            queryMap.put("userValid","1");
        }
        if (null==queryMap.get("orderBy")){
            queryMap.put("orderBy",getOrder("userDate","desc"));
        }
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        queryMap.put("appCode", appCode);
        String keyMap = "IOTUSERMAPS"+pageBounds.getPage()+"|"+pageBounds.getLimit()+"|"+pageBounds.getOffset();
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTUSERMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUser> returnValue =  dao.queryIotUsers(queryMap, pageBounds);

        cachedService.setKey(keyMap,0,returnValue);
        cachedService.setKey("IOTUSERMAPKEY",0,keyMem+","+keyMap);

        return returnValue;
    }

    public List<IotUser> queryIotUsers(Map<String, Object> map) {
        Map<String, Object> queryMap = new HashMap<String, Object>();
        for (String key:map.keySet()){
            queryMap.put(key,map.get(key));
        }
        validatePara(queryMap);
        if (null==queryMap.get("userValid")){
            queryMap.put("userValid","1");
        }
        String[] appCode = {"totem",CommonsUtil.APP_CODE};
        queryMap.put("appCode", appCode);

        if (null==queryMap.get("orderBy")){
            queryMap.put("orderBy",getOrder("userDate","desc"));
        }

        String keyMap = "IOTUSERMAPS";
        keyMap = CommonsUtil.getKeyMap(map,keyMap);
        String keyMem = cachedService.getKey("IOTUSERMAPKEY");
        if (StringUtils.contains(keyMem, keyMap)&&null!=cachedService.getKey(keyMap)){
            return cachedService.getKey(keyMap);
        }else{
            keyMem=StringUtils.isEmpty(keyMem)?"":keyMem;
        }
        List<IotUser> dataList =  dao.queryIotUsers(queryMap);

        cachedService.setKey(keyMap,0,dataList);
        cachedService.setKey("IOTUSERMAPKEY",0,keyMem+","+keyMap);
        return dataList;

    }

    private void validatePara(Map<String,Object> map){
        if (map.get("noUserNames") instanceof String && StringUtils.isNotBlank((String)map.get("noUserNames"))){
            map.put("noUserNames",StringUtils.split((String)map.get("noUserNames"),","));
        }
        if (map.get("noUserPasswords") instanceof String && StringUtils.isNotBlank((String)map.get("noUserPasswords"))){
            map.put("noUserPasswords",StringUtils.split((String)map.get("noUserPasswords"),","));
        }
        if (map.get("noUserTokens") instanceof String && StringUtils.isNotBlank((String)map.get("noUserTokens"))){
            map.put("noUserTokens",StringUtils.split((String)map.get("noUserTokens"),","));
        }
        if (map.get("noUserEmails") instanceof String && StringUtils.isNotBlank((String)map.get("noUserEmails"))){
            map.put("noUserEmails",StringUtils.split((String)map.get("noUserEmails"),","));
        }
        if (map.get("noUserDisplayNames") instanceof String && StringUtils.isNotBlank((String)map.get("noUserDisplayNames"))){
            map.put("noUserDisplayNames",StringUtils.split((String)map.get("noUserDisplayNames"),","));
        }
        if (map.get("noUserValids") instanceof String && StringUtils.isNotBlank((String)map.get("noUserValids"))){
            map.put("noUserValids",StringUtils.split((String)map.get("noUserValids"),","));
        }
        if (map.get("noUserDates") instanceof String && StringUtils.isNotBlank((String)map.get("noUserDates"))){
            map.put("noUserDates",StringUtils.split((String)map.get("noUserDates"),","));
        }
        if (map.get("noUserBalances") instanceof String && StringUtils.isNotBlank((String)map.get("noUserBalances"))){
            map.put("noUserBalances",StringUtils.split((String)map.get("noUserBalances"),","));
        }
    }
    public String getOrder(String sort,String order){
        String[] sorts = StringUtils.split(sort,",");
        String[] orders = StringUtils.split(order,",");
        List<String> returnValue = new ArrayList<String>();
        for (int i=0;i<sorts.length;i++){
            if (StringUtils.equals("iotUserId",sorts[i])){
                returnValue.add("A.IOT_USER_ID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userName",sorts[i])){
                returnValue.add("A.USER_NAME "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userPassword",sorts[i])){
                returnValue.add("A.USER_PASSWORD "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userToken",sorts[i])){
                returnValue.add("A.USER_TOKEN "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userEmail",sorts[i])){
                returnValue.add("A.USER_EMAIL "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userDisplayName",sorts[i])){
                returnValue.add("A.USER_DISPLAY_NAME "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userValid",sorts[i])){
                returnValue.add("A.USER_VALID "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userDate",sorts[i])){
                returnValue.add("A.USER_DATE "+orders[i]);
                continue;
            }
            if (StringUtils.equals("userBalance",sorts[i])){
                returnValue.add("A.USER_BALANCE "+orders[i]);
                continue;
            }

        }
        return StringUtils.join(returnValue,",");
    }
}