figure(1)
hold on

grid on
grid minor
title('Theta 3 acceleration for falling end effector')
xlabel('Theta3 (rad)') % x-axis label
ylabel('Theta3 ddot (rad/s^2)') % y-axis label
axis([0 pi -150 150])

t = pi/512:pi/32:pi;

theta_3_dd = ((g/(0.00749*))*cos(t));
plot(t,theta_3_dd)





