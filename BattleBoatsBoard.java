//lars5831
//khand080
import java.util.Scanner;
class BattleBoatsBoard 
{
    private Cell [][] board;
    private BattleBoats [] boats;
    private int shots=0, turns=0, ships_left, missiles, drones;
    public BattleBoatsBoard (int size){
        board = new Cell [size][size];
        if (size==8){
            boats = new BattleBoats [5];
            ships_left = 5;
            missiles = 1;
            drones = 1;
        }
        else{
            boats = new BattleBoats [10];
            ships_left = 10;
            missiles = 2;
            drones = 2;
        }
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                board[i][j] = new Cell (i, j, '-');
            }
        }
    }
    public int getTurns() {
        return turns;
    }
    public int getShots() {
        return shots;
    }
    public int shipsLeft() {
        return ships_left;
    }

    public void placeBoats(){
        int x,y,len = board.length, inc, index=0;
        int boat_size[] = {5,5,4,4,3,3,3,3,2,2}; //array containing sizes of boats
        double orient; //vertical or horizontal orientation
        boolean check; //flag variable that indicates if the boat can be placed at the chosen (x,y) position or not
        Cell [] arr; // array containing the cells occupied by a boat
        if(len==8){//value of int helps set the number of boats for each mode of difficulty
            inc = 2;
        }
        else{
            inc = 1;
        }
        for(int j=0; j<boat_size.length; j+=inc){
            check = false;
            arr = new Cell [boat_size[j]];
            while(check == false){
                check = true;
                x = (int)Math.floor(Math.random()*len);
                y = (int)Math.floor(Math.random()*len);
                orient = Math.random();
                if(orient<=0.5){ 
                    if((x+boat_size[j])>=len){
                        check=false;
                    }
                    for(int i=x; i<(x+boat_size[j]) && check==true; i++){ //horizontal orientation
                        arr[i-x] = board[i][y];
                        if(board[i][y].get_status()=='B'){
                            check = false;
                        }
                    }
                }
                else{
                    if((y+boat_size[j])>=len){
                        check=false;
                    }
                    for(int i=y; i<(y+boat_size[j]) && check==true; i++){ //vertical orientation
                        arr[i-y] = board[x][i];
                        if(board[x][i].get_status()=='B'){
                            check = false;
                        }
                    }
                }
                if(check==true){
                    if(orient<=0.5){
                        boats[index] = new BattleBoats(arr, boat_size[j],'H');
                    }
                    else{
                        boats[index] = new BattleBoats(arr, boat_size[j],'V');
                    }
                }
            }
            index++ ;
        }
    }

    public void print(){
        int [][] temp = new int [board.length][board.length];
        Cell arr[], t;
        for(int i=0; i<boats.length; i++){
            arr = boats[i].get_arr();
            for(int j=0; j<arr.length; j++){
                t = arr[j];
                temp[t.get_row()][t.get_col()] = i+1;
            }
        }
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                switch(board[i][j].get_status()){
                    case 'M':
                    System.out.print("O\t");
                    break;
                    case 'H':
                    System.out.print("X\t");
                    break;
                    case 'B':
                    System.out.print(temp[i][j]+"\t");
                    break;
                    default:
                    System.out.print("-\t");
                }
            }
            System.out.println("\n");
        }
    }

    public void display(){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                switch(board[i][j].get_status()){
                    case 'M':
                    System.out.print("O ");
                    break;
                    case 'H':
                    System.out.print("X ");
                    break;
                    default:
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public void drone(){
        if(drones==0){
            System.out.println("Drones have been used the max amount of times.");
            return;
        }
        String choice;
        int num, count=0;
        char temp;
        turns += 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to scan a row or column? Type in r for row or c for column.");
        choice = sc.next();
        while((!choice.equals("r")) && (!choice.equals("c"))){
            System.out.println("Invalid input. Please type in r for row or c for column.");
            choice = sc.next();
        }
        System.out.println("Which row or column would you like to scan?");
        num = sc.nextInt();
        while(num<0 || num>=board.length){
            System.out.println("Invalid Input. Please type in a number within the boundaries of the board.");
            num = sc.nextInt();
        }
        if(choice.equals("r")){
            for(int i=0; i<board.length; i++){
                temp = board[num][i].get_status();
                if(temp=='H' || temp=='B'){
                    count++;
                }
            }
        }
        else{
            for(int i=0; i<board.length; i++){
                temp = board[i][num].get_status();
                if(temp=='H' || temp=='B'){
                    count++;
                }
            }
        }
        System.out.println("Drone has scanned "+count+" targets in the specified area.");
        drones--;
    }

    public void fire() {
        System.out.println("Please enter coordinates (x then y): ");
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();
        turns++;
        if (x > board.length-1 || x < 0 || y > board.length-1 || y < 0                   // Checks out of bounds
                || board[x][y].get_status() == 'M' || board[x][y].get_status() == 'H') { // and re-targets.
            System.out.println("Penalty");
            turns++;
        } else {
            if (board[x][y].get_status() == '-') { //Miss
                shots++;
                System.out.println("Miss");
                board[x][y].set_status('M');
            } else { // Hit/Sink
                shots++;
                board[x][y].set_status('H');
                for (int i = 0; i < boats.length; i++) { //Checks to see if a boat sunk
                    if (boats[i].isSunk(board)) {
                        System.out.println("Sunk");
                        BattleBoats[] temp = new BattleBoats [boats.length -1];
                        int iter = 0;
                        for (int j = 0; j < boats.length; j++) { // Removes the sunk boat from the boats array.
                            if (j != i) {
                                temp[iter] = boats[j];
                                iter++;
                            }
                        }
                        boats = temp;
                        ships_left--;
                        return; //Ends loop as soon as sunk boat is found
                    }
                } //Sunk check
                System.out.println("Hit");
            } //Hit/Sink
        } // Viable move if/else
    } //fire

    public void missile() {
        if (missiles == 0) {
            System.out.println("Missiles have been used the max amount of times.");
            return;
        }
        turns++;
        int x,y;
        System.out.println("Where would you like to fire your missile? (Enter x then y) ");
        Scanner sc = new Scanner (System.in);
        x = sc.nextInt();
        y = sc.nextInt();
        while (x > board.length-1 || x < 0 || y > board.length-1 || y < 0 ) { //Checks for out of bounds coords.
            System.out.println("Invalid Coordinates. Please try again");
            x = sc.nextInt();
            y = sc.nextInt();
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (y + j > -1 && y + j < board.length && x + i > -1 && x + i < board.length) {
                    if (board[x + i][y + j].get_status() == '-') { //Miss
                        board[x + i][y + j].set_status('M');
                    } else if (board[x + i][y + j].get_status() == 'B') { //Hits
                        board[x + i][y + j].set_status('H');
                        for (int k = 0; k < boats.length; k++) { //Checks to see if a boat sunk
                            if (boats[k].isSunk(board)) {
                                BattleBoats[] temp = new BattleBoats[boats.length - 1];
                                int iter = 0;
                                for (int l = 0; l < boats.length; l++) { // Removes the sunk boat from the boats array.
                                    if (l != k) {
                                        temp[iter] = boats[l];
                                        iter++;
                                    }
                                }
                                boats = temp;
                                ships_left--;
                                break; //Ends loop as soon as sunk boat is found
                            }
                        } //Sunk check
                    } // Hit/Miss if/else
                } //Within bounds
            }
        }
        missiles--;
    } // Missile
}