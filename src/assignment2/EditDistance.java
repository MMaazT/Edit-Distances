/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.Scanner;

/**
 *
 * @author mmaaz
 */
class Node{
    int row;
    int column;
    int cost;
    Node next;
}

class Path{
    // Method to insert a new node 
    Node head;
    int pathCost;
    
    public void insert(Node n) 
    { 
        // Create a new node with given data 
        Node new_node = new Node(); 
        new_node.next = null; 
        // If the Linked List is empty, 
        // then make the new node as head 
        if (this.head == null) { 
            this.head = new_node;
            pathCost=0;
        } 
        else { 
            Node cur = this.head; 
            while (cur.next != null) { 
                cur = cur.next; 
            } 
            cur.next = new_node;
        } 
    } 
}
public class EditDistance {
    Node table[][];
    String a, b;
   
    public EditDistance(String a,String b){
       this.a=a;
       this.b=b;
       int m= a.length()+1;
       int n= b.length()+1;
       table=new Node[m][n];
       for(int i=0; i<m; i++){
           for (int j = 0; j < n; j++) {
               table[i][j]= new Node();
               table[i][j].row=i;
               table[i][j].column=j;
               table[i][j].cost=0;
           }
       }
    }
    
    /*public int ed(char [] a, char [] b, int m, int n){
        if (m==0) return n;
        if (n==0) return m;
        else if(a[m]== b[n]){
            return ed(a, b, m-1, n-1);
        }
        else{
            return  min(2+ ed(a, b, m-1, n-1),1+ ed(a, b, m-1, n), 1+ ed(a,b, m, n-1));   
        }
    }*/
    public int min(int a, int b, int c){
        return Math.min(a, Math.min(b,c));
    }
    public static int match(String a , String b){
        //calculate the cost, store it in path.path cost, and add it to the cost
        if(a.length()==0 || b.length()==0 || a==null || b==null){
        return 0;
    }
        EditDistance ed= new EditDistance(a,b);
        Path optimal = new Path();
        char[] str1= a.toCharArray();
        char[] str2= b.toCharArray();
        
        for (int i = 1; i < ed.table.length ; i++) {
            for (int j = 1; j < ed.table[i].length; j++) {
                   ed.table[i][j].cost= Math.min(Math.min(2+ ed.table[i-1][j].cost, 2+ ed.table[i][j-1].cost), diff(str1[i-1],str2[j-1])+ ed.table[i][j-1].cost); 
            }
        }
        return ed.table[ed.table.length-1][ed.table[0].length-1].cost;
        
     }
    public static int diff(char c1, char c2){
        if(c1==c2) return 0;
        return 1;
    }
    
    public static void main(String[] args) {
        char [] c= "Snowy".toCharArray();
        char [] d= "Sunny".toCharArray();
 
        Scanner in = new Scanner(System.in);
        String a= in.next();
        String b=in.next();
        //char [] a = in.next().toCharArray();
        //char [] b = in.next().toCharArray();
        //EditDistance e= new EditDistance("Snowy", "Sunny");
        System.out.println(EditDistance.match(a, b));
        
    }
}