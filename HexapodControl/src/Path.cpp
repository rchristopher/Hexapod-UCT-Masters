/**
 * @file Path.cpp
 * @author Ross Christopher (CHRROS005)
 * @date
 * @brief Path class functions.
 **/

#include "Path.h"

Path::Path(void){

}

Path::~Path(void){

}

void Path::makePath(){	//XYZ

	// X variables
	double xi = this->startPoint.p1;
	double xv = this->midPoint.p1;
	double xf = this->endPoint.p1;
	double ax0 = this->startPoint.p1;
	double ax1 = 0;
	double ax2 = 0;
	double ax3 = 	(2/	(tf*tf*tf))*			(32*(xv - xi) - 11*	(xf - xi));
	double ax4 = -	(3/	(tf*tf*tf*tf))*			(64*(xv - xi) - 27*	(xf - xi));
	double ax5 = 	(3/	(tf*tf*tf*tf*tf))*		(64*(xv - xi) - 30*	(xf - xi));
	double ax6 = -	(32/(tf*tf*tf*tf*tf*tf))*	(2*	(xv - xi) - 1*	(xf - xi));

	// Y variables
	double yi = this->startPoint.p2;
	double yv = this->midPoint.p2;
	double yf = this->endPoint.p2;
	double ay0 = this->startPoint.p2;
	double ay1 = 0;
	double ay2 = 0;
	double ay3 = 	(2/	(tf*tf*tf))*			(32*(yv - yi) - 11*	(yf - yi));
	double ay4 = -	(3/	(tf*tf*tf*tf))*			(64*(yv - yi) - 27*	(yf - yi));
	double ay5 = 	(3/	(tf*tf*tf*tf*tf))*		(64*(yv - yi) - 30*	(yf - yi));
	double ay6 = -	(32/(tf*tf*tf*tf*tf*tf))*	(2*	(yv - yi) - 1*	(yf - yi));

	// Z variables
	double zi = this->startPoint.p3;
	double zv = this->midPoint.p3;
	double zf = this->endPoint.p3;
	double az0 = this->startPoint.p3;
	double az1 = 0;
	double az2 = 0;
	double az3 = 	(2/	(tf*tf*tf))*			(32*(zv - zi) - 11*	(zf - zi));
	double az4 = -	(3/	(tf*tf*tf*tf))*			(64*(zv - zi) - 27*	(zf - zi));
	double az5 = 	(3/	(tf*tf*tf*tf*tf))*		(64*(zv - zi) - 30*	(zf - zi));
	double az6 = -	(32/(tf*tf*tf*tf*tf*tf))*	(2*	(zv - zi) - 1*	(zf - zi));

	double t = 0;
	double dt = this->tf/(pathSizeDef-1.0);
	for(int i = 0; i < pathSizeDef; t+=dt, i++){
		pathXYZ[i].p1 = ax6*t*t*t*t*t*t + ax5*t*t*t*t*t + ax4*t*t*t*t + ax3*t*t*t + ax2*t*t + ax1*t + ax0;
		pathXYZ[i].p2 = ay6*t*t*t*t*t*t + ay5*t*t*t*t*t + ay4*t*t*t*t + ay3*t*t*t + ay2*t*t + ay1*t + ay0;
		pathXYZ[i].p3 = az6*t*t*t*t*t*t + az5*t*t*t*t*t + az4*t*t*t*t + az3*t*t*t + az2*t*t + az1*t + az0;

		pathXYZ_dot[i].p1 = (6*ax6*t*t*t*t*t + 5*ax5*t*t*t*t + 4*ax4*t*t*t + 3*ax3*t*t + 2*ax2*t + ax1);
		pathXYZ_dot[i].p2 = (6*ay6*t*t*t*t*t + 5*ay5*t*t*t*t + 4*ay4*t*t*t + 3*ay3*t*t + 2*ay2*t + ay1);
		pathXYZ_dot[i].p3 = (6*az6*t*t*t*t*t + 5*az5*t*t*t*t + 4*az4*t*t*t + 3*az3*t*t + 2*az2*t + az1);
	}

	/** SUPPORT PHASE GENERATION **/
	for(int i = pathSizeDef, j = pathSizeDef-2; i < pathSizeDef*2-2; i++, j--){
		pathXYZ[i].p1 = pathXYZ[j].p1;
		pathXYZ[i].p2 = pathXYZ[j].p2;
		pathXYZ[i].p3 = pathXYZ[0].p3;

		pathXYZ_dot[i].p1 = pathXYZ_dot[pathSizeDef/2+1].p1;
		pathXYZ_dot[i].p2 = pathXYZ_dot[pathSizeDef/2+1].p2;
		pathXYZ_dot[i].p3 = pathXYZ_dot[pathSizeDef/2+1].p3;//j was pathSizeDef/2
	}
}


void Path::setPoints(double radialDistance, double direction, double strideLength, double stepHeight, double bodyHeight, uint16_t rotation, double slopePitch, double slopeRoll){		//one leg at a time

	if(direction > -10){
		direction -= M_PI;
		strideLength*=2;

		double startDeltaH = (strideLength/2.0)*(tan(slopePitch)*cos(direction) + tan(slopeRoll)*sin(direction));
		double endDeltaH   = -(strideLength/2.0)*(tan(slopePitch)*cos(direction) + tan(slopeRoll)*sin(direction));

		this->startPoint.p1 = radialDistance*cos(rotation*M_PI/180.0)+(strideLength/2.0)*cos(direction);
		this->startPoint.p2 = -(radialDistance*sin(rotation*M_PI/180.0)+(strideLength/2.0)*sin(direction));
		this->startPoint.p3 = -bodyHeight + startDeltaH;

		this->midPoint.p1 = radialDistance*cos(rotation*M_PI/180.0);
		this->midPoint.p2 = -(radialDistance*sin(rotation*M_PI/180.0));
		this->midPoint.p3 = -bodyHeight+stepHeight;

		this->endPoint.p1 = radialDistance*cos(rotation*M_PI/180.0)-(strideLength/2.0)*cos(direction);
		this->endPoint.p2 = -(radialDistance*sin(rotation*M_PI/180.0)-(strideLength/2.0)*sin(direction));
		this->endPoint.p3 = -bodyHeight + endDeltaH;

		this->makePath();
	}
}
