package loader;

import database.ProviderDAO;
import models.Provider;

import java.util.Optional;

public class ProviderLoader implements Loader<Provider>{
    /*
     * this method take a provider, and try to save it, if its already exist return db value;
     */
    public Provider load(Provider provider) {
        if (!ProviderDAO.getInstance().save(provider)) {
            Optional<Provider> providerOptional = ProviderDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(provider.getID()))
                    .findFirst();
            if (providerOptional.isPresent()) {
                return providerOptional.get();
            } else {
                throw new IllegalStateException("The provider could not be saved but also not obtained");
            }
        }
        return provider;
    }
}
