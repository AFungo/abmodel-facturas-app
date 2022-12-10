package loader;

import builder.ProviderBuilder;
import builder.TicketBuilder;
import builder.WithholdingBuilder;
import models.*;


public class LoadFromAFIPLine implements Loader<String[]>{

    ProviderLoader providerLoader = new ProviderLoader();
    WithholdingLoader withholdingLoader = new WithholdingLoader();
    TicketLoader ticketLoader = new TicketLoader();

    boolean issuedByMe;

    public LoadFromAFIPLine(boolean issuedByMe){
        this.issuedByMe = issuedByMe;
    }
    /*
     * this method take a String[] of values and create a new ticket
     */
    public String[] load(String[] line){
        ProviderBuilder providerBuilder = new ProviderBuilder();
        Provider provider = (Provider)providerBuilder.build(line[7], line[8], line[9]);
        provider = providerLoader.load(provider);

        WithholdingBuilder withholdingBuilder = new WithholdingBuilder();
        Withholding withholding = (Withholding) withholdingBuilder.build(line[0], line[1], line[2] + line[3],
                provider);//files[i][2] + files[i][3] create the number of ticket/withholding
        withholding = withholdingLoader.load(withholding);

        TicketBuilder ticketBuilder = new TicketBuilder();
        Ticket ticket = (Ticket) ticketBuilder.build(withholding, line[0], line[4], line[5], line[9], line[10], line[11],
                line[12], line[13], line[14], line[15], issuedByMe);
        ticketLoader.load(ticket);
        return line;
    }

}
