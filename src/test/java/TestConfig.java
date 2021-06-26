import org.aeonbits.owner.Config;

@Config.Sources({ "classpath:TestConfig.properties" })

public interface TestConfig extends Config {
    String hostnameYM();
    String Samsung();
    String Xiaomi();

}