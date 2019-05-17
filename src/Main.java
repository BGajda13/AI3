import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Stratego stratego = new Stratego(4);
        Display display = new Display(stratego);
        Scanner in = new Scanner(System.in);

        while(stratego.getMovesLeft() >0){


            //Player
            System.out.print("Give row: ");
            int row = in.nextInt();
            System.out.print("Give column: ");
            int col = in.nextInt();
            stratego.put(row,col,true);

            System.out.println();
            display.display();
            //Computer
            stratego.computerTurn();
            display.display();
        }

        System.out.println("Player score: " + stratego.getPlayerScore());
        System.out.println("Computer score: " + stratego.getComputerScore());

    }
}
