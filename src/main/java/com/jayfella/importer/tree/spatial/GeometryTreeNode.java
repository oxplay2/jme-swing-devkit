package com.jayfella.importer.tree.spatial;

import com.jayfella.importer.tree.menu.GeometryContextMenu;
import com.jme3.scene.Geometry;

import javax.swing.*;

public class GeometryTreeNode extends SpatialTreeNode {

    public GeometryTreeNode(Geometry geometry) {
        super(geometry);
    }

    @Override
    public Geometry getUserObject() {
        return (Geometry) super.getUserObject();
    }

    @Override
    public JPopupMenu getContextMenu() {
        return new GeometryContextMenu(this);
    }
}