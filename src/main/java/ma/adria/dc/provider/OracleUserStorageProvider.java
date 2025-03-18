package ma.adria.dc.provider;



import ma.adria.dc.provider.factory.OracleUserStorageProviderFactory;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.mindrot.jbcrypt.BCrypt;
public class OracleUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

    private final KeycloakSession session;
    private final ComponentModel model;

    private static final Logger logger = LoggerFactory.getLogger(OracleUserStorageProvider.class);

    public OracleUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {

        logger.debug("Validation isvalid : " + user.getUsername());
        if (!supportsCredentialType(credentialInput.getType())) {
            return false;
        }

        String username = user.getUsername();
        String password = credentialInput.getChallengeResponse();

        try (Connection connection = getOracleConnection()) {
            String query = "SELECT password FROM UTILISATEUR WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        //return BCrypt.checkpw(password, storedHashedPassword);
                        String storedPassword = rs.getString("password");
                        return password.equals(storedPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        logger.debug("Recherche getUserById : " + id);
        try (Connection connection = getOracleConnection()) {
            String query = "SELECT * FROM UTILISATEUR WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String username = rs.getString("username");
                        logger.debug("Utilisateur trouvé : " + username);
                        return new OracleUserAdapter(session, realm, model, username);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur SQL lors de la récupération de l'utilisateur par ID", e);
        }
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        logger.debug("Recherche getUserByUsername : " + username);
        try (Connection connection = getOracleConnection()) {
            String query = "SELECT * FROM UTILISATEUR WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        logger.debug("Recherche getUserByUsername found: " + username);
                        return new OracleUserAdapter(session, realm, model, username);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.debug("Recherche getUserByUsername not found: " + username);
        return null;
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        return null;
    }



    @Override
    public void close() {

    }

    private Connection getOracleConnection() throws SQLException {

        logger.debug("connextion provider : ");

        String jdbcUrl = model.getConfig().getFirst(OracleUserStorageProviderFactory.CONFIG_KEY_JDBC_URL);
        String dbUsername = model.getConfig().getFirst(OracleUserStorageProviderFactory.CONFIG_KEY_DB_USERNAME);
        String dbPassword = model.getConfig().getFirst(OracleUserStorageProviderFactory.CONFIG_KEY_DB_PASSWORD);

        logger.error("after,,,: ");

        return DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
    }
}
