/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE OPEN_DRONE_ID_SYSTEM PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* Data for filling the OpenDroneID System message. The System Message contains general system information including the operator location and possible aircraft group information.
*/
public class msg_open_drone_id_system extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_OPEN_DRONE_ID_SYSTEM = 12904;
    public static final int MAVLINK_MSG_LENGTH = 21;
    private static final long serialVersionUID = MAVLINK_MSG_ID_OPEN_DRONE_ID_SYSTEM;


      
    /**
    * Latitude of the operator. If unknown: 0 deg (both Lat/Lon).
    */
    public int operator_latitude;
      
    /**
    * Longitude of the operator. If unknown: 0 deg (both Lat/Lon).
    */
    public int operator_longitude;
      
    /**
    * Area Operations Ceiling relative to WGS84. If unknown: -1000 m.
    */
    public float area_ceiling;
      
    /**
    * Area Operations Floor relative to WGS84. If unknown: -1000 m.
    */
    public float area_floor;
      
    /**
    * Number of aircraft in the area, group or formation (default 1).
    */
    public int area_count;
      
    /**
    * Radius of the cylindrical area of the group or formation (default 0).
    */
    public int area_radius;
      
    /**
    * Specifies the location source for the operator location.
    */
    public short flags;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_OPEN_DRONE_ID_SYSTEM;
              
        packet.payload.putInt(operator_latitude);
              
        packet.payload.putInt(operator_longitude);
              
        packet.payload.putFloat(area_ceiling);
              
        packet.payload.putFloat(area_floor);
              
        packet.payload.putUnsignedShort(area_count);
              
        packet.payload.putUnsignedShort(area_radius);
              
        packet.payload.putUnsignedByte(flags);
        
        return packet;
    }

    /**
    * Decode a open_drone_id_system message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.operator_latitude = payload.getInt();
              
        this.operator_longitude = payload.getInt();
              
        this.area_ceiling = payload.getFloat();
              
        this.area_floor = payload.getFloat();
              
        this.area_count = payload.getUnsignedShort();
              
        this.area_radius = payload.getUnsignedShort();
              
        this.flags = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_open_drone_id_system(){
        msgid = MAVLINK_MSG_ID_OPEN_DRONE_ID_SYSTEM;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_open_drone_id_system(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_OPEN_DRONE_ID_SYSTEM;
        unpack(mavLinkPacket.payload);        
    }

                  
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_OPEN_DRONE_ID_SYSTEM - sysid:"+sysid+" compid:"+compid+" operator_latitude:"+operator_latitude+" operator_longitude:"+operator_longitude+" area_ceiling:"+area_ceiling+" area_floor:"+area_floor+" area_count:"+area_count+" area_radius:"+area_radius+" flags:"+flags+"";
    }
}
        