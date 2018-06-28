package org.totem.iot.dao;

import org.totem.iot.model.IotUser;

import java.util.List;
import java.util.Map;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Mapper;
/**
*  数据访问层
*/
@Mapper
public interface IotUserDao {
    public int addIotUser(IotUser model);

    public int addIotUsers(List<IotUser> models);

    public int updateIotUser(IotUser model);

    public int deleteIotUser(String iotUserId);

    public int deleteIotUsers(Map< String, Object > map);

    public IotUser getById(String iotUserId);

    public List< IotUser> queryIotUsers(Map< String, Object > map, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< IotUser> queryIotUsers(Map< String, Object > map);

    public List< IotUser> queryByQueryStr(String queryStr, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< IotUser> queryByQueryStr(String queryStr);

    public int getCounts(Map< String, Object > map);

}