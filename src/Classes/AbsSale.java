
package Classes;

import javax.swing.JTextField;


public abstract class AbsSale {
    
    
    public AbsSale()
    {
        
    }
    
    public abstract void OneDaySale(JTextField sale,JTextField tax, String date);
    public abstract void FetchSaleByMonth(JTextField sale, JTextField tax, String datestart, String dateend);
    public abstract void FetchMonthlyProfit(JTextField profit, String datestart, String dateend);
    public abstract void OneDayProfit(String date, JTextField Profit);

}
