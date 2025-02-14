package com.barunsw.ojt.day12;

import java.util.Iterator;
import java.util.Properties;

public class PropertiesTest {
	public static void main(String[] args) {
		Properties prop = System.getProperties();
		Iterator iter = prop.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String)iter.next();
			
			System.out.println(String.format("%s=%s", key, prop.get(key)));
		}
	}
}
