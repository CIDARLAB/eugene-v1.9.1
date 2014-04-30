package org.cidarlab.eugene.data.json;

import java.util.ArrayList;
import java.util.List;

import org.cidarlab.eugene.dom.components.Component;
import org.cidarlab.eugene.dom.components.Device;
import org.cidarlab.eugene.dom.components.Part;
import org.cidarlab.eugene.dom.components.types.PartType;
import org.json.JSONObject;

public class Eugene2JSON {

    public static JSONObject toJSON(Device objDevice)
            throws Exception {
        JSONObject deviceJSON = new JSONObject();
        deviceJSON.put("name", objDevice.getName());
        deviceJSON.put("schema", "CompositePart");
        deviceJSON.put("type", "composite");
        List<Component> lstComponents = objDevice.getComponents();

        List<JSONObject> lstComponentsJSON = new ArrayList<JSONObject>();

        for (Component component : lstComponents) {
            JSONObject componentJSON = new JSONObject();
            
            if(component instanceof Device) {
            	lstComponentsJSON.add(toJSON((Device)component));
            } else if(component instanceof PartType) {
            	lstComponentsJSON.add(toJSON((PartType)component));
            } else if(component instanceof Part) {
            	lstComponentsJSON.add(toJSON((Part)component));
            }

        }
        deviceJSON.put("components", lstComponentsJSON);

        return deviceJSON;
    }
    
    public static JSONObject toJSON(PartType partType) 
    		throws Exception {
    	
        JSONObject json = new JSONObject();
        json.put("name", partType.getName());
        json.put("schema", partType.getClass().getCanonicalName().toString());
        
        /*
         * Todo: Properties
         */
        return json;
    }
    
    public static JSONObject toJSON(Part part) 
    		throws Exception {
    	
        JSONObject json = new JSONObject();
        json.put("name", part.getName());
        json.put("schema", part.getClass().getCanonicalName().toString());
        
        /*
         * Todo: Property Values
         */
        
        return json;
    }
}
