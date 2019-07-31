import java.io.*;
import java.util.*;

public class TestsGreedy{
  public static String[][] info = new String[6][4];

  public static void main(String[] args) {
    int[] runTimes = new int[6];
    long start = System.currentTimeMillis();
    Solver greedyTestA = new Solver(createParcels(1,1,2,3),space());
    greedyTestA.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(greedyTestA)+"]";

    start = System.currentTimeMillis();
    Solver greedyTestAB = new Solver(createParcels(12,1,2,3),space());
    greedyTestAB.solveMax();
    info[1][0] ="     [A-B]";
    info[1][1] ="      [A:1 B:2 C:3]";
    info[1][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[1][3] ="            [" +averageProfit(greedyTestAB)+"]";

    start = System.currentTimeMillis();
    Solver greedyTestABC = new Solver(createParcels(123,1,2,3),space());
    greedyTestABC.solveMax();
    info[2][0] ="     [A-B-C]  ";
    info[2][1] ="  [A:1 B:2 C:3]";
    info[2][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[2][3] ="            [" +averageProfit(greedyTestABC)+"]";

    start = System.currentTimeMillis();
    Solver greedyTestValues1 = new Solver(createParcels(123,1,2,3),space());
    greedyTestValues1.solveMax();
    info[3][0] ="     [A-B-C]   ";
    info[3][1] =" [A:1 B:2 C:3]";
    info[3][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[3][3] ="            [" +averageProfit(greedyTestValues1)+"]";

    start = System.currentTimeMillis();
    Solver greedyTestValues2 = new Solver(createParcels(123,2,1,3),space());
    greedyTestValues2.solveMax();
    info[4][0] ="     [A-B-C]  ";
    info[4][1] =" [A:2 B:1 C:3]";
    info[4][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[4][3] ="            [" +averageProfit(greedyTestValues2)+"]";

    start = System.currentTimeMillis();
    Solver greedyTestValues3 = new Solver(createParcels(123,3,2,1),space());
    greedyTestValues3.solveMax();
    info[5][0] ="     [A-B-C]   ";
    info[5][1] =" [A:3 B:2 C:1]";
    info[5][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[5][3] ="            [" +averageProfit(greedyTestValues3)+"]";
    writer(info);
  }

  public static int[][][] space(){
    int[][][] array = new int[5][8][33];
    return array;
  }

  public static void writer(String[][] str){
    try{
    FileWriter writer = new FileWriter("test.txt");
    PrintWriter out = new PrintWriter(writer);
    out.print("[COMBINATIONS]"  );
    out.print("    [VALUES]"  );
    out.print("    [RUNTIME]"  );
    out.println("    [TRUCK PROFIT]"  );
    for(int i=0 ; i<str.length;i++){
      for(int j=0 ; j<str[0].length;j++){
        out.print(str[i][j]);
      }
      out.println("");
    }
    writer.close();
    }catch (IOException ex){

    }
  }

  public static int averageProfit(Solver obj){
    int average = 0;
    for(int i=0; i<=100 ; i++){
      average = average + obj.getMaxValue();
    }
    return average/100;
  }

  public static  List<Parcel> createParcels(int type, int valueA, int valueB, int valueC){
    List<Parcel> pieces = new ArrayList<>();
    if(type==1){//A
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.A,valueA));
      }
    }else if(type==12){//A-B
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.A,valueA));
        pieces.add(new Parcel(Parcel.B,valueB));
      }
    }else if(type==13){//A-C
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.A,valueA));
        pieces.add(new Parcel(Parcel.C,valueC));
      }
    }else if(type==2){//B
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.B,valueB));
      }
    }else if(type==23){//B-C
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.C,valueC));
        pieces.add(new Parcel(Parcel.B,valueB));
      }
    }else if(type==3){//C
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.C,valueC));
      }
    }else if(type==123){
      for(int i=0 ; i<=100 ;i++){
        pieces.add(new Parcel(Parcel.C,valueC));
        pieces.add(new Parcel(Parcel.B,valueB));
        pieces.add(new Parcel(Parcel.C,valueC));
      }
    }
    return pieces;
  }

  public static void printMatrix(int[][][] matrix){
    System.out.println(Arrays.deepToString(matrix));
  }
}
