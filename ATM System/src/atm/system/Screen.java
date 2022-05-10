
package atm.system;


public class Screen {
      public void displayMessage(String message){
        System.out.println(message);
    }
    
     public void displayMessageLine(String message){
        System.out.println(message);
    }
     
     public void displayDollarAmount(double amount){
         System.out.println( amount +" Tk");//$%,.2f
     }
}
