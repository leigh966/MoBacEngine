# MoBacEngine

## The Idea
The idea of this project is to make an extensible, customizable, open-source game engine for making 2d and 3d games. The primary focus was to make a game engine for Java that would render faux-3d graphics simililar to the original Doom and Quake games

## Project Structure
### Project Root
- / - Open this directory in your editor such as IntelliJ to start working on the project
- /target - Where built materials will be stored, you can also change your load order here to change what class(es) to run when the JAR is launched
- /src - Where all java source code is stored, this is the bulk of what you will be editting
- 
### Main Java Source Code
Path: Project Root/main/java
- /data - This is where any code for a project you are creating will be stored, for most users this will be the only directory with any code that you add/change
- /mobac - This contains the code of the engine itself, it is unlikely you will want to change this unless you are contributing to the MoBacEngine itself

## Prerequisites
You will need the following installed and added to your PATH variable:
- Java
- Maven

## Basic Operations
### Cloning The Directory
Clone: `git clone https://github.com/leigh966/MoBacEngine`

### Building The Solution
Enter Directory: `cd MoBacEngine`

Build: `mvn package`

### Running The Built JAR
Navigate To Target Folder: `cd target`

Run The JAR: `java engine-jar-with-dependencies.jar`

