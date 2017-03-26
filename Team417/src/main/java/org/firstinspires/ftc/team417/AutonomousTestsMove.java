package org.firstinspires.ftc.team417;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Tests Move", group = "Swerve")
// @Disabled

public class AutonomousTestsMove extends MasterAutonomous
{
    double speed;
    double startAngle;

    public void runOpMode() throws InterruptedException
    {
        // Initialize hardware and other important things
        initializeRobot();

        VuforiaNav.initVuforia();
        telemetry.addData("Path", "Select Team and Pos...");

        // Wait until we're told to go
        while (!isStarted())
        {
            // allow driver to choose a team
            if (gamepad1.b)
            {
                isRedTeam = true;
            }

            if (gamepad1.x)
            {
                isRedTeam = false;
            }

            // select position one or two, one is closer to the origin
            if (gamepad1.y)
            {
                isStartingPosOne = true;
            }
            if (gamepad1.a)
            {
                isStartingPosOne = false;
            }

            if (isRedTeam)
            {
                if (isStartingPosOne)
                {
                    telemetry.addData("Team: ", "Red 1");
                }
                else
                {
                    telemetry.addData("Team: ", "Red 2");
                }
            }
            else
            {
                if (isStartingPosOne)
                {
                    telemetry.addData("Team: ", "Blue 1");
                }
                else
                {
                    telemetry.addData("Team: ", "Blue 2");
                }
            }
            telemetry.update();
            idle();
        }
        telemetry.update();

        if (isRedTeam) // if team RED
        {
            if (isStartingPosOne)
            {
                // OPTION RED ONE (TOOLS)
                startDelay = 2000;
                pivotAngle = 55; // pivot this amount before acquiring target
                targetAngle = 0; // Vuforia angle
                startDist = 2286;
                targetIndex = 1;
                targetPos[0] = 2743.2f;
                targetPos[1] = mmFTCFieldWidth;
                //telemetry.addData("Team: ", "Red 1"); // display what team we're on after choosing with the buttons
            }
            else
            {
                // OPTION RED TWO (GEARS)
                startDelay = 0;
                pivotAngle = 55; // pivot this amount before acquiring target
                targetAngle = 0; // Vuforia angle
                startDist = 1270;
                targetIndex = 3;
                targetPos[0] = 1524;
                targetPos[1] = mmFTCFieldWidth;
                //telemetry.addData("Team: ", "Red 2"); // display what team we're on after choosing with the buttons
            }
            telemetry.update();
        }
        else // if team BLUE
        {
            if (isStartingPosOne)
            {
                // OPTION BLUE ONE (LEGOS)
                startDelay = 2000;
                pivotAngle = -55;
                targetAngle = -90;
                startDist = 2286;
                targetIndex = 2;
                targetPos[0] = mmFTCFieldWidth;
                targetPos[1] = 2743.2f;
                //telemetry.addData("Team: ", "Blue 1");
            }
            else
            {
                // OPTION BLUE TWO (WHEELS)
                startDelay = 0;
                pivotAngle = -55;
                targetAngle = -90;
                startDist = 1270;
                targetIndex = 0;
                targetPos[0] = mmFTCFieldWidth;
                targetPos[1] = 1524;
                //telemetry.addData("Team: ", "Blue 2");
            }
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        VuforiaNav.startTracking();
        //     pause(startDelay);
        VuforiaNav.getLocation(targetIndex);


        double refAngle = imu.getAngularOrientation().firstAngle;

        TOL_ANGLE = 3.0;
        VUFORIA_TOL_ANGLE = 3.0;
        Kpivot = 1.0/150.0;
        MINSPEED = 0.35;
        TOL = 50;
        Kmove = 1.0/1200.0;

        /*
        // setting for align pivot Vuforia
        TOL_ANGLE = 3.0;
        VUFORIA_TOL_ANGLE = 3.0;
        VUFORIA_TOL = 60;
        MINSPEED = 0.3;

        telemetry.addData("Path", "align pivot vuf");
        telemetry.update();
        alignPivotVuforia(0.6, 0, 600, 4);
        pause(50);

        // setting for pivot Vuforia
        TOL_ANGLE = 2.0;
        VUFORIA_TOL_ANGLE = 2.0;
        Kpivot = 1.0/200.0;
        MINSPEED = 0.3;
        //telemetry.addData("Path", "pivotVuforia");
        //telemetry.update();
        //pivotVuforia(targetAngle, 0.5);
        */


        motorLauncher.setPower(0.85);

        telemetry.addData("Path", "start forwards");
        telemetry.update();
        // go towards center vortex
        moveAverage(0, -200, 0, 0.7, 3);
        pause(100);

        // shoot up to two particles
        motorCollector.setPower(1.0);
        servoParticle.setPosition(0.8);
        pause(300);
        servoParticle.setPosition(0.0);
        pause(1500);
        servoParticle.setPosition(0.8);
        pause(300);
        servoParticle.setPosition(0.0);
        pause(300);
        motorLauncher.setPower(0.0);
        motorCollector.setPower(0.0);

        TOL_ANGLE = 3.0;
        Kpivot = 1.0/100.0;
        MINSPEED = 0.3;
        TOL = 1000.0;
        Kmove = 1.0/1500.0;
        telemetry.addData("Path", "pivot 25");
        telemetry.update();
        //pivotWithReference(-25, refAngle, 0.7); // make sure IMU is on
        moveMaintainHeading(0, -1200, -30, refAngle, 0.9, 7);
        pause(50);
        Kmove = 1.0/1500.0;
        moveMaintainHeading(0, -1500, -12, refAngle, 0.9, 7);
        pause(50);

        //moveMaintainHeading(0, -150, -12, refAngle, 0.9, 7);
        pause(3000);
        MINSPEED = 0.25;
        pivotWithReference(90, refAngle, 0.7);

        /*

        pause(100);

        MINSPEED = 0.25;
        pivotWithReference(-90, refAngle, 0.7);
        */
//------------------------------------------------------------------------------




        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

}
