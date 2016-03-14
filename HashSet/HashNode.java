/**
 * This is a hash node class which has data and reference to next node
 * in the list list at particular bucket in the hash table
 *
 * @version    $Id: HashNode.java, v 1 2015/07/12 $
 *
 * @author     Anusha Balusu, Pankhuri Roy
 **/

class HashNode<E> {
   private E element;
   private HashNode<E> next;

   // default constructor  
   public HashNode() {
      this.element = null;
      this.next = null;
   }
 
   // constructor with 1 arg
   public HashNode(E element) {
      this.element = element;
      this.next = null;
   }

   // constructor with 2 arg
   public HashNode(E element, HashNode<E> next) {
      this.element = element;
      this.next = next;
   }

   public E getElement() {
      return element;
   }

   public void setNextLink(HashNode<E> node) {
      this.next = node;
   }

   public HashNode<E> getNextLink() {
      return next;
   }
} 
