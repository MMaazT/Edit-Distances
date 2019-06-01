/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author mmaaz
 */
class Node1 {
    int row;
    int column;
    int cost;
    Node1 next;
    public String toString() {
        String s = "Row, Column: (" + this.row + ", " + this.column + ")\nCost: " + this.cost+"\n";
        return s;
    }
}
class Path2 {
    Node1 head;
    public void insert(Node1 n) {
        Node1 new_node = n;
        if (this.head == null) {
            this.head = new_node;
        } else {
            Node1 cur = this.head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = new_node;
        }
    }
    public String toString() {
        String s = "";
        Node1 n = this.head;
        while (n != null) {
            s += n.toString();
            n = n.next;
       }return s;
 }
}
public class EditDistance2 {
    String a,b;
    int [][]cost; 
    String operations;
    HashMap<String, String> prevPointers;
    public EditDistance2(String a, String b){
        this.a=a;
        this.b=b;
        cost= new int[a.length()+1][b.length() +1];
        operations="";
      }
        
    public static Path2 match( String a, String b)  {
        EditDistance2 ed= new EditDistance2(a,b);
        for (int i = 0; i< ed.cost.length; i++) {
            for (int j = 0; j < ed.cost[0].length; j++) {
                if(i==0){
                    ed.cost[i][j]=j;
                }
                else if (j==0){
                    ed.cost[i][j]=i;
                }
                else if (a.charAt(i-1)==b.charAt(j-1)){
                    ed.cost[i][j]=ed.cost[i-1][j-1];
                }
                else{
                    int [] ind= {i,j};
                    int [] prevInd=  ed.minInd(ed.cost, ind);
                    int indi =prevInd[0];
                    int indj=prevInd[1];
                    if(indi==i-1 && indj==j-1){
                        ed.cost[i][j]= 1+ed.cost[indi][indj];
                    }
                    else if(indi==i-1 && indj==j){
                        ed.cost[i][j]= 2+ed.cost[indi][indj];
                    }
                    else {
                        ed.cost[i][j]= 2+ed.cost[indi][indj];
                    }
                }
            }
        } 
        //back tracking
        Path2 optimal= new Path2();
        int i= ed.cost.length-1;
        int j= ed.cost[0].length-1;
        while(i!=0 || j!=0 ){
            Node1 n= new Node1();
            n.row=i;
            n.column=j;
            n.cost=ed.cost[i][j];
            optimal.insert(n);
            int [] ind= {i,j};
            int [] prevMin= ed.backtrackInd(ed.cost, ind);
            int indi =prevMin[0];
            int indj= prevMin[1];
            if(indi==0 && indj!=0) j=indj;
            else if(indj==0 && indi!=0) i=indi;
            else{
                i=indi;
                j=indj; 
            }
        }
        Node1 n= new Node1();
        n.row=i;
        n.column=j;
        n.cost=ed.cost[i][j];
        optimal.insert(n);
        //System.out.println(ed.cost[ed.cost.length-1][ed.cost[0].length-1]);        
        int ai=0;
        int bj=0;
        String aux= ed.operations;
        String pairwise= "EditDistance= " + ed.cost[ed.cost.length-1][ed.cost[0].length-1]+ "\n";
        while (ai<aux.length() && bj < b.length()){            
            if(aux.charAt(ai)=='N'){
                pairwise+= a.charAt(ai)+ " "+ b.charAt(bj)+ " " +"0\n";
                ai++;
                bj++;
            }
            else if (aux.charAt(ai)=='S'){
                pairwise+= a.charAt(ai)+ " "+ b.charAt(bj)+ " " +"1\n";
                ai++;
                bj++;
            }
            else if(aux.charAt(ai)=='I'){
                pairwise+= a.charAt(ai)+ " "+ "-"+ " " +"2\n";
                ai++;
            }
            else{
                pairwise+= "-"+ " "+ b.charAt(bj)+ " " +"2\n";
                bj++;
            }
        }
        System.out.print(pairwise);
        System.out.println("Do you want to display the cost matrix as well? Press 1 for a 'Yes', 0 for a 'No'.");
        Scanner in = new Scanner(System.in);
        int d=in.nextInt();
        if(d==1){
            for (int m = 0; m < ed.cost.length; m++) {
                for (int o = 0; o < ed.cost[0].length; o++) {
                    System.out.print(ed.cost[m][o]+ "\t");
                }
                System.out.println("");
            }
        }
        return optimal; 
    }
    private int [] minInd(int cost[][], int [] ind){
        int i= ind[0];
        int j= ind[1];
        if(cost[i-1][j]+2<= cost[i-1][j-1]+1 && cost[i-1][j]+2<= cost[i][j-1]+2){
                ind[0]=i-1;
            }
        else if(cost[i-1][j-1]+1<= cost[i-1][j]+2 &&cost[i-1][j-1]+1<= cost[i][j-1]+2){
                ind[0]=i-1;
                ind[1]=j-1;
            }
        else{
                ind[1]=j-1;
            }
        return ind;
    }
    private int [] backtrackInd(int costs[][], int ind []){
        int i= ind[0];
        int j=ind[1];
        if(costs[i-1][j]+2== costs[i][j]){
            ind[0]=i-1;
            this.operations= "I"+ this.operations;
        }
        else if(costs[i][j-1]+2==costs[i][j]){
             ind[1]=j-1;
             this.operations="D"+ this.operations;
        }
        else if(costs[i-1][j-1]+1==costs[i][j]){
            ind[0]=i-1;
            ind[1]=j-1;
            this.operations= "S"+ this.operations;
        }
        else{
            ind[0]=i-1;
            ind[1]=j-1;
            this.operations= "N" +this.operations;
        }
        return ind;
    }
    
    
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner (System.in);
        String a="";
        String b= "";
        System.out.print("Press 1 to read the strings from a File. Press 2 to read them from the console.\n");
        int d= in.nextInt();
        if(d==1){
            System.out.println("Enter the file name after setting the directory in the current program (currently set for my pc): ");
            Scanner read= new Scanner(new File("D:\\Programming\\DAA Online\\Assignments\\Assignment2\\sequence\\" + in.next()));
            read.useDelimiter("\n");
            a= read.next();
            b= read.next();
            if(b.length()>a.length()){
                String temp=a;
                a=b;
                b=temp;
            }
            System.out.println("String 1: "+ a);
            System.out.println("String 2: "+b);
        }
        
        else if(d==2){
            System.out.print("Enter the strings below:\n");
            a=in.next();
            b=in.next();
            if(b.length()>a.length()){
                String temp=a;
                a=b;
                b=temp;
            }
        }
        else{
            System.out.println("Enter either 1 or 2 only. The Program will now exit.");
            System.exit(0);
        }
        System.out.print("Do you want to display the optimal path too? Press '1' for a Yes, '0' for a No.\n");
        d=in.nextInt();
        if(d==1){
            Path2 opt= EditDistance2.match(a,b);
            System.out.print("Optimal Path:\n");
            System.out.print(opt);
        }
        else if(d==0){
            EditDistance2.match(a,b);   
        }
        else{
            System.out.println("Invalid value entered. The program will now terminate!");
            System.exit(0);
        }
    }
}
