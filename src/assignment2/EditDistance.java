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
    char c1;
    char c2;
    Node next;
    
    public String toString(){
        String s= "Row, Column: (" + this.row + ", "+ this.column+")\nCost: " + this.cost +"\nCharacters: (" + this.c1+ ", "+ this.c2+")\n\n";
        return s;
    }
}

class Path{
    // Method to insert a new node 
    Node head;
    Node tail;
    int pathCost;
    
    public void insert(Node n) {
        Node new_node = n;
        if (this.head == null) { 
            this.head = new_node;
            this.tail=n;
        } 
        else { 
            Node cur = this.head; 
            while (cur.next != null) { 
                cur = cur.next; 
            } 
            cur.next = new_node;
            tail=new_node;
        } 
    }
    public String toString(){
      String s="";
      Node n=this.head;
      while(n.next!=null){
          s+=n.toString();
          n=n.next;
      }
      return s;
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
    public static int match(String a , String b){
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
                   ed.table[i][j].c1=str1[i-1];
                   ed.table[i][j].c2=str2[j-1];
                   ed.table[i][j].row= i;
                   ed.table[i][j].column= j;
                   //optimal.insert(ed.table[i][j]);
                   //System.out.print(optimal.tail);
            }
        }
        Node path= ed.table[ed.table.length-1][ed.table[0].length-1];
        optimal.insert(path);
        int i= ed.table.length-1;
        int j= ed.table[0].length-1;
        while(i>=1 && j>=1){
                String ind = minNode(ed.table[i][j-1], ed.table[i-1][j-1], ed.table[i-1][j]);
                String [] let= ind.split(" ");
                int r= Integer.parseInt(let[0]);
                int c= Integer.parseInt(let[1]);
                //System.out.println(r);
                //System.out.println(c);
                optimal.insert(ed.table[r][c]);
                i=r;
                j=c;
            }
        System.out.println(optimal);
        return ed.table[ed.table.length-1][ed.table[0].length-1].cost;
     }
    public static String minNode(Node a, Node b, Node c){
        int row=0;
        int col=0;
        int m= Math.min(a.cost, Math.min(b.cost, c.cost));
        if(m==a.cost){
            row=a.row;
            col=a.column;
        }
        else if(m==b.cost){
            row=b.row;
            col=b.column;
        }
        if(a.cost<= b.cost && b.cost<=c.cost){
            row=a.row;
            col=a.column;
        }
        else if(b.cost<= a.cost && b.cost<=c.cost){
            row=b.row;
            col=b.column;
        }
        else if(c.cost<= b.cost && c.cost<=a.cost){
            row=c.row;
            col=c.column;
        }
        return row + " " + col;
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
        System.out.println(EditDistance.match(a, b));
        
    }
}