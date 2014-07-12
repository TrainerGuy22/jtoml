package me.grison.jtoml;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface TomlWriter {	
	void write(Map<String, Object> config) throws IOException;
}
