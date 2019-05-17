public class Stratego {
    private int[][] matrix;
    private int movesLeft,playerScore,computerScore;
    public Stratego(int N){
        if(N < 4) throw new IllegalArgumentException("N < 4");
        matrix = new int[N][N];
        this.movesLeft = N*N;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getMovesLeft(){
        return movesLeft;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public void put(int row, int col, boolean player){
        if(matrix[row][col]==0){
            if(player)matrix[row][col] = 1;
            else
                matrix[row][col] = 2;
            movesLeft--;
        }

        /* Score Section */
        int score = 0;
        //Check if move is closing a column
        boolean closedColumn = true;
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][col] == 0) closedColumn = false;
        }
        if(closedColumn) score += matrix.length;

        //Check if move is closing a row
        boolean closedRow = true;
        for (int j = 0; j < matrix.length; j++) {
            if(matrix[row][j] == 0) closedRow = false;
        }
        if(closedRow) score += matrix.length;

        //Check if move is closing left up diagonal
        //Left Diagonal
        boolean closedLeftDiagonal = true;
        int tempScore = 0;
        int startingRow = 0,startingCol=0;
        if(!((row==0 && col==0) ||
                (row == matrix.length-1 && col == matrix.length-1))){
            if (row + col < matrix.length - 1) {
                startingRow = row + col;
                startingCol = 0;

            }
            else {
                startingRow = matrix.length - 1;
                startingCol = row + col - matrix.length + 1;

            }
        }
        else
            closedLeftDiagonal = false;

        //Iterating RIGHT UP
        int j = startingCol;
        for (int i = startingRow; i >= 0; i--) {
            if( j > matrix.length-1) break;
            tempScore++;
            if(matrix[ i ][j++]==0) closedLeftDiagonal = false;

        }

        if(closedLeftDiagonal) score += tempScore;

        //Check if move is closing right diagonal
        //Right Diagonal
        boolean closedRightDiagonal = true;
        tempScore = 0;
        if(!((row == 0 && col == matrix.length-1) ||
                (row == matrix.length-1 && col == 0))){
            if ((row - col) < 0) {
                startingRow = matrix.length - 1 + (row - col);
                startingCol = matrix.length - 1;
            }
            else {
                startingRow = matrix.length - 1;
                startingCol = matrix.length - 1 - (row - col);
            }
        }
        else closedRightDiagonal = false;

       //Iterating LEFT UP
        j = startingCol;
        for (int i = startingRow; i >= 0; i--) {
            if(j<0) break;
            tempScore++;
            if(matrix[ i ][j--]==0) closedRightDiagonal = false;
        }
        if (closedRightDiagonal) score += tempScore;

        StringBuilder msg = new StringBuilder();
        if(player) msg.append("Player ");
        else msg.append("Computer ");
        msg.append("put in row: " + row + " col: " + col + "\n");

        if(closedColumn) msg.append("Move closed column: " + col + "\n");
        if(closedRow) msg.append("Move closed row: " + row + " \n");
        if(closedRightDiagonal) msg.append("Move closed right diagonal\n");
        if(closedLeftDiagonal) msg.append("Move closed left diagonal\n");
        if(score>0){
            msg.append("Move gives " + score + " points");
            if(player) playerScore += score;
            else computerScore += score;
        }

        System.out.println(msg);
    }


    public void computerTurn(){
        int row,col;
        do {
            row = (int) (Math.random() * matrix.length);
            col = (int) (Math.random() * matrix.length);
        }
        while(matrix[row][col]!=0);
        put(row,col,false);

    }

    @Override
    public String toString() {
       return "Use Display class!";
    }

}
