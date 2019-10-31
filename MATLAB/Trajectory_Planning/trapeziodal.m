close all;
clc;
clear;


ti = 0;

qi = 10;
qf = 30;

qv_d = 10;
ta = 1;

tf = (qf-qi)/qv_d + ta;

t_step = 0.0001;

t = ti:t_step:tf;


t1 = 0:t_step:ta-t_step;
t2 = ta:t_step:(tf-ta)-t_step;
t3 = (tf-ta):t_step:tf;

s = size(t1);


q = [qi + (qv_d/(2*ta)).*t1.^2, qi + qv_d.*(t2-ta/2), qf - ( qv_d/ta ).*( (tf-t3).^2 )./2];

s=size(t2);
qv_d_m = zeros(1,s(2));
qv_d_m(1,:)=qv_d;


q_d = [(qv_d*t1)/ta, qv_d_m,  -(qv_d*t3)/ta + (tf*qv_d)/(ta)]

s=size(t1);
qv_d_m_1 = zeros(1,s(2));
qv_d_m_1(1,:)=qv_d;
s=size(t3);
qv_d_m_3 = zeros(1,s(2));
qv_d_m_3(1,:)=qv_d;

s = size(qv_d_m);

q_dd = [ qv_d_m_1/ta, zeros(s(2)), 2*qv_d_m_3];

figure(1)
hold on;
grid on;
grid minor;
plot(t,q)

figure(2)
hold on;
grid on;
% grid minor;
plot(t,q_d);
 
 
figure(3)
hold on;
grid on;
% grid minor;
plot(q_dd);
% 
% 
% 
% 
