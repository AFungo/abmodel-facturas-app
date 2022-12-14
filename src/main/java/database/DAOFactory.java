package database;

import models.ModelEnum;

public class DAOFactory {

    //FIXME: this should be create because its a factory, but this really gets the instance of DAO
    public static DAO get(ModelEnum model) {
        switch (model) {
            case TICKET:
                return TicketDAO.getInstance();
            case WITHHOLDING:
                return WithholdingDAO.getInstance();
            case PROVIDER:
                return ProviderDAO.getInstance();
            case SECTOR:
                return SectorDAO.getInstance();
            case DOLLARPRICE:
                return DollarPriceDAO.getInstance();
            default:
                throw new IllegalArgumentException("DAOFactory received an invalid argument " + model);
        }
    }
}
