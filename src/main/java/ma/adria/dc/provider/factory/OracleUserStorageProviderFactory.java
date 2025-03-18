package ma.adria.dc.provider.factory;

import ma.adria.dc.provider.OracleUserStorageProvider;
import org.jboss.resteasy.logging.Logger;
import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;

import java.util.ArrayList;
import java.util.List;

public class OracleUserStorageProviderFactory implements UserStorageProviderFactory<OracleUserStorageProvider> {

    public static final String PROVIDER_NAME = "dc-provider";


    public static final String CONFIG_KEY_JDBC_URL = "jdbcUrl";
    public static final String CONFIG_KEY_DB_USERNAME = "dbUsername";
    public static final String CONFIG_KEY_DB_PASSWORD = "dbPassword";

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public OracleUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new OracleUserStorageProvider(session, model);
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        List<ProviderConfigProperty> configProperties = new ArrayList<>();


        ProviderConfigProperty jdbcUrl = new ProviderConfigProperty();
        jdbcUrl.setType(ProviderConfigProperty.STRING_TYPE);
        jdbcUrl.setName(CONFIG_KEY_JDBC_URL);
        jdbcUrl.setLabel("JDBC URL");
        jdbcUrl.setHelpText("URL de connexion à la base de données Oracle (ex: jdbc:oracle:thin:@localhost:1521:ORCL)");
        jdbcUrl.setDefaultValue("jdbc:oracle:thin:@localhost:1521:ORCL");
        configProperties.add(jdbcUrl);


        ProviderConfigProperty dbUsername = new ProviderConfigProperty();
        dbUsername.setType(ProviderConfigProperty.STRING_TYPE);
        dbUsername.setName(CONFIG_KEY_DB_USERNAME);
        dbUsername.setLabel("Database Username");
        dbUsername.setHelpText("Nom d'utilisateur pour se connecter à la base de données Oracle");
        configProperties.add(dbUsername);


        ProviderConfigProperty dbPassword = new ProviderConfigProperty();
        dbPassword.setType(ProviderConfigProperty.PASSWORD);
        dbPassword.setName(CONFIG_KEY_DB_PASSWORD);
        dbPassword.setLabel("Database Password");
        dbPassword.setHelpText("Mot de passe pour se connecter à la base de données Oracle");
        configProperties.add(dbPassword);

        return configProperties;
    }

}