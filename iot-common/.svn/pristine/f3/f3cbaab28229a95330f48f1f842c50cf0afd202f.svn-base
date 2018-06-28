package org.totem.iot.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.totem.iot.model.IotUserServiceRelation;
/**
* IotUserServiceRelation 服务层
*/

public interface IotUserServiceRelationService {
    /*
    *新增、更新IotUserServiceRelation
    */
    IotUserServiceRelation updateIotUserServiceRelation(IotUserServiceRelation model);

    /*
    *批量添加IotUserServiceRelation
    */
    int addIotUserServiceRelations(List<IotUserServiceRelation> models);

    //删除IotUserServiceRelation
    int deleteIotUserServiceRelation(String iotUserServiceRelationId);

    /**
    *以指定条件删除IotUserServiceRelation
    */
    int deleteIotUserServiceRelations(Map<String, Object > map);

    //根据标识获取IotUserServiceRelation
    IotUserServiceRelation getById(String iotUserServiceRelationId);

    //删除缓存
    void deleteCache();

    /**
    *按照用户ID查询数据集
    */
    List<IotUserServiceRelation> selectByUserId(String userId);

    /**
    *按照服务ID查询数据集
    */
    List<IotUserServiceRelation> selectByServiceId(String serviceId);

    /**
    *按照服务描述查询数据集
    */
    List<IotUserServiceRelation> selectByServiceDescription(String serviceDescription);

    /**
    *根据前台的排序条件生成排序条件
    */
    String getOrder(String sort,String order);

    /**
    *查询表中符合条件数据的数量
    */
    int getCounts(Map< String, Object > map);

    //获取IotUserServiceRelation数据集
    List<IotUserServiceRelation> queryIotUserServiceRelations(Map<String, Object> map, PageBounds pageBounds);

    //获取IotUserServiceRelation不带分页的数据集
    List<IotUserServiceRelation> queryIotUserServiceRelations(Map<String, Object> map);

    //按查询条件获取带分页的数据集
    List< IotUserServiceRelation> queryByQueryStr(String queryStr, PageBounds pageBounds);

    //按查询条件获取数据集
    List< IotUserServiceRelation> queryByQueryStr(String queryStr);

}