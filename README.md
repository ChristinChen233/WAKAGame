# WAKA Game
Being a cute WAKA, try your best to collect more fruits!

Finally it's your turn to be a smart WAKA, your mission is to eat all fruits and avoid the chase from 4 kinds of ghosts. Don't worry, you will have more than 1 lives! If you are really afraid of the ghost, eat the Soda(Super Fruit), you will have strong ability to kill the ghost!

## Manual
`gradle run`
to start the game.   
Press w a s d to change the direction of WAKA   
The big fruit is Soda   
Avoid being killed (caught by) the ghosts   
Eat all Fruits and you will win!

## Version
gradle 6.5
support dependencies version is stated in dependencies in build.gradle

## Demo video
https://github.com/ChristinChen233/WAKAGame/assets/121276505/361c8849-40e3-43e6-af6d-36f1ec6dafd5

## Description of Design
1. Each kind of elements that appear in the game will be mapped to a single class to manage their behaviors. The instance of GameEngine class is used to handle the interaction between them, while App displays the interactive results in draw() function.
2. The 4 kinds of ghosts (Whim, Chaser, Ambusher, and Ignorant) all have some common behaviors and logic. Differences appear in their target locations of SCATTER and CHASE modes, so the superclass Ghosts is established which defines the whole moving algorithm of ghosts as well as the interaction with the player(WAKA), and the target positions will be re-specified in child classes (Whim...)

Notes:  all files included config1.json to config3.json "and "all map.txt files " are for test cases.

