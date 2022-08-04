package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/${env}.properties")
public interface WebConfig extends Config {
    @Key("browser")
    @DefaultValue("CHROME")
    String browser();

    @Key("baseUrl")
    @DefaultValue("http://demowebshop.tricentis.com")
    String getBaseUrl();

    @Key("remoteUrl")
    String getRemoteUrl();
}
