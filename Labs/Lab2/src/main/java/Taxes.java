import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Taxes {
    Map<TaxNames, Double> taxes;

    public Taxes(String filename) {
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(filename));
            taxes = new TreeMap<TaxNames, Double>();
            taxes.put(TaxNames.TAX_FOR_MAIN_JOB, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_ADDITIONAL_JOB, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_ROYALTY_INCOME, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_SALE_OF_PROPERTY, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_TRANSFERS_FROM_ABROAD, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_CHILD_BENEFITS, Double.parseDouble(bReader.readLine().split(":")[1]));
            taxes.put(TaxNames.TAX_FOR_MATERIAL_HELP, Double.parseDouble(bReader.readLine().split(":")[1]));
            bReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    enum TaxNames {
        TAX_FOR_MAIN_JOB(Individual.IncomeNames.INCOME_FROM_MAIN_JOB),
        TAX_FOR_ADDITIONAL_JOB(Individual.IncomeNames.INCOME_FROM_ADDITIONAL_JOB),
        TAX_FOR_ROYALTY_INCOME(Individual.IncomeNames.ROYALTY_INCOME),
        TAX_FOR_SALE_OF_PROPERTY(Individual.IncomeNames.INCOME_FROM_SALE_OF_PROPERTY),
        TAX_FOR_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT(Individual.IncomeNames.INCOME_FROM_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT),
        TAX_FOR_TRANSFERS_FROM_ABROAD(Individual.IncomeNames.INCOME_FROM_TRANSFERS_FROM_ABROAD),
        TAX_FOR_CHILD_BENEFITS(Individual.IncomeNames.CHILD_BENEFITS),
        TAX_FOR_MATERIAL_HELP(Individual.IncomeNames.MATERIAL_HELP);

        private final Individual.IncomeNames incomeName;

        TaxNames(Individual.IncomeNames incomeName) {
            this.incomeName = incomeName;
        }

        public Individual.IncomeNames getIncomeName() {
            return incomeName;
        }
    }

}
