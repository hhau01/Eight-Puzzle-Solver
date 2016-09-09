/*
** Henry Au
** CS420 - Project 1
** Due: Thursday, April 28, 2016 at Midnight
*  Solve 8-puzzle using A* search algorithm
*/

import java.util.*;

public class EPuzzle{
    public static class EightPuzzle implements Comparable<EightPuzzle>{
        int[] puzzle = new int[9];
        private int htype, hcost, gcost = 0, totalcost = 0;
        private EightPuzzle parent = null;

        public EightPuzzle(int[] p, int g, int h){
            puzzle = Arrays.copyOf(p,p.length);
            gcost = g;
            htype = h;
            hcost = (h == 1) ? h1(p):h2(p);
            totalcost = gcost + hcost;
        }

        private EightPuzzle getParent(){
            return parent;
        }

        //This will return the depth
        private int getGoal(){
            if(parent == null) return 0;
            else return gcost;
        }

        //Find the blank in the puzzle
        private int getBlank(){
            for(int i = 0; i < 9; i++){
                if(puzzle[i] == 0) return i;
            }
            return -1;
        }

        //Get possible moves: "UDLR" Up, Down, Left, Right
        public String getMoves(int[] p){
            int index = getBlank();
            switch(index){
                case 0:
                    return "DR";
                case 1:
                    return "DLR";
                case 2:
                    return "DL";
                case 3:
                    return "UDR";
                case 4:
                    return "UDLR";
                case 5:
                    return "UDL";
                case 6:
                    return "UR";
                case 7:
                    return "ULR";
                case 8:
                    return "UL";
            }
            return "";
        }

        //(1)h1 = the number of misplaced tiles except blank, 0
        private int h1(int[] p){
            int h1 = 0;

            for(int i = 0; i < 9; i++){
                if(p[i] != i && p[i] != 0) h1++;
            }
            return h1;
        }

        //(2)h2 = the sum of the distances of the tiles from their goal positions except blank
        private int h2(int[] p){
            int h2 = 0;

            for(int i = 0; i < 9; i++){
                if(p[i] != 0)
                    //Add horizontal and vertical distances
                    h2 += (Math.abs((p[i]%3)-(i%3))+ Math.abs((p[i]/3)-(i/3)));
            }
            return h2;
        }

        public boolean isGoal(){
            for(int i = 0; i < 9; i++){
                if(puzzle[i] != i) return false;
            }
            return true;
        }

        public void swap(int[] p, int blank, int n){
            int temp = p[blank];
            p[blank] = p[n];
            p[n] = temp;
        }

        //Check if same puzzle configuration
        public boolean checkSame(EightPuzzle other){
            for(int i = 0; i < 9; i++){
                if(this.puzzle[i] != other.puzzle[i]) return false;
            }
            return true;
        }

        //Get all the neighbors of the current configuration
        private LinkedList<EightPuzzle> getNeighbors(){
            LinkedList<EightPuzzle> list = new LinkedList<EightPuzzle>();
            int blank = getBlank();
            int[] neighbor;
            char[] moves = getMoves(puzzle).toCharArray();

            for(int i = 0; i < moves.length; i++){
                switch(moves[i]){
                    case 'U':
                        neighbor = Arrays.copyOf(puzzle,puzzle.length);
                        swap(neighbor,blank,blank-3);
                        EightPuzzle up = new EightPuzzle(neighbor,getGoal()+1,htype);
                        up.parent = this;

                        list.add(up);
                        break;

                    case 'D':
                        neighbor = Arrays.copyOf(puzzle,puzzle.length);
                        swap(neighbor,blank,blank+3);
                        EightPuzzle down = new EightPuzzle(neighbor,getGoal()+1,htype);
                        down.parent = this;

                        list.add(down);
                        break;

                    case 'L':
                        neighbor = Arrays.copyOf(puzzle,puzzle.length);
                        swap(neighbor,blank,blank-1);
                        EightPuzzle left = new EightPuzzle(neighbor,getGoal()+1,htype);
                        left.parent = this;

                        list.add(left);
                        break;

                    case 'R':
                        neighbor = Arrays.copyOf(puzzle,puzzle.length);
                        swap(neighbor,blank,blank+1);
                        EightPuzzle right = new EightPuzzle(neighbor,getGoal()+1,htype);
                        right.parent = this;

                        list.add(right);
                        break;
                }
            }
            return list;
        }

        //Display 3x3 puzzle
        public void printPuzzle(){
            for(int i = 0; i < 9; i++){
                System.out.print(puzzle[i] + " ");
                if((i+1)%3 == 0) System.out.println();
            }
            System.out.println();
        }

        //Overwrite compareTo method
        public int compareTo(EightPuzzle other){
            return this.totalcost - other.totalcost;
        }
    }

    public static void main(String[] args){
        System.out.println("Welcome to 8-Puzzle Solver!");
        System.out.println("(1) Randomly generate 8-puzzle problem");
        System.out.println("(2) Enter your own");

        Scanner input = new Scanner(System.in);
        String user = input.nextLine();
        boolean correctChoice = false;
        int choice = 0;

        while(!correctChoice){
            try{
                choice = Integer.parseInt(user);
                if(choice == 1 || choice == 2){
                    correctChoice = true;
                }
                else{
                    System.out.println("Incorrect input.\nPlease enter 1 or 2");
                    user = input.nextLine();
                }
            }
            catch(Exception e){
                System.out.println("Incorrect input.\nPlease enter 1 or 2");
                user = input.nextLine();
            }
        }

        //Randomly generate puzzle
        if(choice == 1){
            int[] random = {0,1,2,3,4,5,6,7,8};
            boolean solvable = false;

            while(!solvable){
                shuffleArray(random);
                if(isSolvable(random)) solvable = true;
            }

            //h1 heuristic - misplaced tiles

            EightPuzzle randh1 = new EightPuzzle(random,0,1);
            System.out.println("Randomly generated puzzle:\n");
            randh1.printPuzzle();

            System.out.println("Solution using h1 heuristics\n");
            doAStar(randh1);

            //h2 heuristic - manhattan distance
            EightPuzzle randh2 = new EightPuzzle(random,0,2);

            System.out.println("\nSolution using h2 heuristics\n");
            doAStar(randh2);

        }

        //User generated input
        if(choice == 2){
            System.out.println("Enter your configuration(Example:'250148367'): ");
            user = input.nextLine();
            boolean correctInput = false;
            int[] config = new int[9];

            while(!correctInput){
                if(user.length()!=9){
                    System.out.println("Incorrect length\nExample:'250148367'");
                    user = input.nextLine();
                }
                else{
                    try{
                        int num = Integer.parseInt(user);
                        for(int i = 0; i < 9; i++){
                            config[i] = Character.getNumericValue(user.charAt(i));
                        }
                        correctInput = true;
                    }
                    catch(Exception e){
                        System.out.println("Integer format only\n" +
                                "Example:'250148367'");
                        user = input.nextLine();
                    }
                }
            }
            EightPuzzle usrh1 = new EightPuzzle(config,0,1);
            System.out.println("Your puzzle is:\n");
            usrh1.printPuzzle();

            if(!isSolvable(config)){
                System.out.println("Sorry, your puzzle is not solvable");
            }
            else{
                //h1 heuristic - misplaced tiles
                System.out.println("Solution using h1 heuristics\n");
                doAStar(usrh1);

                //h2 heuristic - manhattan distance
                EightPuzzle usrh2 = new EightPuzzle(config,0,2);

                System.out.println("\nSolution using h2 heuristics\n");
                doAStar(usrh2);
            }
        }
    }

    //Do A* algorithm
    public static EightPuzzle doAStar(EightPuzzle ep){
        PriorityQueue<EightPuzzle> open = new PriorityQueue<EightPuzzle>();
        HashSet<EightPuzzle> closed = new HashSet<EightPuzzle>();

        long startTime = System.currentTimeMillis();
        open.add(ep);

        while(!open.isEmpty()){
            //Check if smallest is goal
            EightPuzzle ep1 = open.poll();
            if(ep1.isGoal()){
                Stack<EightPuzzle> solution = genPath(ep1);
                printPath(solution);
                long finishTime = System.currentTimeMillis() - startTime;
                System.out.println("Depth: " + ep1.getGoal() + " Explored: " + closed.size() + " Runtime: " + finishTime + "ms");

                return ep1;
            }

            if(!closed.contains(ep1)) closed.add(ep1);
            LinkedList<EightPuzzle> neighbors = ep1.getNeighbors();

            while(!neighbors.isEmpty()){
                EightPuzzle ep2 = neighbors.poll();
                if(!closed.contains(ep2)) open.add(ep2);
            }
        }

        return ep;
    }

    //Generate solution path by pushing parents onto stack
    public static Stack<EightPuzzle> genPath(EightPuzzle ep){
        Stack<EightPuzzle> stack = new Stack<EightPuzzle>();

        while(ep.getParent() != null){
            stack.add(ep);
            ep = ep.getParent();
        }
        return stack;
    }

    //Print solution path by popping from stack
    public static void printPath(Stack<EightPuzzle> stk){
        while(!stk.isEmpty()){
            EightPuzzle path = stk.pop();
            path.printPuzzle();
            //System.out.println("f(n) = " + path.totalcost + "\n");
        }
    }

    //Check if puzzle is solvable with inversion
    public static boolean isSolvable(int[] p){
        int total = 0;
        for(int i = 0; i < p.length; i++){
            int count = 0;
            for(int j = i; j < p.length; j++){
                if((p[j] < p[i])&&p[j]!=0){
                    count++;
                }
            }
            total+=count;
        }
        return (total % 2 == 0 ? true:false);
    }

    public static void shuffleArray(int[] a){
        List<Integer> l = new ArrayList<>();
        for(int i : a){
            l.add(i);
        }
        Collections.shuffle(l);

        for(int i = 0; i < l.size(); i++){
            a[i] = l.get(i);
        }
    }
}
