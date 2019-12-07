

   public class HashTable {

      static private class ListNode {

         Object key;
         Object value;
         ListNode next; 
      }

      private ListNode[] table; 

      private int count; 

      public HashTable() {

         table = new ListNode[64];
      }

      public HashTable(int initialSize) {
         table = new ListNode[initialSize];
      }

      void dump() {

         System.out.println();
         for (int i = 0; i < table.length; i++) {

            System.out.print(i + ":");
            ListNode list = table[i]; // For traversing linked list number i.
            while (list != null) {
               System.out.print("  (" + list.key + "," + list.value + ")");
               list = list.next;
            }
            System.out.println();
         }
      } // end dump()

      public void put(Object key, Object value) {

         int bucket = hash(key); // Which location should this key be in?
         ListNode list = table[bucket]; // For traversing the linked list
         while (list != null) {

            if (list.key.equals(key))
               break;
            list = list.next;
         }

         if (list != null) {

         }
         else {

            if (count >= 0.75*table.length) {

               resize();
            }
            ListNode newNode = new ListNode();
            newNode.key = key;
            newNode.value = value;
            newNode.next = table[bucket];
            table[bucket] = newNode;
            count++;  // Count the newly added key.
         }
      }

      public Object get(Object key) {

         int bucket = hash(key);  // At what location should the key be?
         ListNode list = table[bucket];  // For traversing the list.
         while (list != null) {
                // Check if the specified key is in the node that
                // list points to.  If so, return the associated value.
            if (list.key.equals(key))
               return list.value;
            list = list.next;  // Move on to next node in the list.
         }

         return null;  
      }

      public void remove(Object key) {  

         int bucket = hash(key);  // At what location should the key be?
         if (table[bucket] == null) {

            return; 
         }
         if (table[bucket].key.equals(key)) {

            table[bucket] = table[bucket].next;
            count--; // Remove new number of items in the table.
            return;
         }

         ListNode prev = table[bucket];  // The node that precedes
                                         // curr in the list.  Prev.next
                                         // is always equal to curr.
         ListNode curr = prev.next;  // For traversing the list,
                                     // starting from the second node.
         while (curr != null && ! curr.key.equals(key)) {
            curr = curr.next;
            prev = curr;
         }

         if (curr != null) {
            prev.next = curr.next;
            count--;  // Record new number of items in the table.
         }
      }

      public boolean containsKey(Object key) {

         int bucket = hash(key);  // In what location should key be?
         ListNode list = table[bucket];  // For traversing the list.
         while (list != null) {
               // If we find the key in this node, return true.
            if (list.key.equals(key))
               return true;
            list = list.next;
         }

         return false;
      }

      public int size() {
            // Return the number of key/value pairs in the table.
         return count;
      }

      private int hash(Object key) {

         return (Math.abs(key.hashCode())) % table.length;
      }

      private void resize() {
            // Double the size of the table, and redistribute the
            // key/value pairs to their proper locations in the
            // new table.
         ListNode[] newtable = new ListNode[table.length*2];
         for (int i = 0; i < table.length; i++) {
               // Move all the nodes in linked list number i 
               // into the new table.  No new ListNodes are 
               // created.  The existing ListNode for each
               // key/value pair is moved to the newtable.
               // This is done by changing the "next" pointer
               // in the node and by making a pointer in the 
               // new table point to the node.
            ListNode list = table[i]; // For traversing linked list number i.
            while (list != null) {
                  // Move the node pointed to by list to the new table.
               ListNode next = list.next;  // The is the next node in the list.
                                           // Remember it, before changing
                                           // the value of list!
               int hash = (Math.abs(list.key.hashCode())) % newtable.length;
                    // hash is the hash code of list.key that is 
                    // appropriate for the new table size.  The
                    // next two lines add the node pointed to by list
                    // onto the head of the linked list in the new table
                    // at position number hash.
               list.next = newtable[hash];
               newtable[hash] = list;
               list = next;  // Move on to the next node in the OLD table.
            }
         }
         table = newtable;  // Replace the table with the new table.
      } // end resize()

   } // end class HashTable