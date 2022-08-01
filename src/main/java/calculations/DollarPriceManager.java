package calculations;

import database.DollarPriceDAO;
import models.DollarPrice;
import models.Withholding;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DollarPriceManager {

    private static final int daysRoundLimit = 4;

    public static DollarPrice getDollarPrice(Withholding t) {
        Date ticketDate = (Date)t.getValues().get("date");
        DollarPrice price;

        Optional<DollarPrice> priceOptional = DollarPriceDAO.getInstance()
                .getAll().stream().filter(d -> d.getValues().get("date").equals(ticketDate)).findFirst();
        //use obtained price, otherwise the closest price
        price = priceOptional.orElseGet(() -> getApproximatePrice(ticketDate));

        return price;
    }

    public static int getDaysRoundLimit() {
        return daysRoundLimit;
    }

    private static DollarPrice getApproximatePrice(Date ticketDate) {
        List<DollarPrice> higherPrice = DollarPriceDAO.getInstance().getAll().stream()
                .filter(d -> ((Date)d.getValues().get("date")).compareTo(ticketDate) > 0).collect(Collectors.toList());
        List<DollarPrice> lowerPrice = DollarPriceDAO.getInstance().getAll().stream()
                .filter(d -> ((Date)d.getValues().get("date")).compareTo(ticketDate) < 0).collect(Collectors.toList());
        Optional<DollarPrice> minHigherPrice = higherPrice.stream().min(Comparator.comparing(d -> (Date)d.getValues().get("date")));
        Optional<DollarPrice> maxLowerPrice = lowerPrice.stream().max(Comparator.comparing(d -> (Date)d.getValues().get("date")));

        if (minHigherPrice.isPresent() && maxLowerPrice.isPresent()) {
            return getNearestDatePrice(minHigherPrice.get(), maxLowerPrice.get(), ticketDate);
        } else if (!minHigherPrice.isPresent() && maxLowerPrice.isPresent()) {
            return maxLowerPrice.get();
        } else if (minHigherPrice.isPresent()) {
            return minHigherPrice.get();
        } else {
            throw new IllegalStateException("No price loaded");
        }
    }

    private static DollarPrice getNearestDatePrice(DollarPrice priceBefore, DollarPrice priceAfter, Date currentDate) {
        Date dateBefore = (Date)priceBefore.getValues().get("date");
        Date dateAfter = (Date)priceAfter.getValues().get("date");
        //getting time of each date
        long currentTime = currentDate.getTime();
        long afterTime = dateAfter.getTime();
        long beforeTime = dateBefore.getTime();
        //getting difference between both dates
        long afterAbs = Math.abs(afterTime - currentTime);
        long beforeAbs = Math.abs(currentTime - beforeTime);
        //return the DollarPrice of the nearest date
        return afterAbs <= beforeAbs ? priceAfter : priceBefore;
    }

    /**
     * check the days distance between missed dollars prices
     */
    public static int getDaysDifference(Date ticketDate, DollarPrice price) {
        long currentTime = ticketDate.getTime();
        long nearestTime = ((Date) price.getValues().get("date")).getTime();
        return (int) (Math.abs(nearestTime - currentTime) / 86400000);
    }

}
