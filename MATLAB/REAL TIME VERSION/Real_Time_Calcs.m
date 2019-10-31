%% Preamble
clc
clear
close all

disp("MATLAB STARTING...");

%% Symbols for all to work
disp("Defining Symbols...")
syms theta1 theta2 theta3 theta1_ theta2_ theta3_ theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd

digits(32);      %number of digits for vpa


%% Variable Declarations
disp("Declaring Variables...")

% Enter desired end effector parameters**********************************
P = [0.153799; 0.153799; -0.0726359];                     %m -- [x, y, z]
P_d = [0.01; 0.01; 0.01; 0; 0.004; 0.01];                   %m/s      ;     rad/s
P_dd = [0; 0; 0; 0; 0; 0];     %m/s^2    ;       rad/s^2

L1z = 0.008;
L1x = 0.05317;
L2 = 0.102;
L3 = 0.152;

L1xc = L1x/2;
L1zc = L1z/2;
L2c = L2/2;
L3c = L3/2;

m = [0.14327;0.04977;0.15456];
g = 9.80665;

%% Inverse Kinematics to find motor theta values
disp("Computing Inverse Kinematics...");

x = P(1);
y = P(2);
z = P(3);
inverse_kinematics;

theta

%% Forward Kinematics
disp("Computing Forward Kinematics...");

forward_kinematics;
vpa(T);


%% Calculate the Jacobian and the derivative of the Jacobian and sub in the foot posision information
disp("Computing The Jacobian Matrix...");

jacobian;

%% Calculate theta dot

theta_d =  J_plus*P_d 

%% Calulate theta double dot

J_d = [   (19*theta_d(3)*sin(theta(2) + theta(3))*sin(theta(1)))/125 - (theta_d(1)*cos(theta(1))*(15200*cos(theta(2) + theta(3)) + 10200*cos(theta(2)) + 5317))/100000 + (theta_d(2)*sin(theta(1))*(15200*sin(theta(2) + theta(3)) + 10200*sin(theta(2))))/100000, theta_d(2)*cos(theta(1))*((19*cos(theta(2) + theta(3)))/125 + (51*cos(theta(2)))/500) + (19*theta_d(3)*cos(theta(2) + theta(3))*cos(theta(1)))/125 - theta_d(1)*sin(theta(1))*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500), (19*theta_d(2)*cos(theta(2) + theta(3))*cos(theta(1)))/125 + (19*theta_d(3)*cos(theta(2) + theta(3))*cos(theta(1)))/125 - (19*theta_d(1)*sin(theta(2) + theta(3))*sin(theta(1)))/125;
 - (theta_d(1)*sin(theta(1))*(15200*cos(theta(2) + theta(3)) + 10200*cos(theta(2)) + 5317))/100000 - (19*theta_d(3)*sin(theta(2) + theta(3))*cos(theta(1)))/125 - (theta_d(2)*cos(theta(1))*(15200*sin(theta(2) + theta(3)) + 10200*sin(theta(2))))/100000, theta_d(2)*sin(theta(1))*((19*cos(theta(2) + theta(3)))/125 + (51*cos(theta(2)))/500) + (19*theta_d(3)*cos(theta(2) + theta(3))*sin(theta(1)))/125 + theta_d(1)*cos(theta(1))*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500), (19*theta_d(1)*sin(theta(2) + theta(3))*cos(theta(1)))/125 + (19*theta_d(2)*cos(theta(2) + theta(3))*sin(theta(1)))/125 + (19*theta_d(3)*cos(theta(2) + theta(3))*sin(theta(1)))/125;
         0,(19*theta_d(3)*sin(theta(2) + theta(3)))/125 + theta_d(2)*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500),                                                                              (19*theta_d(2)*sin(theta(2) + theta(3)))/125 + (19*theta_d(3)*sin(theta(2) + theta(3)))/125;
         0, -theta_d(1)*cos(theta(1)), -theta_d(1)*cos(theta(1));
         0, -theta_d(1)*sin(theta(1)), -theta_d(1)*sin(theta(1));
         0, 0, 0];
 %Jd ends here

theta_dd = J_plus*( P_dd - J_d*theta_d )

%% Dynamics
disp("Computing The Dynamics...");

dynamics;

digits(4);      %number of digits for vpa
Tau_1 = vpa(Tau(1))

Tau_2 = vpa(Tau(2))

Tau_3 = vpa(Tau(3))


