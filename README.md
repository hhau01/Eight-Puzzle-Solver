## Usage
	
To Run:
````
javac EPuzzle.java
java EPuzzle
````
The program will prompt the user with two choices either `1` or `2`:

`1` is to randomly generate an 8-puzzle.

`2` is to enter a user generated 8-puzzle.


Next, enter a configuration. For example:
````
430285617
````
Will give you an 8-puzzle of:
````
4 3 0 
2 8 5 
6 1 7 
````

## Sample
````
Welcome to 8-Puzzle Solver!
(1) Randomly generate 8-puzzle problem
(2) Enter your own
1
Randomly generated puzzle:

4 3 0 
2 8 5 
6 1 7 

Solution using h1 heuristics

4 3 5 
2 8 0 
6 1 7 

4 3 5 
2 0 8 
6 1 7 

4 3 5 
0 2 8 
6 1 7 

0 3 5 
4 2 8 
6 1 7 

3 0 5 
4 2 8 
6 1 7 

3 2 5 
4 0 8 
6 1 7 

3 2 5 
4 1 8 
6 0 7 

3 2 5 
4 1 8 
6 7 0 

3 2 5 
4 1 0 
6 7 8 

3 2 0 
4 1 5 
6 7 8 

3 0 2 
4 1 5 
6 7 8 

3 1 2 
4 0 5 
6 7 8 

3 1 2 
0 4 5 
6 7 8 

0 1 2 
3 4 5 
6 7 8 

Depth: 14 Explored: 3016 Runtime: 12ms

Solution using h2 heuristics

4 3 5 
2 8 0 
6 1 7 

4 3 5 
2 0 8 
6 1 7 

4 3 5 
0 2 8 
6 1 7 

0 3 5 
4 2 8 
6 1 7 

3 0 5 
4 2 8 
6 1 7 

3 2 5 
4 0 8 
6 1 7 

3 2 5 
4 1 8 
6 0 7 

3 2 5 
4 1 8 
6 7 0 

3 2 5 
4 1 0 
6 7 8 

3 2 0 
4 1 5 
6 7 8 

3 0 2 
4 1 5 
6 7 8 

3 1 2 
4 0 5 
6 7 8 

3 1 2 
0 4 5 
6 7 8 

0 1 2 
3 4 5 
6 7 8 

Depth: 14 Explored: 33 Runtime: 3ms
````
