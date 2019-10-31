%% Preamble
clc
clear
close all

disp("MATLAB STARTING...");

%% Symbols for all to work
disp("Defining Symbols...")
% syms x y z x_dd y_dd z_dd L1x L1z L1 L2 L3 L1xc L1zc L2c L3c m1 m2 m3 g real
% syms theta1 theta2 theta3 theta1_ theta2_ theta3_ theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd
syms m1 m2 m3 L1x L1z L2 L3 L1xc L1zc L2c L3c g x y z xd yd zd xdd ydd zdd th1 th2 th3 th1d th2d th3d th1dd th2dd th3dd real

digits(32);      %number of digits for vpa


%% Variable Declarations
disp("Declaring Variables...")

X_ = [0.05317+0.102; 0; 0.008-0.152];                       %m -- [x, y, z]
X_d_ = [0.1; 0.0; 0.0; 0; 0.0; 0.0];                  %m/s      ;     rad/s
X_dd_ = [0.0; 0.0; 0.0; 0; 0; 0];                           %m/s^2    ;       rad/s^2

L1z_ = 0.008;
L1x_ = 0.05317;
L2_ = 0.102;
L3_ = 0.152;

L1xc_ = L1x/2;
L1zc_ = L1z/2;
L2c_ = L2/2;
L3c_ = L3/2;

m_ = [0.14327;0.04977;0.15456];

%% Inverse Kinematics to find motor theta values
disp("Computing Inverse Kinematics...");

inverse_kinematics;

theta_final = (double(subs(theta, [x y z L1z L1x L2 L3], [X_(1) X_(2) X_(3) L1z_ L1x_ L2_ L3_])))

%% Forward Kinematics
disp("Computing Forward Kinematics...");

forward_kinematics;
% T = (subs(T, [L1z L1x L2 L3 theta1 theta2 theta3], [L1z_ L1x_ L2_ L3_ theta(1) theta(2) theta(3)]))


%% Calculate the Jacobian and the derivative of the Jacobian and sub in the foot posision information
disp("Computing The Jacobian Matrix...");

jacobian;

%% Calculate theta dot

% theta_d_final =  J_plus*X_d_ 
% theta_d = theta_d_final;

%% Calulate theta double dot

% J_d = [   (19*theta_d(3)*sin(theta(2) + theta(3))*sin(theta(1)))/125 - (theta_d(1)*cos(theta(1))*(15200*cos(theta(2) + theta(3)) + 10200*cos(theta(2)) + 5317))/100000 + (theta_d(2)*sin(theta(1))*(15200*sin(theta(2) + theta(3)) + 10200*sin(theta(2))))/100000, theta_d(2)*cos(theta(1))*((19*cos(theta(2) + theta(3)))/125 + (51*cos(theta(2)))/500) + (19*theta_d(3)*cos(theta(2) + theta(3))*cos(theta(1)))/125 - theta_d(1)*sin(theta(1))*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500), (19*theta_d(2)*cos(theta(2) + theta(3))*cos(theta(1)))/125 + (19*theta_d(3)*cos(theta(2) + theta(3))*cos(theta(1)))/125 - (19*theta_d(1)*sin(theta(2) + theta(3))*sin(theta(1)))/125;
%  - (theta_d(1)*sin(theta(1))*(15200*cos(theta(2) + theta(3)) + 10200*cos(theta(2)) + 5317))/100000 - (19*theta_d(3)*sin(theta(2) + theta(3))*cos(theta(1)))/125 - (theta_d(2)*cos(theta(1))*(15200*sin(theta(2) + theta(3)) + 10200*sin(theta(2))))/100000, theta_d(2)*sin(theta(1))*((19*cos(theta(2) + theta(3)))/125 + (51*cos(theta(2)))/500) + (19*theta_d(3)*cos(theta(2) + theta(3))*sin(theta(1)))/125 + theta_d(1)*cos(theta(1))*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500), (19*theta_d(1)*sin(theta(2) + theta(3))*cos(theta(1)))/125 + (19*theta_d(2)*cos(theta(2) + theta(3))*sin(theta(1)))/125 + (19*theta_d(3)*cos(theta(2) + theta(3))*sin(theta(1)))/125;
%          0,(19*theta_d(3)*sin(theta(2) + theta(3)))/125 + theta_d(2)*((19*sin(theta(2) + theta(3)))/125 + (51*sin(theta(2)))/500),                                                                              (19*theta_d(2)*sin(theta(2) + theta(3)))/125 + (19*theta_d(3)*sin(theta(2) + theta(3)))/125;
%          0, -theta_d(1)*cos(theta(1)), -theta_d(1)*cos(theta(1));
%          0, -theta_d(1)*sin(theta(1)), -theta_d(1)*sin(theta(1));
%          0, 0, 0];
 %Jd ends here

% theta_dd_final = J_plus*( X_dd_ - J_d*theta_d )

%% Dynamics
disp("Computing The Dynamics...");

dynamics;

%subsOutc = [theta1 theta2 theta3 theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd L1z L1x L2 L3 x y z L1xc L1zc L2c L3c m1 m2 m3 g];
%subsInc = [theta_final(1) theta_final(2) theta_final(3) theta_d_final(1) theta_d_final(2) theta_d_final(3) theta_dd_final(1) theta_dd_final(2) theta_dd_final(3) L1z_ L1x_ L2_ L3_ X_(1) X_(2) X_(3) L1xc_ L1zc_ L2c_ L3c_ m_(1) m_(2) m_(3) 9.80665];

subsOutc = [L1z L1x L2 L3 L1xc L1zc L2c L3c m1 m2 m3 g];
subsInc = [L1z_ L1x_ L2_ L3_ L1xc_ L1zc_ L2c_ L3c_ m_(1) m_(2) m_(3) -9.80665];


Tau = subs(Tau,subsOutc,subsInc);
Tau = subs(Tau,subsOutc,subsInc);
Tau = subs(Tau,subsOutc,subsInc);

digits(4);      %number of digits for vpa
Tau_1_subs = vpa( Tau(1) )

Tau_2_subs = vpa( Tau(2) )

Tau_3_subs = vpa( Tau(3) )

disp("MATLAB FINISHED");

%tester_file;
