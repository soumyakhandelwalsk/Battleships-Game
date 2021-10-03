//lars5831
//khand080
class BattleBoats
{
    private int size;
    private char orient;
    private Cell [] arr;

    public BattleBoats(Cell [] a, int len, char orientation){
        size = len;
        orient = orientation;
        arr = a;
        for(int i=0; i<len; i++){
            arr[i].set_status('B');
        }
    }
    public Cell [] get_arr(){
        return arr;
    }

    public boolean isSunk(Cell[][] board) { //Checks to see if the boat sunk.
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (board[arr[i].get_row()][arr[i].get_col()].get_status() == 'H') {
                count++;
            }
        }
        if (count == arr.length) { // If all locations of the boat have H, then it is sunk.
            return true;
        }
        return false;
    }

}