# Irrgarten - Labyrinth Survival Game (Java)

Irrgarten is a turn-based labyrinth survival game built entirely in Java. Developed as a comprehensive showcase of advanced Object-Oriented Programming (OOP) and software engineering principles, this project evolves from basic class structures into a robust Model-View-Controller (MVC) architecture complete with a graphical interface.

This project was developed as part of the Object-Oriented Programming and Design (PDOO) course at the University of Granada, Spain, with the main goal of applying design patterns and software engineering best practices.

## 🚀 Core Architecture & OOP Principles

* **MVC Pattern & Interfaces:** The system strictly decouples the game logic (Model) from user interaction (View)[cite: 20]. By implementing a common `UI` interface, the Controller can seamlessly govern either the console-based `TextUI` or the event-driven Swing `GraphicalUI` without modifying a single line of business logic.
* **Generics & Type Safety:** Implements parameterized classes such as `CardDeck<T extends CombatElement>` to manage randomized weapon and shield rewards, demonstrating compile-time safety and advanced static typing constraints.
* **Inheritance & Polymorphism:** Utilizes abstract parent classes (`LabyrinthCharacter` for players/monsters, and `CombatElement` for weapons/shields) to maximize code reuse, enforce method overriding, and enable dynamic binding.
* **Probabilistic Behavior (Fuzzy Logic):** Features a specialized `FuzzyPlayer` class. When a standard player dies and successfully resurrects, they dynamically transform into a `FuzzyPlayer`, replacing deterministic movement and combat with probability-based actions calculated by a `Dice` engine.

## 🎮 Game Mechanics

* **Objective:** Navigate a grid-based labyrinth to reach the exit square while avoiding or engaging static monsters.
* **Combat System:** Moving into a monster's square triggers automatic, turn-based combat lasting up to 10 rounds.
* **Resource Management:** Combat relies on weapons and shields that degrade with use. Winning battles yields randomized health and equipment rewards drawn from the generic card decks.

## 🛠️ Tech Stack & Execution

* **Language:** Java SE
* **GUI:** Java Swing (`JFrame`, `JDialog` for modal inputs)
* **Build Tool:** Maven

**Setup & Execution:**
Since this project uses Maven, you can easily compile and run it from your terminal without needing a specific IDE:

1. Clone the repository:
   
   git clone [https://github.com/your-username/irrgarten-java.git](https://github.com/your-username/irrgarten-java.git)
   
   cd irrgarten-java

3. Build the executable JAR using Maven:
   
   mvn clean package

4. Run the game:

   java -jar target/irrgarten-game-1.0.0.jar
