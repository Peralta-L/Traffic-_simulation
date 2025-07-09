# Traffic_simulation
# Developer’s Guide: Traffic Simulation

 Course : CMSC 335  
 Project : Traffic Simulation GUI  
 Student : Luis  
 Date : 08/07/2025  

---

## 1. Program Overview

This JavaFX-based simulation demonstrates a multithreaded approach to modeling urban traffic. Cars dynamically traverse intersections, pausing for red lights and reporting speed and position data. Traffic lights cycle independently, and a digital clock updates every second.

User controls allow:
- Starting, stopping, and continuing the simulation
- Adding/removing cars and intersections
- Viewing traffic light status and vehicle metrics in real time

---

## 2. Compilation & Execution Instructions

### Requirements
- Java Development Kit (JDK) version 8 or later
- JavaFX (pre-installed in JDK 8; modular after JDK 11)
- IDE (IntelliJ IDEA, Eclipse, VS Code) or command line tools

Run: Main.java → Run in IDE.


#	Test Component |Action Taken |	Expected Behavior	
1	Launch program |Start program|	 GUI appears with control panel and layout.
2	Start Button   |Clicked	     |   Cars and lights begin movement; clock updates.
3	Stop Button	   |Clicked	     |   Movement freezes; threads pause.
4	Continue Button|Clicked	     |   Movement resumes from pause.
5	Add Car	Clicked|New car      |   appears with updated labels.
6	Remove Car	   |Clicked	     |   Last car removed, layout updates.
7	Add Light	   |Clicked	     |   New intersection light appears, labeled.
8	Remove Light   |Clicked	     |   Last light removed, panel refreshes.
9	Timer Accuracy |Observed	 |   Clock updates every 1 second.
10	Interactions   |Observed	 |   Car stops at red, resumes on green.
