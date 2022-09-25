package views.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;

import controller.Controller;
import database.DBManager;
import filters.Filter;
import logger.Handler;
import models.Provider;
import utils.AutoSuggestor;
import utils.AutoSuggestorManager;
import views.*;

public class ViewMediator {

    private Controller controller;
    
    //Autosugestors
    private AutoSuggestorManager autoSuggestorManager;

    //views
    Map<String,JFrame> views;
    Map<String,JTable> tables;
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

        autoSuggestorManager = new AutoSuggestorManager();

        views = new HashMap<>();
        tables = new HashMap<>();

        tables.put("ticketsTable", new javax.swing.JTable());
        mainView = new View(this.controller, this, tables.get("ticketsTable"));
        views.put("mainView", mainView);
        providerLoader = new ProviderLoaderView(this.controller, this);
        views.put("providerLoader", providerLoader);
        filtersView = new FiltersView(this.controller, this);
        views.put("filtersView", filtersView);
        tables.put("providersTable", new javax.swing.JTable());
        providersView = new ProvidersView(controller, this, tables.get("providersTable"));
        views.put("providersView", providersView);
        columnSelectorView = new ColumnSelectorView(this);
        views.put("columnSelectorView", columnSelectorView);
        ticketLoaderView = new TicketLoaderView(this.controller, this);
        views.put("ticketLoaderView", ticketLoaderView);
        withholdingLoaderView = new WithholdingLoaderView(this.controller, this);
        views.put("withholdingLoaderView", withholdingLoaderView);
        sectorsView = new SectorsView(this);
        views.put("sectorsView", sectorsView);
        providerLoader = new ProviderLoaderView(this.controller, this);
        views.put("providerLoader", providerLoader);
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

    /**
     * sets visible or not the view with the given view name, in case there is no such view an exception is thrown
     * @param visible indicates to hide or show the view
     * @param viewName is the name identifier to search the view
     */
    public void setViewVisible(boolean visible, String viewName) {
        JFrame view = views.get(viewName);
        if (view == null) {
            throw new IllegalArgumentException("there is no view registered as " + viewName);
        }
        view.setVisible(visible);
    }

    public void updateProviderSuggestions() {
        autoSuggestorManager.updateProviderSuggestions(getProvidersName());
    }

    public void updateSectorSuggestions() {
        autoSuggestorManager.updateSectorSuggestions(getSectorsName());
    }

    /**
     * This method get all filters from filtersView and return it
     * @return array of filter from filtersView
     */
    public Filter[] getFilters(){
        return filtersView.getFilters().toArray(new Filter[0]);
    }

    /**
     * gets the table with the given table name, in case there is no such table an exception is thrown
     * @param tableName is the name identifier to search the table
     * @return the table with that table name
     */
    public JTable getTable(String tableName) {
        JTable table = tables.get(tableName);
        if (table == null) {
            throw new IllegalArgumentException("there is no table registered as " + tableName);
        }
        return table;
    }

    public AutoSuggestorManager getAutoSuggestorManager() { return autoSuggestorManager; }

    /*
     * return the name of all providers
     */
    public List<String> getProvidersName() {
        return controller.getProviders().stream().map(s -> (String) s.getValues().get("name")).collect(Collectors.toList());
    }
    /*
     * return the name of all sectors
     */
    public List<String> getSectorsName() {
        return controller.getSector().stream().map(s -> (String)s.getValues().get("name")).collect(Collectors.toList());
    }


    
}
