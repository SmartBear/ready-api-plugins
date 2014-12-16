#Ready! API Architecture

##Guice and Governator

Ready! API uses Google [Guice](https://github.com/google/guice/wiki/GettingStarted) as its dependency-injection 
framework and NetFlix [Governator](https://github.com/Netflix/governator/wiki) for additional functionality 
(@PostCreate, @PreDestroy, etc). All plugin classes that are loaded via one of the described plugin annotations will
get instantiated through Guice and thus have the possibility to inject any other exposed Ready! API class.

