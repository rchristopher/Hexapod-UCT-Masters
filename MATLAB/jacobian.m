    syms th1d th2d th3d xd yd zd real

    forward_kinematics;
    
    z0 = [0;0;1];
    z1 = [sin(th1);-cos(th1);0];
    z2 = z1;
    
    p0 = [0;0;0];
    p1 = simplify( A_1(1:3,4) );
    p2 = A_1*A_2;
    p2 = simplify( p2(1:3,4) );
    p3 = A_1*A_2*A_3;
    p3 = simplify( p3(1:3,4) );
    
    J = simplify( [cross(z0,(p3-p0)),cross(z1,(p3-p1)),cross(z2,(p3-p2))  ; z0, z1, z2] );
    
           
    J_plus = (inv(transpose(J)*J))*transpose(J);
    
    X = [xd; yd; zd;0;th2d+th3d; th1d];
    
    thd = J_plus*X;
    
    thds = [th1d;th2d;th3d];
    
%     sol = structfun(@simplify,(solve(thd == thds,[th1d th2d th3d])),'Uniform',0);
%    
%     th1d = simplify(sol.th1d,'Steps',100)
%     th2d = simplify(sol.th2d,'Steps',100)
%     th3d = simplify(sol.th3d,'Steps',100)
%     
%     params = [0.053,0.008,0.102,0.153,  0.1,0.4,0.5,    0.001,pi/2-pi/3,0.001];
%     
%     format long
%     
%     th1d_subs = abs(double(subs(th1d,[L1x, L1z, L2, L3, xd, yd, zd, th1, th2, th3],params))*9.549296596425384)
%     th2d_subs = abs(double(subs(th2d,[L1x, L1z, L2, L3, xd, yd, zd, th1, th2, th3],params))*9.549296596425384)
%     th3d_subs = abs(double(subs(th3d,[L1x, L1z, L2, L3, xd, yd, zd, th1, th2, th3],params))*9.549296596425384)
   
    
    