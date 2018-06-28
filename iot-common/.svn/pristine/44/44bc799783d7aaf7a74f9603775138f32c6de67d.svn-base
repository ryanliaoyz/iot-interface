package org.totem.iot.dao;

import org.totem.iot.model.IotUserServiceRelation;

import java.util.List;
import java.util.Map;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Mapper;
/**
*  数据访问层
*/
@Mapper
public interface IotUserServiceRelationDao {
    public int addIotUserServiceRelation(IotUserServiceRelation model);

    public int addIotUserServiceRelations(List<IotUserServiceRelation> models);

    public int updateIotUserServiceRelation(IotUserServiceRelation model);

    public int deleteIotUserServiceRelation(String iotUserServiceRelationId);

    public int deleteIotUserServiceRelations(Map< String, Object > map);

    public IotUserServiceRelation getById(String iotUserServiceRelationId);

    public List< IotUserServiceRelation> queryIotUserServiceRelations(Map< String, Object > map, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< IotUserServiceRelation> queryIotUserServiceRelations(Map< String, Object > map);

    public List< IotUserServiceRelation> queryByQueryStr(String queryStr, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< IotUserServiceRelation> queryByQueryStr(String queryStr);

    public int getCounts(Map< String, Object > map);

}