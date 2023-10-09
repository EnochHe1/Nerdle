//Enoch He
//March 22, 2022
//This program simulates an  online game called "Nerdle."

import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class Gameshow {
	
	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {
		Song song1 = new Song();
		Song song2 = new Song();
		Song song3 = new Song();
		song3.musicCreate("src/AmazingMusic.wav");
		System.out.println("ATTENTION ALL SELF-PROCLAIMED NERDS!");
		System.out.println("To test if you're a true nerd, try our new game, \"NERDLE,\" to figure out if you have the brains to solve the puzzle. ");
		rules();
		System.out.println("Let's get started now, before your brain cells begin to decompose... ");
		while (true) {
            if (userAction() == 1) {
            	System.out.println();
                Guesses(song1, song2);
            } else {
                System.out.println("Awww, done so early? ;)  Input anything to completely end the program. Hope you enjoyed! ");
                song3.play();
                break;
            }

        }
		s.nextLine();
		song3.stop();
		s.close();
	}
	
	// This method describes the rules of Nerdle. 
	public static void rules() {
		System.out.println("If you have played our sister game, \"Wordle,\" then the rules are quite similar.");
		System.out.println("Essentially, there are 8 blanks, and you must be fill in each blank. ");
		System.out.println("Each blank must be one of the following characters: 0 1 2 3 4 5 6 7 8 9 + - * / =");
		System.out.println("You must be able to fill in all of the 8 blanks to write out an unknown VALID mathethematical equation.");
		System.out.println("For example, 6 * 3 + 2 = 20 could be a valid equation. Spaces DO NOT MATTER for your input. As for the output...");
		System.out.println();
		System.out.println("1. Characters that are present in the equation and in the right position will be repeated.");
		System.out.println("2. Characters that are present in the equation, still need to be added, and are in the wrong position will be rewritten as a question mark.");
		System.out.println("3. Characters that are NOT present in the equation will remain as an underscore.");
		System.out.println();
		System.out.println("You will have a total of 6 tries to guess the mathematical equation.");
		System.out.println("There will be NO leading zeroes in any of the numbers in the unknown math equation!");
		System.out.println("As well, the answer order matters, so 16 + 53 = 69 is different from 53 + 16 = 69.");
		System.out.println("TIP: Don't forget the order of operations!");
		System.out.println();
	}
	
	// This method is essentially the bulk of the main menu, asking if the user would like to play or not 
	public static int userAction() {
        int input = 0;
        String input1 = "";
        System.out.println("Would you like to start a new Nerdle Puzzle?");
        while (input !=1 && input !=2 ) {
            try {
                System.out.print("Enter 1 to continue, or 2 to exit: ");
                input1 = s.nextLine().trim();
                input = Integer.parseInt(input1);
                if(input > 2 || input < 1) System.out.println("That's not a valid input, please enter either 1 or 2" + "\n" + "\n");
            } 
            catch (Exception e) {
                System.out.println("Please type \"1\" to continue, or \"2\" to exit.... It's not rocket science, buddy.");
                System.out.println();
            }
        }

        return input;
    }
	
	// This method generates the actual Nerdle Puzzle
	public static String puzzleGenerator() {
		String output = "";
        boolean ansInt = false;
        int nums = randomInteger(2,3);
        while(output.length()!=8||ansInt==false) {
            output ="";
            double ans = 0;
            ansInt = false;
            
            double[]numbers = new double[nums];
            char[]ops = new char[nums-1];
            for(int i=0; i<nums; i++) {
                numbers[i] = (double)randomInteger(1,999);
            }
            for(int i=0; i<nums-1; i++) {
                ops[i] = OperationGenerator();
            }
            if(nums==2) {
                ans = eval(numbers[0],ops[0],numbers[1]);
            } else {
                if((ops[1]=='*'||ops[1]=='/')&&(ops[0]=='-'||ops[0]=='+')) {
                    ans = eval(numbers[0],ops[0],eval(numbers[1],ops[1],numbers[2]));
                } else {
                ans = eval(eval(numbers[0],ops[0],numbers[1]),ops[1],numbers[2]);
                }
            }
            if(ans%1 == 0) {
                ansInt = true;
            }
            for(int i=0; i<nums; i++) {
                output += (int)numbers[i]+"";
                if(i<nums-1) {
                    output += ops[i];
                }
            }
            output += "="+(int)ans;
        }
        return output;    
	}
	
	//This method simplifies the creation of a random number within two bounds.
	public static int randomInteger(int lowerBound, int higherBound) {
        return (int)(Math.random()*(higherBound-lowerBound+1)) + lowerBound;
    }
	
	//This method randomly generates a mathematical operation for the Nerdle (one of +,-,*,/)
	public static char OperationGenerator() {
		int roll = (int)(Math.random()*4)+1;
		char operation = '0';
		if (roll == 1) {
			operation = '+';
		} else if (roll == 2) {
			operation = '-';
		} else if (roll == 3) {
			operation = '*';
		} else if (roll == 4) {
			operation = '/';
		}
		return operation;
	}
	
	//This method simplifies calculations.
	public static double eval(double a, char c, double b) {
        if(c =='/') {
            return a/b;
        } else if(c=='*') {
            return a*b;
        } else if(c=='+') {
            return a+b;
        } else {
            return a-b;
        }
    }
	
	//This method checks if the user has inputed a guess identical to the Nerdle puzzle
	public static boolean checkEqual(String guess, String puzzle) {
		guess = guess.replaceAll(" ", "");
		boolean isEqual = false;
		if (guess.equals(puzzle)) {
			isEqual = true;
		}
		return isEqual;
	}
	
	// This method checks if the user has inputed a valid mathematical expression.
	public static boolean checkValidInput(String s) {
		s = s.replaceAll(" ", "");
        if(s.length()!=8) {
            return false;
        }
        String ans = "";
        ArrayList<String>numbers = new ArrayList<String>();
        ArrayList<Character>operations = new ArrayList<Character>();
        ArrayList<Integer>num = new ArrayList<Integer>();
        if(s.indexOf('=') == 4 || s.indexOf('=')==5||s.indexOf('=')==6) {
        numbers.add("");
        boolean prevIsOp = false;
        for(int i=0; i<s.length(); i++) {
            if(s.charAt(i)!='=') {
                if(s.charAt(i)=='*'||s.charAt(i)=='/'||s.charAt(i)=='+'||s.charAt(i)=='-') {
                    prevIsOp = true;
                    operations.add(s.charAt(i));
                }else if(prevIsOp) {
                    numbers.add(s.charAt(i)+"");
                    prevIsOp=false;
                } else {
                    numbers.set(numbers.size()-1, numbers.get(numbers.size()-1)+s.charAt(i));
                }
            } else {
                for(int j=i+1; j<s.length(); j++) {
                    ans+=s.charAt(j);
                }
                break;
            }
        }
        } else {
            return false;
        }
        
        try {
            for(String k: numbers) {
                num.add(Integer.parseInt(k));
            }
        } catch(Exception e) {
            return false;
        }
        try {
            if(operations.size()==1) {
                if(eval(num.get(0),operations.get(0),num.get(1))==Integer.parseInt(ans)) {
                    return true;
                } else {
                    return false;
                }
            } else if(operations.size()==2) {
                if((operations.get(1)=='*'||operations.get(1)=='/')&&(operations.get(0)=='+'||operations.get(0)=='-')) {
                    if(eval(num.get(0),operations.get(0),eval(num.get(1),operations.get(1),num.get(2)))==Integer.parseInt(ans)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if(eval(eval(num.get(0),operations.get(0),num.get(1)),operations.get(1),num.get(2))==Integer.parseInt(ans)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch(Exception e) {
            return false;
        }
        return false;
    }
	
	//This method is essentially the entire Nerdle guessing system 
	public static void Guesses(Song song1, Song song2) {
		song1.musicCreate("src/Victory sound effect.wav"); 
		song2.musicCreate("src/Losing sound effect.wav");
		String puzzle = puzzleGenerator();
		System.out.println(puzzle);
		String output = "_ _ _ _ _ _ _ _";
		System.out.println(output);
		String input = s.nextLine();
		System.out.println();
		boolean hasWon = false; 
		for (int i = 0; i < 6; i++) {
			while (!checkValidInput(input)) { // Checking that the user has inputed a valid mathematical expression
				System.out.println(".... We asked you for a VALID mathematical expression .... Please input a guess that is 8 CHARACTERS LONG and does compute!");
				System.out.println("Make sure the number to the right of the equals sign is equal to the expression on the left!");
				System.out.println(output);
				input = s.nextLine();
				System.out.println();
			}
			output = Check(input, puzzle);
			System.out.println(output);
			if(checkEqual(input,puzzle)) {
				hasWon = true;
				break;
			} else if (!checkEqual(input,puzzle) && i < 3){
				input = s.nextLine();
				System.out.println();
			} else if (!checkEqual(input,puzzle) && i == 3){ // User has opportunity to use first life line after 4 guesses
				System.out.println("Would you like to use Lifeline #1? Press 1 if you would like to, and 2 if you would not.");
				int userinput = 0;
				String input1 = "";
				while (userinput !=1 && userinput !=2 ) {
		            try {
		                System.out.print("Enter 1 to continue, or 2 to exit: ");
		                input1 = s.nextLine().trim();
		                userinput = Integer.parseInt(input1);
		                if(userinput > 2 || userinput < 1) System.out.println("That's not a valid input, please enter either 1 or 2" + "\n" + "\n");
		            } 
		            catch (Exception e) {
		                System.out.println("Please type \"1\" to continue, or \"2\" to exit.... It's not rocket science, buddy.");
		                System.out.println();
		            }
		        }
				if (userinput == 1) { // if user would like to use first life line
					System.out.println();
					System.out.println("This lifeline will give you one random character in the Nerdle equation that you may or may not have already used. Its position will not be given, however.");
					System.out.println("If you have already used this given character, and you are sure it cannot be a duplicate, too bad so sad. You can try to bank on the next lifeline, though.");
					System.out.print("The character that this lifeline generated is...    ");
					System.out.println(Lifeline1(puzzle));
					System.out.println();
				} else if (userinput == 2) {
					System.out.println("THAT'S THE SPIRIT!! Lifelines are for cheaters!");
					System.out.println();
				}
				System.out.println("Your previous input was " + input);
				System.out.println("The corresponding output was " + output);
				System.out.println("Guess again!");
				System.out.println(output);
				input = s.nextLine();
				System.out.println();
			} else if (!checkEqual(input,puzzle) && i == 4){ // User has opportunity to use second life line after 5 guesses
				System.out.println("Would you like to use Lifeline #2? Press 1 if you would like to, and 2 if you would not.");
				int userinput = 0;
				String input1 = "";
				while (userinput !=1 && userinput !=2 ) {
		            try {
		                System.out.print("Enter 1 to continue, or 2 to exit: ");
		                input1 = s.nextLine().trim();
		                userinput = Integer.parseInt(input1);
		                if(userinput > 2 || userinput < 1) System.out.println("That's not a valid input, please enter either 1 or 2" + "\n" + "\n");
		            } 
		            catch (Exception e) {
		                System.out.println("Please type \"1\" to continue, or \"2\" to exit.... It's not rocket science, buddy.");
		                System.out.println();
		            }
		        } 
				if (userinput == 1) {  // if user would like to use second life line
					System.out.println();
					System.out.println("This lifeline will give you the position and value of the first character in the Nerdle puzzle that you have not guessed properly.");
					System.out.println("This means that you have either not inputted this character at all or you have not inputted this character in the right position.");
					System.out.print("This character is...    ");
					System.out.println(Lifeline2(input, puzzle));
					System.out.println();
				} else if (userinput == 2) {
					System.out.println("THAT'S THE SPIRIT!! Lifelines are for cheaters!");
					System.out.println();
				}
				System.out.println("Your previous input was " + input);
				System.out.println("The corresponding output was " + output);
				System.out.println("Guess one final time!");
				System.out.println(output);
				input = s.nextLine();
				System.out.println();
			} else {
				System.out.println();
			}
		}
		if (hasWon == true) {    // if the user has won
			song1.play();
			System.out.println("HUZZZAAHHH! You WIN! Looks like you're ACTUALLY SMART!");
		} else {  				// if the user has lost
			song2.play();
			System.out.println();
			System.out.println("YOU LOST! Better luck next time... Unless it's a skill issue ;)  As for the answer.... it was ");
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t\t\t" + puzzle);
			System.out.println();
			System.out.println();
		}	
	}
	
	/* Method that returns the output, telling the user whether each of their inputed characters is right  
	 * and in the right location, right and in the wrong location, or completely wrong.
	 */
	public static String Check(String guess, String puzzle) {
		guess = guess.replaceAll(" ", "");
		char[]temp = new char[8];   
		for(int i=0; i<8; i++) {
			temp[i] = puzzle.charAt(i);
		}
		char[]ret = new char[8];
		Arrays.fill(ret, '_'); 
		for(int i=0; i<8; i++) {
			if(temp[i]==guess.charAt(i)) {
				ret[i] = guess.charAt(i);
				temp[i] = '&';
			}
		}
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(temp[i]==guess.charAt(j) && ret[j]=='_') {
					temp[i] = '&';
					ret[j] = '?';
				}
			}
		}
		String a = "";
		for(int i=0; i<8; i++) {
			a+=ret[i];
		}
		a = a.replace("", " ").trim();
		return a;
	}
	
	// Life line method #1 that generates a random character in the puzzle
	public static String Lifeline1(String puzzle) {
		int index = (int)(Math.random()*8);
		String values = "";
		values += puzzle.substring(index, index+1);
		if(values.equals("=")) {   // The life line would be utterly redundant if the returned character was an equal sign
			index++;
			values = puzzle.substring(index, index+1);
		}
		return values;
	}
	
	// Life line method #2 that generates the first character and its position that the user's input does not match
	public static String Lifeline2(String guess, String puzzle) {
		int position = -1;
		String value = "";
		for (int i = 0; i < 8; i++) {
			if (!guess.substring(i,i+1).equals(puzzle.substring(i,i+1))) {
				value += puzzle.substring(i,i+1);
				break;
			}
		}
		position = puzzle.indexOf(value);
		
		return value + " and it is character # " + (position +1) + " in the Nerdle equation.";
	}
	
}
	
class Song {

    public Clip clip;
    public void musicCreate(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);

            if(musicPath.exists()) {
                 AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            }
            else {
                System.out.println("Music not available.");
            }
        }
        catch(Exception ex) {
        	System.out.println("Error.");
        }
    }

    // method for playing music
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }

    // method for stopping music
    public void stop(){
         clip.stop();
    }
}
