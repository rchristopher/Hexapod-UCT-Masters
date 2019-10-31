%% Preamble
clc
clear
close all

%% Symbols for all to work
syms x y z theta1 theta2 theta3 L1x L1z L1 L2 L3 L1xc L1zc L2c L3c real

%% Canonical Transformation Matrices

forward_kinematics;

%% Inverse Kinematics to find motor theta values

inverse_kinematics;

%% Calculate the Jacobian and the derivative of the Jacobian and sub in the foot posision information

jacobian;

%% Dynamics

dynamics;

%% Calculate theta dot

%theta_d = double( J_plus*X_d )

%% Calculate omega of end effector

syms theta1_d theta2_d theta3_d

theta_d = [theta1_d;theta2_d;theta3_d];

X = simplify( J*theta_d );

X_w = X(4:6,:)

%% Calulate theta double dot

%theta_dd = J_MP*( X_dd - J_d*theta_d )

%% Calculate the forces on the foot from the motor torques

%W = inv(J')*T                          %forces on foot [x;y;z] in N

%% Input Variables

L1z_ = 0.008;
L1x_ = 0.05317;
L2_ = 0.102;
L3_ = 0.152;

X = [0.05; 0.05; -0.2];                     %m -- [x, y, z]
X_d = [0.01; 0.01; 0.01;0;0;0];                   %m/s      ;     rad/s
X_dd = [0; 0.0; -0.001; 0.0; 0.0; 0.0];     %m/s^2    ;       rad/s^2

