[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.light0x00/light-json/badge.svg)
](https://repo1.maven.org/maven2/io/github/light0x00/light-json/) 
[![Java support](https://img.shields.io/badge/Java-9+-green?logo=java&logoColor=white)](https://openjdk.java.net/)

Very tiny, even less than 400 line codes.

```xml
<dependency>
    <artifactId>light-json</artifactId>
    <groupId>io.github.light0x00</groupId>
    <version>0.0.2</version>
</dependency>
```

## Usage

parse a json to map

```kotlin
LightJson.parseMap("""{"a":123,"b":false,"c":"c\"c\"cc","d":[{},1,"2",false,null,{"e":"eeee"}]}""")
```

parse a json to list

```kotlin
LightJson.parseList("""[1,2,{"a":"aaa"}]""")
```

stringify a object to json

```kotlin
LightJson.stringify(mapOf("a" to "aaa",b to listOf<Any>(1,2)))
```

## Features

***Precise visual error report***

```kotlin
LightJson.parseMap("""{"a":1,"b"::"bbb"}""")
```

```txt
io.github.light0x00.lightjson.internal.LightJsonException: Unrecognized identifier
"a":1,"b"::"bbb"}
         ^
 at line 1 column 11
```


***Circular dependencies detection***

```kotlin
    val alice = User("Alice")
    val bob = User("Bob")
    val cindy = User("Cindy")

    alice.friend = bob
    bob.friend = cindy
    cindy.friend = alice

    val json = LightJson.stringify(alice)
```

```txt
io.github.light0x00.lightjson.internal.LightJsonException: Circular dependencies detected:
User(name='Alice')->User(name='Bob')->User(name='Cindy')->User(name='Alice')
```
