close all;
clc;
clear;


ti = 0;
tf = 2;

qi = 10;
qf = 10;

qi_d = -1;
qf_d = -1;


a0 = qi;
a1 = qi_d;
a2 = ( -3*(qi-qf) - (2*qi_d+qf_d)*(tf-ti) )/(tf-ti)^2;
a3 = ( 2*(qi-qf) + (qi_d+qf_d)*(tf-ti) )/(tf-ti)^3;


t_step = 0.001;

t = ti:t_step:tf;
ti = zeros(size(t));
ti(:) = ti;

q = (a0 + a1.*(t-ti) + a2.*(t-ti).^2 + a3.*(t-ti).^3);
q_d = a1 + 2*a2.*(t-ti) + 3*a3.*(t-ti).^2;
q_dd = 2*a2 + 6*a3.*(t-ti);





figure(1)
hold on;
grid on;
% grid minor;
plot(t,q);

figure(2)
hold on;
grid on;
% grid minor;
plot(t,q_d);


figure(3)
hold on;
grid on;
% grid minor;
plot(t,q_dd);




