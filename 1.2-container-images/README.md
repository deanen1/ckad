# Overview 

This is a simple java application that can be used to explore the different options when running a Kubernetes Job. 

|Argument|Values|Default Value|Description|
|--|--|--|--|
|outcome|complete or fail|complete|Determines whether the application should terminate with a success exit code or not|
|after|Positive integer value|0|Number of seconds to wait before the application should wait before terminating|
|exitCode|Positive integer value between 0 - 255|0|When outcome is 'fail' this argument can be used to control the exit code to be used on termination|

# Usage

Running the application as a java application:

First build the application:

> \> mvn clean package

Running the application with defaults: 

> \>  java -jar target/simple-java-app-0.0.1-SNAPSHOT.jar

Running the application with arguments:

> \>  java -jar target/simple-java-app-0.0.1-SNAPSHOT.jar --outcome=fail --after=3 --exitCode=34
