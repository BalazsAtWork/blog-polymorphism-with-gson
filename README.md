
# Gson and Object Hierarchies


[Gson][1] is a small Java library for serializing objects into JSON and deserializing JSON back to java objects.
But how can we deal with object polymorphism in Gson?


## Serializing a Simple Object

Imagine a [POJO][4] named `Car`.

```java
class Car {
    private int cargoCapacityInLiter;
    //...
}
```

To serialize objects to JSON, we just create an instance of `Gson` with the `GsonBuilder` and use the `toJson` method.

```java
Gson gson = new GsonBuilder().create();
String jsonResult = gson.toJson(new Car());
```

This will give us the result as a JSON String: `{"cargoCapacityInLiter":0}`

To convert this JSON back to a Java object, we use the `fromJson` method.

```java
Car car = gson.fromJson(jsonResult, Car.class);
```

That's pretty straight forward!


## Serializing Object Hierarchies

Let us extend our structure with an abstract class `Vehicle`, a `Plane` and a `Bicycle` POJO. The existing `Car` also extends the `Vehicle` class.

```java
abstract class Vehicle {
    private int maxSpeed;
    //...
}

class Car extends Vehicle {
    private int cargoCapacityInLiter;
    //...
}

class Plane extends Vehicle {
    private int wingspanInMeter;
    //...
}

class Bicycle extends Vehicle {
    private int frameHeight;
    //...
}
```

If we serialize instances of these three classes to JSON, ...

```java
Gson gson = new GsonBuilder().create();
String carJson = gson.toJson(new Car());
String planeJson = gson.toJson(new Plane());
String bicycleJson = gson.toJson(new Bicycle());
```

... we get the following strings.

```json
{"cargoCapacityInLiter":0,"maxSpeed":0}
{"wingspanInMeter":0,"maxSpeed":0}
{"frameHeight":0,"maxSpeed":0}
```

But here is the problem: How we can distinguish these objects during *deserialization*?
The JSON strings have lost the type information. Is this string a `Car` or a `Bicycle`? Gson cannot infer that just from the field names. And if we try to deserialize an abstract `Vehicle` ...

```java
Vehicle vehicle = gson.fromJson(c, Vehicle.class);
```

... we will get an `InstantiationException`.


## Preserving Runtime Type Information

The solution is to make the type information (e.g. what type of object we are serializing) available during deserialization by including it in the serialized string:

```json
{"type":"Car","cargoCapacityInLiter":0,"maxSpeed":0}
```

This is a common problem, so it is somewhat surprising that Gson does not already offer an option to do that.
The Gson project on GitHub contains an additional library, [gson-extras][2], which provides that functionality in form of `TypeAdapter`s. However this library is [not officially released and maintained][5].

We now have two options. We can locally install the gson-extras library by cloning the official GitHub repo and running `mvn install`. However, we saw failing tests and a `pom.xml` incompatibility with our version of Maven. The recommended alternative is to just copy what we need to our project. In our case, this is just `RuntimeTypeAdapterFactory.java`.

Next, we have to create a `RuntimeTypeAdapterFactory` of the base type `Vehicle` and register all subclasses of `Vehicle`, each with a descriptive label. We then create the `Gson` instance as we did previously, but this time we also let Gson know about our object hierarchy.

```java
RuntimeTypeAdapterFactory<Vehicle> vehicleAdapterFactory = RuntimeTypeAdapterFactory.of(Vehicle.class, "type")
    .registerSubtype(Car.class, "Car")
    .registerSubtype(Plane.class, "Plane")
    .registerSubtype(Bicycle.class, "Bicycle");

Gson gson = new GsonBuilder().registerTypeAdapterFactory(vehicleAdapterFactory).create();
```

When we serialize now ...

```java
String carJson = gson.toJson(new Car(), Vehicle.class);
String planeJson = gson.toJson(new Plane(), Vehicle.class);
String bicycleJson = gson.toJson(new Bicycle(), Vehicle.class);
```

... a type field with the previously specified label is included in the JSON strings.

```json
{"type":"Car","cargoCapacityInLiter":0,"maxSpeed":0}
{"type":"Plane","wingspanInMeter":0,"maxSpeed":0}
{"type":"Bicycle","frameHeight":0,"maxSpeed":0}
```

Now we can deserialize `Vehicle` instances without ambiguities. We only have to provide the abstract class as type parameter.

```java
Vehicle car = gson.fromJson(carJson, Vehicle.class);
Vehicle plane = gson.fromJson(planeJson, Vehicle.class);
Vehicle bicycle = gson.fromJson(bicycleJson, Vehicle.class);
```

It was suprising that Gson doesn't include handling object hierarchies in their core library. This makes such a routine task harder than expected.

If given a choice, we would generally prefer [Jackson][6], which offers annotation-driven polymorphic de-/serialization out-of-the-box. The only annoyance with Jackson is that base class annotations have to reference the subtypes, which introduces circular dependencies and limits reuse. But that is a different topic and for another post.

A working example of the code used in this post is available on [GitHub][3].


[1]: https://github.com/google/gson
[2]: https://github.com/google/gson/tree/master/extras
[3]: https://github.com/BalazsAtWork/blog-polymorphism-with-gson
[4]: https://en.wikipedia.org/wiki/Plain_old_Java_object
[5]: https://github.com/google/gson/issues/845
[6]: https://github.com/FasterXML/jackson
