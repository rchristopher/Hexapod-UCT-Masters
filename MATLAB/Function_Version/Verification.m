%% Preamble
clc
clear
close all

%%
L1z = 0.008;
L1x = 0.05317;
L2 = 0.102;
L3 = 0.152;
lengths = [L1z, L1x, L2, L3];
com_lengths = [L1z/2 L1x/2 L2/2 L3/2];

L = 0.12554;
Pos = [0.28371;0;-0.144];

LEG = deg2rad( 0 );    %-1 for relative to leg coordinate system

Vel = [0; 0; 0.0; 0; 0.0; 0.0];                  %m/s      ;     rad/s
Acc = [0.0; 0.0; 0.00; 0.0; 0.0; 0.0];                           %m/s^2    ;       rad/s^2

theta =  (inverse_kinematics_func(Pos,lengths,-1,LEG))           %-1 for elbow up

T = forward_kinematics_func(theta,lengths)

[J, theta_d, theta_dd] = jacobian_func(lengths, theta, Vel, Acc)

contact_force = 0*transpose(J)*[  0; 0; 10 ; 0; 0; 0 ];    %10N vertically up (ground force)

Tau = dynamics_func(com_lengths, lengths, theta, theta_d, theta_dd, contact_force)


rad2deg(theta)


%% Plotting
figure(1);
hold on;
grid on;
grid minor;
axis([0 0.31 -0.27 0.27]);

L1_line = line([0 L1x], [0 L1z], 'LineWidth',3);
L2_line = line([L1x (L2*cos(theta(2))+L1x )], [L1z (L2*sin(theta(2))+L1z)], 'Color', 'red', 'LineWidth',3);
L3_line = line( [(L2*cos(theta(2))+L1x ), ( L2*cos(theta(2))+L1x + L3*cos(theta(3)+theta(2)) )], [(L2*sin(theta(2))+L1z), ( L2*sin(theta(2))+L1z + L3*sin(theta(3)+theta(2)) )], 'Color', 'blue', 'LineWidth',3);

hold off;

figure(2);
hold on;
grid on;
grid minor;
axis([-0.4 0.4 -0.05 0.4]);
    
t=linspace(-pi/2,pi/2,15)

r=0.053
x = [0.05317*sin(t), 0.1*sin(t),0.15*sin(t), 0.2*sin(t),0.25*sin(t), 0.3072*sin(t)];
y = [0.05317*cos(t), 0.1*cos(t),0.15*cos(t), 0.2*cos(t),0.25*cos(t), 0.3072*cos(t)];
pbaspect([0.8/0.45 1 1])
scatter(x,y,20, 'filled', 'MarkerEdgeColor','red','MarkerFaceColor','red');

x = 0.05317*sin(t);
y = 0.05317*cos(t);
plot(x,y, 'Color','blue','LineWidth',2);

x = 0.3072*sin(t);
y = 0.3072*cos(t);
plot(x,y, 'Color','blue','LineWidth',2);

x = [0.05317, 0.3072];
y = [0, 0];
plot(x,y, 'Color','blue','LineWidth',2);

x = [-0.05317, -0.3072];
y = [0, 0];
plot(x,y, 'Color','blue','LineWidth',2);

hold off;