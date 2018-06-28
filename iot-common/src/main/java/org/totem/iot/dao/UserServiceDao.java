package org.totem.iot.dao;

import org.totem.iot.model.UserService;

import java.util.List;
import java.util.Map;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Mapper;
/**
*  数据访问层
*/
@Mapper
public interface UserServiceDao {
    public int addUserService(UserService model);

    public int addUserServices(List<UserService> models);

    public int updateUserService(UserService model);

    public int deleteUserService(String serviceId);

    public int deleteUserServices(Map< String, Object > map);

    public UserService getById(String serviceId);

    public List< UserService> queryUserServices(Map< String, Object > map, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< UserService> queryUserServices(Map< String, Object > map);

    public List< UserService> queryByQueryStr(String queryStr, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< UserService> queryByQueryStr(String queryStr);

    public int getCounts(Map< String, Object > map);

}