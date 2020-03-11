miroslav.trninic@gmail.com

Ohboy, I am reinventing the wheel with new DIP/IoC/DI framework.


 Locate executable location and type (jar or directory) from the directory of main class (Boot)
 Boot (main class)
 new LocationResolver (service/interface)
 returns Location (data model)
 LocationType (enum)
 Class scanner (in directory of .jar)
 ClassLocator interface (JAR of directory implementation)
 scan for classes, strip, store in set and return
 Configuration stack
    OhboyConfig - project based configuration
        BaseConfig - for extension
            CustomAnnotationConfig - extended
  ServiceDetails model (reflection data about service)
        ServiceDetailConstructorComparator
 