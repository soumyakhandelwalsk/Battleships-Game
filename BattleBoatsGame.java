//lars5831
//khand080
import java.util.Scanner;
class BattleBoatsGame
{
    public static void main(String [] args){
        String mode;
        BattleBoatsBoard game;
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello. welcome to BattleBoats! Please enter 1 to play in standard mode or 2 to play in expert.");
        mode = sc.next(); //Taking in a string to prevent an error being thrown when input isn't an int
        while((!mode.equals("1")) && (!mode.equals("2"))){
             System.out.println("Invalid input. Please enter 1 to play in standard mode or 2 to play in expert.");
             mode = sc.next();
             break;
        }
        if(mode.equals("1")){
            game = new BattleBoatsBoard(8);
            System.out.println("You need to sink 5 boats to win");
        }
        else{
            game = new BattleBoatsBoard(12);
            System.out.println("You need to sink 10 boats to win");
        }
        game.placeBoats();
        game.display();
        while (game.shipsLeft() > 0) {
            System.out.println("What would you like to do? ");
            switch(sc.next()) {
                case "fire":
                    game.fire();
                    game.display();
                    break;
                case "drone":
                    game.drone();
                    game.display();
                    break;
                case "missile":
                    game.missile();
                    game.display();
                    break;
                case "q":
                    System.out.println("You fired " + game.getShots() + " shots. Game lasted " + game.getTurns() + " turns.");
                    return;
                case "c":
                    game.print();
                    break;
                default:
                    System.out.println("That is not an option");
            }
        }
        System.out.println("You fired " + game.getShots() + " shots to sink all of the ships in " + game.getTurns() + " turns.");
    }
}