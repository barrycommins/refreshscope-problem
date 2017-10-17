# Refresh Scope Problem Demo

This project was an attempt to figure out an issue with using Spring Boot `@ConfigurationProperties` 
with multi-value properties (Lists, Maps, etc.)


The unexpected behaviour observed was that when removing an item from the property and calling the `/refresh` endpoint, 
the value was not being removed. 
Replacing or adding values worked fine.

The solution appears to be to explicitly set the `@ConfigurationProperties` annotated class as:

```java
@RefreshScope
@Component
@ConfigurationProperties
public class RefreshScopeConfigurationProperties {
    
}
```

Using this, you get an aspect-advised version of the class that supports full refresh.

This can be manually verified by uncommenting the top level `application.yml` and starting the application.
The first call to `/list` will return the properties.

If the values are then changed (e.g. item2 removed), and an empty body `{}` is POSTed to `/refresh`, 
the next call to `/list` will show the changed value.
