miroslav.trninic@gmail.com

Ohboy, I am reinventing the wheel with new DIP/IoC/DI framework.

# Repeated patterns
    service (behavior)
    model (data)
    config (change)
    set (data structure)
    reflection


 1. Locate executable location and type (jar or directory) from the directory of main class (Boot)
 Boot (main class)
 new LocationResolver (service/interface)
 returns Location (data model)
 LocationType (enum)
 2. Class scanner (in directory of .jar)
 ClassLocator interface (JAR of directory implementation)
 scan for classes, strip, store in set and return
 
 3. Container annotations intefaces
 4. Configuration stack
    OhboyConfig - project based configuration
        BaseConfig - for extension
            CustomAnnotationConfig - extended
  5. ServiceDetails model (reflection data about service)
        ServiceDetailConstructorComparator
  6. ServiceScanner interface
  7. ServiceScanner is mapping found classes to found annotations and saving them to temporary (runtime) HashSet
  8. ServiceBeanDetails extends ServiceDetails - details about annotated service bean method
  9. ObjectInstantiationService - responsible for invoking annotated services and beans inside the services
  10. InstantiationConfig/EnqueuedServiceDetails
  11. ServiceInstanitationService
 
