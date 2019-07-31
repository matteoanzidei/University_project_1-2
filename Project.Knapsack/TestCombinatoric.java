import java.io.*;
import java.util.*;

public class TestCombinatoric {

  public static void main(String[] args) {
    String[][] info = new String[6][4];

    long start = System.currentTimeMillis();
    Combinatorics combi1 = new Combinatorics(5, 8, 33, 1, 2, 3);
    combi1.createCombinations();
    List<Parcel> pieces1 = Combinatorics.createParcels(combi1.solution.quantityComb, new int[]{1, 2, 3});
    Solver algorithm1 = new Solver(pieces1, space());
    algorithm1.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm1)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi2 = new Combinatorics(5, 8, 33, 3, 2, 1);
    combi2.createCombinations();
    List<Parcel> pieces2 = Combinatorics.createParcels(combi2.solution.quantityComb, new int[]{3, 2, 1});
    Solver algorithm2 = new Solver(pieces2, space());
    algorithm2.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm2)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi3 = new Combinatorics(5, 8, 33, 1, 3, 2);
    combi3.createCombinations();
    List<Parcel> pieces3 = Combinatorics.createParcels(combi3.solution.quantityComb, new int[]{1, 3, 2});
    Solver algorithm3 = new Solver(pieces3, space());
    algorithm3.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm3)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi4 = new Combinatorics(5, 8, 33, 1, 2, 0);
    combi4.createCombinations();
    List<Parcel> pieces4 = Combinatorics.createParcels(combi4.solution.quantityComb, new int[]{1, 2, 0});
    Solver algorithm4 = new Solver(pieces4, space());
    algorithm4.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm4)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi5 = new Combinatorics(5, 8, 33, 1, 0, 2);
    combi5.createCombinations();
    List<Parcel> pieces5 = Combinatorics.createParcels(combi5.solution.quantityComb, new int[]{1, 0, 2});
    Solver algorithm5 = new Solver(pieces5, space());
    algorithm5.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm5)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi6 = new Combinatorics(5, 8, 33, 0, 1, 2);
    combi6.createCombinations();
    List<Parcel> pieces6 = Combinatorics.createParcels(combi6.solution.quantityComb, new int[]{0, 1, 2});
    Solver algorithm6 = new Solver(pieces6, space());
    algorithm6.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm6)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi7 = new Combinatorics(5, 8, 33, 2, 1, 0);
    combi7.createCombinations();
    List<Parcel> pieces7 = Combinatorics.createParcels(combi7.solution.quantityComb, new int[]{2, 1, 0});
    Solver algorithm7 = new Solver(pieces7, space());
    algorithm7.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm7)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi8 = new Combinatorics(5, 8, 33, 2, 0, 1);
    combi8.createCombinations();
    List<Parcel> pieces8 = Combinatorics.createParcels(combi8.solution.quantityComb, new int[]{2, 0, 1});
    Solver algorithm8 = new Solver(pieces8, space());
    algorithm8.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm8)+"]";

    start = System.currentTimeMillis();
    Combinatorics combi9 = new Combinatorics(5, 8, 33, 0, 2, 1);
    combi9.createCombinations();
    List<Parcel> pieces9 = Combinatorics.createParcels(combi9.solution.quantityComb, new int[]{0, 2, 1});
    Solver algorithm9 = new Solver(pieces9, space());
    algorithm9.solveMax();
    info[0][0] ="     [A]    ";
    info[0][1] ="    [A:1 B:2 C:3]";
    info[0][2] ="     ["+(System.currentTimeMillis() - start)+"]";
    info[0][3] ="            [" +averageProfit(algorithm9)+"]";
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
}
