package views;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;

import controller.Controller;
import models.Provider;
import utils.AutoSuggestor;

public class ViewMediator {

    
    
    
    private Controller controller;
    
    //Autosugestors
    private AutoSuggestor providersAutoSuggestor;
    private AutoSuggestor sectorsAutoSuggestor;
    private JComboBox providersComboBox;
    private JComboBox sectorsComboBox;

    //views
    private ProviderLoaderView providerLoader;

    public ViewMediator(){
        
        providersComboBox = new JComboBox();
        providersAutoSuggestor = new AutoSuggestor(providersComboBox, getProvidersName());
        providersAutoSuggestor.autoSuggest();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectorsName());
        sectorsAutoSuggestor.autoSuggest();


        providerLoader = new ProviderLoaderView(controller);

    }

    public AutoSuggestor getSectorAutosuggestor(){
        return sectorsAutoSuggestor;
    }

    public void setProviderLoaderVisible(){
        providerLoader.setVisible(true);
    }

    public void updateSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
        sectorsAutoSuggestor.setSuggestions(getSectorsName());
    }
    /*
     * return the name of all providers
     */

    private List<String> getProvidersName() {
        return controller.getProviders().stream().map(s -> (String) s.getValues().get("name")).collect(Collectors.toList());
    }
    /*
     * return the name of all sectors
     */
    private List<String> getSectorsName() {
        return controller.getSector().stream().map(s -> (String)s.getValues().get("name")).collect(Collectors.toList());
    }
    
}
