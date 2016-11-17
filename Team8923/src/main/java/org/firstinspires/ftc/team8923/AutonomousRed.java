package org.firstinspires.ftc.team8923;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*
 *  Autonomous OpMode for red alliance. The OpMode is setup with a gamepad during initialization,
 *  so robot can start at one of two locations, and can complete any objective in any order
 */
@Autonomous(name = "Auto Red", group = "Autonomous")
public class AutonomousRed extends MasterAutonomous
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        alliance = Alliance.RED;

        initAuto();

        waitForStart();

        // Wait for requested number of milliseconds
        sleep(delayTime * 1000);

        vuforiaLocator.startTracking();

        // Completes each objective in the routine in order
        for(Objectives objective : routine)
        {
            // Get the next objective from the routine, and run it
            switch(objective)
            {
                // Only complete the requested objective
                case BEACON_LEFT:
                    pressLeftBeacon();
                    break;
                case BEACON_RIGHT:
                    pressRightBeacon();
                    break;
                case PARK_RAMP:
                    parkOnRamp();
                    break;
                case PARK_CENTER:
                    parkOnCenter();
                    break;
            }
        }

        // TODO: Remove when testing is done. This is just so we can read the results
        sleep(10000);
    }

    // TODO: Should we make these generic, or keep them alliance specific?
    // TODO: Should the numbers below use constants? At least some, like vision target locations?
    private void parkOnRamp() throws InterruptedException
    {
        double angleToRamp = Math.toDegrees(Math.atan2(3300 - robotY, 600 - robotX));
        turnToAngle(angleToRamp);
        driveToPoint(600, 3300, angleToRamp);
    }

    // TODO: Test me
    // This also knocks off the cap ball if it's present
    private void parkOnCenter() throws InterruptedException
    {
        double angleToCenter = Math.toDegrees(Math.atan2(2000 - robotY, 1500 - robotX));
        turnToAngle(angleToCenter);
        driveToPoint(1500, 2000, angleToCenter);
    }

    private void pressLeftBeacon() throws InterruptedException
    {
        pressBeacon(1524, 3657.6);
    }

    private void pressRightBeacon() throws InterruptedException
    {
        pressBeacon(2743.2, 3657.6);
    }

    private void pressBeacon(double beaconX, double beaconY) throws InterruptedException
    {
        double angleToEndOfTape = Math.toDegrees(Math.atan2(beaconY - robotY - 450, beaconX - robotX));

        // Go to the end of the tape in front of the beacon
        turnToAngle(angleToEndOfTape);
        driveToPoint(beaconX, beaconY - 700, angleToEndOfTape);
        turnToAngle(90);

        // Give Vuforia a chance to start tracking the target
        sleep(500);

        // Only actually looks if vision target isn't visible
        lookForVisionTarget();

        // Reposition after tracking target
        driveToPoint(beaconX, beaconY - 700, 90);

        // Get colors of both sides of beacon. Parameters are in mm from center of vision target
        float[] colorLeft = new float[3];
        float[] colorRight = new float[3];
        Color.colorToHSV(vuforiaLocator.getPixelColor(-60, 230, 30), colorLeft);
        Color.colorToHSV(vuforiaLocator.getPixelColor(60, 230, 30), colorRight);

        /*
         * Compare the hues of each side. The hue color wheel has red at 360 degrees, and blue at
         * 240 degrees. Subtracting the angles from each other results in some positive or negative
         * number. The sign can tell us which side is red and blue. In this case, the left hue is
         * subtracted from the right hue; a positive sign means left is red, negative mean right.
         */
        if(subtractAngles(colorLeft[0], colorRight[0]) > 0)
        {
            // Press left side if it's red
            telemetry.log().add("Left is red");
            // Go in front of left button
            driveToPoint(beaconX - 65, beaconY - 100, 90, 0.3);
            // Move forward to press button
            driveToPoint(beaconX - 65, beaconY - 37, 90, 0.3);
            sleep(500);
        }
        else
        {
            // Press right side if it's red
            telemetry.log().add("Right is red");
            // Go in front of right button
            driveToPoint(beaconX + 65, beaconY - 100, 90, 0.3);
            // Move forward to press button
            driveToPoint(beaconX + 65, beaconY - 37, 90, 0.3);
            sleep(500);
        }

        // Back away from beacon
        driveToPoint(beaconX, beaconY - 700, 90, 0.3);
    }
}
