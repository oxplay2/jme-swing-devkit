package com.jayfella.devkit.properties.component;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FloatComponent extends ReflectedSdkComponent<Float> {

    private JPanel contentPanel;
    private JFormattedTextField valueTextField;
    private JLabel propertyNameLabel;
    private JButton nullButton;

    public FloatComponent() {
        this(null, null, null, false);
    }

    public FloatComponent(boolean nullable) {
        this(null, null, null, nullable);
    }

    public FloatComponent(Object object, Method getter, Method setter) {
        this(object, getter, setter, false);

    }

    public FloatComponent(Object object, String declaredGetter, String declaredSetter) throws NoSuchMethodException {
        this(object,
                object.getClass().getDeclaredMethod(declaredGetter),
                object.getClass().getDeclaredMethod(declaredSetter, float.class));
    }

    public FloatComponent(Object object, Method getter, Method setter, boolean nullable) {
        super(object, getter, setter);

        setNullable(nullable);

        valueTextField.setFormatterFactory(new FloatFormatFactory());

        if (getter != null) {
            try {
                if (getter.getReturnType() == float.class) {
                    setValue((float) getter.invoke(object));
                } else if (getter.getReturnType() == Float.class) {
                    setValue((Float) getter.invoke(object));
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        nullButton.setVisible(isNullable());
        nullButton.addActionListener(l -> setValue(null));
    }

    @Override
    public void setValue(Float value) {
        super.setValue(value);

        if (!isBinded()) {

            SwingUtilities.invokeLater(() -> {

                if (value == null) {
                    if (isNullable()) {
                        valueTextField.setText("");
                    } else {
                        valueTextField.setText("0");
                    }
                } else {
                    this.valueTextField.setText("" + value);
                }

                bind();
            });
        }

    }

    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void bind() {
        super.bind();

        this.valueTextField.getDocument().addDocumentListener(new DocumentListener() {

            private void set() {

                String inputVal = valueTextField.getText().trim();

                if (inputVal.isEmpty()) {
                    if (isNullable()) {
                        setValue(null);
                    } else {
                        setValue(0.0f);
                    }
                } else {
                    setValue(Float.parseFloat(valueTextField.getText()));
                }

            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                set();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                set();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                set();
            }
        });
    }

    @Override
    public void setPropertyName(String propertyName) {
        super.setPropertyName(propertyName);
        propertyNameLabel.setText("Float: " + propertyName);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        contentPanel.add(spacer1, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        valueTextField = new JFormattedTextField();
        contentPanel.add(valueTextField, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        propertyNameLabel = new JLabel();
        propertyNameLabel.setText("Float");
        contentPanel.add(propertyNameLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        contentPanel.add(separator1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        nullButton = new JButton();
        nullButton.setText("null");
        contentPanel.add(nullButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
