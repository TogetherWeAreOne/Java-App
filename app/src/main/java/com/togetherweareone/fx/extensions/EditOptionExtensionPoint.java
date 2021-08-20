package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface EditOptionExtensionPoint extends ExtensionPoint {

    void loadEditOptionModifications(Scene scene);
}
