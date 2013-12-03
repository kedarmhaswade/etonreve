class InputArray {
  public static void main(String[] args) { 
    int howMany = Integer.valueOf(args[0]);
    int max = Integer.valueOf(args[1]);
    System.out.println(howMany);
    java.util.Random r = new java.util.Random();
    for (int i = 0; i < howMany; i++) {
      System.out.println(r.nextInt(max));
    }
  }
}

