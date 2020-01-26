public class GenerateColumn {
    private int columns = 0;
    private int[][] ret;

    public GenerateColumn(int columns){
        this.columns = columns;
        ret = new int[columns][columns];
    }

    public int[][] fill(int[][] ret){
        for(int k = 0; k < columns; k++){
            for(int j = 0; j < columns; j++){
                ret[k][j] = (int)(Math.random() * 2);
            }
        }
        return ret;
    }

    public String why(int[][] ret){
        String temp = "";
        for(int k = 0; k < columns; k++){
            for(int j = 0; j < columns; j++){
                temp += ret[k][j] + ", ";
            }
            temp += "\n";

        }
        return temp;
    }

    public int[][] getRet() {
        return ret;
    }
}
