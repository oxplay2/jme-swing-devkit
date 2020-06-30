package com.jayfella.importer.properties.component;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.jme3.texture.Texture2D;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Texture2DComponent extends SdkComponent {

    private JPanel contentPanel;
    private JLabel propertyNameLabel;
    private Texture2DPanel imagePanel;
    private JTextField pathTextField;

    public Texture2DComponent() {
        super(null, null, null);

        initCustomLayout();
    }

    public Texture2DComponent(Object parent, Method getter, Method setter) {
        super(parent, getter, setter);

        initCustomLayout();

        try {
            setValue(getter.invoke(parent));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void initCustomLayout() {
        contentPanel.setLayout(new VerticalLayout());

        this.imagePanel = new Texture2DPanel();
        this.contentPanel.add(imagePanel);

        propertyNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public JComponent getJComponent() {
        return contentPanel;
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);

        if (!isBinded()) {

            Texture2D texture2D = (Texture2D) value;

            SwingUtilities.invokeLater(() -> {

                // if the texture is embedded it won't have a key.
                if (texture2D.getKey() != null) {
                    this.pathTextField.setText(texture2D.getKey().getName());
                } else {
                    this.pathTextField.setText(texture2D.getName());
                }

                this.imagePanel.setTexture(texture2D);
                this.imagePanel.revalidate();

                bind();
            });
        }

    }

    private void set() {
        setValue(pathTextField.getText());

    }

    @Override
    public void bind() {
        super.bind();

        this.pathTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                set();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                set();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                set();
            }
        });

    }

    @Override
    public void setPropertyName(String propertyName) {
        super.setPropertyName(propertyName);
        propertyNameLabel.setText("Texture2D: " + propertyName);
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
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        propertyNameLabel = new JLabel();
        propertyNameLabel.setText("Label");
        contentPanel.add(propertyNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPanel.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pathTextField = new JTextField();
        contentPanel.add(pathTextField, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}