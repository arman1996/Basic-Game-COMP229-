In my implementation of the memento pattern I employed the following classes.
CareTaker.java
Memento.java
Stage.java

Details about Care Taker:
I made a CareTaker class which held a reference to the stored memento called "save".
It initiated the creation of a memento object when a key was pressed.
I did this by allowing it to implement KeyObserver (interface).
I also had to make and register an instance of the CareTaker class inside stage.
This would allow the CareTaker class to be notified when a key was typed.
When SPACE is typed, the createMemento() method within Stage is called.
When R is typed, the setStateFromMemento() method within Stage is called.

Details about Originator (Stage):
My Stage class was my originator.
It was responsible for supplying the information required for me to create
and restore a memento.
I had to create an instance of Memento in order to achieve this.
I had to pass the CareTaker class an instance of stage.
This allowed Stage to process the necessary information, pass it to the Memento class
and return the reference to the CareTaker.
The Stage class also had a method that would restore the state of the game.
The saved reference within the CareTaker would be passed on to the setStateFromMemento()
method within Stage.

Details about Memento:
Memento has a String Array in which it stores the relevant information.
It has a getter and a setter method, that allows for it to return this information.

Overall Design:
So, the CareTaker class only watches for if a key is typed.
It only holds a reference to the actual memento object that is created.

The Stage class is responsible for creating the memento object and passing relevant
information to the Memento class.
The Stage class is also responsible for fetching this stored information and
restoring the state of the game.

The Memento class is responsible for making a deep copy of the information that is
handed to it.

Problem with using this pattern:
The reference of the memento is lost once the game is turned off.
Thus, this is not a good way to sav the state of a game.
However, this pattern is ideal for undo-ing mistakes that may have been made
while the game is running.
