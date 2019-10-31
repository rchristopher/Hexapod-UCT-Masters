syms th1 th2 th3 L1x L1z L2 L3 real
    
A_1 = [[cos(th1),    0,   sin(th1),    L1x*cos(th1)];
            [sin(th1),    0,   -cos(th1),   L1x*sin(th1)];
             [0,              1,       0,          L1z];
             [0,              0,       0,          1]];



A_2 = [[cos(th2)  -sin(th2)       0       L2*cos(th2)];
             [sin(th2)   cos(th2)       0       L2*sin(th2)];
             [0              0                1           0];
             [0              0                0           1]];



A_3 = [[cos(th3)  -sin(th3)       0       L3*cos(th3)];
             [sin(th3)   cos(th3)       0       L3*sin(th3)];
             [0              0                1           0];
             [0              0                0           1]]; 
    

    
T = simplify( A_1*A_2*A_3);  %correct