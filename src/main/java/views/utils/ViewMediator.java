package views.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;

import controller.Controller;
import database.DBManager;
import filters.Filter;
import logger.Handler;
import models.Provider;
import utils.AutoSuggestor;
import views.*;

public class ViewMediator {

    private Controller controller;
    
    //Autosugestors
    private AutoSuggestor providersAutoSuggestor;
    private AutoSuggestor sectorsAutoSuggestor;
    private JComboBox providersComboBox;
    private JComboBox sectorsComboBox;

    //views
    private CalculusView calculusView;
    private ColumnSelectorView columnSelectorView;
    private FiltersView filtersView;
    private ProviderLoaderView providerLoader;
    private ProvidersView providersView;
    private SectorsView sectorsView;
    private TicketLoaderView ticketLoaderView;
    private WithholdingLoaderView withholdingLoaderView;

    private View mainView;
    
    public ViewMediator(Controller controller){
        this.controller = controller;

        providersComboBox = new JComboBox();
        providersAutoSuggestor = new AutoSuggestor(providersComboBox, getProvidersName());
        providersAutoSuggestor.autoSuggest();
        sectorsComboBox = new JComboBox();
        sectorsAutoSuggestor = new AutoSuggestor(sectorsComboBox, getSectorsName());
        sectorsAutoSuggestor.autoSuggest();

        providerLoader = new ProviderLoaderView(this.controller, this);
        filtersView = new FiltersView(this.controller, this);
        providersView = new ProvidersView(controller, this);
        columnSelectorView = new ColumnSelectorView(this);
        ticketLoaderView = new TicketLoaderView(this.controller, this);
        withholdingLoaderView = new WithholdingLoaderView(this.controller, this);
        sectorsView = new SectorsView(this);
        providerLoader = new ProviderLoaderView(this.controller, this);
        mainView = new View(this.controller, this);
    }

    public void setMainViewVisible(boolean visible){
        Handler globalExceptionHandler = new Handler(mainView);
        Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);

        mainView.setVisible(true);

        mainView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                DBManager.closeConnection();
            }
        });
    }

    public AutoSuggestor getProviderAutosuggestor(){
        return providersAutoSuggestor;
    }

    public AutoSuggestor getSectorAutosuggestor(){
        return sectorsAutoSuggestor;
    }

    //TODO: maybe we can do a single method with a switch for setting visible each view
    public void setProviderLoaderVisible(boolean visible){ providerLoader.setVisible(visible); }

    public void setFiltersViewVisible(boolean visible){ filtersView.setVisible(visible); }

    public void setTicketLoaderVisible(boolean visible){ ticketLoaderView.setVisible(visible); }

    public void setColumnSelectorVisible(boolean visible){ columnSelectorView.setVisible(visible); }

    public void setSectorsViewVisible(boolean visible){ sectorsView.setVisible(visible); }

    public void setWithholdingLoaderVisible(boolean visible){ withholdingLoaderView.setVisible(visible); }

    public void setCalculusViewVisible(boolean visible){ calculusView.setVisible(visible); }

    public void updateProviderSuggestions() {
        providersAutoSuggestor.setSuggestions(getProvidersName());
    }

    public void updateFiltersSuggestions() { filtersView.updateSuggestions(); }

    public void updateTicketLoaderSuggestions() { ticketLoaderView.updateSuggestions(); }

    public void updateSectorSuggestions() {
        sectorsAutoSuggestor.setSuggestions(getSectorsName());
    }

    /**
     * This method get all filters from filtersView and return it
     * @return array of filter from filtersView
     */
    public Filter[] getFilters(){
        return filtersView.getFilters().toArray(new Filter[0]);
    }

    public JTable getTicketTable(){return mainView.getTicketsTable();}

    public JTable getProviderTable(){return providersView.getTable();}

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
