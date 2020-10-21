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
}