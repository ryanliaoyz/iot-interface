package org.totem.iot.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.totem.iot.model.IotDevice;
/**
* IotDevice 服务层
*/

public interface IotDeviceService {
    /*
    *新增、更新IotDevice
    */
    IotDevice updateIotDevice(IotDevice model);

    /*
    *批量添加IotDevice
    */
    int addIotDevices(List<IotDevice> models);

    //删除IotDevice
    int deleteIotDevice(String deviceId);

    /**
    *以指定条件删除IotDevice
    */
    int deleteIotDevices(Map<String, Object > map);

    //根据标识获取IotDevice
    IotDevice getById(String deviceId);

    //删除缓存
    void deleteCache();

    /**
    *按照设备厂商查询数据集
    */
    List<IotDevice> selectByDeviceManufa(String deviceManufa);

    /**
    *按照设备类型查询数据集
    */
    List<IotDevice> selectByDeviceType(String deviceType);

    /**
    *按照设备服务查询数据集
    */
    List<IotDevice> selectByDeviceService(String deviceService);

    /**
    *按照设备名查询数据集
    */
    List<IotDevice> selectByDeviceName(String deviceName);

    /**
    *按照设备归属查询数据集
    */
    List<IotDevice> selectByDeviceBelonging(String deviceBelonging);

    /**
    *根据前台的排序条件生成排序条件
    */
    String getOrder(String sort,String order);

    /**
    *查询表中符合条件数据的数量
    */
    int getCounts(Map< String, Object > map);

    //获取IotDevice数据集
    List<IotDevice> queryIotDevices(Map<String, Object> map, PageBounds pageBounds);

    //获取IotDevice不带分页的数据集
    List<IotDevice> queryIotDevices(Map<String, Object> map);

    //按查询条件获取带分页的数据集
    List< IotDevice> queryByQueryStr(String queryStr, PageBounds pageBounds);

    //按查询条件获取数据集
    List< IotDevice> queryByQueryStr(String queryStr);

}