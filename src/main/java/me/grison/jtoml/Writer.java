package me.grison.jtoml;

import java.io.File;
import java.util.Map;

public interface Writer {

	void write(File file, Map<String, Object> contents);
	
}
