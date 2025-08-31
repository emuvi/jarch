package br.com.pointel.jarch.gears;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author emuvi
 */
public class CharsSectionsMap extends LinkedHashMap<String, ArrayList<String>> {
    
    public List<String> newSection(String name) {
        var result = new ArrayList<String>();
        put(name, result);
        return result;
    }
    
}
