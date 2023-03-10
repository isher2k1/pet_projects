import java.util.*;

public class Bookkeeping {

    public static Map<Taxes.TaxNames, Double> getTaxesMap(Individual ind, Taxes taxes){
        Map<Taxes.TaxNames, Double> calculatedTaxes = new TreeMap<Taxes.TaxNames, Double>();
        for (Map.Entry<Taxes.TaxNames, Double> entry : taxes.taxes.entrySet()) {
            double taxAmount = entry.getValue() * ind.incomes.get(entry.getKey().getIncomeName());
            calculatedTaxes.put(entry.getKey(), taxAmount);
        }
        return calculatedTaxes;
    }

    public static Map<Taxes.TaxNames, Double> sortTaxes(Individual ind, Taxes taxes){
        Map<Taxes.TaxNames, Double> taxesMap = Bookkeeping.getTaxesMap(ind, taxes);
        List<Map.Entry<Taxes.TaxNames, Double>> list = new ArrayList<>(taxesMap.entrySet());

        list.sort(Map.Entry.comparingByValue());

        Map<Taxes.TaxNames, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Taxes.TaxNames, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static double calculateProfit(Individual ind, Taxes taxes){
        double profit = 0;
        for (Map.Entry<Individual.IncomeNames, Double> entry : ind.incomes.entrySet()) {
            profit += entry.getValue();
        }

        profit += ind.incomes.get(Individual.IncomeNames.CHILD_BENEFITS) * (ind.countOfChild-1);

        for (Map.Entry<Taxes.TaxNames, Double> entry : getTaxesMap(ind,taxes).entrySet()) {
            profit -= entry.getValue();
        }
        return profit;
    }

    public static Double calculatedTax(Individual ind, Taxes taxes){
        double calc = 0;
        for (Map.Entry<Taxes.TaxNames, Double> entry : taxes.taxes.entrySet()) {
            double taxAmount = entry.getValue() * ind.incomes.get(entry.getKey().getIncomeName());
            calc += taxAmount;
        }
        return calc;
    }
}
