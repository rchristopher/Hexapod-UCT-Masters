%% Clear everything
clc
clear
close all

%% Add folders to the path
addpath('Optim','SavedResults',genpath('Robot'),genpath('Libraries'))

%% Open the main model and load parameters
robotParameters
walkingRobot