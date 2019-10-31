    clear;
    close all;
    clc;
    
    q1tq=[];
    v1tq=[];
    a1tq=[];
    q1tc=[];
    v1tc=[];
    a1tc=[];
    q1tL=[];
    v1tL=[];
    a1tL=[];
    tt=[];
    

    
    q = [0.2, 0.3, 0.2];
    v = [0, 0, 0];
    a = [0, 0, 0];
    
    s = size(q);
    
    v0 = v(1);  vf = v(3);
    ac0 = a(1); acf = a(3);
    
    syms t0 tf Mq b1q q1_i q1_f
    
        Mq = [1 t0 t0^2 t0^3 t0^4 t0^5;
             0 1 2*t0 3*t0^2 4*t0^3 5*t0^4;
             0 0 2 6*t0 12*t0^2 20*t0^3;
             1 tf tf^2 tf^3 tf^4 tf^5;
             0 1 2*tf 3*tf^2 4*tf^3 5*tf^4;
             0 0 2 6*tf 12*tf^2 20*tf^3];       %quintic
    
        b1q = [q1_i; v0; ac0; q1_f; vf; acf]; a1q = Mq\b1q %quintic
    
    for i=1:1:s(2)-1
    
        t0 = i-1
        tf = i

        t = linspace(t0,tf,100); %time vector (linearly spaced)

        %XYZ Coordinates when t=t0 (LEG1)
        q1_i = q(i);

        %XYZ Coordinates when t=tf (LEG1)
        q1_f = q(i+1);

        Mq = [1 t0 t0^2 t0^3 t0^4 t0^5;
             0 1 2*t0 3*t0^2 4*t0^3 5*t0^4;
             0 0 2 6*t0 12*t0^2 20*t0^3;
             1 tf tf^2 tf^3 tf^4 tf^5;
             0 1 2*tf 3*tf^2 4*tf^3 5*tf^4;
             0 0 2 6*tf 12*tf^2 20*tf^3];       %quintic
    
        b1q = [q1_i; v0; ac0; q1_f; vf; acf]; a1q = Mq\b1q; %quintic

        Mc = [1 t0 t0^2 t0^3;
             0 1 2*t0 3*t0^2;
             1 tf tf^2 tf^3;
             0 1 2*tf 3*tf^2;];       %cubic
         
        b1c = [q1_i; v0; q1_f; vf]; a1c = Mc\b1c; %cubic
         
        ML = [1 t0;
             1 tf;];       %linear         

        b1L = [q1_i; q1_f]; a1L = ML\b1L; %linear

        %position, velocity and acceleration for joint 1
        q1tq = [q1tq, a1q(1) + a1q(2).*t+a1q(3).*t.^2+a1q(4).*t.^3+a1q(5).*t.^4+a1q(6).*t.^5];
        v1tq = [v1tq, a1q(2) +2*a1q(3).*t+3*a1q(4).*t.^2+4*a1q(5).*t.^3+5*a1q(6).*t.^4];
        a1tq = [a1tq, 2*a1q(3) + 6*a1q(4).*t+12*a1q(5).*t.^2+20*a1q(6).*t.^3];    %quintic

        q1tc = [q1tc, a1c(1) + a1c(2).*t+a1c(3).*t.^2+a1c(4).*t.^3];
        v1tc = [v1tc, a1c(2) +2*a1c(3).*t+3*a1c(4).*t.^2];
        a1tc = [a1tc, 2*a1c(3) + 6*a1c(4).*t];    %cubic
        

        q1tL = [q1tL, a1L(1) + a1L(2).*t];
        v1tL = [v1tL, a1L(2)]
        a1tL = zeros(1,200);    %linear        

        tt = [tt, t];

    end
    
        val1 = v1tL(1);
        val2 = v1tL(2);
        v1tL(1:100) = val1;
        v1tL(101:200) = val2;    %linear

        v1tc = [v1tc(1:100) (v1tc(103:202)+1.2)];
        v1tq = [v1tq(1:100) (v1tq(103:202)-12)];
    
figure(1);
axis([-0.5 2.5 0.18 0.32 ]);
hold on;
grid on;
grid minor;
title('Position');
xlabel('Time (s)');
ylabel('Position');
% plot(tt,q1tL, 'Color', 'blue');
% plot(tt,q1tc, 'Color', 'blue');
plot(tt,q1tq, 'Color', 'blue');

plot(0, q(1),'.', 'Color', 'blue')
plot(1, q(2),'.', 'Color', 'blue')
plot(2, q(3),'.', 'Color', 'blue')
hold off;

figure(2);
title('Velocity');
xlabel('Time (s)');
ylabel('Velocity');
axis([-0.5 2.5 -0.2 0.2 ]);
hold on;
grid on;
grid minor;

% plot(tt,v1tL, 'Color', 'red');
% plot(tt,v1tc, 'Color', 'blue');
plot(tt,v1tq, 'Color', 'blue');

hold off;

 figure(3);
 hold on;
 grid on;
 grid minor;
 title('Acceleration');
 axis([-0.5 2.5 -0.7 0.7 ]);
 
 plot(tt,a1tL, 'Color', 'blue');
%  plot(tt,a1tc, 'Color', 'blue');
%  plot(tt,a1tq, 'Color', 'blue');

 hold off;