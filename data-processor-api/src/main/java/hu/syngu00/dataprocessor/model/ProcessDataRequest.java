package hu.syngu00.dataprocessor.model;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;


public class ProcessDataRequest extends HashMap<String, Multimap<String, Object>> {

    public ProcessDataRequest() {
        super();
    }

    public <T> void put(String label, String attribute, T element) {
        Multimap<String, Object> multimap = this.computeIfAbsent(label, k -> ArrayListMultimap.create());
        multimap.put(attribute, element);
    }

    public <T> void put(String label, String attribute, Collection<T> elements) {
        Multimap<String, Object> multimap = this.computeIfAbsent(label, k -> ArrayListMultimap.create());
        multimap.putAll(attribute, elements);
    }
}
