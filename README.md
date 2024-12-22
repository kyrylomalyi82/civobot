# CIVOBOT

**CIVOBOT** is a powerful Discord bot designed to facilitate multiplayer games of **Civilization** by managing game sessions, assigning civilizations to players, and generating game environments dynamically. Built with Spring Boot and Discord4J, CIVOBOT leverages the flexibility of modern Java technologies to create an interactive and scalable bot for gamers.

## Features

- **Game Session Management**: Start and manage multiplayer Civilization game sessions.
- **Civilization Assignment**: Randomly assigns civilizations to players with customizable pick limits.
- **World Generation**: Provides different world generation options for the game.
- **Multilingual Support**: Supports multiple languages including English, German, Russian, Spanish, Chinese, and Ukrainian, with dynamic language switching.
- **Seamless Discord Integration**: Built using **Discord4J** for real-time interaction with users on Discord servers.

## Key Skills Demonstrated

1. **Backend Development**: Expertise in Spring Boot to manage bot functionality, game sessions, and user interactions.
2. **API Integration**: Integration with Discord’s API using Discord4J to handle events and commands in real time.
3. **Randomization Algorithms**: Developed algorithms to randomly assign civilizations and world generations to players.
4. **Localization & Internationalization**: Implemented multi-language support to cater to diverse global users.
5. **Reactive Programming**: Used **Reactor** for efficient handling of asynchronous event-driven operations.
6. **Game Logic**: Built game session handling logic for multiplayer games, ensuring smooth user experiences with dynamic game data.
7. **Version Control**: Utilized Git and GitHub for version control and collaborative development.

## Technologies Used

- **Java**: Primary programming language used for backend development.
- **Spring Boot**: Framework for building the application and managing game sessions.
- **Discord4J**: Library for integrating the bot with Discord.
- **Reactor**: Library for reactive programming, handling asynchronous operations.
- **Lombok**: Used to reduce boilerplate code for getters, setters, and other repetitive tasks.
- **Maven**: Dependency management and project build system.


## Command Reference

!startgame: Starts a new game session.
!players @player1 @player2 ...: Add players to the game.
!picks <number>: Set the number of civilization picks (1 to 5).
!setlang <language>: Change the bot's language (e.g., en, de, es, ru, ua, cn).
!help: Displays a list of available commands (could be added as a future feature).

Example Usage
Start a game:

- !startgame

-Add players to the game:
!players @Player1 @Player2 @Player3

-Set the number of civilization picks:
!picks 3

-Set the bot language to Spanish:
!setlang es
Future Features


Feel free to reach out if you have questions or want to contribute to the project:

Email: [kyrylomalyi82@gmail.com]
GitHub: [https://github.com/kyrylomalyi82]




