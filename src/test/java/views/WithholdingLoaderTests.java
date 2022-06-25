/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package views;

import facturas.app.Controller;
import facturas.app.database.DBManager;
import facturas.app.database.DBManager.TypeDB;
import facturas.app.views.ProviderLoaderView;
import facturas.app.views.View;
import facturas.app.views.WithholdingLoaderView;
import static org.assertj.swing.core.matcher.JTextComponentMatcher.withName;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author agustinnolasco
 */
public class WithholdingLoaderTests {
    
    private FrameFixture demo;
    
    @BeforeEach
    public void setUp() {
        // Create the connection with the db using the type TESTING
        DBManager.createConnection(TypeDB.TESTING);
        DBManager.initializeDB();
        
        Controller ctrl = new Controller();   
        View mainView = GuiActionRunner.execute(() -> new View(ctrl));
        
        // Create a provider to be used in the tests
        ProviderLoaderView provView = GuiActionRunner.execute(() -> new ProviderLoaderView(ctrl, mainView));
        FrameFixture provFixture = new FrameFixture(provView);
        provFixture.show();
        provFixture.textBox(withName("providerNameTextField")).setText("PROVEEDOR1");
        provFixture.textBox("providerDocTextField").setText("12345");
        provFixture.button("addProvider").click();
        provFixture.cleanUp();
        
        // Create the view that will be tested
        WithholdingLoaderView frame = GuiActionRunner.execute(() -> new WithholdingLoaderView(ctrl, mainView));
        demo = new FrameFixture(frame);
        demo.show();
    }
    
    @AfterEach
    public void tearDown() {
        // reset the db and close the connection
        DBManager.deleteDB();
        DBManager.closeConnection();
        demo.cleanUp();
    }

    //@Test
    /* Load a withholding sample */
    public void test1() {
        // Arrange
        String number = "1234";
        String iva = "10";
        int provider = 0;
        
        // Act
        demo.textBox("numberTextField").enterText(number);
        demo.textBox("ivaTextField").enterText(iva);
        demo.comboBox("providersComboBox").selectItem(provider);
        demo.textBox("dateTextField").setText("01-01-2021");
        demo.button("loadWithholding").click();
        
        // Assert
        demo.textBox("showLastTicketNumberTextField").requireText(number);
        demo.textBox("showLastIvaTextField").requireText(iva);
        demo.textBox("showLastProfitsTextField").requireText("");
        demo.textBox("showLastDateTextField").requireText("01/01/2021");
    }
}
