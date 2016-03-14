import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;

public class PlayerView implements PlayerViewInterface, Observer{

   public void update(Observable o, Object obj) {
      Connect4FieldModel field = ((Connect4FieldModel) o);
      if(field.getChanged().equals("column")) {
         printPlayerPrompt("Computer", field.getComputerMove()); 
      }
   }

   public int nextMove(PlayerModelInterface p) {
      int column = -1;
      try {  
         printPlayerPrompt(p.getName()); 
         Scanner playerInput = new Scanner(System.in);
         column = playerInput.nextInt();
      }catch(Exception e) {
         System.out.println("Input not an integer");
         column = nextMove(p);      
      }                          
      return column;              
   }

   public void printPlayerPrompt(String name) {
      System.out.print(name + "'s move : ");
   }

   public void printPlayerPrompt(String name,int column) {
      System.out.println(name + "'s move : " + column);
   }


}
