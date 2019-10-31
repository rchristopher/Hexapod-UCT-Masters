%% Preamble
clc
clear
close all

%%


syms t;

x = abs(sin(0.5*t));
y = 0*t;
z = 0.5*(sign(sin(t)) + 1)*sin(t);


figure
fplot3(x,y,z,[0 10]);

title('XYZ_T');
set(gca,'XTick',0:pi/4:2*pi) 
set(gca,'XTickLabel',{'0','pi/4','pi/2','3*pi/4','pi','5*pi/4','3*pi/2','7*pi/4','2*pi'}) 
set(gca,'YTick',0:pi/4:2*pi) 
set(gca,'YTickLabel',{'0','pi/4','pi/2','3*pi/4','pi','5*pi/4','3*pi/2','7*pi/4','2*pi'}) 
set(gca,'ZTick',0:pi/4:2*pi) 
set(gca,'ZTickLabel',{'0','pi/4','pi/2','3*pi/4','pi','5*pi/4','3*pi/2','7*pi/4','2*pi'}) 

%%
Fs = 8*pi;                   % samples per second
StopTime = 8*pi;             % seconds
t_ = [0:1/Fs:StopTime-1/Fs];     % seconds

x=subs(x,t,t_);
y=subs(y,t,t_);
z=subs(z,t,t_);

figure;
subplot(3,1,1);
plot(t_,x);
xlabel('time (in seconds)');
title('XT');
set(gca,'XTick',0:pi/4:2*pi) 
set(gca,'XTickLabel',{'0','pi/4','pi/2','3*pi/4','pi','5*pi/4','3*pi/2','7*pi/4','2*pi'}) 
subplot(3,1,2);
plot(t_,y);
xlabel('time (in seconds)');
title('YT');
set(gca,'XTick',0:pi/4:2*pi) 
set(gca,'XTickLabel',{'0','pi/4','pi/2','3*pi/4','pi','5*pi/4','3*pi/2','7*pi/4','2*pi'}) 
subplot(3,1,3);
plot(t_,z);
xlabel('time (in seconds)');
title('ZT');
set(gca,'XTick',0:pi/4:2*pi) 
set(gca,'XTickLabel',{'0','pi/4','pi/2','3*pi/4','pi','5*pi/4','3*pi/2','7*pi/4','2*pi'}) 
