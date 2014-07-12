package me.grison.jtoml.impl;

import me.grison.jtoml.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Toml parsing class front-end.
 * 
 * <code>
 *     Toml toml = Toml.parse("pi = 3.141592653589793");
 *     Double pi = toml.getDouble("pi");
 * </code>
 *
 * @author <a href="mailto:a.grison@gmail.com">$Author: Alexandre Grison$</a>
*/
public class Toml {
	
    private static final Logger LOGGER = Logger.getLogger(Toml.class.getName());
    
    /** The default {@link TomlParser} loaded from {@link ServiceLoader}. Defaults to {@link SimpleTomlParser} if none found*/
    private static TomlParser parser;

    /**
     * Retrieve a TomlParser on classpath.
     */
    static {
        initDefaultParser();
    }

    public static Map<String, Object> parse(String tomlString) {
        return parse(tomlString, null);
    }

    public static Map<String, Object> parse(String tomlString, TomlParser tomlParser) {
        return parseString(tomlString);
    }

    public static Map<String, Object> parse(File file) throws IOException {
        return parse(file, null);
    }

    public static Map<String, Object> parse(File file, TomlParser tomlParser) throws IOException {
        return parseFile(file);
    }

    public static Map<String, Object> parseString(String string) {
        return parser.parse(string);
    }

	public static Map<String, Object> parseFile(File file) throws FileNotFoundException {
        return parseString(Util.FileToString.read(file));
	}

    /*
     * Uses a ServiceLoader to locate available {@link TomlParser} on classpath.
     * If none is found, the default {@link SimpleTomlParser} is used
     *
     * @throws IllegalStateException if too much {@link TomlParser} are found on classpath.
     */
    private static void initDefaultParser() {
        List<TomlParser> parsers = new ArrayList<TomlParser>();
        Iterator<TomlParser> parserIterator = ServiceLoader.load(TomlParser.class).iterator();
        while(parserIterator.hasNext()) parsers.add(parserIterator.next());
        // check too much (built-in one always available + one additional is OK)
        if (parsers.size() > 2) {
            throw new IllegalStateException("Too much TomlParser found on classpath: " + parsers);
        }
        // iterate on all available parsers
        for (TomlParser parser: parsers) {
            LOGGER.log(Level.CONFIG, "Found TomlParser instance on classpath: " + parser.getClass().getName());
            if (SimpleTomlParser.class.equals(parser.getClass()) && parser != null) {
                continue;
            }
            Toml.parser = parser;
        }
        // last-chance fallback
        if (parser == null) {
            parser = new SimpleTomlParser();
            LOGGER.log(Level.WARNING, "No TomlParser service loaded, defaulting to: " + parser.getClass().getName());
        }
    }

	public static void write(File file, Map<String, Object> config) {
		try {
			(new SimpleTomlWriter(file)).write(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
