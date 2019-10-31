    clear;
    close all;
    clc;
    
    q1tq=[];
    v1tq=[];
    a1tq=[];

    tt=[];
    
    q = [0.2, 0.3, 0.2];
    v = [0, 0, 0];
    a = [0, 0, 0];
    
    s = size(q);
    
    a=[0,0,0,0,0,0,0];
    
    t0 = 0;
    tf = 2;
    
    a(1) = q(1);
    a(2) = 0;
    a(3) = 0;
    a(4) = (2/(tf^3))*(32*(q(2)-q(1)) - 11*(q(3)-q(1)));
    a(5) = -(3/(tf^4))*(64*(q(2)-q(1)) - 27*(q(3)-q(1)));
    a(6) = (3/(tf^5))*(64*(q(2)-q(1)) - 30*(q(3)-q(1)));
    a(7) = -(32/(tf^6))*(2*(q(2)-q(1)) - 1*(q(3)-q(1)));
      


        t = linspace(t0,tf,100); %time vector (linearly spaced)
      
        %position, velocity and acceleration for joint 1
        q1tq =  a(1) + a(2).*t+a(3).*t.^2+a(4).*t.^3+a(5).*t.^4+a(6).*t.^5+a(7).*t.^6;
        v1tq =  a(2) +2*a(3).*t+3*a(4).*t.^2+4*a(5).*t.^3+5*a(6).*t.^4+6*a(7).*t.^5;
        a1tq =  2*a(3) + 6*a(4).*t+12*a(5).*t.^2+20*a(6).*t.^3+30*a(7).*t.^4;    

        tt = [tt, t];


       
   %     v1tq = [v1tq(1:100) (v1tq(103:202)-12)];
    
figure(1);
axis([-0.5 2.5 0.18 0.32 ]);
hold on;
grid on;
grid minor;
title('Position');
xlabel('Time (s)');
ylabel('Position');
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

plot(tt,v1tq, 'Color', 'blue');

hold off;

 figure(3);
 hold on;
 grid on;
 grid minor;
 title('Acceleration');
 axis([-0.5 2.5 -0.7 0.7 ]);
 
 plot(tt,a1tq, 'Color', 'blue');

 hold off;