package br.com.pointel.jarch.gears;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author emuvi
 */
public class CharsSectionsMap extends LinkedHashMap<String, ArrayList<String>> {
    
    public ArrayList<String> newSection(String name) {
        var result = new ArrayList<String>();
        put(name, result);
        return result;
    }
    
}
