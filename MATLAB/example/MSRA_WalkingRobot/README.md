# README
### Walking Robot Simulation
### Copyright 2017 The MathWorks, Inc.

## 1. startupDemo
This will set up the search path and open the main model, walkingRobot.slx

## 2. optimizeRobotMotion
This is the main optimization script, which has several options.
   
### a. parallelFlag [true|false]
Use Parallel Computing Toolbox or MATLAB Distributed Computing Server to speed up optimization.
This requires that you have already set up a default parallel computing cluster.

### b. accelFlag [true|false]
Run the simulations in Accelerator mode.
This will turn off visualization with the Mechanics Explorer, but will run faster.

### c. actuatorType [1|2|3]
Different fidelity actuators for simulation. Suggest running optimization with actuatorType = 2.
* 1: Ideal motion actuation
* 2: Torque actuation with PID control
* 3: Electric motor actuation with average-model PWM, H-Bridge, and PID control

## 3. compareActuatorTypes 
Compares simulation results for a trajectory across different actuator types.

## Multiphysics and Contact Libraries
For convenience, local copies of the Simscape Multibody Multiphysics Library and
Simscape Multibody Contact Forces Library have been included with this submission. 
If you would like to install the latest version of these libraries, you can find
them from the Add-On Explorer, or on the File Exchange
* Simscape Multibody Multiphysics Library: https://www.mathworks.com/matlabcentral/fileexchange/37636-simscape-multibody-multiphysics-library
* Simscape Multibody Contact Forces Library: https://www.mathworks.com/matlabcentral/fileexchange/47417-simscape-multibody-contact-forces-library

