package assignment2;
import java.util.Scanner;
/*
 @author mmaaz
 */
class Node {
    int row;
    int column;
    int cost;
    char c1;
    char c2;
    int prevRow=0;
    int prevCol=0;
    String pos;
    Node next;
    public String toString() {
        String s = "Row, Column: (" + this.row + ", " + this.column + ")\nCost: " + this.cost + "\nCharacters: (" + this.c1 + ", " + this.c2 + ") \nPrevious: (" + this.prevRow + ", "+ this.prevCol+ ")\n\n";
        return s;
    }
}
class Path {
    Node head;
    Node tail;
    int pathCost;
    public void insert(Node n) {
        Node new_node = n;
        if (this.head == null) {
            this.head = new_node;
            this.tail = n;
        } else {
            Node cur = this.head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = new_node;
            tail = new_node;
        }
    }
    public String toString() {
        String s = "";
        Node n = this.head;
        while (n.next != null) {
            s += n.toString();
            n = n.next;
       }return s;
 }}
public class EditDistance {
    Node table[][];
    String a, b;
    public EditDistance(String a, String b) {
        this.a = a;
        this.b = b;
        int m = a.length() + 1;
        int n = b.length() + 1;
        table = new Node[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = new Node();
                table[i][j].row = i;
                table[i][j].column = j;
                table[i][j].cost = 0;
    }}}
    public static int match(String a, String b) {
        if (a.length() == 0 || b.length() == 0 || a == null || b == null) {
            return 0;
        }
        EditDistance ed = new EditDistance(a, b);
        Path optimal = new Path();
        char[] str1 = a.toCharArray();
        char[] str2 = b.toCharArray();
        for (int i = 1; i < ed.table.length; i++) {
            for (int j = 1; j < ed.table[i].length; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    ed.table[i][j].cost = ed.table[i - 1][j - 1].cost;
                    ed.table[i][j].prevRow = i - 1;
                    ed.table[i][j].prevCol = j - 1;
                }
                else if (i == 1) {
                    ed.table[i][j].cost = j;
                    ed.table[i][j].prevRow = i;
                    ed.table[i][j].prevCol = j - 1;
                } else if (j == 1) {
                    ed.table[i][j].cost = i;
                    ed.table[i][j].prevRow = i - 1;
                    ed.table[i][j].prevCol = j;
                }
                else{
                    ed.table[i][j].cost = Math.min(Math.min(2 + ed.table[i - 1][j].cost, 2 + ed.table[i][j - 1].cost), 1 + ed.table[i - 1][j - 1].cost);
                    int minval= ed.table[i][j].cost;
                    String [] minLoc= minLoc(i,j, minval, ed.table).split(" ");
                    //System.out.println(minLoc[0] + minLoc[1]);
                    ed.table[i][j].prevRow= Integer.parseInt(minLoc[0]);
                    ed.table[i][j].prevCol= Integer.parseInt(minLoc[1]);
                }
                ed.table[i][j].c1 = str1[i - 1];
                ed.table[i][j].c2 = str2[j - 1];
                ed.table[i][j].row = i;
                ed.table[i][j].column = j;
            }
        }
        Node path = ed.table[ed.table.length - 1][ed.table[0].length - 1];
        int i = ed.table.length - 1;
        int j = ed.table[0].length - 1;
        optimal.insert(path);
        while (i > 0 || j > 0) {
            optimal.insert(ed.table[ed.table[i][j].prevRow][ed.table[i][j].prevCol]);
            i=ed.table[i][j].prevRow;
            j=ed.table[i][j].prevCol;
        }
        //System.out.println(optimal);
        for (int k = 0; k < ed.table.length; k++) {
            for (int l = 0; l < ed.table[k].length; l++) {
                System.out.print(ed.table[k][l].cost + "\t");
            }
            System.out.println();
        }
        return ed.table[ed.table.length - 1][ed.table[0].length - 1].cost;
    }
    public static String minLoc(int i, int j, int minVal, Node [][] table){
        int mini;
        int minj;
        String loc="";
        if(minVal-2==table[i-1][j].cost){
            mini=i-1;
            minj=j;
            loc=minj + " " + minj;   
            return loc;
        }
        else if(minVal-2==table[i][j-1].cost){
            mini=i;
            minj=j-1;
            loc=minj + " " + minj; 
            return loc;
        }
        else if(minVal-1==table[i-1][j-1].cost){
            mini=i-1;
            minj=j-1;
            loc=minj + " " + minj; 
            return loc;    
    }
        return loc;
    }
    public static void main(String[] args) {
        char[] c = "Snowy".toCharArray();
        char[] d = "Sunny".toCharArray();
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.println(EditDistance.match(a, b));

    }
}
