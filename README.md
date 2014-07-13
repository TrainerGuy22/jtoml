TOML for Java
===
This is a parser for a subset of the [TOML](https://raw.github.com/mojombo/toml/) markup language, using Java.

Differences from normal TOML
----

jTOML only supports groups one layer thick, you can't have groups inside groups like TOML.

Disclaimer
----

I forked this primarially for my own personal usage. The tests are out of date, and documentation can be lacking. I might improve on these in the future.

Usage
----

### Parsing

```java
Map<String, Object> toml = Toml.parse("pi = 3.14\nfoo = \"bar\""); // parse a String
toml = Toml.parse(new File("foo.toml")); // or a file
```

### Getting values

The `TomlWrapper` class support different types of getters so that you can retrieve a specific type or the underlying `Object` without casting.

```java
// get different types
TomlWrapper toml = new TomlWrapper(Toml.parse(new File("foo.toml")));
toml.get("foo"); // Object
toml.getString("foo"); // String
toml.getBoolean("foo"); // Boolean
toml.getDate("foo"); // Calendar
toml.getDouble("foo"); // Double
toml.getLong("foo"); // Long
toml.getList("foo"); // List<Object>
toml.getMap("foo"); // Map<String, Object>
```

jTOML also supports generating TOML files.

**Note:** Supported types are `Long`, `String`, `Double`, `Boolean`, `Calendar`, `List`, `Map` or Objects having the pre-cited types only.


License
-----
[MIT License (MIT)](http://opensource.org/licenses/mit-license.php).

See the [`LICENSE`](https://github.com/agrison/jtoml/blob/master/LICENSE) file.
