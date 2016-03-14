/**
 * This is implementation of hash set using arrays
 * If there is a collision, the objects are stored in link lists
 * If the load factor is greater than 0.75, rehashing is done
 *
 * @version    $Id: HashSetNew.java, v 1 2015/07/12 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

import java.util.*;

public class HashSetNew<E> extends HashSet<E> {
   final static int INITIAL_SIZE = 16;
   static int REHASH_SIZE = INITIAL_SIZE;
   private HashNode<E>[] table;
   private static final long serialVersionUID = -1113582265865921787L;
   private int size;
   private double loadFactor = 0.75;

   // test scenario
   public static void main(String [] args) {
      HashSetNew<Integer> aHashSetNew = new HashSetNew<Integer>();
      aHashSetNew.add(1);
      aHashSetNew.add(2);
      aHashSetNew.add(3);
      aHashSetNew.add(4);
      aHashSetNew.add(5);
      System.out.println(aHashSetNew.contains(2));
      //aHashSetNew.remove(2);
      for(int i=7;i<10;i++) 
         aHashSetNew.add(i);
      System.out.println("Size--------------: "+ aHashSetNew.size());
      for(int i=0;i<10;i++) { 
         System.out.println("Is "+ i + " present? " + aHashSetNew.contains(i));
      }
      System.out.println("Size--------------: "+ aHashSetNew.size());

      Iterator<Integer> it = aHashSetNew.iterator();
      System.out.println(it.hasNext());

      for(int i=0;i<10;i++)  
         System.out.println(it.next());
   }

   // constructor
   @SuppressWarnings({"unchecked","rawtypes"})
   public HashSetNew() {
      table = new HashNode[INITIAL_SIZE];
   }

   /** 
    * Gets the hashcode for the object within the table's length
    *
    * @param   obj   object for which hashcode is to be calculated
    *
    * @return        hash code of obj
    **/
   private int getHashCode(Object obj) {
      int hash = obj.hashCode() % table.length;
      if(hash < 0) hash += table.length;
      return hash;
   }

   /**
    * Add the object in the table if not present and increments size by 1
    * Returns false if object already present in table
    *
    * @param   obj   object to be added
    *
    * @return        returns true if element is added otherwise false
    **/ 
   public boolean add(E obj) {
      boolean isAdded = false;
      if(!contains(obj)) {
         int hash = getHashCode(obj);         
         HashNode<E> node = new HashNode<E>(obj, null);
         if(table[hash] == null)
            table[hash] = new HashNode<E>();
         if(table[hash].getNextLink() != null) {
            node.setNextLink(table[hash].getNextLink());
         }
         table[hash].setNextLink(node); 
         isAdded = true;
         //  System.out.println(obj+" Added at "+hash );
         size += 1;

         if( (size * 1.0) /table.length > loadFactor) {
            rehash(this);
         }
      }

      return isAdded;
   }

   /**
    * Clears the hash set by freeing the table and setting size to 0
    **/
   public void clear() {
      for(int index = 0; index < table.length; index++) {
         table[index] = null; 
      }
      size = 0;
   }

   /**
    * Checks if object is present in the table
    *
    * @param   obj      object to be checked
    *
    * @return           true if object is present, otherwise false
    **/
   public boolean contains(Object obj) {
      int hash = getHashCode(obj);
      HashNode<E> node = table[hash];
      boolean isPresent = false;
      while(node != null) {
         node = node.getNextLink();
         if(node!=null && node.getElement()!=null && node.getElement().equals(obj)) {
            isPresent = true;
            break;
         }

      }
      return isPresent;
   }

   // Returns true is hash set has no object else false
   public boolean isEmpty() {
      return ((size == 0) ? true : false);
   }

   // Iterates on the hash set
   public Iterator<E> iterator() {
      return new Iterator<E>(this);
   }

   /**
    * Removes the object from the hash set if present and decrements size
    * Returns false if object is not present
    *
    * @param      obj      object to be removed
    *
    * @return              true if object is (present) and removed false otherwise
    **/ 
   public boolean remove(Object obj) {
      boolean isRemoved = false;
      int hash = getHashCode(obj);
      HashNode<E> node = table[hash];
      while(node != null && node.getNextLink() != null) {
         if(node.getNextLink().getElement().equals(obj) ) {
            node.setNextLink( node.getNextLink().getNextLink() );
            isRemoved = true;
            size -= 1;
            break;
         }else {
            node = node.getNextLink();
         }
      }
      return isRemoved;
   }

   /**
    * Returns the number of objects present in the hashset
    **/ 
   public int size() {
      return size;
   }
 
   /**
    * Rehash the hash set, size is doubled
    *
    * @param   hs    hashset to be rehashed
    *
    * @return        void
    **/ 
   @SuppressWarnings({"unchecked","rawtypes"})
   public void rehash(HashSetNew<E> hs) {
      REHASH_SIZE = REHASH_SIZE * 2;
      HashSetNew<E> hsNew = new HashSetNew<E>();
      // copy the old table to new one and initialize the old one with new size
      hsNew.table = hs.table;
      hs.table = new HashNode[REHASH_SIZE];
      hsNew.size = hs.size;
      hs.size = 0;
      Iterator<E> it = hsNew.iterator();
      while(it.hasNext()) {
         E obj = it.next();
         hs.add(obj);
      }
      //System.out.println("Size after rehash "+REHASH_SIZE);
      hsNew.clear();
   }

   /* 
    * Iterator inner class
    * Iterates on the hashset
    **/
   public static class Iterator<E> implements java.util.Iterator<E>{

      private int index;
      private int iteratedCount;
      private HashSetNew<E> aHashSetNewObj;
      private HashNode<E> node;

      /** 
       * Constructor with hashset as parameter
       *
       * @param   obj   hashset to be iterated
       **/ 
      public Iterator(HashSetNew<E> obj) {
         this.aHashSetNewObj = obj;
         index = 0;
         iteratedCount = 0;
         int pos = 0;
         while(aHashSetNewObj.table[pos] == null) {
            pos++;
         }
         node = aHashSetNewObj.table[pos].getNextLink();
      }

      // True if next element is present in hashset
      public boolean hasNext() {
         return ( (iteratedCount < aHashSetNewObj.size()) ? true : false );
      }

      /**
       * Gets the next element in the hashset
       * If no more elements present, throws NoSuchElementException
       *
       * @return     next hash node in the hashset
       **/
      public E next() {
         E obj = null;
         boolean found = false;
         while(index < aHashSetNewObj.table.length) {
            while(node != null && node.getElement() != null) {
               obj = node.getElement();
               node = node.getNextLink();
               found = true;
               iteratedCount++;
               break;
            }
            if(found == true) {
               break;
            }
            index++;
            while(index < aHashSetNewObj.table.length && aHashSetNewObj.table[index] == null) {
               index++;
            }
            if(index < aHashSetNewObj.table.length) {
               node = aHashSetNewObj.table[index].getNextLink();
            }
         }
         if(index >= aHashSetNewObj.table.length) {
            throw new NoSuchElementException();
         }
         return obj;
      }

      public void remove() {
      }

   } // End Iterator inner class
}
