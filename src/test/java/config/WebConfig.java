package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/${env}.properties")
public interface WebConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("http://demowebshop.tricentis.com")
    String getBaseUrl();

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key("selenideLogin")
    String getSelenideLogin();

    @Key("selenidePass")
    String getSelenidePass();
}
