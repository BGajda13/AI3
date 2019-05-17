import javax.xml.ws.Dispatch;

public class Display {
    Stratego st;
    public Display(Stratego stratego){
        if(stratego.getMatrix().length >  9) throw new IllegalArgumentException("Display class doesnt handle N>9");
        st = stratego;
    }

    /*Display Section*/
    private String player = "\u26AB" + "\u2004";  //
    private String opponent = "\u26AA" + "\u2004"; //
    private String empty = "\u3000" + "\u2009"; // u3000

    //Corners
    private String LUC = "\u250C"; //┌
    private String RUC = "\u2510"; //┐
    private String LLC = "\u2514"; //└
    private String RLC = "\u2518"; //┘

    //Lines
    private String HOR = "\u2500\u2500"; //──
    private String VER = "\u2502"; //│

    //Joints
    private String UPJ = "\u252C"; //┬
    private String LOJ = "\u2534"; //┴
    private String LEJ = "\u251C"; //├
    private String RIJ = "\u2524\n"; //┤

    //Middle crosses
    private String MIC = "\u253C"; //┼

    private String headerLine(int header){
        StringBuilder sb = new StringBuilder();
        sb.append(empty);
        for (int i = 0; i < header; i++) {
            sb.append(" "+ i + " ");
        }
        sb.append("  Player: " + st.getPlayerScore() + ",  Ai: " + st.getComputerScore());
        sb.append("\n");
        return sb.toString();
    }
    private String firstLine(int l){
        StringBuilder sb = new StringBuilder();
        sb.append(empty);
        sb.append(LUC + HOR);
        for (int i = 1; i < l; i++) {
            sb.append(UPJ + HOR);
        }
        sb.append(RUC);
        sb.append("   Moves left: ");
        return sb.toString();
    }
    private String middleLine(int l){
        StringBuilder sb = new StringBuilder();
        sb.append(empty);
        sb.append(LEJ);
        for (int i = 1; i < l ; i++) {
            sb.append(HOR + MIC);
        }
        sb.append(HOR + RIJ);
        return sb.toString();
    }
    private String lastLine(int l){
        StringBuilder sb = new StringBuilder();
        sb.append(empty);
        sb.append(LLC + HOR);
        for (int i = 2; i < l+1;  i++) {
            sb.append(LOJ + HOR);
        }
        sb.append(RLC);
        return sb.toString();
    }
    private String contentLine(int row[],int header){
        StringBuilder sb = new StringBuilder();
        sb.append(header+" ");
        sb.append(VER);
        for (int i = 0; i < row.length ; i++) {
            if(row[i]==1) sb.append(player);
            else if(row[i]==2) sb.append(opponent);
            else sb.append(empty);
            sb.append(VER);
        }
        sb.append("\n");
        return sb.toString();
    }

    public void display(){
        int[][] board = st.getMatrix();
        int movesLeft = st.getMovesLeft();

        StringBuilder sb = new StringBuilder();
        sb.append(headerLine(board.length));
        sb.append(firstLine(board.length));
        sb.append(movesLeft + "\n");
        sb.append(contentLine(board[0],0));

        for (int i = 1; i < board.length; i++) {
            sb.append(middleLine(board.length));
            sb.append(contentLine(board[i],i));
        }
        sb.append(lastLine(board.length));
        System.out.println(sb);
    }
}
