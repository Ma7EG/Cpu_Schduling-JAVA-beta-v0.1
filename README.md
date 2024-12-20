# CPU Scheduling Algorithms Project

This project contains implementations of several CPU scheduling algorithms for educational purposes. The algorithms included are:

- **Shortest Job First (SJF)**
- **Round Robin (RR)**
- **Priority Non-preemptive (PNP)**

## Requirements

- **Java Development Kit (JDK)** version 8 or higher.
- **IDE** such as **Eclipse** or **IntelliJ IDEA**, or you can compile and run the project directly from the command line.

## Getting Started

Follow these steps to run the project on your local machine:

### Step 1: Clone the Repository

You need to clone the repository to your local machine. Open a terminal or command prompt and run:

```bash
git clone https://github.com/Ma7EG/Cpu_Schduling-JAVA-beta-v0.1

## Step 2: Import into IDE
Open your preferred IDE (e.g., Eclipse, IntelliJ IDEA).
Import the cloned project into the IDE:
In Eclipse, go to File > Import > Existing Projects into Workspace, and select the project folder.
In IntelliJ IDEA, select File > Open, then navigate to the project folder and open it.
Step 3: Run the Project
To run the project:

Open the main class file: The project contains different classes for each scheduling algorithm. Make sure you run the main class for the algorithm you want to test.

Execute the program:

In Eclipse, right-click on the main class file and select Run As > Java Application.
In IntelliJ IDEA, click on the green run button (▶) in the top-right corner.
Step 4: User Interface
The project provides a GUI (Graphical User Interface) for interacting with the algorithms. You can enter the following parameters:

Burst Time: The time required for a process to complete.
Priority: The priority of a process (for the PNP algorithm).
Quantum: The time slice used for Round Robin scheduling.
Process ID: The unique identifier for each process.
Step 5: Algorithms and Output
Each scheduling algorithm will calculate and display the following information for each process:

Completion Time: When the process finishes.
Turnaround Time: The total time taken from arrival to completion.
Waiting Time: The time the process has been in the ready queue.
Step 6: Modify and Test
Feel free to modify the processes or parameters in the GUI to see how different inputs affect the scheduling.

Contributing
If you'd like to contribute to this project, feel free to fork the repository, make changes, and submit a pull request. Please make sure to test your changes thoroughly before submitting.
