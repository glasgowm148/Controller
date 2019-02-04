package oose.vcs;

import javax.swing.*;
import java.awt.*;

class ButtonSetup {

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


        frame.add( toolBar, BorderLayout.NORTH );

        return frame;


    }

    class MyButton extends JButton {



     /*   public MyButton() {
            this( null );
        } */

        public MyButton(String text) {
            super( text );
            super.setContentAreaFilled( false );
        }

        @Override
        public void paintComponent(Graphics g) {
            if (getModel().isPressed() || getModel().isSelected()) {
                g.setColor( Color.GREEN );

            } else if (getModel().isRollover()) {
                g.setColor( Color.lightGray );
            } else {
                g.setColor( getBackground() );
            }
            g.fillRect( 0, 0, getWidth(), getHeight() );
            super.paintComponent( g );
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }




    }


}
