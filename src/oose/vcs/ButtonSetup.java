package oose.vcs;

import javax.swing.*;
import java.awt.*;

class ButtonSetup {

    JFrame createAndShowButton(JFrame frame, JToolBar toolBar) {


        final MyButton bStart = new MyButton( "Start" );
        final MyButton bAccelerate = new MyButton( "Accelerate" );
        final MyButton bCruise = new MyButton( "Cruise" );
        final MyButton bDecelerate = new MyButton( "Decelerate" );
        final MyButton bStop = new MyButton( "Stop" );


        toolBar.add( bStart );
        toolBar.add( bAccelerate );
        toolBar.add( bCruise );
        toolBar.add( bDecelerate );
        toolBar.add( bStop );

        Controller.config( bStart );
        Controller.config( bAccelerate );
        Controller.config( bCruise );
        Controller.config( bDecelerate );
        Controller.config( bStop );

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add( bStart );
        group.add( bAccelerate );
        group.add( bCruise );
        group.add( bDecelerate );
        group.add( bStop );


        frame.add( toolBar, BorderLayout.NORTH );

        return frame;


    }

    class MyButton extends JToggleButton {

        MyButton(String text) {
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
