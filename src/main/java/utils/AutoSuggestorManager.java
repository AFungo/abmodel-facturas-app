package utils;

import java.util.LinkedList;
import java.util.List;

/**
 * this class is meant to act as a subject for the added observers (autosuggestors)
 */
public class AutoSuggestorManager {

    private List<AutoSuggestor> providerAutoSuggestors;
    private List<AutoSuggestor> sectorAutoSuggestors;

    public AutoSuggestorManager() {
        providerAutoSuggestors = new LinkedList<>();
        sectorAutoSuggestors = new LinkedList<>();
    }

    /**
     * registers the given provider autosuggestor to be an observer and be updated
     * @param autoSuggestor the autosuggestor to add
     */
    public void registerProviderAutoSuggestor(AutoSuggestor autoSuggestor) {
        providerAutoSuggestors.add(autoSuggestor);
    }

    /**
     * registers the given sector autosuggestor to be an observer and be updated
     * @param autoSuggestor the autosuggestor to add
     */
    public void registerSectorAutoSuggestor(AutoSuggestor autoSuggestor) {
        sectorAutoSuggestors.add(autoSuggestor);
    }

    /**
     * updates all the registered provider autosuggestors with the given suggestions
     * @param suggestions the new suggestions
     */
    public void updateProviderSuggestions(List<String> suggestions) {
        for (AutoSuggestor providerSuggestor : providerAutoSuggestors) {
            providerSuggestor.setSuggestions(suggestions);
        }
    }

    /**
     * updates all the registered sector autosuggestors with the given suggestions
     * @param suggestions the new suggestions
     */
    public void updateSectorSuggestions(List<String> suggestions) {
        for (AutoSuggestor sectorSuggestor : sectorAutoSuggestors) {
            sectorSuggestor.setSuggestions(suggestions);
        }
    }

}
