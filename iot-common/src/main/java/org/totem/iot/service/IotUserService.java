package org.totem.iot.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.totem.iot.model.IotUser;
import org.totem.iot.model.UserService;
import org.totem.iot.model.IotDevice;
/**
* IotUser 服务层
*/

public interface IotUserService {
    /*
    *新增、更新IotUser
    */
    IotUser updateIotUser(IotUser model);

    /*
    *批量添加IotUser
    */
    int addIotUsers(List<IotUser> models);

    //删除IotUser
    int deleteIotUser(String iotUserId);

    /**
    *以指定条件删除IotUser
    */
    int deleteIotUsers(Map<String, Object > map);

    //根据标识获取IotUser
    IotUser getById(String iotUserId);

    //删除缓存
    void deleteCache();

    /**
    *按照用户名查询数据集
    */
    List<IotUser> selectByUserName(String userName);

    /**
    *按照密码查询数据集
    */
    List<IotUser> selectByUserPassword(String userPassword);

    /**
    *按照密钥查询数据集
    */
    List<IotUser> selectByUserToken(String userToken);

    /**
    *按照邮箱查询数据集
    */
    List<IotUser> selectByUserEmail(String userEmail);

    /**
    *按照显示名查询数据集
    */
    List<IotUser> selectByUserDisplayName(String userDisplayName);

    /**
    *按照激活查询数据集
    */
    List<IotUser> selectByUserValid(String userValid);

    /**
    *按照注册日期查询数据集
    */
    List<IotUser> selectByUserDate(String userDate);

    /**
    *按照余额查询数据集
    */
    List<IotUser> selectByUserBalance(String userBalance);

    /**
    *获取SERVICE_ID的成员<服务>
    */
    List<UserService> getUserServiceServiceIdChilds(String userServiceServiceId,String authUser);

    /**
    *获取DEVICE_BELONGING的成员<设备>
    */
    List<IotDevice> getIotDeviceDeviceBelongingChilds(String iotDeviceDeviceBelonging,String authUser);

    /**
    *根据前台的排序条件生成排序条件
    */
    String getOrder(String sort,String order);

    /**
    *查询表中符合条件数据的数量
    */
    int getCounts(Map< String, Object > map);

    //获取IotUser数据集
    List<IotUser> queryIotUsers(Map<String, Object> map, PageBounds pageBounds);

    //获取IotUser不带分页的数据集
    List<IotUser> queryIotUsers(Map<String, Object> map);

    //按查询条件获取带分页的数据集
    List< IotUser> queryByQueryStr(String queryStr, PageBounds pageBounds);

    //按查询条件获取数据集
    List< IotUser> queryByQueryStr(String queryStr);

}