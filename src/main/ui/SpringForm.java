package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpringForm extends JPanel {
    //TODO: Make this dynamic so you can pass in the the parameters for different forms
    JPanel springForm;

    public SpringForm(String[] labels) {
        int labelsLength = labels.length;
        final JTextField[] textField = new JTextField[labels.length];
        //Create and populate the panel.
        springForm = new JPanel(new SpringLayout());
        for (int i = 0; i < labelsLength; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            springForm.add(l);
            textField[i] = new JTextField(10);
            l.setLabelFor(textField[i]);
            springForm.add(textField[i]);
        }
        JButton button = new JButton("Submit");
        springForm.add(new JLabel());
        springForm.add(button);


        //Lay out the panel.
        SpringUtilities.makeCompactGrid(springForm,
                labelsLength + 1, 2, //rows, cols
                7, 7,        //initX, initY
                7, 7);       //xPad, yPad

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < labels.length; i++) {
                    System.out.println(labels[i] + "->" + textField[i].getText());
                }
            }
        });
    }

    public JPanel getSpringForm() {
        return springForm;
    }
}
