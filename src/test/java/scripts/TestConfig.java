package scripts;

import org.aeonbits.owner.Config;

@Config.Sources({ "classpath:TestConfig.properties" })

public interface TestConfig extends Config {

    String name();
    String latName();
    String surname();
    String latSurname();
    String blogName();
    String birthDate();
    String country();
    String city();
    String englishLevel();
    String readyToRelocate();
    String workRegime();
    String contactOneType();
    String contactOneLink();
    String contactTwoType();
    String contactTwoLink();
    String gender();
    String company();
    String work();

}