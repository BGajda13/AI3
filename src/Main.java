import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int N = 5;
        Stratego stratego = new Stratego(N);
        Display display = new Display(stratego);
        Scanner in = new Scanner(System.in);
        System.out.println("STRATEGO N="+N+"\n");

        while(stratego.getMovesLeft()>0){

            int row,col;
//            //Player
            do {
                display.display();
                System.out.print("Give row: ");
                row = in.nextInt();
                System.out.print("Give column: ");
                col = in.nextInt();
            }
            while(!stratego.tryPut(row,col,true));
            //stratego.randomMove();

            display.display();

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            //Computer
            stratego.greedyMove();

        }

        System.out.println("Player score: " + stratego.getPlayerScore());
        System.out.println("Computer score: " + stratego.getComputerScore());

    }
}
