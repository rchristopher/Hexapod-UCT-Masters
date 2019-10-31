%parameters of b (conditions vector)
%syms t0 tf t q1_0 q2_0 q3_0 q1_1 q2_1 q3_1 M b1 b2 b3 a1 a2 a3
clc;
clear;
close all;

Pe = [0.1 0 -0.1; 0.15 0 -0.05; 0.2 0 -0.1 ];

s = size(Pe);

% [t,q1,q2,q3,v1,v2,v3,a1,a2,a3] = Polynomial_path_gen_func(Pe);
%[t,q1,q2,q3,v1,v2,v3,a1,a2,a3] = Trigonometric_path_gen_func(Pe,0.1,0);
%[t,q1,q2,q3,v1,v2,v3,a1,a2,a3] = Trap_path_gen_heu_func(Pe);
[t,x,y,z,v1,v2,v3,a1,a2,a3] = Polynomial_path_gen_func_xyz(Pe, 4);



figure(1);
hold on;
grid on;
grid minor;
title('Positions');
plot(t,x);
plot(t,y);
plot(t,z);
legend('q1','q2','q3');
hold off;
% 
% figure(2);
% title('Velocities');
% hold on;
% grid on;
% grid minor;
% plot(t,v1(1:end-s(1)+1));
% % plot(t,v2(1:end-2));
% %plot(t,v3(1:end-1));
% legend('v1','v2','v3');
% hold off;
% 
% % figure(3);
% % hold on;
% % grid on;
% % grid minor;
% % title("Accelerations");
% % plot(t,a1(1:end));
% % plot(t,a2(1:end));
% % plot(t,a3(1:end));
% % legend('a1','a2','a3');
% % hold off;
% 
% % L1z = 0.008;
% % L1x = 0.05317;
% % L2 = 0.102;
% % L3 = 0.152;
% 
% % x = (cos(q1).*(15200.*cos(q2 + q3) + 10200.*cos(q2) + 5317))/100000;
% % y = (sin(q1).*(15200.*cos(q2 + q3) + 10200.*cos(q2) + 5317))/100000;
% % z = (19.*sin(q2 + q3))/125 + (51.*sin(q2))/500 + 1/125;
% % 
% % figure(4);
% % plot(x,z);
% % title("Foot Tip Position Plot");
% % xlabel("X");
% % ylabel("Z");
% % grid on;
% % grid minor;
% % axis auto;
% %pbaspect([1 1 1]);
