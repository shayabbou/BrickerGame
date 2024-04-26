## BrickerGame

Welcome to Bricker Game! It's a classic brick-breaker game with a twist.
Each brick has a different collision strategy. You, as the player, have three lives
to break as many bricks as you can without losing the ball.


## Game Rules

- The player starts with three lives, a main ball, and a paddle.
- Use the main paddle to bounce the main ball and break bricks.
- Losing a life occurs when the ball falls below the paddle.
- Gain extra lives by destroying bricks with heart strategy and catching the falling
hearts with the main paddle.
- Complete each level by breaking all bricks without losing all lives.
- The game ends when all lives are lost or all levels are completed.
- The default layout is 8 bricks in a row and 7 in a column, but you can change
 it using the command line arguments "<cols> <rows>".


## Usage

- Use the left and right arrow keys to move the paddle.
- Break bricks by bouncing the ball off the paddle and into them.
- Keep the ball in play to prevent losing lives.
- Collect power-ups to gain advantages or extra lives.


## Features - Brick Strategies

- Break and disappear.
- Create a puck ball - white balls that can fall below the paddle without losing a life.
- Create an extra paddle - a higher paddle that disappears after 4 collisions with the puck or main ball.
- Change the camera location - keeps the main ball centered.
- Create extra lives - hearts fall from bricks with extra live strategy.
- Double strategies - bricks can have two strategies from the mention strategies above. .


## Installation

To play BrickGame, follow these steps:
1. Fork the Repository: Click on the "Fork" button in the upper-right corner of the repository's page. This action will create a copy of the repository in your GitHub account.
2. Clone this repository to your local machine using `git clone https://github.com/shayabbou/BrickerGame.git`.
3. Navigate to the repository directory by running `cd BrickerGame`.
4. Compile all Java files in the directory using a Java compiler.
   You can use the wildcard (*) to compile all Java files at once:
   ```bash
   javac *.java
4. In order to run the game - 'java BrickerGameManager'.
5. Start playing and enjoy!

##VideoLink

https://www.loom.com/share/c5f44c74d5cb4d2fb67b1ded64ea01ed?sid=72ef9f91-f07d-40d3-a694-96bfdcc1186b
