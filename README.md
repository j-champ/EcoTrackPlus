# EcoTrack+
A Java-based desktop application that helps users track their carbon footprint, monitor weekly carbon budget, visualize analytics, and maintain persistent user profiles. The application demonstrates object-oriented principles, Swing GUI, JavaFX charts, file-based storage, and multithreading.

## Features

### User Authentication
- Login and registration using file-based storage  
- Per-user directories containing:  
  - credentials.txt  
  - profile.txt  
  - activity_history.csv  
  - badges.txt  
- Passwords stored as hashes  
- User data is restored automatically on login  

### Activity Tracking
Supports logging:
- Travel activities (Car, Bus, Train, Bike, Walk)  
- Food activities (Veg, Non-Veg, Vegan)  
- Energy usage (Electricity consumption)

Each activity updates:
- CO₂ emission calculation  
- Weekly carbon budget  
- EcoScore  
- User history and badges  

### Analytics Dashboard
Implemented using JavaFX embedded inside Swing:
- Pie chart for emissions by category  
- Bar chart for category comparison  
- Line chart showing emission trends  

### Weekly Carbon Budget
- Default weekly limit: 60 kg CO₂  
- Visual budget indicator  
- Warning levels (High, Medium, Low)  
- Automatic weekly reset using a background thread  

### EcoScore System
Score updates based on emission levels:
- Very low emission → +5  
- Low → +3  
- Medium → +1  
- High → -1  

### Badge System
Awards badges based on user progress:
- Green Beginner  
- Eco Warrior  
- Carbon Saver  

Badges are restored per user and saved persistently.

## Architecture Overview

### Model Layer
Contains core business logic:
- Activity classes (Travel, Food, Energy, RestoredActivity)  
- CarbonBudget and BudgetExceededException  
- EcoScoreEngine and ScoreHistory  
- Badge system (Badge, BadgeManager)  
- User system (User, UserManager)  
- HistoryManager  
- Multithreading (BudgetMonitorTask, WeeklyResetThread)

### Controller Layer
- ActivityController: handles activities, budget, score, badges, and persistence  
- DashboardController: provides analytics data for charts and the dashboard  

### View Layer
- Swing UI: LoginForm, RegisterForm, Dashboard, AddActivityForm, BadgeViewer  
- JavaFXAnalytics: chart visualisation  
- SwingFXPanelBridge: integrates JavaFX into Swing  

## Concepts Demonstrated
This project covers major topics from the Java OOP and Java Programming syllabus:
- Classes, Objects, Inheritance, Polymorphism, Abstraction, Encapsulation  
- Abstract classes, Interfaces, Nested classes  
- Exception handling (custom and built-in)  
- Multithreading (Thread, Runnable, interrupt handling)  
- Java I/O (FileReader, FileWriter, BufferedReader, Path API)  
- Collections (List, Map, ArrayList, HashMap)  
- Generics (List<Badge>, Map<String, Double>)  
- GUI programming (Swing)  
- JavaFX chart rendering  

## Project Structure
src/main/
controller/
model/
activity/
badges/
budget/
history/
score/
threads/
user/
view/
swing/
javafx/
MainApp.java
Jasmitha V  
EcoTrack+ Development Project
