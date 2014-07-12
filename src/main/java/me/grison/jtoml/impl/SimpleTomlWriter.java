package me.grison.jtoml.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import me.grison.jtoml.TomlWriter;
import me.grison.jtoml.Util;

public class SimpleTomlWriter implements TomlWriter {
	
	private File file;
	
	public SimpleTomlWriter(File file) {
		this.file = file;
	}
	
	@Override
	public void write(Map<String, Object> config) throws IOException {
		file.createNewFile();
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("# Autogenerated by jTOML.\n\n");
		
		for(String key : config.keySet()) {
			Object result = config.get(key);
			if(result instanceof Map) {
				builder.append("[" + key + "]\n");
				Map<String, Object> map = (Map<String, Object>) result;
				for(String key1 : map.keySet()) {
					Object result1 = config.get(key1);
					builder.append(key + " = " + parse(result) + "\n");
				}
				builder.append("\n\n");
			} else {
				builder.insert(0, key + " = " + parse(result) + "\n");
			}
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		writer.write(builder.toString());
		writer.close();
	}
	
	private String parse(Object result) {
		if(result instanceof String) {
			return '"' + result.toString() + '"';
		} else if(result instanceof List) {
			StringBuilder builder = new StringBuilder();
			builder.append("[");
			for(Object item : ((List) result)) {
				builder.append(parse(item));
			}
			builder.append("]");
			return builder.toString();
		} else {
			return result.toString();
		}
	}
	
}
