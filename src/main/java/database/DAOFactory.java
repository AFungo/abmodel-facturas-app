package database;

public class DAOFactory {

    public static DAO get(DAOEnum dao) {
        switch (dao) {
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
                throw new IllegalArgumentException("DAOFactory received an invalid argument " + dao);
        }
    }
}
