import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Individual {
    Map<IncomeNames, Double> incomes;
    int countOfChild;

    public Individual(String filename) {
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(filename));
            incomes = new TreeMap<IncomeNames, Double>();
            incomes.put(IncomeNames.INCOME_FROM_MAIN_JOB, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.INCOME_FROM_ADDITIONAL_JOB, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.ROYALTY_INCOME, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.INCOME_FROM_SALE_OF_PROPERTY, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.INCOME_FROM_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.INCOME_FROM_TRANSFERS_FROM_ABROAD, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.CHILD_BENEFITS, Double.parseDouble(bReader.readLine().split(":")[1]));
            incomes.put(IncomeNames.MATERIAL_HELP, Double.parseDouble(bReader.readLine().split(":")[1]));
            this.countOfChild = Integer.parseInt(bReader.readLine().split(":")[1]);
            bReader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Individual{" +
                "incomes=" + incomes +
                ", countOfChild=" + countOfChild +
                '}';
    }

    enum IncomeNames{
        INCOME_FROM_MAIN_JOB,
        INCOME_FROM_ADDITIONAL_JOB,
        ROYALTY_INCOME,
        INCOME_FROM_SALE_OF_PROPERTY,
        INCOME_FROM_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT,
        INCOME_FROM_TRANSFERS_FROM_ABROAD,
        CHILD_BENEFITS,
        MATERIAL_HELP
    }
}
