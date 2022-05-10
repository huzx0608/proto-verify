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

### 2.1.2 Jackson Deserialization Annotations

1. JsonCreator

The @JsonCreator annotation is used to indicate a constructor that should be used to create an instance of the class.

2. JacksonInject

@JacksonInject indicates that a property will get its value from the injection and not from the JSON data.

3. JsonAnySetter

@JsonAnySetter allows us the flexibility of using a Map as standard properties. On deserialization, the properties from JSON will simply be added to the map.

4. JsonSetter

@JsonSetter is an alternative to @JsonProperty that marks the method as a setter method.

This is incredibly useful when we need to read some JSON data, but the target entity class doesn't exactly match that data, and so we need to tune the process to make it fit.

5. JsonDeserialize

@JsonDeserialize indicates the use of a custom deserializer.

6. JsonAlias

The @JsonAlias defines one or more alternative names for a property during deserialization.

## 2.2 Jackson Polymorphic Type Handling Annotations

Next let's take a look at Jackson polymorphic type handling annotations:
- @JsonTypeInfo – indicates details of what type information to include in serialization
- @JsonSubTypes – indicates sub-types of the annotated type
- @JsonTypeName – defines a logical type name to use for annotated class




