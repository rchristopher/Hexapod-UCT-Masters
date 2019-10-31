%% Preamble
clc
clear
close all

disp("MATLAB STARTING...");

%% Symbols for all to work
disp("Defining Symbols...")
syms x y z L1x L1z L1 L2 L3 L1xc L1zc L2c L3c m1 m2 m3 g real
syms theta1 theta2 theta3 theta1_ theta2_ theta3_ theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd

digits(32);      %number of digits for vpa


%% Variable Declarations
disp("Declaring Variables...")
L1z_ = 0.008;
L1x_ = 0.05317;
L2_ = 0.102;
L3_ = 0.152;

L1xc_ = L1x/2;
L1zc_ = L1z/2;
L2c_ = L2/2;
L3c_ = L3/2;

%X_ = [0.153799; 0.153799; -0.0726359];                     %m -- [x, y, z]
%currently getting this value from forward kinematics T
X_d_ = [0.01; 0.01; 0.01; 0; 0.004; 0.01];                   %m/s      ;     rad/s
X_dd_ = [0; 0; 0; 0; 0; 0];     %m/s^2    ;       rad/s^2

theta_ = deg2rad( [ 45; 30; -90] );

m_ = [0.14327;0.04977;0.15456];



%% Canonical Transformation Matrices
disp("Computing Forward Kinematics...");


forward_kinematics;
T = vpa(subs(T, [L1z L1x L2 L3 theta1 theta2 theta3], [L1z_ L1x_ L2_ L3_ theta_(1) theta_(2) theta_(3)]))

subsOut = [theta1 theta2 theta3 L1z L1x L2 L3 x y z L1xc L1zc L2c L3c m1 m2 m3 g];
subsIn = [theta_(1) theta_(2) theta_(3) L1z_ L1x_ L2_ L3_ T(1,4) T(2,4) T(3,4) L1xc_ L1zc_ L2c_ L3c_ m_(1) m_(2) m_(3) 9.80665];

%% Inverse Kinematics to find motor theta values
disp("Computing Inverse Kinematics...");


inverse_kinematics;

theta = subs(theta, [x y z L1z L1x L2 L3], [T(1,4) T(2,4) T(3,4) L1z_ L1x_ L2_ L3_])


if( (vpa(theta) - vpa(theta_)) < 1e-30)
    disp("***INVERSE & FORWARD KINEMATICS MATCH***");
    disp(" ");
else
    disp("******************************INVERSE & FORWARD KINEMATICS DONT MATCH******************************");
end

%% Calculate the Jacobian and the derivative of the Jacobian and sub in the foot posision information
disp("Computing The Jacobian Matrix...");

jacobian;

%% Dynamics
disp("Computing The Dynamics...");

dynamics;

subsOutc = [theta1 theta2 theta3 L1z L1x L2 L3 x y z L1xc L1zc L2c L3c m1 m2 m3 g];
subsInc = [theta_(1) theta_(2) theta_(3) L1z_ L1x_ L2_ L3_ T(1,4) T(2,4) T(3,4) L1xc_ L1zc_ L2c_ L3c_ m_(1) m_(2) m_(3) 9.80665];

Tau = subs(Tau,subsOutc,subsInc);
Tau = subs(Tau,subsOutc,subsInc);

digits(4);      %number of digits for vpa
Tau_1_subs = vpa(         simplify(collect(simplify((vpa( (collect(Tau(1),[theta_dd theta_d theta])) ) ),'Steps',200),[theta_dd theta_d theta])           )  )

Tau_2_subs = vpa(         simplify(collect(simplify((vpa( (collect(Tau(2),[theta_dd theta_d theta])) ) ),'Steps',200),[theta_dd theta_d theta])           )  )

Tau_3_subs = vpa(         simplify(collect(simplify((vpa( (collect(Tau(3),[theta_dd theta_d theta])) ) ),'Steps',200),[theta_dd theta_d theta])           )  )

%% Calculate theta dot

%theta_d = double( J_plus*X_d )

%% Calculate omega of end effector
% 
% theta_d = [theta1_d;theta2_d;theta3_d];
% 
% X = simplify( J*theta_d );
% 
% X_w = X(4:6,:)

%% Calulate theta double dot

%theta_dd = J_MP*( X_dd - J_d*theta_d )

%% Calculate the forces on the foot from the motor torques

%W = inv(J')*T                          %forces on foot [x;y;z] in N

%% Input Variables



