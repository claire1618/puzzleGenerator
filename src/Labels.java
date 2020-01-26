public class Labels {
    private int[][] tops;
    private int[][] helper;
    private int[][] side;
    private int[][] helper2;
    private int[][] ret;

    public Labels(int[][] ret){
        this.ret = ret;
        tops = new int[ret.length][ret.length];
        helper = new int[ret.length][ret.length];
        helper2 = new int[ret.length][ret.length];
        side = new int[ret.length][ret.length];
        for(int k = 0; k < ret.length; k++){
            for(int j = 0; j < ret.length; j++) {
                side[k][j] = ret[k][j];
            }
        }
    }

    public int[][] getSide(int[][] ret){
        int h = 0;
        int count = 0;
        for(int k = 0; k < side.length; k++){
            for(int j = 0; j < side.length; j++){
                if(side[k][j] == 1){
                    count++;
                }
                if (side[k][j] == 0 & count > 0) {
                    helper2[h][k] = count;
                    h++;
                    count = 0;
                }
                if(j == side.length - 1 && count > 0){
                    helper2[h][k] = count;
                }
            }
            count = 0;
            h = 0;
        }
        System.out.println(tellMe(helper2));

        return helper2;
    }

    public int [][] getTop(int[][] ret){
        int count = 0;
        for(int a = 0; a < ret.length; a++){
            for(int b = 0; b < ret.length; b++){
               tops[a][b] = ret[b][a];
            }
        }

        int h = 0;

        for(int k = 0; k < tops.length; k++){
            for(int j = 0; j < tops.length; j++){
                if(tops[k][j] == 1){
                    count++;
                }
                if (tops[k][j] == 0 & count > 0) {
                    helper[k][h] = count;
                    h++;
                    count = 0;
                }
                if(j == tops.length - 1 && count > 0){
                    helper[k][h] = count;
                }
            }
            count = 0;
            h = 0;
        }

        System.out.println(tellMe(helper));

        return helper;
    }


    public String tellMe(int[][] ret){
        String hold = "";
        for(int k = 0; k < ret.length; k++){
            for(int j = 0; j < ret.length; j++){
                hold += ret[k][j];
            }
            hold += "\n";
        }
        return hold;
    }
}
