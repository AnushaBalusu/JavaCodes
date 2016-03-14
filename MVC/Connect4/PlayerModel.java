/**
 * 
 * This class implements the Player model. 
 *
 * @version    $Id: PlayerModel.java, v 1 2015/05/10 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

public class PlayerModel implements PlayerModelInterface{
   private String name;
   private char gamePiece;
   private Connect4FieldModel theField;

   public PlayerModel() {
   }

   /**
    * Constructs a player.
    *
    * @param      theField
    * @param      name     Player's name
    * @param      gamePiece   + and *
    *
    */

   public PlayerModel(Connect4FieldModel theField, String name, char gamePiece) {
      this.theField = theField;
      this.name = name;
      this.gamePiece = gamePiece;
   }

   public char getGamePiece() {
      return gamePiece;
   }

   public String getName() {
      return name;
   }

   public Connect4FieldModel getTheField() {
      return theField;
   }
}
