package hu.syngu00.dataprocessor.model;

import java.util.HashMap;
import java.util.Map;


public class ProcessDataResponse extends HashMap<String, Map<String, Object>> {

    public ProcessDataResponse() {
        super();
    }

    public <T> void put(String label, String attribute, T element) {
        Map<String, Object> map = this.computeIfAbsent(label, k -> new HashMap<>());
        map.put(attribute, element);
    }

}
