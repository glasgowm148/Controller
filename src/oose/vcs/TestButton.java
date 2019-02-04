package oose.vcs;

import javax.swing.*;
import java.awt.*;

public class TestButton {

    protected JFrame createAndShowGUI(JFrame frame, JToolBar toolBar) {


        final MyButton bStart = new MyButton( "Start" );
        final MyButton bAccel = new MyButton( "Accelerate" );
        final MyButton bCruise = new MyButton( "Cruise" );
        final MyButton bDecel = new MyButton( "Decelerate" );
        final MyButton bStop = new MyButton( "Stop" );


        toolBar.add( bStart );
        toolBar.add( bAccel );
        toolBar.add( bCruise );
        toolBar.add( bDecel );
        toolBar.add( bStop );

        Controller.config( bStart );
        Controller.config( bAccel );
        Controller.config( bCruise );
        Controller.config( bDecel );
        Controller.config( bStop );


        bStart.setPressedBackgroundColor( Color.GREEN );
        bAccel.setPressedBackgroundColor( Color.GREEN );
        bCruise.setPressedBackgroundColor( Color.GREEN );
        bDecel.setPressedBackgroundColor( Color.GREEN );
        bStop.setPressedBackgroundColor( Color.GREEN );

        bStart.setHoverBackgroundColor( Color.lightGray );
        bAccel.setHoverBackgroundColor( Color.lightGray );
        bCruise.setHoverBackgroundColor( Color.lightGray );
        bDecel.setHoverBackgroundColor( Color.lightGray );
        bStop.setHoverBackgroundColor( Color.lightGray );


        frame.add( toolBar, BorderLayout.NORTH );

        return frame;


    }

    class MyButton extends JButton {

        private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;

        public MyButton() {
            this( null );
        }

        public MyButton(String text) {
            super( text );
            super.setContentAreaFilled( false );
        }

        @Override
        public void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor( pressedBackgroundColor );
            } else if (getModel().isRollover()) {
                g.setColor( hoverBackgroundColor );
            } else {
                g.setColor( getBackground() );
            }
            g.fillRect( 0, 0, getWidth(), getHeight() );
            super.paintComponent( g );
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }

        public Color getHoverBackgroundColor() {
            return hoverBackgroundColor;
        }

        public void setHoverBackgroundColor(Color hoverBackgroundColor) {
            this.hoverBackgroundColor = hoverBackgroundColor;
        }

        public Color getPressedBackgroundColor() {
            return pressedBackgroundColor;
        }

        public void setPressedBackgroundColor(Color pressedBackgroundColor) {
            this.pressedBackgroundColor = pressedBackgroundColor;
        }
    }


}
