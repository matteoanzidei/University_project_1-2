
import java.util.*;

public class Combinatorics{
  private List<Combination> optimalCom;
  private int width;
  private int height;
  private int depth;
  private int valueA;
  private int valueB;
  private int valueC;
  private final int truckVolume;
  private final int smallestVolume = 2;
  private int N;
  public static Combination solution;

    /**
     * Solver class for the Linear Programming approach
     * @param width
     * @param height
     * @param depth
     * @param avalueA
     * @param avalueB
     * @param avalueC
     */
  public Combinatorics(int width,int height,int depth,int avalueA,int avalueB,int avalueC){
    this.optimalCom = new ArrayList<Combination>();
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.valueA = avalueA;
    this.valueB = avalueB;
    this.valueC = avalueC;
    this.truckVolume = width * height * depth;
    this.N = this.truckVolume / 2;
  }

    /**
     * Finds all the the potential Combinations
     * And sorts them by highest value
     */
  public void createCombinations(){
    for(int i = 0; i < N; i++)
      for(int j = 0; j < N; j++)
        for(int k = 0; k < N; k++)
          if(i*2 + j*3 + k*3.375 <= this.truckVolume)
            this.optimalCom.add(new Combination(i, j, k, valueA, valueB, valueC));
    sortOptimalCom();
    this.solution = optimalCom.get(0);
  }

    /**
     * Sorts the Combinations array by highest to lowest value
     */
  public void sortOptimalCom(){
        Combination temp;
        for (int i = 1; i < this.optimalCom.size(); i++) {
            for(int j = i ; j > 0 ; j--){
                if(this.optimalCom.get(j).value > this.optimalCom.get(j-1).value){
                    temp = this.optimalCom.get(j);
                    this.optimalCom.set(j, this.optimalCom.get(j-1));
                    this.optimalCom.set(j-1, temp);
                }
            }
        }
    }

    /**
     * Creates a Parcel List to be passed to either Greedy or Divide and Conquer
     * @param sol
     * @param val
     * @return
     */
    public static List<Parcel> createParcels(int[] sol, int[] val){
        List<Parcel> p = new ArrayList<>();
        for(int len = 0; len < sol.length; len++){
            for(int amount = 0; amount < sol[len]; amount++){
                switch(len){
                    case 0: p.add(new Parcel(Parcel.A, val[len])); break;
                    case 1: p.add(new Parcel(Parcel.B, val[len])); break;
                    case 2: p.add(new Parcel(Parcel.C, val[len])); break;
                }
            }
        }
        return p;
    }
    /*public  List<Parcel> getOptimalComb(){
      this.optimalCom;
    }*/
}
