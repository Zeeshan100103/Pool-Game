PoolGame Assignment 3

Commands to Run the Game

Run: `gradle run` to load default config from resources folder or
`gradle run --args="'insert_config_file_path'"` to load custom config.

Generate the JavaDoc documentation:`gradle javadoc`

List of Features
1. Pockets and More Coloured Balls
2. Difficulty Level
3. Time and Score
4. Undo and Cheat

Design Pattern Used

1. Facade pattern - GameDifficulty interface, GameEasy, GameHard, GameLevel, GameMedium, ReloadJson
2. Memento pattern - UndoConfig interface, Undo, BallParameter, and UndoMemento
3. Observer pattern - UpdateScoreObserver, Score and PoolTable and Game
4. Builder pattern - BallBuilder interface, BallBuilderDirector, BlackBallBuilder, BlueBallBuilder, BrownBallBuilder,
                     GreenBallBuilder, OrangeBallBuilder, PurpleBallBuilder, RedBallBuilder, WhiteBallBuilder, and
                     YellowBallBuilder
5. Factory Build pattern - ConfigFactory interface, BallsConfigFactory, ConfigFactoryRegistry, PocketsConfigFactory, and
                           TableConfigFactory
6. Strategy pattern - BallPocketStrategy interface, GameReset, PocketOnce, Pocketthrice and PocketTwice

* Select Difficulty Level

The game start with default easy level. To change the Level click on the level you want to start
Currently there are 3 level - easy, normal and hard

* Undo Move

To undo to the previous move click on the undo button and the action will revert.
The action will only revert to last move and can't go any back

* Cheat Function

The cheat function work at any level. Just press the corresponding key on keyboard to remove that color ball
R - RED Ball
Y - YELLOW Ball
G - GREEN Ball
B - BROWN Ball
L - BLUE Ball
P - PURPLE Ball
A - BLACK Ball
O - ORANGE Ball


Note - 1) The sample Json files are inside the src/main/resources/ folder. It contains 3 sample json file for 3 different
          level
       2) The command line will print the win and score message when needed as well as will print the path of the json file
          currently in use when changing level to show it is working properly.
       3) The Javadoc can be generated through the gradle javadoc, and it is generated inside the build folder. A copy of it
          has been moved outside build folder for viewing use.



