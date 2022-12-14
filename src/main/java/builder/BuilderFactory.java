package builder;

import models.ModelEnum;

public class BuilderFactory {

    public static Builder create(ModelEnum model) {
        switch (model) {
            case TICKET:
                return new TicketBuilder();
            case WITHHOLDING:
                return new WithholdingBuilder();
            case PROVIDER:
                return new ProviderBuilder();
            case SECTOR:
                return new SectorBuilder();
            case DOLLARPRICE:
                return new DollarPriceBuilder();
            default:
                throw new IllegalArgumentException("BuilderFactory received an invalid argument " + model);
        }
    }
}
