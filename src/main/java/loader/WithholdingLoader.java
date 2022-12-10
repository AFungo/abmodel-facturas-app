package loader;

import database.WithholdingDAO;
import models.Withholding;

import java.util.Optional;

public class WithholdingLoader implements Loader<Withholding>{
    /*
     * this method take a withholding, and try to save it, if its already exist return db value;
     */
    public Withholding load(Withholding withholding){
        if (!WithholdingDAO.getInstance().save(withholding)) {
            Optional<Withholding> withholdingOptional = WithholdingDAO.getInstance().getAll().stream()
                    .filter(p -> p.getID().equals(withholding.getID()))
                    .findFirst();
            if (withholdingOptional.isPresent()) {
                return withholdingOptional.get();
            } else {
                throw new IllegalStateException("The withholding could not be saved but also not obtained");
            }
        }
        return withholding;
    }
}
