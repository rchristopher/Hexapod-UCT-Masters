syms x y z t L1x L1z L1 L2 L3 L1xc L1zc L2c L3c m1 m2 m3 A B C D E F G H  real
syms theta1 theta2 theta3 theta1_ theta2_ theta3_ theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd


subsOutc = [theta1 theta2 theta3 theta1_d theta2_d theta3_d theta1_dd theta2_dd theta3_dd L1z L1x L2 L3 x y z L1xc L1zc L2c L3c m1 m2 m3 g];
subsInc = [theta_final(1) theta_final(2) theta_final(3) theta_d_final(1) theta_d_final(2) theta_d_final(3) theta_dd_final(1) theta_dd_final(2) theta_dd_final(3) L1z_ L1x_ L2_ L3_ X_(1) X_(2) X_(3) L1xc_ L1zc_ L2c_ L3c_ m_(1) m_(2) m_(3) 9.80665];

digits(10);
double(subs(subs(Tau,subsOutc,subsInc),subsOutc,subsInc))
rad2deg(double(subs(subs(theta_final,subsOutc,subsInc),subsOutc,subsInc)))
%double(subs(subs(theta_d_final,subsOutc,subsInc),subsOutc,subsInc))
%double(subs(subs(theta_dd_final,subsOutc,subsInc),subsOutc,subsInc))