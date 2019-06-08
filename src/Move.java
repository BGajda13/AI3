public class Move {
    int score,col,row;

    public Move(int score, int col, int row) {
        this.score = score;
        this.col = col;
        this.row = row;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
