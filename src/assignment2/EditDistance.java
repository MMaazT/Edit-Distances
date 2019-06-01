package assignment2;
import java.io.File;
import java.util.Scanner;
/*
 @author mmaaz
 */
class Node {
    int row;
    int column;
    int cost;
    int pairWiseCost;
    char c1;
    char c2;
    int prevRow=0;
    int prevCol=0;
    String prevPos;
    boolean inPath=false;
    Node next;
    public String toString() {
        String s = "Row, Column: (" + this.row + ", " + this.column + ")\nCost: " + this.cost + "\nCharacters: (" + this.c1 + ", " + this.c2 + ") \nPrevious: (" + this.prevRow + ", "+ this.prevCol+ ")\n" + this.prevPos+"\n\n";
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
        for (int i = 0; i < ed.table.length; i++) {
            for (int j = 0; j < ed.table[i].length; j++) {
                if (i == 0) {
                    ed.table[i][j].cost = j;
                    ed.table[i][j].prevRow = -1;
                    ed.table[i][j].prevCol = j-1;
                    ed.table[i][j].prevPos= "L+2";
                } else if (j == 0) {
                    ed.table[i][j].cost = i;
                    ed.table[i][j].prevRow = i-1;
                    ed.table[i][j].prevCol = -1;
                    ed.table[i][j].prevPos= "U+2";
                }
                else if (str1[i - 1] == str2[j - 1]) {
                    ed.table[i][j].cost = ed.table[i - 1][j - 1].cost;
                    ed.table[i][j].prevRow = i - 1;
                    ed.table[i][j].prevCol = j - 1;
                    ed.table[i][j].prevPos= "D+0";
                }
                else{
                    ed.table[i][j].cost = Math.min(Math.min(2 + ed.table[i - 1][j].cost, 2 + ed.table[i][j - 1].cost), 1+ ed.table[i - 1][j - 1].cost);
                    String [] minLoc= minLoc(i,j, ed.table).split(" ");
                    ed.table[i][j].prevRow= Integer.parseInt(minLoc[0]);
                    ed.table[i][j].prevCol= Integer.parseInt(minLoc[1]);
                }
                ed.table[i][j].row = i;
                ed.table[i][j].column = j;
            }
        }
        Node path = ed.table[ed.table.length - 1][ed.table[0].length - 1];
        int i = ed.table.length - 1;
        int j = ed.table[0].length - 1;
        path.inPath=true;
        optimal.insert(path);
        int count=0;
        while (i >0 && j >0) {
            optimal.insert(ed.table[ed.table[i][j].prevRow][ed.table[i][j].prevCol]);
            ed.table[ed.table[i][j].prevRow][ed.table[i][j].prevCol].inPath=true;
            i=ed.table[i][j].prevRow;
            j=ed.table[i][j].prevCol;
            count++;
        }
        System.out.println(optimal);
        for (int k = 1; k < ed.table.length; k++) {
            for (int l = 1; l < ed.table[k].length; l++) {
                System.out.print(ed.table[k][l].cost + "\\" + ed.table[k][l].prevPos + "\\" + ed.table[k][l].inPath+"\t");
            }
            System.out.println();
        }
        //System.out.println(count);
        //pairwise(optimal, a,b);
        return ed.table[ed.table.length - 1][ed.table[0].length - 1].cost;
    }
     public static void pairwise(Path p,  String a, String b){
            Node n= p.head;
            char arr1 []= new char [a.length()];
            char arr2 []=new char[a.length()];
            int cos []= new int[Math.max(a.length(), b.length())];
            int i= a.length()-1;
            int j= b.length()-1;
            while(i>=0 && j>=0){
                if(n.prevPos=="D+0"){
                    arr1[i]=a.charAt(i);
                    arr2[i]=b.charAt(j);
                    n.c1=a.charAt(i);
                    n.c2=b.charAt(j);
                    n.pairWiseCost=0;
                    cos[i]=0;
                    i-=1;
                    j-=1;
                    n=n.next;
                }
                else if(n.prevPos=="D+1"){
                    arr1[i]=a.charAt(i);
                    arr2[i]=b.charAt(j);
                    n.c1=a.charAt(i);
                    n.c2=b.charAt(j);
                    n.pairWiseCost=1;
                    cos[i]=1;
                    i-=1;
                    j-=1;
                    n=n.next;
                }
                else if(n.prevPos=="U+2"){
                    arr1[i]= '-';
                    arr2[i]=b.charAt(j);
                    n.c1='-';
                    n.c2=b.charAt(j);
                    n.pairWiseCost=2;
                    cos[i]=2;
                    j-=1;
                    n=n.next;
                }
                else if(n.prevPos=="L+2"){
                    arr1[i]= a.charAt(i);
                    arr2[i]='-';
                    n.c1=a.charAt(i);
                    n.c2='-';
                    n.pairWiseCost=2;
                    i-=1;
                    cos[i]=2;
                    n=n.next;
                }
            }
            for (int k = 0; k < a.length(); k++) {
                System.out.println(arr1[k]+ " "+ arr2[k]+ " "+ cos[k]);
         }
        }
    public static String minLoc(int i, int j,  Node [][] table){
        String smallest="";
        if(table[i-1][j].cost+2<= table[i-1][j-1].cost+1 && table[i-1][j].cost+2<= table[i][j-1].cost+2){
            smallest= (i-1)+ " " + j;
            table[i][j].prevPos= "U+2";
        }
        else if(table[i][j-1].cost+2<= table[i-1][j-1].cost+1 && table[i][j-1].cost+2<= table[i-1][j].cost+2){
            smallest= (i)+ " " + (j-1);
            table[i][j].prevPos= "L+2";
        }
        else{
            smallest= (i-1)+ " " + (j-1);
            table[i][j].prevPos= "D+1";
    }
        return smallest;
    }   
    public static void main(String[] args) throws Exception {
        char[] c = "Snowy".toCharArray();
        char[] d = "Sunny".toCharArray();
        Scanner in = new Scanner(System.in);
        Scanner read = new Scanner (new File("D:\\Programming\\DAA Online\\Assignments\\Assignment2\\sequence\\"+ in.next()));
        read.useDelimiter("\n");
        
            String a = read.next();
            String b = read.next();
        System.out.println(EditDistance.match(a, b));

    }
}
