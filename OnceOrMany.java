 /**
  * Comparison of 2 strings.
  *
  * @author Anusha Balusu, Pankhuri Roy
  */

class OnceOrMany {

   public static boolean singelton(String literal, String aNewString) {
      // Checks the reference and value of 2 strings and returns true if both are equal
      return ( literal == aNewString );
   }
   public static void main( String args[] ){
      String aString = "xyz";
      // Java evaluates expression from left to right.

      // Starting from left, "1.  xyz == aString: " and "xyz" are 2 strings. 
      // So string concatenation happens. Now the new string 
      // "1.  xyz == aString: xyz" is comared to aString. So output is false.
      System.out.println("1.  xyz == aString: " +     "xyz" == aString   );
      // As precedence of () is higher than +, it evaluates "xyz" == aString
      // and then concatenates the result (true) to the string "2.  xyz == aString: "
      System.out.println("2.  xyz == aString: " +   ( "xyz" == aString ) );
      // a new object is created with value as "xyz" literal      
      String newString = new String("xyz");
      //The reference of newString is different from reference of literal "xyz". Hence false
      System.out.println("xyz == new String(xyz)\n    " + ("xyz" == newString) );
      // Same literals. Hence true
      System.out.println("1: " + singelton("xyz", "xyz"));
      // Same value but 2nd argument is a new object which implies different reference. Hence false.
      System.out.println("2: " + singelton("xyz", new String("xyz") ));
      // "xy" concatenates to "z" and forms "xyz". Same literals. Hence true
      System.out.println("3: " + singelton("xyz", "xy" + "z"));
      // "x" concatenates to "y" and then to "z" to form "xyz". Same literals. Hence true.
      System.out.println("4: " + singelton("x" + "y" + "z", "xyz"));
      // First "x" concatenates to "y" and forms "xy". Now "xy" concatenates to new String object "z" 
      // and forms "xyz". This new object has different reference than the literal "xyz". Hence false
      System.out.println("5: " + singelton("x" + "y" + new String("z"), "xyz"));
      // First "y" concatenates to "z" and forms "yz" as brackets have higher precedence. Then "x" 
      // is concatenated to "yz" to form "xyz". Same literals finally. Hence true.
      System.out.println("6: " + singelton("x" + ( "y" + "z"), "xyz"));
      // First "y" concatenates to "z" and forms "yz" as brackets have higher precedence. Then character
      // 'x' concatenates to "yz" and forms "xyz". Same literals finally. Hence true. 
      System.out.println("7: " + singelton('x' + ( "y" + "z"), "xyz"));
   }
}
