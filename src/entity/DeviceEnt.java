/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author ysa
 */
public class DeviceEnt {
    
    public long id;
    public long userId;
    public String keyPush;
    public String deviceId;
    public String deviceName;
    public String osName;
    public String osVersion;
    public String appKey;
    public String appVersion;
    public short status;
    public long dateCreated;
    public long lastActiveDated;
    public long lastReceived;
    
    public DeviceEnt(DeviceEnt item){
        this.id = item.id;
        this.userId = item.userId;
        this.keyPush = item.keyPush;
        this.deviceId = item.deviceId;
        this.deviceName = item.deviceName;
        this.osName = item.osName;
        this.osVersion = item.osVersion;
        this.appKey = item.appKey;
        this.appVersion = item.appVersion;
        this.status = item.status;
        this.dateCreated =item.dateCreated;
        this.lastActiveDated = item.lastActiveDated;
        this.lastReceived = item.lastReceived;
    }
    
    
    public DeviceEnt(){}
}
