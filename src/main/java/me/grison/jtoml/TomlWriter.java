package me.grison.jtoml;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import me.grison.jtoml.impl.Toml;

public interface TomlWriter {	
	void write(Map<String, Object> config) throws IOException;
}
