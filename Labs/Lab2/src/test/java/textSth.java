import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class textSth {


//        Individual i = new Individual("income.txt");
//        System.out.println(i);
//        Taxes x = new Taxes("tax.txt");
//        System.out.println(x);
//        System.out.println();
//        System.out.println(Bookkeeping.getTaxesMap(i, x));
//        System.out.println("Profit: "+Bookkeeping.calculateProfit(i, x));
//        System.out.println("Sum of taxes: "+Bookkeeping.calculatedTax(i, x));




        private Individual i = new Individual("income.txt");
        private Taxes x = new Taxes("tax.txt");


            @Test
            public void checkTaxesMap() {
                Map<Taxes.TaxNames, Double> taxesMap = Bookkeeping.getTaxesMap(i, x);
                Assert.assertEquals(3600, taxesMap.get(Taxes.TaxNames.TAX_FOR_MAIN_JOB), 0.0);
                Assert.assertEquals(1800, taxesMap.get(Taxes.TaxNames.TAX_FOR_ADDITIONAL_JOB), 0.0);
                Assert.assertEquals(80, taxesMap.get(Taxes.TaxNames.TAX_FOR_ROYALTY_INCOME), 0.0);
                Assert.assertEquals(100, taxesMap.get(Taxes.TaxNames.TAX_FOR_SALE_OF_PROPERTY), 0.0);
                Assert.assertEquals(45, taxesMap.get(Taxes.TaxNames.TAX_FOR_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT), 0.0);
                Assert.assertEquals(250, taxesMap.get(Taxes.TaxNames.TAX_FOR_TRANSFERS_FROM_ABROAD), 0.0);
                Assert.assertEquals(0, taxesMap.get(Taxes.TaxNames.TAX_FOR_CHILD_BENEFITS), 0.0);
                Assert.assertEquals(0, taxesMap.get(Taxes.TaxNames.TAX_FOR_MATERIAL_HELP), 0.0);
            }

            @Test
            public void checkInd() {
                Assert.assertEquals(20000, i.incomes.get(Individual.IncomeNames.INCOME_FROM_MAIN_JOB), 0.0);
                Assert.assertEquals(10000, i.incomes.get(Individual.IncomeNames.INCOME_FROM_ADDITIONAL_JOB), 0.0);
                Assert.assertEquals(400, i.incomes.get(Individual.IncomeNames.ROYALTY_INCOME), 0.0);
                Assert.assertEquals(2000, i.incomes.get(Individual.IncomeNames.INCOME_FROM_SALE_OF_PROPERTY), 0.0);
                Assert.assertEquals(900, i.incomes.get(Individual.IncomeNames.INCOME_FROM_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT), 0.0);
                Assert.assertEquals(5000, i.incomes.get(Individual.IncomeNames.INCOME_FROM_TRANSFERS_FROM_ABROAD), 0.0);
                Assert.assertEquals(2490, i.incomes.get(Individual.IncomeNames.CHILD_BENEFITS), 0.0);
                Assert.assertEquals(1180, i.incomes.get(Individual.IncomeNames.MATERIAL_HELP), 0.0);
                Assert.assertEquals(2, i.countOfChild, 0.0);
            }

            @Test
            public void checkTaxes() {
                Assert.assertEquals(0.18, x.taxes.get(Taxes.TaxNames.TAX_FOR_MAIN_JOB), 0.0);
                Assert.assertEquals(0.18, x.taxes.get(Taxes.TaxNames.TAX_FOR_ADDITIONAL_JOB), 0.0);
                Assert.assertEquals(0.2, x.taxes.get(Taxes.TaxNames.TAX_FOR_ROYALTY_INCOME), 0.0);
                Assert.assertEquals(0.05, x.taxes.get(Taxes.TaxNames.TAX_FOR_SALE_OF_PROPERTY), 0.0);
                Assert.assertEquals(0.05, x.taxes.get(Taxes.TaxNames.TAX_FOR_RECEIVING_MONEY_AND_PROPERTY_AS_GIFT), 0.0);
                Assert.assertEquals(0.05, x.taxes.get(Taxes.TaxNames.TAX_FOR_TRANSFERS_FROM_ABROAD), 0.0);
                Assert.assertEquals(0, x.taxes.get(Taxes.TaxNames.TAX_FOR_CHILD_BENEFITS), 0.0);
                Assert.assertEquals(0, x.taxes.get(Taxes.TaxNames.TAX_FOR_MATERIAL_HELP), 0.0);
            }

            @Test
            public void checkProfit() {
                double profit = Bookkeeping.calculateProfit(i, x);
                Assert.assertEquals(38585, profit, 0.0);
            }

            @Test
            public void checkTaxesAmount() {
                double taxes = Bookkeeping.calculatedTax(i, x);
                Assert.assertEquals(5875, taxes, 0.0);
            }


}
