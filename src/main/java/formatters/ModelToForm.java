package formatters;

import models.*;

public class ModelToForm {

    public static Object[] toForm(Ticket ticket) {
        Object[] values = new Object[11];
        values[0] = ticket.getValues().get("type");
        return values;
    }
    
    public static Object[] profitWithholdingToForm(Withholding withholding) {
        throw new UnsupportedOperationException("TODO");
    }

    public static Object[] IVAWithholdingToForm(Withholding withholding) {
        throw new UnsupportedOperationException("TODO");
    }

    public static Object[] toForm(Provider provider) {
        Object[] values = new Object[11];
        values[0] = provider.getValues().get("type");
        return values;
    }

}
