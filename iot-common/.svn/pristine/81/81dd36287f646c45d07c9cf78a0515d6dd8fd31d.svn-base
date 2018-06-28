package org.totem.iot.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.totem.iot.model.UserService;
/**
* UserService 服务层
*/

public interface UserServiceService {
    /*
    *新增、更新UserService
    */
    UserService updateUserService(UserService model);

    /*
    *批量添加UserService
    */
    int addUserServices(List<UserService> models);

    //删除UserService
    int deleteUserService(String serviceId);

    /**
    *以指定条件删除UserService
    */
    int deleteUserServices(Map<String, Object > map);

    //根据标识获取UserService
    UserService getById(String serviceId);

    //删除缓存
    void deleteCache();

    /**
    *按照服务名查询数据集
    */
    List<UserService> selectByServiceName(String serviceName);

    /**
    *按照服务类型查询数据集
    */
    List<UserService> selectByServiceType(String serviceType);

    /**
    *按照服务描述查询数据集
    */
    List<UserService> selectByServiceDescription(String serviceDescription);

    /**
    *根据前台的排序条件生成排序条件
    */
    String getOrder(String sort,String order);

    /**
    *查询表中符合条件数据的数量
    */
    int getCounts(Map< String, Object > map);

    //获取UserService数据集
    List<UserService> queryUserServices(Map<String, Object> map, PageBounds pageBounds);

    //获取UserService不带分页的数据集
    List<UserService> queryUserServices(Map<String, Object> map);

    //按查询条件获取带分页的数据集
    List< UserService> queryByQueryStr(String queryStr, PageBounds pageBounds);

    //按查询条件获取数据集
    List< UserService> queryByQueryStr(String queryStr);

}