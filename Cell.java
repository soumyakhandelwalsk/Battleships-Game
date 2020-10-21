class Cell
{
    public int row, col;
    public char status; 
    public Cell(int row, int col, char status){
        this.row = row;
        this.col = col;
        this.status = status;
    }
    public char get_status(){
        return status;
    }
    public void set_status(char c){
        status = c;
    }
    public int get_row(){
        return row;
    }
    public int get_col(){
        return col;
    }
}