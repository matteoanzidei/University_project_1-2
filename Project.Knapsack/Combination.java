
public class Combination{
  public static final double VOLUMEA = 2;
  public static final double VOLUMEB = 3;
  public static final double VOLUMEC = 3.375;
  public int[] quantityComb;
  public int value = 0;
  public double volume = 0;

  /**
   * Combination class to save a potential combination
   * @param numA
   * @param numB
   * @param numC
   * @param valueA
   * @param valueB
   * @param valueC
   */
  public Combination(int numA, int numB, int numC, int valueA, int valueB, int valueC){
    this.quantityComb = new int[]{numA, numB, numC};
    this.value = (valueA * quantityComb[0]) + (valueB * quantityComb[1]) + (valueC * quantityComb[2]);
    this.volume = (VOLUMEA * quantityComb[0]) + (VOLUMEB * quantityComb[1]) + (VOLUMEC * quantityComb[2]);
  }

  public int getValue(){
    return value;
  }
  public double getVolume(){
    return volume;
  }
  public int getQuantityA(){
    return quantityComb[0];
  }
  public int getQuantityB(){
    return quantityComb[1];
  }
  public int getQuantityC(){
    return quantityComb[2];
  }
}
