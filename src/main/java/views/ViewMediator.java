package views;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;

import controller.Controller;
import filters.Filter;
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

    private CalculusView calculuesView;
    private ColumnSelectorView columnSelectorView;
    private FiltersView filtersView;
    private ProviderLoaderView providerLoader;
    private ProvidersView providersView;
    private SectorsView sectorsView;
    private TicketLoaderView ticketLoaderView;
    private View mainView;
    private WithholdingLoaderView withholdingLoaderView;

    
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

    public void setProviderLoaderVisible(boolean visible){
        providerLoader.setVisible(visible);
    }

    public void updateSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
        sectorsAutoSuggestor.setSuggestions(getSectorsName());
    }

    /**
     * This method get all filters from filtersView and return it
     * @return array of filter from filtersView
     */
    public Filter[] getFilters(){
        return filtersView.getFilters().toArray(new Filter[0]);
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
