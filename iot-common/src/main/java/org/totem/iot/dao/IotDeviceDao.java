package org.totem.iot.dao;

import org.totem.iot.model.IotDevice;

import java.util.List;
import java.util.Map;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.annotations.Mapper;
/**
*  数据访问层
*/
@Mapper
public interface IotDeviceDao {
    public int addIotDevice(IotDevice model);

    public int addIotDevices(List<IotDevice> models);

    public int updateIotDevice(IotDevice model);

    public int deleteIotDevice(String deviceId);

    public int deleteIotDevices(Map< String, Object > map);

    public IotDevice getById(String deviceId);

    public List< IotDevice> queryIotDevices(Map< String, Object > map, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< IotDevice> queryIotDevices(Map< String, Object > map);

    public List< IotDevice> queryByQueryStr(String queryStr, PageBounds pageBounds);

    /**
    *不带分页的查询
    */
    public List< IotDevice> queryByQueryStr(String queryStr);

    public int getCounts(Map< String, Object > map);

}