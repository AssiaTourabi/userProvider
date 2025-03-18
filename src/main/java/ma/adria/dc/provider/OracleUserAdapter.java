package ma.adria.dc.provider;



import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OracleUserAdapter extends AbstractUserAdapter {

    private final String username;
    private static final Logger logger = LoggerFactory.getLogger(OracleUserStorageProvider.class);

    public OracleUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, String username) {
        super(session, realm, model);
        logger.debug("OracleUserAdapter créé avec username aaaa: " + username);
        this.username = username;
    }

    @Override
    public String getUsername() {
        logger.debug("OracleUserAdapter.getUsername : " + username);
        return username;
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return Collections.emptyMap(); // Aucun attribut supplémentaire dans cet exemple
    }

    /*@Override
    public Set<RoleModel> getRoles() {
         return userDetails.getAuthorities().stream()
        .map(authority -> session.roles().getRealmRole(realm, authority))
        .collect(Collectors.toSet());
}

*/
}
