package org.totem.iot.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

/**
* IotService 服务层
*/

public interface IotServiceService {
    /*
    *新增、更新IotService
    */
    IotService updateIotService(IotService model);

    /*
    *批量添加IotService
    */
    int addIotServices(List<IotService> models);

    //删除IotService
    int deleteIotService(String serviceId);

    /**
    *以指定条件删除IotService
    */
    int deleteIotServices(Map<String, Object > map);

    //根据标识获取IotService
    IotService getById(String serviceId);

    //删除缓存
    void deleteCache();

    /**
    *按照服务名查询数据集
    */
    List<IotService> selectByServiceName(String serviceName);

    /**
    *按照服务类型查询数据集
    */
    List<IotService> selectByServiceType(String serviceType);

    /**
    *按照服务描述查询数据集
    */
    List<IotService> selectByServiceDescription(String serviceDescription);

    /**
    *根据前台的排序条件生成排序条件
    */
    String getOrder(String sort,String order);

    /**
    *查询表中符合条件数据的数量
    */
    int getCounts(Map< String, Object > map);

    //获取IotService数据集
    List<IotService> queryIotServices(Map<String, Object> map, PageBounds pageBounds);

    //获取IotService不带分页的数据集
    List<IotService> queryIotServices(Map<String, Object> map);

    //按查询条件获取带分页的数据集
    List< IotService> queryByQueryStr(String queryStr, PageBounds pageBounds);

    //按查询条件获取数据集
    List< IotService> queryByQueryStr(String queryStr);

}