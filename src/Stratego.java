public class Stratego {
    static String tab = "\u0009"+"\u0009"+"\u0009"+"\u0009";
    private int[][] matrix;
    private int movesLeft,playerScore,computerScore;
    public Stratego(int N){
        if(N < 4) throw new IllegalArgumentException("N < 4");
        matrix = new int[N][N];
        this.movesLeft = N*N;
        System.out.println("MAX POINTS :" + getMaxScore());
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

    public int getMaxScore(){
        int maxScore = 0;
        //Za kolumny i wiersze
        maxScore += matrix.length * matrix.length *2;

        int tempScore = 0;
        //Za przekątne niegłówne
        for (int i = 2; i < matrix.length; i++) {
            tempScore += i;
        }
        //Symetria
        tempScore *=2;
        //Srodkowa
        tempScore +=matrix.length;
        //Symatetria przekatnych
        tempScore *=2;

        maxScore += tempScore;
        return maxScore;
    }

    private void undo(int row, int col){
        matrix[row][col] = 0;
    }
    private int checkScore(int row, int col){
        matrix[row][col] = 3;
        int score =
                checkRow(row)
                + checkCol(col)
                + checkLeftDiagonal(row,col)
                + checkRightDiagonal(row,col);
        undo(row,col);
        return score;
    }

    //Check if move is closing a row
    private int checkRow(int row){
        boolean closedRow = true;
        for (int j = 0; j < matrix.length; j++) {
            if(matrix[row][j] == 0) closedRow = false;
        }
        if(closedRow) return matrix.length;
        return 0;
    }

    //Checks if move is closing a column
    private int checkCol(int col){
        boolean closedColumn = true;
        for (int i = 0; i < matrix.length; i++) {
            if(matrix[i][col] == 0) closedColumn = false;
        }
        if(closedColumn) return matrix.length;
        return 0;
    }

    //Check if move is closing left up diagonal
    private int checkLeftDiagonal(int row,int col){
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
        if(closedLeftDiagonal) return tempScore;
        return 0;
    }

    //Check if move is closing right diagonal
    private int checkRightDiagonal(int row, int col){
        int tempScore = 0;
        int startingRow = 0,startingCol=0;
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
        int j = startingCol;
        for (int i = startingRow; i >= 0; i--) {
            if(j<0) break;
            tempScore++;
            if(matrix[ i ][j--]==0) closedRightDiagonal = false;
        }
        if (closedRightDiagonal) return tempScore;
        return 0;
    }

    //Zakłada się wstawienie prawidłowego !!
    private void put(int row, int col, boolean player){
        //wstawienie
        if(matrix[row][col]==0){
            if(player)matrix[row][col] = 1;
            else
                matrix[row][col] = 2;
            movesLeft--;
        }

        /* Score Section */
        int score = 0;
        //Check if move is closing a column
        boolean closedColumn = false;
        int colScore = checkCol(col);
        if(colScore > 0) {
            score += colScore;
            closedColumn = true;
        }
        //Check if move is closing a row
        boolean closedRow = false;
        int rowScore = checkRow(row);
        if(rowScore > 0) {
            score += rowScore;
            closedRow = true;
        }
        //Check if move is closing left up diagonal
        boolean closedLeftDiagonal = false;
        int leftDiaScore = checkLeftDiagonal(row,col);
        if(leftDiaScore > 0) {
            score += leftDiaScore;
            closedLeftDiagonal = true;
        }
        //Check if move is closing right diagonal
        boolean closedRightDiagonal = false;
        int rightDiaScore = checkRightDiagonal(row,col);
        if(rightDiaScore > 0){
            score += rightDiaScore;
            closedRightDiagonal = true;
        }

        //Message
        StringBuilder msg = new StringBuilder();
        if(player) msg.append(Display.ANSI_GREEN + tab + "Player ");
        else msg.append(Display.ANSI_BLUE + tab+"Computer ");
        msg.append("put in row: " + row + " col: " + col + "\n"+Display.ANSI_RESET);

        if(closedColumn) msg.append(Display.ANSI_CYAN + tab + "Move closed column: " + col + "\n");
        if(closedRow) msg.append(Display.ANSI_CYAN +  tab + "Move closed row: " + row + " \n");
        if(closedRightDiagonal) msg.append(Display.ANSI_CYAN + tab+"Move closed right diagonal\n");
        if(closedLeftDiagonal) msg.append(Display.ANSI_CYAN + tab+"Move closed left diagonal\n");
        if(score>0){
            msg.append(tab+"Move gives " + score + " points" + Display.ANSI_RESET);
            if(player) playerScore += score;
            else computerScore += score;
        }

        System.out.println(msg);
    }

    public boolean tryPut(int row, int col, boolean player){
        if(matrix[row][col] == 0 ){
            put(row,col,player);
            return true;
        }
        else{
            System.out.println(Display.ANSI_RED + "WRONG MOVE!!! TRY ANOTHER!!!" + Display.ANSI_RESET);
            return false;
        }

    }

    //AI MOVES
    public void randomMove(){
        int row,col;
        do {
            row = (int) (Math.random() * matrix.length);
            col = (int) (Math.random() * matrix.length);
        }
        while(matrix[row][col]!=0);
        put(row,col,true);
    }
    public void greedyMove(){
        // Looking for most prominent move
        int bestScore = 0, bestRow = 0, bestCol = 0;
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if(matrix[row][col] == 0){
                    int tempScore = checkScore(row,col);
                    if(tempScore > bestScore){
                        bestScore = tempScore;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }
        if(bestScore > 0){
            put(bestRow,bestCol,false);
            return;
        }
        //If there is no prominent move, then look for a best col or row
        int[] rowsRank = new int[matrix.length];
        int[] colsRank = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowScore = 0;
            int colScore = 0;
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j]!=0) rowScore++;
                if(matrix[j][i]!=0) colScore++;
            }
            rowsRank[i] = rowScore;
            colsRank[i] = colScore;
        }

        int maxR = rowsRank[0], indexR=0;
        for (int i = 0; i < rowsRank.length; i++) {
            if(rowsRank[i] > maxR){
                maxR = rowsRank[i];
                indexR = i;
            }
        }

        int maxC = colsRank[0], indexC=0;
        for (int i = 0; i < colsRank.length; i++) {
            if(colsRank[i] > maxC){
                maxC = colsRank[i];
                indexC = i;
            }
        }

        if(maxC > maxR){
            for (int i = 0; i < matrix.length; i++) {
                if(matrix[i][indexC]==0){
                    put(i,indexC,false);
                    return;
                }
            }
        }
        else if(maxC < maxR){
            for (int i = 0; i < matrix.length; i++) {
                if(matrix[indexR][i]==0){
                    put(indexR,i,false);
                    return;
                }
            }
        }
        else
            randomMove();
        return;
    }


    @Override
    public String toString() {
       return "Use Display class!";
    }

    public int[][] cloneArr() {
        int[][] result = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }


}
