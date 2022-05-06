# 1. Study Java common libraries

# 2. Introduction about Library

## 2.1 Jackson Annotations

    Jackson Annotations is a library that provides annotations for Jackson. The module is `jackson-modules`.

### 2.1.1 Jackson Serialization Annotations

1. JsonAnyGetter

The @JsonAnyGetter annotation allows for the flexibility of using a Map field as standard properties.

2. JsonGetter

The @JsonGetter annotation is an alternative to the @JsonProperty annotation, which marks a method as a getter method.

3. JsonPropertyOrder

We can use the @JsonPropertyOrder annotation to specify the order of properties on serialization.

4. JsonRawValue

The @JsonRawValue annotation can instruct Jackson to serialize a property exactly as is.

5. JsonValue

@JsonValue indicates a single method that the library will use to serialize the entire instance.

For example, in an enum, we annotate the getName with @JsonValue so that any such entity is serialized via its name:

6. JsonRootName

The @JsonRootName annotation is used, if wrapping is enabled, to specify the name of the root wrapper to be used.

Wrapping means that instead of serializing a User to something like:

7. JsonSerializer

@JsonSerialize indicates a custom serializer to use when marshalling the entity.



