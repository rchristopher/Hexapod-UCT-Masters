/**
 * @file Path.h
 * @author Ross Christopher (CHRROS005)
 * @date
 * @brief Path class
 **/

#ifndef INCLUDE_PATH_H
#define INCLUDE_PATH_H

#include "stm32f4xx.h"
#include "Point.h"
#include "Math.h"

/**
 * 	@brief Path Class
 *
 *	@details
 *
 **/
class Path{

private:
	/** @brief A point object holding the starting points for the legs path. **/
	Point<double> startPoint;
	/** @brief A point object holding the middle points for the legs path. **/
	Point<double> midPoint;
	/** @brief A point object holding the end points for the legs path. **/
	Point<double> endPoint;

	/**
	 * 	@brief Generates the path points from the starting, middle and end points.
	 *
	 *	@details
	 *
	 **/
	void makePath();

public:

	/** @brief The path size that is to be generated. **/
	#define pathSizeDef 7		//note also update currentPathPoint initial value in Leg.cpp, THIS MUST BE AN ODD NUMBER

	 /** @brief Integer version of the path size. **/
	const int pathSize = pathSizeDef;

	 /** @brief Final time for path generation.  **/
	double tf = 2;					//special case of tv = tf/2

	/** @brief An array holding the path points as XYZ values. **/
	Point<double> pathXYZ[pathSizeDef*2-2];

	/** @brief An array holding the path velocities as XYZ_dot values. **/
	Point<double> pathXYZ_dot[pathSizeDef*2-2];

	/**
	 * @brief Default constructor for the class.
	 *
	 * @details Class constructor
	 *
	 **/
	Path(void);

	/**
	 * @brief Default destructor for the class.
	 *
	 * @details Class destructor cleans up memory by deleting pointers and lists.
	 *
	 **/
	~Path(void);

	/**
	 * 	@brief Sets the path parameters and generates the path points.
	 *
	 *	@param radialDistance The distance from the center of the Hexapod to the center of the support phase line.
	 *	@param direction The direction of the Hexapod.
	 *	@param strideLength The stride length of each leg.
	 *	@param stepHeight The step height of each leg.
	 *	@param bodyHeight The body height of the Hexapod.
	 *	@param slopePitch The pitch of the slope the Hexapod is walking on.
	 *	@param slopeRoll The roll of the slope the Hexapod is walking on.
	 *	@param rotation The rotation of the leg relative to leg 1.
	 *
	 *	@details
	 *
	 **/
	void setPoints(double radialDistance, double direction, double strideLength, double stepHeight, double bodyHeight, uint16_t rotation, double slopePitch, double slopeRoll);

};

#endif
