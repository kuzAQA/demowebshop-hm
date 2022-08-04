package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/authData.properties")
public interface AuthData extends Config {
    @Key("email")
    String email();

    @Key("pass")
    String pass();
}
