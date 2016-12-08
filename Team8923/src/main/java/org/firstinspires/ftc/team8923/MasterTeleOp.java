package org.firstinspires.ftc.team8923;

/**
 * This class contains all objects and methods that should be accessible by all TeleOpModes for the CapBot
 */
abstract class MasterTeleOp extends Master
{
    void driveMecanumTeleOp()
    {
        if(gamepad1.dpad_down)
            slowModeDivisor = 4.0;
        else if(gamepad1.dpad_up)
            slowModeDivisor = 1.0;

        double y = -gamepad1.left_stick_y; // Y axis is negative when up
        double x = gamepad1.left_stick_x;

        double angle = Math.toDegrees(Math.atan2(-x, y)); // 0 degrees is forward
        double power = calculateDistance(x, y);
        double turnPower = -gamepad1.right_stick_x; // Fix for clockwise being a negative rotation

        driveMecanum(angle, power, turnPower);
    }

    void runLift()
    {
        if(gamepad2.dpad_up)
            motorLift.setPower(1.0);
        else if(gamepad2.dpad_down)
            motorLift.setPower(-1.0);
        else
            motorLift.setPower(0);
    }

    void grabCapBall()
    {
        if(gamepad2.a)
        {
            servoGrabberRight.setPosition(ServoPositions.GRABBER_RIGHT_RELEASE.pos);
            servoGrabberLeft.setPosition(ServoPositions.GRABBER_LEFT_RELEASE.pos);
        }
        if(gamepad2.x)
        {
            servoGrabberRight.setPosition(ServoPositions.GRABBER_RIGHT_STOW.pos);
            servoGrabberLeft.setPosition(ServoPositions.GRABBER_LEFT_STOW.pos);
        }
    }

    void runCollector()
    {
        if(gamepad2.right_trigger != 0)
        {
            motorCollector.setPower(1.0);
        }
        if(gamepad2.left_trigger != 0)
        {
            motorCollector.setPower(-1.0);
        }
    }
}
