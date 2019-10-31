    %% Preamble
    clc
    clear
    close all
    
    % 0    deg < theta1 < 90  deg
    % -150 deg < theta2 < 150 deg
    % -150 deg < theta3 < 150 deg

    startpos = [0.05317+0.102;0.0;0.0];
    endpos = [0.05317+0.05;0.0;-0.2];
    
    number_of_points = 1;

    path = path_gen_func(startpos,endpos,number_of_points,0,0);
    
    t = 0:0.1:number_of_points;
    path = 0.1*[(6.*t-3).*cos(2.*(6.*t-3)); sin(3.*(6.*t-3)) ; cos(3-6.*t) ]
    
    figure
    plot3(path(1,:), path(2,:), path(3,:))
    
    
    s = size(path);
    speed = zeros(6,s(2));
    acc = zeros(6,s(2));

    [theta, theta_d, theta_dd, Tau] = hex_motion_func(path, speed, acc)

    %% Plot theta values
    f1 = figure(5);
    hold on;
    
    grid on;
    grid minor;
    plot(t,theta(1,:),'LineWidth',1.5)
    title("Theta");
    xlabel("Time (s)");
    ylabel("Theta (rad)");
    
    plot(t,theta(2,:), 'LineWidth',1.5)

    plot(t,theta(3,:), 'LineWidth',1.5)

    legend("Theta 1", "Theta 2", "Theta 3");
 
    hold off;
    
     %% Plot torque values
    f1 = figure(7);
    hold on;
    
    grid on;
    grid minor;
    plot(t,Tau(1,:),'LineWidth',1.5)
    title("Tau");
    xlabel("Time (s)");
    ylabel("Tau (Nm)");
    
    plot(t,Tau(2,:), 'LineWidth',1.5)
    title("Tau 2");

    plot(t,Tau(3,:), 'LineWidth',1.5)
    title("Tau 3");

    legend("Tau 1", "Tau 2", "Tau 3");
 
    hold off;
    
    %% Plot link layout
%     f2 = figure(2);
%     hold on;
%     grid on;
%     grid minor;
%     axis([0 0.31 -0.27 0.27]);
%     xlabel("x axis link positions (y=0)");
%     ylabel("z axis link positions");
%     L1z = 0.008;
%     L1x = 0.05317;
%     L2 = 0.102;
%     L3 = 0.152;
%     
%     CM = lines(s(2));
%     
%     for i=1:s(2)
%         L1_line(i) = line([0 L1x], [0 L1z], 'LineWidth', 1.5, 'Color', 'black');
%         L2_line(i) = line([L1x (L2*cos(theta(2,i))+L1x )], [L1z (L2*sin(theta(2,i))+L1z)], 'Color', CM(i,:), 'LineWidth',1.5);
%         L3_line(i) = line( [(L2*cos(theta(2,i))+L1x ), ( L2*cos(theta(2,i))+L1x + L3*cos(theta(3,i)+theta(2,i)) )], [(L2*sin(theta(2,i))+L1z), ( L2*sin(theta(2,i))+L1z + L3*sin(theta(3,i)+theta(2,i)) )], 'Color', CM(i,:), 'LineWidth',1.5);
%     end 
%     
%     hold off
%         