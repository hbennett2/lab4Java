Name the two patterns you wish to implement:
1. Factory method --> add new types of events without modifying main code --> encapsulation
2. Observer pattern --> updates many componets whe event list changes --> componets can react to events wihtout changing main code

Describe how you implement the patterns, including additional interfaces, classes, and how these will integrate with the classes that you already have.
-->  Create an EventFactory class with a createEvent method that generates Event instances based on a specified type. 
--> Make an EventObserver interface, EventListPanel can implement this interface. When an event is created or updated, EventListPanel will be notified and can refresh the UI.
