public class MainX {

  public void execute() {
     final int x = 1 + 2;
     System.out.println(x);
  }

  public static void main(final String [] args) {
    System.out.println("Running");
    final MainX var = new MainX();
    var.execute();
    System.out.println("Done");
  }

}
