public class Main {
    public static void main(String[] args) {
        Individual i = new Individual("income.txt");
        //System.out.println(i);
        Taxes x = new Taxes("tax.txt");
        //System.out.println(x);
        //System.out.println();
        System.out.println(Bookkeeping.getTaxesMap(i, x));
        System.out.println("Profit: "+Bookkeeping.calculateProfit(i, x));
        System.out.println("Sum of taxes: "+Bookkeeping.calculatedTax(i, x));
        System.out.println("SortedTaxes "+Bookkeeping.sortTaxes(i, x));
    }
}
